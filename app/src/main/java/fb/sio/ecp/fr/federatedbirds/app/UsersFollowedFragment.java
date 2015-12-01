package fb.sio.ecp.fr.federatedbirds.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 01/12/15.
 */
public class UsersFollowedFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<User>>{

    private static int LOADER_USERS_FOLLOWED = 1;
    private UsersFollowedAdapter mUsersFollowedAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.followed_fragment,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.followed_list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsersFollowedAdapter = new UsersFollowedAdapter();
        listView.setAdapter(mUsersFollowedAdapter);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new UsersFollowedLoader(getContext(),null);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users_followed) {
        mUsersFollowedAdapter.setFollowedUsers(users_followed);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}
