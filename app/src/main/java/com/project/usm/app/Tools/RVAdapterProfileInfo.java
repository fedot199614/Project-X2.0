package com.project.usm.app.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.R;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RVAdapterProfileInfo extends RecyclerView.Adapter<RVAdapterProfileInfo.Profile>{




    List<String> title;
    List<String> info;
    Activity activity;

    public RVAdapterProfileInfo(ProfileInfo profInfo,Activity activity){
        title = profInfo.getTitle();
        info = profInfo.getInfo();
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
        personViewHolder.title.setText(title.get(i));
        personViewHolder.info.setText(info.get(i));
        personViewHolder.fontAwesome.setOnClickListener(click->{
            CustomDialogClass dialog = new CustomDialogClass(activity,personViewHolder.title.getText().toString(),personViewHolder.title.getText().toString());
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return info.size();
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
