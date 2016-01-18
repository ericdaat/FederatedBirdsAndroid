package fb.sio.ecp.fr.federatedbirds.app.messages;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.model.Message;

/**
 * Created by Eric on 24/11/15.
 */
public class MessagesLoader extends AsyncTaskLoader<List<Message>>{

    private List<Message> mResult;
    private Long mUserId;

    public MessagesLoader(Context context, Long userId) {
        super(context);
        mUserId = userId;
    }

    @Override
    protected void onStartLoading() {
        /**
         * If mResult is not empty, then we already loaded results, so we don't
         * need to get them again
         * If it is, then load the results by calling deliverResult(mResult)
         * Note : we must use forceLoad() to get the results
         */
        super.onStartLoading();
        if (mResult != null){
            deliverResult(mResult);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Message> loadInBackground() {
        /**
         * loadInBackground handles long loading in an asynchronous way
         */
        try {
            return ApiClient.getInstance(getContext()).getMessages(mUserId);
        } catch (IOException e) {
            Log.e("MessagesLoader","Failed to load messages",e);
            return null;
        }
    }

    @Override
    public void deliverResult(List<Message> data) {
        /**
         * This method implements super.deliverResult
         * and is used to store the results in a List passed
         * as a parameter
         */
        mResult = data;
        super.deliverResult(data);
    }
}
