package projet.jee.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "", "/", "/admin/*", "/profil/*"
})
public class ConnectionNeededFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession(false) != null){
            // Si l'utilisateur est connecté
            System.out.println(request.getSession().getId());
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // Sinon, si l'utilisateur n'est pas connecté
            System.out.println("Connexion avec user_id 2");
            request.getSession().setAttribute("user_id", 2L);// TODO Remove
            response.sendRedirect("/");
        }

    }
}