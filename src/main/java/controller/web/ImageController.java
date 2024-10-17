package controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet(name = "ImageController", value = "/uploadImage")
@MultipartConfig
public class ImageController extends HttpServlet {

    private static final String BASE_UPLOAD_DIRECTORY = "C:/Java Code/DanamaFE/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String folder = request.getParameter("folder");
        Part filePart = request.getPart("file");

        if (folder == null || folder.isEmpty() || filePart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
            return;
        }

        String uploadDirectory = BASE_UPLOAD_DIRECTORY + folder;
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = getFileName(filePart);
        File fileToSave = new File(uploadDirectory, fileName);
        filePart.write(fileToSave.getAbsolutePath());

        String filePath = folder + "/" + fileName;
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write("{\"location\": \"" + filePath + "\"}");
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cdPart : contentDisposition.split(";")) {
            if (cdPart.trim().startsWith("filename")) {
                return cdPart.substring(cdPart.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}