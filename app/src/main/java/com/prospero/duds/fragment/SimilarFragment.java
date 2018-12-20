package com.prospero.duds.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.prospero.duds.MainActivity;
import com.prospero.duds.view.SimilarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class SimilarFragment extends BaseFragment {

    private JSONArray jsonArray = null;

    public SimilarFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        BottomNavigationView navigation = MainActivity.activity.getNavigation();
        navigation.setEnabled(false);
        navigation.setVisibility(View.GONE);

        /*mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                MainActivity.activity.getNavigation().setSelectedItemId(Menu.FIRST + position);
            }
        });*/
        return rootView;
    }

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<View>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jobj = jsonArray.getJSONObject(i);
                SimilarView view = new SimilarView(getContext());
                view.setData(jobj);
                views.add(view);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return views;
    }
}
