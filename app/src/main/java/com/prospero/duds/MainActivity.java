package com.prospero.duds;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    //private ArrayList<String> deniedPermissions = new ArrayList<>();

    //final Lock lock = new ReentrantLock();
    //final Condition waitPermissions = lock.newCondition();

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

/*    @TargetApi(23)
    public boolean validatePermission(String[] permissions) {
        System.out.println("Validate permissions for " + Arrays.toString(permissions));
        ArrayList<String> nonGrantedPermissions = new ArrayList<>();
        for (String permission: permissions) {
            System.out.println("Permissions : " + permission);
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                nonGrantedPermissions.add(permission);
            }
        }
        if (!nonGrantedPermissions.isEmpty()) {
            lock.lock();
            try {
                String[] newArray = new String[nonGrantedPermissions.size()];
                for(int i=0; i<nonGrantedPermissions.size(); i++)
                {
                    newArray[i] = nonGrantedPermissions.get(i);
                }
                ActivityCompat.requestPermissions(this, newArray, 1);
                        //requestPermissions(newArray, 1);
                waitPermissions.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            for (String permission: nonGrantedPermissions) {
                if (deniedPermissions.contains(permission)) {
                    return false;
                }
            }
        }
        return true;
    }*/

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("OnRequestPermissions " + permissions.toString() + " ||| " + grantResults.toString());
        for(int i = 0; i < permissions.length; i++){
            String permission = permissions[i];
            if (deniedPermissions.contains(permission)) {
                deniedPermissions.remove(permission);
            }
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        //waitPermissions.signal();
    }*/

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
