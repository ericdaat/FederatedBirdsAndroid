package fb.sio.ecp.fr.federatedbirds.app.users;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
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
    private static final int SELECT_PHOTO = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;

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
                picChange();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void picChange(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

        //permission for accessing gallery
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    //permission problem
                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bmp = BitmapFactory.decodeFile(filePath);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    try {
                        ApiClient.getInstance(this).postAvatar(byteArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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

