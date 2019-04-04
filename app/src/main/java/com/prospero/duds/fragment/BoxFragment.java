package com.prospero.duds.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.ao.Box;
import com.prospero.duds.view.BoxView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class BoxFragment extends BaseFragment {

    private JSONArray jsonArray = null;
    private String filepath;

    public BoxFragment() {
        super();
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        try {
            jsonArray = new JSONArray(bundle.getString("array"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        filepath = bundle.getString("filepath");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int position = item.getItemId() - Menu.FIRST;
            if (position < views.size()) {
                mViewPager.setCurrentItem(position);
                return true;
            }
            return false;
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        BottomNavigationView navigation = MainActivity.activity.getNavigation();
        //navigation.getMenu().clear();
        //int menu_id = Menu.FIRST;
        //for (View view: views) {
        //    if (navigation.getMenu().size() < 5) {
        //        navigation.getMenu().add(Menu.NONE, menu_id, Menu.NONE, R.string.menu_box_view).setIcon(R.drawable.ic_wallpaper_black_24dp);
        //        menu_id++;
        //    }
        //}
        navigation.setEnabled(false);
        navigation.setVisibility(View.GONE);

        /*navigation.setEnabled(true);
        navigation.setVisibility(View.VISIBLE);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                    MainActivity.activity.getNavigation().setSelectedItemId(Menu.FIRST + position);
            }
        });*/
        mFloatingActionButton.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<View>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    BoxView view = new BoxView(getContext(), this);
                    view.setFilepath(filepath);
                    view.setImage(new Box(jobj));
                    views.add(view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return views;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public byte[] getBytes() {
        BoxView view = (BoxView) mBasePagerAdapter.getView(mViewPager.getCurrentItem());
        return view.getBytes();

    }
}
