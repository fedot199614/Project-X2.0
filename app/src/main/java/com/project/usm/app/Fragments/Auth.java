package com.project.usm.app.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import android.widget.Toast;

import com.project.usm.app.MainActivity;
import com.project.usm.app.Presenter.Auth_Presenter;
import com.project.usm.app.R;
import com.project.usm.app.Tools.KeyBoardManager;
import com.project.usm.app.View.Auth_View;

public class Auth extends Fragment implements Auth_View {
    private TextInputEditText login;
    private TextInputEditText password;
    private ProgressDialog dialog;
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
        password = getActivity().findViewById(R.id.password);
        Auth_Presenter authPresenter = new Auth_Presenter(this);
        authPresenter.initTabLayout();
        auth.setOnClickListener(click -> {
            authPresenter.onLogin(login.getText().toString(), password.getText().toString());
        });
        login.setOnFocusChangeListener((v,hasFocus)->{
            if(!hasFocus){
            KeyBoardManager.hideKeyboard(v);
        }
        });

        password.setOnFocusChangeListener((v,hasFocus)->{
            if(!hasFocus){
                KeyBoardManager.hideKeyboard(v);
            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void initTabBar(){
        TabLayout tabBar = (TabLayout) getActivity().findViewById(R.id.tabLayout);
        tabBar.setVisibility(View.GONE);
    }

    @Override
    public void initAuthState() {
        MainActivity.getNavManager().sign_outViewVisibility();
    }

    @Override
    public void initHomePage() {
        RV_Main mainNewsList = new RV_Main();
        FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
        fr.addToBackStack(null);
        fr.replace(R.id.mainFrame,mainNewsList).commit();
    }


    @Override
    public void showLoading() {
         dialog = ProgressDialog.show(getContext(),"","Авторизация. Подождите..");
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();

    }


    @Override
    public void onLoginMessageError() {
        Toast.makeText(getContext(),"Ошибка авторизации",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccessfully() {
        Toast.makeText(getContext(),"Успешная авторизация",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorValidationLogin(int errorCodeValidation) {

        if(errorCodeValidation==0) {
            login.setError(getString(R.string.emptyInput));
        }else if(errorCodeValidation==1){
            login.setError(getString(R.string.wrongFormat));
        }else if(errorCodeValidation==2){
            login.setError(getString(R.string.wrongLength));
        }
    }

    @Override
    public void showErrorValidationPassword(int errorCodeValidation) {

        if(errorCodeValidation==0) {
            password.setError(getString(R.string.emptyInput));
        }else if(errorCodeValidation==1){
            password.setError(getString(R.string.wrongFormat));
        }else if(errorCodeValidation==2){
            password.setError(getString(R.string.wrongLength));
        }else if(errorCodeValidation==3) {
            password.setError(getString(R.string.wrongLength));
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
