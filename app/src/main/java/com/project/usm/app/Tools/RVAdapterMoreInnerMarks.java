package com.project.usm.app.Tools;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.project.usm.app.DTO.MarkResponseResource;
import com.project.usm.app.DTO.TermResponseResource;
import com.project.usm.app.R;

import java.util.List;

class RVAdapterMoreInnerMarks extends RecyclerView.Adapter<RVAdapterMoreInnerMarks.MarkMoreInnerViewHolder>{



    List<MarkResponseResource> markResponseResource;
    Activity activity;
    public RVAdapterMoreInnerMarks(List<MarkResponseResource> response, Activity activity){
        this.activity = activity;
        this.markResponseResource = response;
    }


    @NonNull
    @Override
    public RVAdapterMoreInnerMarks.MarkMoreInnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marks_more_inner_model, viewGroup, false);
        RVAdapterMoreInnerMarks.MarkMoreInnerViewHolder pvh = new RVAdapterMoreInnerMarks.MarkMoreInnerViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterMoreInnerMarks.MarkMoreInnerViewHolder personViewHolder, int i) {
        personViewHolder.mark_discipline.setText(markResponseResource.get(i).getDisciplineName());
        personViewHolder.mark_mark.setText(markResponseResource.get(i).getMark());
        personViewHolder.mark_credits.setText(markResponseResource.get(i).getCredits());
        personViewHolder.mark_ects.setText(markResponseResource.get(i).getEcts());
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return markResponseResource.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class MarkMoreInnerViewHolder extends RecyclerView.ViewHolder {

        TextView mark_discipline;
        TextView mark_mark;
        TextView mark_credits;
        TextView mark_ects;



        MarkMoreInnerViewHolder(View itemView) {
            super(itemView);
            mark_discipline = (TextView) itemView.findViewById(R.id.mark_discipline);
            mark_mark = (TextView) itemView.findViewById(R.id.mark_mark);
            mark_credits = (TextView) itemView.findViewById(R.id.mark_credits);
            mark_ects = (TextView) itemView.findViewById(R.id.mark_ects);


            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}
