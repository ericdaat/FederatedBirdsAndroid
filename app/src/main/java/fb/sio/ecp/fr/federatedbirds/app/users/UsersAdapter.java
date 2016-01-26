package fb.sio.ecp.fr.federatedbirds.app.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.model.User;

/**
 * Created by Eric on 01/12/15.
 */
public class UsersAdapter
        extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<User> mUsers;

    public void setUsers(List<User> users){
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        final User user = mUsers.get(position);

        Picasso.with(holder.mAvatarView.getContext())
                .load(user.avatar)
                .into(holder.mAvatarView);

        holder.mUserNameView.setText(user.login);

        holder.mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch activity here
                Log.d("Avatar clicked", user.login);
                Intent intent = new Intent(v.getContext(),UserDetailsActivity.class);
                intent.putExtra("user", (Serializable) user);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }


    public class UsersViewHolder extends RecyclerView.ViewHolder{

        private ImageView mAvatarView;
        private TextView mUserNameView;
        private FloatingActionButton mFollowButton;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mAvatarView = (ImageView) itemView.findViewById(R.id.avatar);
            mUserNameView = (TextView) itemView.findViewById(R.id.username);
            mFollowButton = (FloatingActionButton) itemView.findViewById(R.id.follow);
        }
    }

}
