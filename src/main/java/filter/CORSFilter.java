package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*") // Apply this filter to all URLs
public class CORSFilter implements Filter {
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:5173", // First FE URL
            "https://yellow-water-02a239700.5.azurestaticapps.net/" // Second FE URL
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String origin = httpRequest.getHeader("Origin");

        // Check if the origin is allowed and add CORS headers to the response
        if (ALLOWED_ORIGINS.contains(origin)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true"); // If you need to allow cookies
        }

        // For OPTIONS preflight requests, return OK status without further processing
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Pass the request along the filter chain (to the servlet)
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Clean up any resources if needed
    }
}
