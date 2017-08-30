package com.team2783.vision;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;

public class Gradient_Draw extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hsv_bottom_sheet);

        // Get the widgets reference from XML layout
        LinearLayout ll = (LinearLayout) findViewById(R.id.popup_window);
        final ImageView iv = (ImageView) findViewById(R.id.GradientImage);
        Button btn = (Button) findViewById(R.id.update_gradient);

        // Set a click listener for Button widget
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize a new GradientDrawable
                GradientDrawable gd = new GradientDrawable();

                // Set the color array to draw gradient
                gd.setColors(new int[]{
                        Color.RED,
                        Color.GREEN,
                        Color.YELLOW,
                        Color.CYAN
                });

                // Set the GradientDrawable gradient type linear gradient
                gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);

                // Set GradientDrawable shape is a rectangle
                gd.setShape(GradientDrawable.RECTANGLE);

                // Set 3 pixels width solid blue color border
                gd.setStroke(3, Color.BLUE);

                // Set GradientDrawable width and in pixels
                gd.setSize(450, 150); // Width 450 pixels and height 150 pixels

                // Set GradientDrawable as ImageView source image
                iv.setImageDrawable(gd);
            }
        });
    }
}