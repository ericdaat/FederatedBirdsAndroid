package fb.sio.ecp.fr.federatedbirds.app.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.app.MainActivity;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;

/**
 * Created by Eric on 30/11/15.
 */
public abstract class LoginTaskFragment extends DialogFragment {

    /**
     * To avoid typos, we use two constants declared as
     * public to be reused later on in the code
     */
    protected static final String ARG_LOGIN = "login";
    protected static final String ARG_PASSWORD = "password";
    protected static final String ARG_EMAIL = "email";

    public abstract void setArguments(String login, String password, String email);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public  abstract Dialog onCreateDialog(Bundle savedInstanceState);


}
