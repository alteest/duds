package com.prospero.duds.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.prospero.duds.MainActivity;

import java.util.HashMap;
import java.util.Map;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public abstract class BaseView extends RelativeLayout {
    protected ImageButton mImageButton;
    protected String mFilepath = null;
    protected Map<String, ShowcaseView> mShowcaseViews = new HashMap<String, ShowcaseView>();

    public void showShowcaseViews() {return;}

    public BaseView(Context context) {
        super(context);
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(getLayoutResource(), this);
    }

    protected abstract int getLayoutResource();

    public void setFilepath(String filepath) {
        mFilepath = filepath;
    }

    public String getFilepath() {
        return mFilepath;
    }

}
