// TODO
//package projet.jee.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.annotation.Order;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = "/api/*")
//@Order(1)
//public class ApiKeyFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String key = request.getParameter("API-KEY");
//
//        if (key==null) {
//            res.sendRedirect("/api-key/missing");
//        } else {
//            if (!key.equals("PASS")){ // TODO
//                res.sendRedirect("/api-key/bad");
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}
