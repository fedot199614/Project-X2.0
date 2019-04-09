package com.project.usm.app.Presenter;

import com.project.usm.app.View.ProfileView;

public class ProfilePresenter implements IProfilePresenter{
    ProfileView profileView;
    public ProfilePresenter(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void initStartState() {
        profileView.initTabBar();
        profileView.initBaseOption();
        profileView.initSettingsListener();
    }
}
