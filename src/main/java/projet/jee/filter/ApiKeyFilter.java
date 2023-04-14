package projet.jee.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import projet.jee.others.Constants;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
@Order(1)
public class ApiKeyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String key = req.getHeader("API-KEY");

        if (key==null) {
            res.sendRedirect("/api-key/missing");
        } else {
            if (!key.equals(Constants.API_KEY)){
                res.sendRedirect("/api-key/bad");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}