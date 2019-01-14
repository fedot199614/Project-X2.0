package com.project.usm.app.Tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.usm.app.Model.ScheduleModel;
import com.project.usm.app.R;

import java.util.List;

public class RVAdapterSchedule extends RecyclerView.Adapter<RVAdapterSchedule.Schedule>{



    List<ScheduleModel> sch;
   public RVAdapterSchedule(List<ScheduleModel> sch){

       this.sch = sch;
    }


    @NonNull
    @Override
    public Schedule onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_model, viewGroup, false);
        Schedule sch = new Schedule(v);
        return sch;
    }

    @Override
    public void onBindViewHolder(@NonNull Schedule personViewHolder, int i) {
        personViewHolder.subjectName.setText(sch.get(i).getSubjectName());
        personViewHolder.professorName.setText(sch.get(i).getProfessorName()+" "+sch.get(i).getCabinetNumber()+"/"+sch.get(i).getBlock());
        personViewHolder.textViewtimeStart.setText(sch.get(i).getTimeStart());
        personViewHolder.textViewtimeEnd.setText(sch.get(i).getTimeEnd());
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return sch.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class Schedule extends RecyclerView.ViewHolder {
        CardView cvSchedule;
        TextView textViewtimeStart;
        TextView textViewtimeEnd;
        TextView subjectName;
        TextView professorName;

        Schedule(View itemView) {
            super(itemView);
            cvSchedule = (CardView)itemView.findViewById(R.id.cvSchedule);
            subjectName = (TextView) itemView.findViewById(R.id.subjectName);
            professorName = (TextView) itemView.findViewById(R.id.professorName);
            textViewtimeStart = (TextView) itemView.findViewById(R.id.textViewtimeStart);
             textViewtimeEnd = (TextView) itemView.findViewById(R.id.textViewTimeEnd);
            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}