/**
 * @Author God-X-ShenYue
 * Remake by @Shirayuki
 * @Date 2024/07/29 23:23
*/
package com.shi.a10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;

public class callme {

    public static void shira(final Activity activity) {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        if (defaultSharedPreferences.getBoolean("0", false)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Typeface defaultFromStyle = Typeface.defaultFromStyle(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        layoutParams.setMargins(90, 0, 90, 10);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.height = 80;
        layoutParams2.gravity = 17;

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final AlertDialog create = builder.create();
        create.setCancelable(false);
        create.setIcon(0x7f020000);
        create.setView(linearLayout);

        View view = new View(activity);
        linearLayout.addView(view);
        view.setLayoutParams(layoutParams2);

        ImageView imageView = new ImageView(activity);
        linearLayout.addView(imageView);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(250, 250);
        imageParams.gravity = Gravity.CENTER_HORIZONTAL;
        imageView.setLayoutParams(imageParams);
        try {
            InputStream inputStream = activity.getAssets().open("tb");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView textView = new TextView(activity);
        linearLayout.addView(textView);
        textView.setGravity(17);
        textView.setText("Warning for you");
        textView.setTypeface(defaultFromStyle);
        textView.setTextColor(-12294368);
        textView.setTextSize(25);

        View view2 = new View(activity);
        linearLayout.addView(view2);
        view2.setLayoutParams(layoutParams2);

        TextView textView2 = new TextView(activity);
        linearLayout.addView(textView2);
        textView2.setTextSize(18);
        textView2.setText("️️️If you use my mod, it means you have agreed to report any errors to me, but if the error is not from my mod, it doesn't need to be reported...\nprovide error via log so I can know what kind of error it is maybe in the future I will make it so that it prints an error log if i have time");
        textView2.setTextColor(-12294368);
        textView2.setPadding(100, 50, 100, 50);

        Button button = new Button(activity);
        button.setLayoutParams(layoutParams);
        linearLayout.addView(button);
        button.setText("Agree");
        button.setTextColor(-12294368);
        button.setBackground(buttonback1());
        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    create.dismiss();
                }
            });

        Button button2 = new Button(activity);
        button2.setLayoutParams(layoutParams);
        linearLayout.addView(button2);
        button2.setText("Contact Me");
        button2.setTextColor(-12294368);
        button2.setBackground(buttonback2());
        button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse("https://t.me/Lyn_Mountain"));
                    activity.startActivity(intent);
                    create.dismiss();
					
					}
					});
					
					
            Button button4 = new Button(activity);
            button4.setLayoutParams(layoutParams);
            linearLayout.addView(button4);
            button4.setText("Don't Show");  // Changed text
            button4.setTextColor(-12294368);
            button4.setBackground(buttonback4());
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Save preference to never show again
                    SharedPreferences.Editor editor = defaultSharedPreferences.edit();
                    editor.putBoolean("0", true);
                    editor.apply();
                    
                    create.dismiss();
                }
            });

        View view3 = new View(activity);
        linearLayout.addView(view3);
        view3.setLayoutParams(layoutParams2);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(100.0f);
        gradientDrawable.setColor(-789272);
        create.getWindow().setBackgroundDrawable(gradientDrawable);
        create.show();
        WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams();
        layoutParams3.copyFrom(create.getWindow().getAttributes());
        layoutParams3.width = 1000;
        create.getWindow().setAttributes(layoutParams3);
    }
    

    private static Drawable buttonback1() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadii(new float[]{70.0f, 70.0f, 70.0f, 70.0f, 0.0f, 0.0f, 0.0f, 0.0f});
        gradientDrawable.setColor(-2496571);
        return gradientDrawable;
    }

    private static Drawable buttonback2() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f});
        gradientDrawable.setColor(-2496571);
        return gradientDrawable;
    

	}

    private static Drawable buttonback4() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 70.0f, 70.0f, 70.0f, 70.0f});
        gradientDrawable.setColor(-2496571);
        return gradientDrawable;
    }
	
}
