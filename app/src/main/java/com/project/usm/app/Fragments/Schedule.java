package com.project.usm.app.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.project.usm.app.AOP.Annotations.InitTabBar;
import com.project.usm.app.AOP.Annotations.ListItemSelected;
import com.project.usm.app.Model.ScheduleModel;
import com.project.usm.app.R;
import com.project.usm.app.Tools.NavItems;

import com.project.usm.app.Tools.RVAdapterSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Schedule extends Fragment implements TabLayout.OnTabSelectedListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    private RVAdapterSchedule adapter;
    private  Map<Integer,List<ScheduleModel>> mapSchedule;
    private RecyclerView rv;

    public Schedule() {
        // Required empty public constructor
    }

    public static Schedule newInstance(String param1, String param2) {
        Schedule fragment = new Schedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @InitTabBar(check = InitTabBar.Check.VISIBLE)
    @ListItemSelected(item = ListItemSelected.Item.SCHEDULE)
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


        List<ScheduleModel> monday = new ArrayList<>();
        monday.add(new ScheduleModel("9:45","11:15","4","Propr.intel","413","Bodiul.T"));
        monday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));
        monday.add(new ScheduleModel("13:30","15:00","4","Manag. Inovat","413","Sperelup.L"));
        monday.add(new ScheduleModel("8:00","9:30","4","Manag. Inovat","332","Sperelup.L"));
        monday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        monday.add(new ScheduleModel("11:30","13:00","4","Proect. Intert","247","Sirkeli.V"));
        monday.add(new ScheduleModel("13:30","15:00","4","Prog. Driverilor","138","Narolschi.I"));

        List<ScheduleModel> tuesday = new ArrayList<>();
        tuesday.add(new ScheduleModel("8:00","9:30","4","Manag. Inovat","332","Sperelup.L"));
        tuesday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        tuesday.add(new ScheduleModel("11:30","13:00","4","Proect. Intert","247","Sirkeli.V"));
        tuesday.add(new ScheduleModel("13:30","15:00","4","Prog. Driverilor","138","Narolschi.I"));

        List<ScheduleModel> wednesday = new ArrayList<>();
        wednesday.add(new ScheduleModel("8:00","9:30","4","Sistem. Intelegenta","421b","Capatina.G"));
        wednesday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        wednesday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));

        List<ScheduleModel> thursday = new ArrayList<>();
        thursday.add(new ScheduleModel("8:00","9:30","4","Sistem. Intelegenta","421b","Capatina.G"));
        thursday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        thursday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));
        thursday.add(new ScheduleModel("8:00","9:30","4","Sistem. Intelegenta","421b","Capatina.G"));
        thursday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        thursday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));
        thursday.add(new ScheduleModel("9:45","11:15","4","Propr.intel","413","Bodiul.T"));
        thursday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));
        thursday.add(new ScheduleModel("13:30","15:00","4","Manag. Inovat","413","Sperelup.L"));
        thursday.add(new ScheduleModel("8:00","9:30","4","Manag. Inovat","332","Sperelup.L"));
        thursday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        thursday.add(new ScheduleModel("11:30","13:00","4","Proect. Intert","247","Sirkeli.V"));
        thursday.add(new ScheduleModel("13:30","15:00","4","Prog. Driverilor","138","Narolschi.I"));

        List<ScheduleModel> friday = new ArrayList<>();
        friday.add(new ScheduleModel("8:00","9:30","4","Sistem. Intelegenta","421b","Capatina.G"));
        friday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        friday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));
        friday.add(new ScheduleModel("9:45","11:15","4","Propr.intel","413","Bodiul.T"));
        friday.add(new ScheduleModel("11:30","13:00","4","Propr.intel","413","Bodiul.T"));
        friday.add(new ScheduleModel("13:30","15:00","4","Manag. Inovat","413","Sperelup.L"));
        friday.add(new ScheduleModel("8:00","9:30","4","Manag. Inovat","332","Sperelup.L"));
        friday.add(new ScheduleModel("9:45","11:15","4","Sistem. Intelegenta","413","Capatina.G"));
        friday.add(new ScheduleModel("11:30","13:00","4","Proect. Intert","247","Sirkeli.V"));
        friday.add(new ScheduleModel("13:30","15:00","4","Prog. Driverilor","138","Narolschi.I"));

        List<ScheduleModel> saturday = new ArrayList<>();
        List<ScheduleModel> sunday = new ArrayList<>();

        mapSchedule = new HashMap<>();

        mapSchedule.put(0,monday);
        mapSchedule.put(1,tuesday);
        mapSchedule.put(2,wednesday);
        mapSchedule.put(3,thursday);
        mapSchedule.put(4,friday);
        mapSchedule.put(5,saturday);
        mapSchedule.put(6,sunday);



        rv = (RecyclerView)getView().findViewById(R.id.rv_schedule);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        adapter = new RVAdapterSchedule(monday);
        rv.setAdapter(adapter);


    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void runLayoutAnimation(){
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(rv.getContext(), R.anim.layout_animation_fall_down);
        rv.setLayoutAnimation(controller);
        rv.getAdapter().notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        runLayoutAnimation();
        adapter = new RVAdapterSchedule(mapSchedule.get(tab.getPosition()));
        rv.setAdapter(adapter);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
