package fb.sio.ecp.fr.federatedbirds;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;
import fb.sio.ecp.fr.federatedbirds.model.Message;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 24/11/15.
 */
public class ApiClient {

    private static ApiClient mInstance;
    private static final String API_BASE = "localhost:8080/";


    /**
     * We need to have a way to get the context of the application
     * We use a singleton pattern by having it stored as a private field
     * and then use lazy instantiation
     */
    public static synchronized ApiClient getInstance(Context context) {
        if (mInstance == null){
            mInstance = new ApiClient(context);
        }
        return mInstance;
    }
    private Context mContext;

    private ApiClient(Context context){
        /**
         * Default constructor must be private because we don't want the user
         * to call it, and have more than one context.
         */
        mContext = context.getApplicationContext();
    }

    private <T> T get(String path, Type type) throws IOException {
        return method("GET", null, path, type);
    }

    private <T> T post(String path, Object body, Type type) throws IOException {
        return method("POST",body,path,type);
    }

    private <T> T method(String method, Object body, String path, Type type) throws IOException {
        /**
         * This method can either be a POST or a GET method
         * If it's a POST, send the body first and then wait for the response
         * to return it as a Json object
         * If it's a GET, just read the response
         */
        String url = API_BASE + path;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        String token = TokenManager.getUserToken(mContext);
        if (token != null) {
            connection.addRequestProperty("Authorization","Bearer " + token);
        }
        if (body != null){
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            try {
                new Gson().toJson(body,writer);
            } finally {
                writer.close();
            }
        }
        Reader reader = new InputStreamReader(connection.getInputStream());
        try {
            return new Gson().fromJson(reader, type);
        } finally {
            reader.close();
        }
    }

    public List<Message> getMessages(Long userId) throws IOException {
        /**
         * get the list of messages from server
         * if we don't specify a user, get all the messages posted
         * else get the messages from this user
         */
        TypeToken<List<Message>> type = new TypeToken <List<Message>>() {};
        String path;
        if (userId == null){
            path = "messages";
        } else {
            path = "users/" + userId + "/messages";
        }
        return get(path,type.getType());
    }

    public User getUser(long id) throws IOException{
        /**
         * return a user by its id
         */
        return get("users" + id,User.class);
    }

    public String login(String login, String password) throws IOException {
        /**
         * do the login with string and password
         * post them and return the answer that will be either the token
         * or null.
         */
        JsonObject body = new JsonObject();
        body.addProperty("login",login);
        body.addProperty("password",password);
        return post("auth/token",body,String.class);
    }
}
