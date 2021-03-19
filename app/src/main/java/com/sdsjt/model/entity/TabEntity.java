package com.sdsjt.model.entity;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;
    public String eventName;
    public Fragment fragment;

    public TabEntity(String title, String eventName, int selectedIcon, int unSelectedIcon, Fragment fragment) {
        this.title = title;
        this.eventName = eventName;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.fragment = fragment;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    public String getEventName() {
        return eventName;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
