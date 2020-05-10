package com.example.carcrashdetection;

import android.Manifest;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;

public class MainActivityTest {

    private static final int ITEM = 1;
    private MainActivity mainActivity;


    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule grantPermissionRule =GrantPermissionRule.grant(android.Manifest.permission.READ_PHONE_STATE);
    @Rule
    public GrantPermissionRule grantPermissionRule1 =GrantPermissionRule.grant(Manifest.permission.FOREGROUND_SERVICE);
    @Rule
    public GrantPermissionRule grantPermissionRule2 =GrantPermissionRule.grant(android.Manifest.permission.SYSTEM_ALERT_WINDOW);
    @Rule
    public GrantPermissionRule grantPermissionRule3 =GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);
    @Rule
    public GrantPermissionRule grantPermissionRule4 =GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);




}