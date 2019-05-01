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

import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Setter;

public class RVAdapterProfileInfo extends RecyclerView.Adapter<RVAdapterProfileInfo.Profile>{




    List<String> title;
    List<String> info;
    Activity activity;
    Map<String,Boolean> editable;
    Set<String> key;
    Iterator<String> iteratorKey;
    String queryName = "";

    public RVAdapterProfileInfo(ProfileInfo profInfo, Activity activity){
        this.title = profInfo.getTitle();
        this.info = profInfo.getInfo();
        this.editable = profInfo.getEditable();
        this.key = editable.keySet();
        this.activity = activity;
        this.iteratorKey =  key.iterator();
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

        String query = "";
        if(iteratorKey.hasNext()){
            String key = iteratorKey.next();
            if(key.equals("email") || key.equals("streetAddress") || key.equals("phoneNumber")) {
                personViewHolder.setKey(key);
            }else{
                personViewHolder.fontAwesome.setVisibility(View.GONE);
            }
        }
        personViewHolder.fontAwesome.setOnClickListener(click->{

            CustomDialogClass dialog = new CustomDialogClass(personViewHolder.info,activity,personViewHolder.title.getText().toString(),personViewHolder.key);
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


    @Setter
    public static class Profile extends RecyclerView.ViewHolder {
        TextView title;
        TextView info;
        FontAwesome fontAwesome;
        String key;


        Profile(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_profile_info);
            info = (TextView) itemView.findViewById(R.id.profile_info);
            fontAwesome = (FontAwesome) itemView.findViewById(R.id.edit_text);
        }
    }
}
