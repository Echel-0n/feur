package projet.jee.others;

import java.util.regex.Pattern;

public abstract class Constants {
    public final static String DOMAIN_NAME = "localhost";
    public final static Pattern DOMAIN_NAME_PATTERN =
            Pattern.compile(" *://localhost/*");

    public static boolean fromMySite (String url){
        return DOMAIN_NAME_PATTERN.matcher(url).find();
    }
}
