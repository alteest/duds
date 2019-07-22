package com.prospero.duds.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
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

    private JSONObject jsonObject = null;

    public SimilarFragment() {
        super();
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        try {
            jsonObject = new JSONObject(bundle.getString("array"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        mFloatingActionButton.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        try {
            String uuid = jsonObject.getString("request");
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
            try {
                SimilarView view = new SimilarView(this);
                view.setData(uuid, data.getJSONObject(i));
                views.add(view);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return views;
    }
}
