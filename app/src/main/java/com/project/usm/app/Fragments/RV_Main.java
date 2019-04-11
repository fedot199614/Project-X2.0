package com.project.usm.app.Fragments;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioTrack;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.project.usm.app.AOP.Annotations.InitTabBar;
import com.project.usm.app.AOP.Annotations.ListItemSelected;
import com.project.usm.app.MainActivity;
import  com.project.usm.app.Model.News;
import com.project.usm.app.Presenter.HomeNews;
import  com.project.usm.app.R;
import com.project.usm.app.Tools.NavItems;
import  com.project.usm.app.Tools.RVAdapter;
import  com.project.usm.app.Tools.RecyclerItemClickListener;
import com.project.usm.app.View.Home_View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RV_Main extends Fragment implements Home_View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  HomeNews homePresenter;
    private RecyclerView rv;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RV_Main() {

    }



    public static RV_Main newInstance(String param1, String param2) {
        RV_Main fragment = new RV_Main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_rv__main, container, false);
    }
    @ListItemSelected(item = ListItemSelected.Item.MAIN)
    @InitTabBar(check = InitTabBar.Check.GONE)
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        homePresenter = new HomeNews(this);
        homePresenter.Init();
        homePresenter.setAnimFade(this,getActivity());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public RecyclerView InitRV() {
        rv = (RecyclerView)getView().findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        return rv;
    }

    @Override
    public RVAdapter createRVAdapter(List<News> newsList) {
        RVAdapter adapter = new RVAdapter(newsList);
        return adapter;
    }

    @Override
    public void itemClickListener(RecyclerView rv, List<News> model) {
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SharedNews sn = new SharedNews();
                homePresenter.setAnimFade(sn,getActivity());
                homePresenter.beginTransaction(getFragmentManager(),sn,getString(R.string.sharedNews),position,view,model);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }


    @Override
    public void onRefresh() {
        SwipeRefreshLayout refresh =  (SwipeRefreshLayout)getActivity().findViewById(R.id.container);
        refresh.setOnRefreshListener(()->{
            Toast.makeText(getContext(), "Жду сервис!!!!!", Toast.LENGTH_SHORT).show();
            homePresenter.refreshAnim();
            refresh.setRefreshing(false);
            //---------------------------------------------------TEST


            Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager =
                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("test", "My channel",
                        NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("My channel description");
                channel.enableLights(true);
                channel.enableVibration(true);
                channel.setLightColor(Notification.DEFAULT_LIGHTS);
                channel.setSound(ringURI,new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);


            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(getContext(), "test")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Title")
                            .setContentText("Тест push-up").setDefaults(Notification.DEFAULT_ALL).
                             setContentIntent(pendingIntent).setAutoCancel(true);




            notificationManager.notify(0, builder.build());
        });
    }

    @Override
    public void runLayoutAnimation() {
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(rv.getContext(), R.anim.layout_animation_fall_down);
        rv.setLayoutAnimation(controller);
        rv.getAdapter().notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
