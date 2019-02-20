package com.prospero.duds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.view.BaseView;
import com.prospero.duds.view.UploadImageView;
import com.prospero.duds.view.UploadPhotoView;
import com.prospero.duds.view.UploadView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends BaseFragment {

    public UploadFragment() {
        super();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_image:
                    mViewPager.setCurrentItem(0);
                    //((BaseView)views.get(0)).showShowcaseViews();
                    return true;
                case R.id.navigation_photo:
                    mViewPager.setCurrentItem(1);
                    //((BaseView)views.get(1)).showShowcaseViews();
                    return true;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mDotsTabLayout.setVisibility(View.INVISIBLE);

        BottomNavigationView navigation = MainActivity.activity.getNavigation();
        navigation.setEnabled(true);
        navigation.setVisibility(View.VISIBLE);
        navigation.getMenu().clear();
        navigation.inflateMenu(R.menu.upload_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    BaseView view = (BaseView) views.get(mViewPager.getCurrentItem());
                    view.showShowcaseViews();
                }
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        MainActivity.activity.getNavigation().setSelectedItemId(R.id.navigation_image);
                        return;
                    case 1:
                        MainActivity.activity.getNavigation().setSelectedItemId(R.id.navigation_photo);
                        return;
                }
            }
        });
        ((BaseView) views.get(mViewPager.getCurrentItem())).showShowcaseViews();

        //mBasePagerAdapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            UploadView view = (UploadView) mBasePagerAdapter.getView(mViewPager.getCurrentItem());
            view.onActivityResult(data);
        }
    }

    @Override
    protected ArrayList<View> getViews() {
        ArrayList<View> views = new ArrayList<>();
        views.add(new UploadImageView(this.getContext()));
        views.add(new UploadPhotoView(this.getContext()));
        return views;
    }

    public String getFilepath() {
        UploadView view = (UploadView) mBasePagerAdapter.getView(mViewPager.getCurrentItem());
        return view.getFilepath();
    }

}