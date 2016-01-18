package fb.sio.ecp.fr.federatedbirds.utils;

/**
 * Created by Eric on 27/11/15.
 */
public class ValidationUtils {

    /**
     * tools to check signIn and password syntax with regular expressions
     */

    private static final String LOGIN_PATTERN = "^[A-Za-z0-9_-]{4,12}$";
    private static final String PASSWORD_PATTERN = "^\\w{4,12}$";
    private static final String EMAIL_PATTERN = "^*@*$";


    public static boolean validateLogin(String login) {
        return login != null && login.matches(LOGIN_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }
}
