package com.sdsjt.listener;

import java.util.List;


public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
