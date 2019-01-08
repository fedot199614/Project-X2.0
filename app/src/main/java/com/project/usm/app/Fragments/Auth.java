package com.project.usm.app.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.usm.app.Presenter.Auth_Presenter;
import com.project.usm.app.R;
import com.project.usm.app.View.Auth_View;

public class Auth extends Fragment implements Auth_View {
    private ProgressBar progressBar;
    private TextInputEditText login;
    private TextInputEditText password;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Auth() {
        // Required empty public constructor
    }

    public static Auth newInstance(String param1, String param2) {
        Auth fragment = new Auth();
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

        return inflater.inflate(R.layout.fagment_auth, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


        Button auth = getActivity().findViewById(R.id.registrationBtn);
        login = getActivity().findViewById(R.id.email);

        progressBar = getActivity().findViewById(R.id.progressBarAuth);
        progressBar.setVisibility(View.INVISIBLE);
        Auth_Presenter authPresenter = new Auth_Presenter(this);

        auth.setOnClickListener(click -> {
            authPresenter.onLogin(login.getText().toString(), "");
        });


    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showLoading() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoginMessage() {

    }

    @Override
    public void showErrorValidation(int errorCodeValidation) {

        if(errorCodeValidation==0) {
            login.setError(getString(R.string.emptyInput));
        }else if(errorCodeValidation==1){
            login.setError(getString(R.string.wrongFormat));
        }else if(errorCodeValidation==2){
            login.setError(getString(R.string.shortIDNP));
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
