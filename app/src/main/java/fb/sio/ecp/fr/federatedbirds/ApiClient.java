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

    public static synchronized ApiClient getInstance(Context context) {
        if (mInstance == null){
            mInstance = new ApiClient(context);
        }
        return mInstance;
    }

    private Context mContext;

    private ApiClient(Context context){
        mContext = context.getApplicationContext();
    }

    private <T> T get(String path, Type type) throws IOException {
        return method("GET", null, path, type);
    }

    private <T> T post(String path, Object body, Type type) throws IOException {
        return method("POST",body,path,type);
    }


    private <T> T method(String method, Object body, String path, Type type) throws IOException {
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
        return get("users" + id,User.class);
    }

    public String login(String login, String password) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("login",login);
        body.addProperty("password",password);
        return post("auth/token",body,String.class);
    }
}
