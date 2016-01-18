package fb.sio.ecp.fr.federatedbirds.app.users;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 01/12/15.
 */
public abstract class UsersLoader extends AsyncTaskLoader<List<User>> {

    private List<User> mResult;
    private Long mUserId;

    public UsersLoader(Context context, Long userId) {
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
        try {
            return getUsers(mUserId);
        } catch (IOException e) {
            Log.e("UsersLoader", "Failed to load users");
            return null;
        }
    }

    protected abstract List<User> getUsers(Long userId) throws IOException;

    @Override
    public void deliverResult(List<User> data) {
        mResult = data;
        super.deliverResult(data);
    }
}
