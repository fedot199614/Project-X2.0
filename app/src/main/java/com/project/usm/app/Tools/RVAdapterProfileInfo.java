package com.project.usm.app.Tools;

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

    public RVAdapterProfileInfo(ProfileInfo profInfo){
        title = profInfo.getTitle();
        info = profInfo.getInfo();
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


        Profile(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_profile_info);
            info = (TextView) itemView.findViewById(R.id.profile_info);
        }
    }
}
