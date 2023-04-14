package projet.jee.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/admin/*", "/profile", "/activities/*", "/users/*","/search"
})
@Order(1)
public class ConnectionNeededFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession(false) != null && request.getSession(false).getAttribute("userId") != null ){
            // Si l'utilisateur est connecté
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // Sinon, si l'utilisateur n'est pas connecté
            response.sendRedirect("/");
        }

    }
}