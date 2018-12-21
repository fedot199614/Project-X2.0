package com.project.usm.app.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.usm.app.R;
import com.project.usm.app.View.Home_View;

public class SharedNews extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SharedNews() {

    }



    public static SharedNews newInstance(String param1, String param2) {
        SharedNews fragment = new SharedNews();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.news_model_shared_main, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        TextView title = (TextView) getView().findViewById(R.id.title_model_sh);
        TextView newsFull = (TextView) getView().findViewById(R.id.news_model_sh);
        Bundle bundle = getArguments();
        title.setText(bundle.getString("title"));
        newsFull.setText(bundle.getString("news"));

        System.out.print(getFragmentManager().getBackStackEntryCount());


    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
