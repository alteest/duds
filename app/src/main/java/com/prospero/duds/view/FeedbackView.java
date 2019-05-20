package com.prospero.duds.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prospero.duds.MainActivity;
import com.prospero.duds.R;
import com.prospero.duds.async.PostFeedback;
import com.prospero.duds.fragment.BaseFragment;
import com.prospero.duds.fragment.FeedbackFragment;

public class FeedbackView extends BaseView {

    private EditText mMessage;

    public FeedbackView(BaseFragment fragment) {
        super(fragment);

        mMessage = findViewById(R.id.feedback_text);
        Button sendButton = findViewById(R.id.feedback_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackFragment fragment = (FeedbackFragment) MainActivity.activity.getCurrentFragment();
                fragment.showProgressBar();
                new PostFeedback().execute(fragment);
            }
        });
    }

    @Override
    protected void onLayout (boolean changed,
                             int left,
                             int top,
                             int right,
                             int bottom) {
        if (changed) {
            mFragment.hideActionButton();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.feedback_view;
    }

    public String getMessage() {
        return mMessage.getText().toString();
    }
}
