package com.reactnative.ivpusic.imagepicker;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.yalantis.ucrop.UCropActivity;

public class CustomUCropActivity extends UCropActivity {

    public static final String EXTRA_TOP_MARGIN = "extra_top_margin";
    public static final String EXTRA_BOTTOM_MARGIN = "extra_bottom_margin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get margin values from intent extras (in dp)
        int topMarginDp = getIntent().getIntExtra(EXTRA_TOP_MARGIN, 0);
        int bottomMarginDp = getIntent().getIntExtra(EXTRA_BOTTOM_MARGIN, 0);

        if (topMarginDp > 0 || bottomMarginDp > 0) {
            applyMargins(topMarginDp, bottomMarginDp);
        }
    }

    private void applyMargins(int topMarginDp, int bottomMarginDp) {
        // Find the root content view
        View rootView = findViewById(android.R.id.content);

        if (rootView instanceof ViewGroup) {
            ViewGroup rootViewGroup = (ViewGroup) rootView;

            // Get the first child (the actual UCrop layout)
            if (rootViewGroup.getChildCount() > 0) {
                View uCropView = rootViewGroup.getChildAt(0);
                ViewGroup.LayoutParams layoutParams = uCropView.getLayoutParams();

                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;

                    // Convert dp to pixels
                    int topMarginPx = dpToPx(topMarginDp);
                    int bottomMarginPx = dpToPx(bottomMarginDp);

                    // Set margins
                    marginParams.setMargins(
                        marginParams.leftMargin,
                        topMarginPx,
                        marginParams.rightMargin,
                        bottomMarginPx
                    );

                    uCropView.setLayoutParams(marginParams);
                } else {
                    // If not MarginLayoutParams, create new FrameLayout.LayoutParams with margins
                    int topMarginPx = dpToPx(topMarginDp);
                    int bottomMarginPx = dpToPx(bottomMarginDp);

                    FrameLayout.LayoutParams newParams = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    newParams.setMargins(0, topMarginPx, 0, bottomMarginPx);
                    uCropView.setLayoutParams(newParams);
                }
            }
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}