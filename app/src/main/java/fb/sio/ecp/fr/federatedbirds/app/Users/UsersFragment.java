package fb.sio.ecp.fr.federatedbirds.app.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 01/12/15.
 */
public abstract class UsersFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<User>>{

    private static int LOADER_USERS = 0;
    private UsersAdapter mUsersAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.users_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.followed_list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsersAdapter = new UsersAdapter();
        listView.setAdapter(mUsersAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(
                LOADER_USERS,
                null,
                this
        );
    }

    @Override
    public abstract Loader<List<User>> onCreateLoader(int id, Bundle args);

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users_followed) {
        mUsersAdapter.setUsers(users_followed);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}
