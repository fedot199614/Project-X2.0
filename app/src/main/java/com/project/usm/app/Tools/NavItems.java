package com.project.usm.app.Tools;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.View;

import com.project.usm.app.R;

public class NavItems {
    public static Menu getNavMenu(Activity activity){
        NavigationView items = (NavigationView) activity.findViewById(R.id.nav_view);
        return items.getMenu();
    }
}
