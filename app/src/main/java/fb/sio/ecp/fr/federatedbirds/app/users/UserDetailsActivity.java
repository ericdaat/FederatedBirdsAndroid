package fb.sio.ecp.fr.federatedbirds.app.users;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 26/01/16.
 */
public class UserDetailsActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_activity);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        TextView textView = (TextView) findViewById(R.id.username);
        ImageView imageView = (ImageView) findViewById(R.id.avatar);


        textView.setText(user.login);
        Picasso
                .with(this)
                .load(user.avatar)
                .into(imageView);
    }

}

