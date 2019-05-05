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
import com.project.usm.app.DTO.StudentYearResponseResource;
import com.project.usm.app.DTO.TermResponseResource;
import com.project.usm.app.R;

import java.text.DecimalFormat;
import java.util.List;

public class RVAdapterInnerMarks extends RecyclerView.Adapter<RVAdapterInnerMarks.MarkInnerViewHolder>{



    List<TermResponseResource> termResponseResource;
    Activity activity;
    public RVAdapterInnerMarks(List<TermResponseResource> response, Activity activity){
        this.activity = activity;
        this.termResponseResource = response;
    }


    public String termCounter(int index){
        DecimalFormat df = new DecimalFormat("#.##");
        Double summTermMarks = 0.0;
        Integer summTermSubjects = 0;
        Double result = 0.0;
        List<MarkResponseResource> marks =  termResponseResource.get(index).getMarks();

            for(MarkResponseResource  e : marks){
                if(e.getMark() != null) {
                    summTermMarks += Double.valueOf(e.getMark());
                    summTermSubjects++;
                }
            }

        result = (double) summTermMarks/summTermSubjects;

        return  df.format(result);
    }

    @NonNull
    @Override
    public RVAdapterInnerMarks.MarkInnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marks_second_model, viewGroup, false);
        RVAdapterInnerMarks.MarkInnerViewHolder pvh = new RVAdapterInnerMarks.MarkInnerViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterInnerMarks.MarkInnerViewHolder personViewHolder, int i) {
        personViewHolder.term.setText(termResponseResource.get(i).getTermNumber());
        personViewHolder.middle_term.setText(termCounter(i));

        LinearLayoutManager llm = new LinearLayoutManager(activity);
        personViewHolder.rv.setLayoutManager(llm);
        personViewHolder.rv.setHasFixedSize(true);

        RVAdapterMoreInnerMarks adapter = new RVAdapterMoreInnerMarks(termResponseResource.get(i).getMarks(),activity);
        personViewHolder.rv.setAdapter(adapter);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(personViewHolder.rv.getContext(), R.anim.layout_animation_fall_down);
        personViewHolder.rv.setLayoutAnimation(controller);
        personViewHolder.rv.getAdapter().notifyDataSetChanged();
        personViewHolder.rv.scheduleLayoutAnimation();
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return termResponseResource.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class MarkInnerViewHolder extends RecyclerView.ViewHolder {

        TextView term;
        TextView middle_term;
        RecyclerView rv;


        MarkInnerViewHolder(View itemView) {
            super(itemView);
            middle_term = (TextView) itemView.findViewById(R.id.middle_term);
            term = (TextView) itemView.findViewById(R.id.term);
            rv = (RecyclerView) itemView.findViewById(R.id.rv_third_mark);

            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}