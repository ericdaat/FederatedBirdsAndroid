package fb.sio.ecp.fr.federatedbirds.app.users;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.app.messages.MessagesAdapter;
import fb.sio.ecp.fr.federatedbirds.app.messages.MessagesLoader;
import fb.sio.ecp.fr.federatedbirds.model.Message;
import fb.sio.ecp.fr.federatedbirds.model.User;


/**
 * Created by Eric on 26/01/16.
 */
public class UserDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Message>>{

    private MessagesAdapter mMessagesAdapter;
    private static final int LOADER_MESSAGES = 0;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //attach the layout
        setContentView(R.layout.user_details_activity);

        //get the user from the intent
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        TextView textView = (TextView) findViewById(R.id.username);
        ImageView imageView = (ImageView) findViewById(R.id.avatar);

        //display user's login and avatar
        textView.setText(user.login);
        Picasso
                .with(this)
                .load(user.avatar)
                .into(imageView);

        //attach a recycler view which is the view containing the messages
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.messages_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesAdapter = new MessagesAdapter();
        mRecyclerView.setAdapter(mMessagesAdapter);

        registerForContextMenu(imageView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_user_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editavatar:
                Log.d("item selected", "edit avatar");
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().initLoader(
                LOADER_MESSAGES,
                null,
                this
        );
    }

    @Override
    public Loader<List<Message>> onCreateLoader(int id, Bundle args) {
        return new MessagesLoader(this, null);
    }

    @Override
    public void onLoadFinished(Loader<List<Message>> loader, List<Message> messages) {
        mMessagesAdapter.setMessages(messages);
    }

    @Override
    public void onLoaderReset(Loader<List<Message>> loader) {

    }

}

