package com.prospero.duds.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.locale.LocaleHelper;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public final class SettingsActivity extends PreferenceActivity {

    private AppCompatDelegate mDelegate;
    private boolean mNeedRecreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNeedRecreate = false;
        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();

        new MaterialShowcaseView.Builder(this).useFadeAnimation();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_language)));
            bindPreferenceClick(findPreference(getString(R.string.clear_cache)));
        }
    }

    private static void bindPreferenceSummaryToValue(@NonNull Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private static void bindPreferenceClick(@NonNull Preference preference) {
        preference.setOnPreferenceClickListener(sBindPreferenceClickListener);
        //sBindPreferenceClickListener.onPreferenceClick(preference);
    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, @NonNull Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                if (index >= 0) {
                    preference.setSummary(listPreference.getEntries()[index]);
                    if (!listPreference.getValue().equals(newValue)) {
                        LocaleHelper.setLocale(MainActivity.activity, stringValue);
                        MainActivity.activity.recreate();
                        ((SettingsActivity) preference.getContext()).mNeedRecreate = true;
                    }
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    private static Preference.OnPreferenceClickListener sBindPreferenceClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            MainActivity.activity.getFileCache().clear();
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
}
