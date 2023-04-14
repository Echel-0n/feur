package projet.jee.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import projet.jee.entity.User;
import projet.jee.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {
        "/admin","/admin/*"
})
@Order(2)
public class AdminFilter implements Filter {
    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean pass = false;

        if (request.getSession(false) != null){
            // L'utilisateur a une session.
            Object userId = request.getSession(false).getAttribute("userId");
            if (userId != null){
                // L'utilisateur est connecté.
                Optional<User> ou = userService.findById((long)userId);
                if (ou.isPresent()){
                    // L'utilisateur est administrateur.
                    pass = ou.get().isAdmin();
                }
            }
        }
       if (pass) {
            // L'utilisateur est administrateur.
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/");
        }

    }
}