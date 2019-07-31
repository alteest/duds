package com.prospero.duds;

import android.Manifest;
import android.os.Build;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static MainActivity activity;
    //public static String baseUrl = "http://52.166.124.125/review/";
    public static String baseUrl = "http://134.209.204.54/api/";

    private FileCache mFileCache;

    private BottomNavigationView mNavigation = null;
    private Map<Integer, Label> labels = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        mFileCache = new FileCache(getApplicationContext());
        //mFileCache.list();
        //mFileCache.clear();

        LocaleHelper.onAttach(this);
        setContentView(R.layout.main);

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
        /*
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
        */
    }
}
