package projet.jee.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "", "/", "/admin/*", "/profil/*"
})
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession(false) != null){
            // Si l'utilisateur est connecté
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // Sinon, si l'utilisateur est déconnecté
            request.getSession(); // TODO Remove
            response.sendRedirect("/connexion");
        }

    }

    @Override
    public void init(FilterConfig filterConfig) {}
    @Override
    public void destroy() {}
}