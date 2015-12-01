package fb.sio.ecp.fr.federatedbirds.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 01/12/15.
 */
public class UsersFollowedAdapter
        extends RecyclerView.Adapter<UsersFollowedAdapter.UsersFollowedViewHolder> {

    private List<User> mFollowedUsers;

    public void setFollowedUsers (List<User> followedUsers){
        mFollowedUsers = followedUsers;
        notifyDataSetChanged();
    }

    @Override
    public UsersFollowedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        return new UsersFollowedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsersFollowedViewHolder holder, int position) {
        User user = mFollowedUsers.get(position);
        holder.mUserNameTextView.setText(user.login);
    }

    @Override
    public int getItemCount() {
        return mFollowedUsers != null ? mFollowedUsers.size() : 0;
    }


    public class UsersFollowedViewHolder extends RecyclerView.ViewHolder{
        private ImageView mUserAvatarView;
        private TextView mUserNameTextView;

        public UsersFollowedViewHolder(View itemView) {
            super(itemView);
            mUserAvatarView = (ImageView) itemView.findViewById(R.id.followed_avatar);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.followed_username);
        }
    }

}
