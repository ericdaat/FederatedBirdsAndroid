package fb.sio.ecp.fr.federatedbirds.app.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import fb.sio.ecp.fr.federatedbirds.app.messages.MessagesAdapter;
import fb.sio.ecp.fr.federatedbirds.app.messages.MessagesLoader;
import fb.sio.ecp.fr.federatedbirds.app.messages.PostMessageFragment;
import fb.sio.ecp.fr.federatedbirds.model.Message;


/**
 * Created by Eric on 30/11/15.
 */
public class HomeFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Message>> {

    private static final int LOADER_MESSAGES = 0;
    private static final int REQUEST_POST_MESSAGE = 0;

    private MessagesAdapter mMessagesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * The system calls this when it's time for the fragment to draw its user
         * interface for the first time. To draw a UI for your fragment,
         * you must return a View from this method that is the root of your
         * fragment's layout. You can return null if the fragment does not provide a UI.
         */
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * When the view is created, attach the components to it
         */

        //attach the toolbar as SupportActionBar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //attach a recycler view which is the view containing the messages
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.messages_list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessagesAdapter = new MessagesAdapter();
        listView.setAdapter(mMessagesAdapter);

        //attach a floating action button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostMessageFragment postFragment = new PostMessageFragment();
                postFragment.setTargetFragment(HomeFragment.this, REQUEST_POST_MESSAGE);
                postFragment.show(getFragmentManager(),"post_dialog");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //refresh screen after posting a message
        switch (requestCode){
            case REQUEST_POST_MESSAGE:
                if (resultCode == Activity.RESULT_OK){
                    //delete existing loader to download new results
                    getLoaderManager().restartLoader(LOADER_MESSAGES,null,this);
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(
                LOADER_MESSAGES,
                null,
                this
        );
    }

    @Override
    public Loader<List<Message>> onCreateLoader(int id, Bundle args) {
        return new MessagesLoader(getContext(), null);
    }

    @Override
    public void onLoadFinished(Loader<List<Message>> loader, List<Message> messages) {
        mMessagesAdapter.setMessages(messages);
    }

    @Override
    public void onLoaderReset(Loader<List<Message>> loader) { }
}