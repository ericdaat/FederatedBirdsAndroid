package fb.sio.ecp.fr.federatedbirds;

import android.content.Context;
import android.util.Log;

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

    private static final String API_BASE = "http://10.0.2.2:8080/";
    private static ApiClient mInstance;

    private Context mContext;
    private ApiClient(Context context){
        /**
         * Default constructor must be private because we don't want the user
         * to call it, and have more than one context.
         */
        mContext = context.getApplicationContext();
    }


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

    private <T> T get(String path, Type type) throws IOException {
        return method("GET", path, null, type);
    }

    private <T> T post(String path, Object body, Type type) throws IOException {
        return method("POST",path,body,type);
    }

    private <T> T method(String method, String path, Object body, Type type) throws IOException {
        String url = API_BASE + path;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        String token = TokenManager.getUserToken(mContext);
        if (token != null) {
            connection.addRequestProperty("Authorization", "Bearer " + token);
        }
        if (body != null) {
            Writer writer = new OutputStreamWriter(connection.getOutputStream());
            try {
                new Gson().toJson(body, writer);
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
        TypeToken<List<Message>> type = new TypeToken<List<Message>>() {};
        String path = userId == null ? "messages" : "users/" + userId + "/messages";
        return get(path, type.getType());
    }

    public Message postMessage(String text) throws IOException {
        Message message = new Message();
        message.text = text;
        return post("messages", message, Message.class);
    }

    public List<User> getUserFollowing(Long userId) throws IOException {
        String id = userId != null ? Long.toString(userId) : "me";
        TypeToken<UsersList> type = new TypeToken<UsersList>() {};
        UsersList list = get("users/" + id + "/following", type.getType());
        return list.users;
    }

    public List<User> getUserFollowers(Long userId) throws IOException {
        String id = userId != null ? Long.toString(userId) : "me";
        TypeToken<UsersList> type = new TypeToken<UsersList>() {};
        UsersList list = get("users/" + id + "/followers", type.getType());
        return list.users;
    }

    public User getUser(long id) throws IOException {
        return get("users/" + id, User.class);
    }

    public String signIn(String login, String password) throws IOException {
        /**
         * do the signIn with string and password
         * post them and return the answer that will be either the token
         * or null.
         */
        JsonObject body = new JsonObject();
        body.addProperty("login",login);
        body.addProperty("password",password);
        return post("auth/token", body, String.class);
    }

    public String signup(String login, String password, String email) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("login",login);
        body.addProperty("password",password);
        body.addProperty("email",email);
        return post("users",body,String.class);
    }

    public static class UsersList {
        public final List<User> users;
        public final String cursor;

        public UsersList(List<User> users, String cursor) {
            this.users = users;
            this.cursor = cursor;
        }
    }
}
