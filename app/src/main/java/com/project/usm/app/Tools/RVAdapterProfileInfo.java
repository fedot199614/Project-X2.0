package com.project.usm.app.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.usm.app.DTO.UserProfileOneElement;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Setter;

public class RVAdapterProfileInfo extends RecyclerView.Adapter<RVAdapterProfileInfo.Profile>{




    List<UserProfileOneElement> profInfo;
    Activity activity;




    public RVAdapterProfileInfo(ProfileInfo profInfo, Activity activity){
        this.profInfo = profInfo.getData();
        this.activity = activity;

    }


    @NonNull
    @Override
    public Profile onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_model, viewGroup, false);
        Profile profInfo = new Profile(v);
        return profInfo;
    }

    @Override
    public void onBindViewHolder(@NonNull Profile personViewHolder, int i) {
        personViewHolder.title.setText(profInfo.get(i).getName());
        personViewHolder.info.setText(profInfo.get(i).getResponseResult());
        if(!profInfo.get(i).getEditable()){
            personViewHolder.fontAwesome.setVisibility(View.GONE);
        }

        personViewHolder.fontAwesome.setOnClickListener(click->{

            CustomDialogClass dialog = new CustomDialogClass(profInfo.get(i),personViewHolder.info,activity,personViewHolder.title.getText().toString(),profInfo.get(i).getQueryName());
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return profInfo.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class Profile extends RecyclerView.ViewHolder {
        TextView title;
        TextView info;
        FontAwesome fontAwesome;



        Profile(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_profile_info);
            info = (TextView) itemView.findViewById(R.id.profile_info);
            fontAwesome = (FontAwesome) itemView.findViewById(R.id.edit_text);
        }
    }
}
