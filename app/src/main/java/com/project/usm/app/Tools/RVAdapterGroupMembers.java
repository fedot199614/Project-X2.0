package com.project.usm.app.Tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVAdapterGroupMembers extends RecyclerView.Adapter<RVAdapterGroupMembers.MemberViewHolder>{



        List<ProfileInfo> profileInfo;
        public RVAdapterGroupMembers(List<ProfileInfo> profileInfo){

        this.profileInfo = profileInfo;
        }


@NonNull
@Override
public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_members_model, viewGroup, false);
        MemberViewHolder pvh = new MemberViewHolder(v);
        return pvh;
        }

@Override
public void onBindViewHolder(@NonNull MemberViewHolder personViewHolder, int i) {
        personViewHolder.name.setText(profileInfo.get(i).getProfileResponseResource().getFirstName()+" "+profileInfo.get(i).getProfileResponseResource().getLastName());
        Picasso.get().load(profileInfo.get(i).getProfileResponseResource().getProfileImageUrl()).into( personViewHolder.img);
        //personViewHolder.img.setImageBitmap(profileInfo.get(i).getAvatar());
}

@Override
public int getItemCount() {
        return profileInfo.size();
        }


@Override
public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        }



public static class MemberViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    CircleImageView img;

    MemberViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.member_name);
        img = (CircleImageView)itemView.findViewById(R.id.member_avatar);

    }
}
}