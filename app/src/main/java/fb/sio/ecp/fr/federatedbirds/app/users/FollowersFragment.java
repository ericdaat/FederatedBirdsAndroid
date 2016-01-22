package fb.sio.ecp.fr.federatedbirds.app.users;

import android.os.Bundle;
import android.support.v4.content.Loader;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 09/01/16.
 */
public class FollowersFragment extends UsersFragment {
    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new FollowersLoader(getContext(),null);
    }
}
