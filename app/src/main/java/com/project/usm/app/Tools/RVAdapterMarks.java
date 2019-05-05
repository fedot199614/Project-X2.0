package com.project.usm.app.Tools;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.usm.app.DTO.MarkResponseResource;
import com.project.usm.app.DTO.StudentYearResponseResource;
import com.project.usm.app.DTO.TermResponseResource;
import com.project.usm.app.Model.News;
import com.project.usm.app.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class RVAdapterMarks extends RecyclerView.Adapter<RVAdapterMarks.MarkViewHolder>{



    List<StudentYearResponseResource> studentMarksResponse;
    Activity activity;
    public RVAdapterMarks(List<StudentYearResponseResource> response, Activity activity){
        this.activity = activity;
        this.studentMarksResponse = response;
    }

    public String yearCounter(int index){
        DecimalFormat df = new DecimalFormat("#.##");
        Double summAnnualMarks = 0.0;
        Integer summAnnualSubjects = 0;
        Double result = 0.0;
        List<TermResponseResource> terms =  studentMarksResponse.get(index).getTerms();

        for(TermResponseResource element: terms){
          List<MarkResponseResource> marksResponse =  element.getMarks();
          for(MarkResponseResource  e : marksResponse){
              if(e.getMark() != null) {
                  summAnnualMarks += Double.valueOf(e.getMark());
                  summAnnualSubjects++;
              }
          }
        }
        result = (double) summAnnualMarks/summAnnualSubjects;

        return  df.format(result);
    }

    @NonNull
    @Override
    public RVAdapterMarks.MarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marks_model_main, viewGroup, false);
        RVAdapterMarks.MarkViewHolder pvh = new RVAdapterMarks.MarkViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterMarks.MarkViewHolder personViewHolder, int i) {
        personViewHolder.middle_mark_year.setText(yearCounter(i));
        personViewHolder.yearMark.setText(studentMarksResponse.get(i).getStudentYear());


        LinearLayoutManager llm = new LinearLayoutManager(activity);
        personViewHolder.rv.setLayoutManager(llm);
        personViewHolder.rv.setHasFixedSize(true);

        RVAdapterInnerMarks adapter = new RVAdapterInnerMarks(studentMarksResponse.get(i).getTerms(),activity);
        personViewHolder.rv.setAdapter(adapter);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(personViewHolder.rv.getContext(), R.anim.layout_animation_fall_down);
        personViewHolder.rv.setLayoutAnimation(controller);
        personViewHolder.rv.getAdapter().notifyDataSetChanged();
        personViewHolder.rv.scheduleLayoutAnimation();

        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return studentMarksResponse.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class MarkViewHolder extends RecyclerView.ViewHolder {

        TextView yearMark;
        TextView middle_mark_year;
        RecyclerView rv;


        MarkViewHolder(View itemView) {
            super(itemView);
            middle_mark_year = (TextView) itemView.findViewById(R.id.middle_mark_year);
            yearMark = (TextView) itemView.findViewById(R.id.yearMark);
            rv = (RecyclerView) itemView.findViewById(R.id.rv_second_mark);

            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}