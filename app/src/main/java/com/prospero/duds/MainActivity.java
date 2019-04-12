package com.prospero.duds;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;

import com.prospero.duds.ao.Label;
import com.prospero.duds.cache.FileCache;
import com.prospero.duds.fragment.BaseFragment;
import com.prospero.duds.fragment.StartupFragment;
import com.prospero.duds.locale.LocaleHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int UPLOAD = 0;
    public static final int BOX = 1;
    public static final int SIMILAR = 2;

    public static MainActivity activity;
    //public static String baseUrl = "http://52.166.124.125/review/";
    public static String baseUrl = "http://134.209.204.54/api/";

    private FileCache mFileCache;

    private BottomNavigationView mNavigation = null;
    private Map<Integer, Label> labels = new HashMap<>();

    private int mode;

    @TargetApi(23)
    public boolean validatePermission(String[] permissions) {
        System.out.println("Validate permissions for " + Arrays.toString(permissions));
        for (String permission: permissions) {
            System.out.println("Permissions : " + permission);
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        mFileCache = new FileCache(getApplicationContext());
        //mFileCache.list();
        //mFileCache.clear();

        LocaleHelper.onAttach(this);
        setContentView(R.layout.main);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.toolbar_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        return true;
                }
                return false;
            }
        });
        */

        BaseFragment currentFragment = getCurrentFragment();
        //if ((currentFragment == null) || (currentFragment.getClass() != UploadFragment.class)) {
            // TODO use current fragment if (currentFragment == null) {
        if (currentFragment == null) {
            //setFragment(new UploadFragment());
            setFragment(new StartupFragment());
        }
        mNavigation = findViewById(R.id.navigation);

        //new GetLabelsTask().execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //System.out.println("OnRequestPermissions " + permissions.toString() + " ||| " + grantResults.toString());
        boolean granted = true;
        // If request is cancelled, the result arrays are empty.
        for (int grantResult: grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                System.out.println("Denied!!!");
                granted = false;
                break;
            }
        }
        if (!granted) {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            //Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            System.out.println("DENIED!!!!");
        }
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    public void setFragment(@NonNull BaseFragment fragment) {
        if (getCurrentFragment() != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, "CURRENT_FRAGMENT")
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, "CURRENT_FRAGMENT")
                    .commit();
        }
    }

    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
    }

    public BottomNavigationView getNavigation() {
        return mNavigation;
    }

    public FileCache getFileCache() {
        return mFileCache;
    }
    public Map<Integer, Label> getLabels() {
        return labels;
    }

    public static void appendLog(String text)
    {
        File logFile = new File("sdcard/log.file");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
