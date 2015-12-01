package fb.sio.ecp.fr.federatedbirds.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Eric on 27/11/15.
 */
public class TokenManager {

    /**
     * This class manages user's token
     * We use a SharedPreferences instance to store tokens
     * SharedPreferences is dictionary that persists the values
     * We have to specify the context which we use
     */

    private static final String AUTH_PREFERENCES = "auth";
    private static final String TOKEN_KEY = "token";

    public static String getUserToken(Context context){
        SharedPreferences sp = context.getSharedPreferences(AUTH_PREFERENCES,Context.MODE_PRIVATE);
        return sp.getString(TOKEN_KEY,null);
        /**
         * getString return a String in which we have to extract the corresponding key
         * If the key isn't found, then return null because there's no token for this user.
         */
    }

    public static void setUserToken(Context context, String token){
        /**
         * This function works similarly than the getUserToken
         * Except there's no setString method like we used getString
         * We use putString and apply instead
         */
        SharedPreferences sp = context.getSharedPreferences(AUTH_PREFERENCES,Context.MODE_PRIVATE);
        sp.edit().putString(TOKEN_KEY,token).apply();
        Log.i(TokenManager.class.getSimpleName(),"User token saved : " + token);
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        Log.i(TokenManager.class.getSimpleName(), "Auth preferences cleared");
    }

}
