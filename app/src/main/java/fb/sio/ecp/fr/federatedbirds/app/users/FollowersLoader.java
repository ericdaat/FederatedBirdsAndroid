package fb.sio.ecp.fr.federatedbirds.app.users;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 09/01/16.
 */
public class FollowersLoader extends UsersLoader {
    public FollowersLoader(Context context, Long userId) {
        super(context, userId);
    }

    @Override
    protected List<User> getUsers(Long userId) throws IOException {
        return ApiClient.getInstance(getContext()).getUserFollowers(userId);
    }
}
