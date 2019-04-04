package com.prospero.duds.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.view.BaseView;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    protected ViewPager mViewPager;
    protected BasePagerAdapter mBasePagerAdapter;
    protected TabLayout mDotsTabLayout;

    protected ArrayList<View> views = null;

    private ImageButton leftNav;
    private ImageButton rightNav;
    public FloatingActionButton mFloatingActionButton;

    private ProgressBar progressBarLoad;

    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (views == null) {
            views = getViews();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.base_view, container, false);

        mBasePagerAdapter = new BasePagerAdapter(views);

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mBasePagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                toggleNavigationVisibility(position);
            }
        });
        mDotsTabLayout = (TabLayout) rootView.findViewById(R.id.dots_tab_layout);
        mDotsTabLayout.setupWithViewPager(mViewPager, true);
        if (views.size() > 4) {
            mDotsTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else if (views.size() < 2) {
            mDotsTabLayout.setVisibility(View.INVISIBLE);
        } else {
            mDotsTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        leftNav = (ImageButton) rootView.findViewById(R.id.left_nav);
        rightNav = (ImageButton) rootView.findViewById(R.id.right_nav);

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        progressBarLoad = (ProgressBar) rootView.findViewById(R.id.progress_load);
        hideProgressBar();

        BottomNavigationView navigation = MainActivity.activity.getNavigation();
        if (views.size() < 2) {
            navigation.setEnabled(false);
            navigation.setVisibility(View.GONE);
        } else {
            navigation.setEnabled(true);
            navigation.setVisibility(View.VISIBLE);
        }

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.search_button);
        mFloatingActionButton.bringToFront();

        toggleNavigationVisibility(mViewPager.getCurrentItem());

        return rootView;
    }

    protected abstract ArrayList<View> getViews();

    private void toggleNavigationVisibility(int position) {
        if (position == Math.max(views.size() - 1, 0)) {
            rightNav.setVisibility(View.INVISIBLE);
        } else {
            rightNav.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            leftNav.setVisibility(View.INVISIBLE);
        } else {
            leftNav.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBar() {
        progressBarLoad.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBarLoad.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    public void showActionButton() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    public void hideActionButton() {
        mFloatingActionButton.setVisibility(View.GONE);
    }
}
