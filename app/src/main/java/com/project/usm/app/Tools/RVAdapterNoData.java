package com.project.usm.app.Tools;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.usm.app.DTO.SubjectResponseResource;
import com.project.usm.app.R;

import java.util.List;

public class RVAdapterNoData extends RecyclerView.Adapter<RVAdapterNoData.NoData>{

    Activity activity;
    public RVAdapterNoData(Activity activity){
    this.activity = activity;

    }


    @NonNull
    @Override
    public RVAdapterNoData.NoData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.no_info, viewGroup, false);
        RVAdapterNoData.NoData sch = new RVAdapterNoData.NoData(v);
        return sch;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterNoData.NoData personViewHolder, int i) {
        personViewHolder.text2.setText(activity.getResources().getString(R.string.noLecture));

        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class NoData extends RecyclerView.ViewHolder {

        TextView text2;


        NoData(View itemView) {
            super(itemView);

            text2 = (TextView) itemView.findViewById(R.id.no);

            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}