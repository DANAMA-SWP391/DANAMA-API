package controller.web;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.PublicAccessType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@MultipartConfig
@WebServlet(name = "FileUploadController", value = "/uploadFileToAzure")
public class FileUploadController extends HttpServlet {
    // Azure Blob storage details
    private static final String CONNECTION_STRING = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
    private static final String CONTAINER_NAME = "images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file"); // Retrieves the file from the request

        if (filePart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file uploaded");
            return;
        }

        // Get the original MIME type of the uploaded file (e.g., image/png, image/jpeg)
        String fileType = filePart.getContentType();

        // Generate a unique file name to avoid collisions
        String fileName = UUID.randomUUID().toString() + "_" + filePart.getSubmittedFileName();

        try (InputStream fileInputStream = filePart.getInputStream()) {
            // Upload the image to Azure Blob Storage
            String imageUrl = uploadImageToAzureBlob(fileInputStream, filePart.getSize(), fileName, fileType);
            System.out.println(imageUrl);
            // Return the URL to the frontend
            response.setContentType("application/json");
            response.getWriter().write("{\"imageUrl\": \"" + imageUrl + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error uploading image");
        }
    }

    // Method to upload the image to Azure Blob Storage and return the URL
    private String uploadImageToAzureBlob(InputStream fileInputStream, long fileSize, String fileName, String fileType) {
        // Create a BlobServiceClient using the connection string
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildClient();

        // Get the container client
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);

        // Ensure the container exists
        if (!containerClient.exists()) {
            containerClient.create();
            containerClient.setAccessPolicy(PublicAccessType.CONTAINER, null);
        }

        // Get the blob client for the uploaded file
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        // Upload the file using file size from Part.getSize()
        blobClient.upload(fileInputStream, fileSize, true);

        // Set the content type dynamically based on the uploaded file's type
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(fileType);
        blobClient.setHttpHeaders(headers);

        // Return the URL of the uploaded file
        return blobClient.getBlobUrl();
    }
}