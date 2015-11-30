package fb.sio.ecp.fr.federatedbirds.app;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.Message;

/**
 * Created by Eric on 24/11/15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Message> mMessages;

    public void setMessages(List<Message> messages){
        mMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Create the ViewHolder from the message_item.xml layout
         * Use the LayoutInflater to obtain the view from the layout
         * Then we have the View, call the MessageViewHolder constructor to
         * return the proper MessageViewHolder object.
         */
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        /**
         * This method will assign the content of a MessageViewHolder
         * based on its position.
         * It is used when scrolling the list and dynamically assign
         * content
         */
        Message message = mMessages.get(position);
        holder.mTextView.setText(message.text);
    }

    @Override
    public int getItemCount() {
        return mMessages != null ? mMessages.size() : 0;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        private ImageView mUserAvatarView;
        private TextView mTextView;

        public MessageViewHolder(View itemView){
            /**
             * A message item will contain a text, and the avatar of the user
             * Given a View that refers to a message_item.xml, get the avatar
             * and the text from their id.
             */
            super(itemView);
            mUserAvatarView = (ImageView) itemView.findViewById(R.id.avatar);
            mTextView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}
