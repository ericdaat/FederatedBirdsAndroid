package fb.sio.ecp.fr.federatedbirds.app;

import android.os.Bundle;
import android.support.v4.content.Loader;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 07/01/16.
 */
public class FollowedFragment extends UsersFragment {

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new FollowedLoader(getContext(),null);
    }
}
