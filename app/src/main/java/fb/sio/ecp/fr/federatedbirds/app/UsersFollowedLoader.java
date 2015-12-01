package fb.sio.ecp.fr.federatedbirds.app;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 01/12/15.
 */
public class UsersFollowedLoader extends AsyncTaskLoader<List<User>> {

    private List<User> mResult;
    private Long mUserId;

    public UsersFollowedLoader(Context context, Long userId) {
        super(context);
        mUserId = userId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null){
            deliverResult(mResult);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<User> loadInBackground() {
        return ApiClient.getInstance(getContext()).getFollowedUsers(mUserId);
    }

    @Override
    public void deliverResult(List<User> data) {
        mResult = data;
        super.deliverResult(data);
    }
}
