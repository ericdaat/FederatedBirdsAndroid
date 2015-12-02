package fb.sio.ecp.fr.federatedbirds.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.Message;

/**
 * Created by Eric on 02/12/15.
 */
public class PostMessageFragment extends DialogFragment {

    private EditText mMessagesText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getLayoutInflater(savedInstanceState).inflate(R.layout.new_message,null);
        mMessagesText = (EditText) v.findViewById(R.id.message);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.new_message)
                .setView(v)
                .setPositiveButton(R.string.post, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message = mMessagesText.getText().toString();

                        if (TextUtils.isEmpty(message)) {
                            Toast.makeText(getContext(),R.string.empty_message_error,Toast.LENGTH_LONG);
                            //stop code here
                            return;
                        }
                        //post is an asynchronous operation
                        PostTaskFragment taskFragment = new PostTaskFragment();
                        taskFragment.setArgument(message);
                        taskFragment.show(getFragmentManager(),"post_progress");
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        return dialog;
    }


    public static class PostTaskFragment extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public void setArgument(String message) {
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            setArguments(args);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            AsyncTaskCompat.executeParallel(
                    new PostTaskFragment.PostTask()
            );
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setIndeterminate(true);
            dialog.setMessage(getString(R.string.progress));
            return dialog;
        }

        private class PostTask extends AsyncTask<Void, Void, Message> {

            @Override
            protected Message doInBackground(Void... params) {
                try {
                    String message = getArguments().getString("message");
                    return ApiClient.getInstance(getContext()).postMessage(message);
                } catch (IOException e) {
                    Log.e(LoginActivity.class.getSimpleName(), "Post failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Message message) {
                if (message != null) {


                } else {
                    Toast.makeText(getContext(), R.string.message_failed, Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        }
    }
}
