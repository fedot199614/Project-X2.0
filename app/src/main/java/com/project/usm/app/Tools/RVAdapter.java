package com.project.usm.app.Tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.usm.app.Model.News;
import com.project.usm.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{



    List<News> news;
   public RVAdapter(List<News> news){

       this.news = news;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_model_main, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {
        personViewHolder.title.setText(news.get(i).getTitle());
        personViewHolder.news.setText(news.get(i).getModel());
        personViewHolder.time.setText(news.get(i).getPublishDate());
        personViewHolder.author.setText(news.get(i).getAuthorName());
        if(!news.get(i).urlListIsEmpty()){
            Picasso.get().load(news.get(i).getImgURL().get(0)).resize(150, 150)
                    .centerCrop().into(personViewHolder.img);
        }
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView news;
        TextView time;
        TextView author;
        ImageView img;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            news = (TextView) itemView.findViewById(R.id.news_model);
            title = (TextView) itemView.findViewById(R.id.member_name);
            time = (TextView) itemView.findViewById(R.id.time);
            author = (TextView) itemView.findViewById(R.id.authorName);
            img = (ImageView)itemView.findViewById(R.id.imagePrev);
            //personName = (TextView)itemView.findViewById(R.id.person_name);
            //personAge = (TextView)itemView.findViewById(R.id.person_age);
            //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}