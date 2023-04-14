package projet.jee.others;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import projet.jee.entity.User;
import projet.jee.service.UserService;

import java.util.Arrays;
import java.util.Optional;

public abstract class Constants {

    public static void applyModel(Model model, HttpServletRequest request,
                                  UserService userService){
        model.addAttribute("isConnected", isConnected(request));
        model.addAttribute("isAdmin", isAdmin(request, userService));
    }
    public static Boolean isConnected(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            if (session.getAttribute("userId") != null){
                return true;
            }
        }
        return null;
    }
    public static Boolean isAdmin(HttpServletRequest request,
                                  UserService userService){
        HttpSession session = request.getSession(false);
        if (session != null){
            Object oUId = session.getAttribute("userId");
            if (oUId != null){
                Long uId = (Long) oUId;
                Optional<User> ou = userService.findById(uId);
                if (ou.isPresent() && ou.get().isAdmin()){
                    return true;
                }
            }
        }
        return null;
    }

    public static void setLastPage(
            String value,
            HttpServletResponse response
    ){
        Cookie cookie = new Cookie("last_page", value);
        response.addCookie(cookie);
    }
    public static String getLastPage(
            String defaultPage,
            HttpServletRequest request
    ) {
        Optional<String> os =
                Arrays.stream(request.getCookies())
                        .filter(cookie -> cookie.getName().equals("last_page"))
                        .map(Cookie::getValue)
                        .findAny();
        return os.orElse(defaultPage);
    }
}
