package com.prospero.duds.async;

import com.prospero.duds.view.SimilarView;

public class ThumbData {
    private SimilarView view;
    private boolean mode;

    public ThumbData(SimilarView view, boolean mode) {
        this.view = view;
        this.mode = mode;
    }

    public SimilarView getSimilarView() {
        return view;
    }

    public boolean getMode() {
        return mode;
    }
}
