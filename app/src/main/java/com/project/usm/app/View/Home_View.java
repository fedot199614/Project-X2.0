package com.project.usm.app.View;

import android.support.v7.widget.RecyclerView;

import com.project.usm.app.Model.News;
import com.project.usm.app.Tools.RVAdapter;

import java.util.List;

public interface Home_View {
    RecyclerView InitRV();
    RVAdapter createRVAdapter(List<News> newsList);
    void itemClickListener(RecyclerView rv,List<News> model);


}
