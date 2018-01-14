package cn.vikingden.notes.security;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Viking Den on 2018/1/14.
 */

public class NoScreenShotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set flags
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        //add text
        TextView mContent = new TextView(this) ;
        mContent.setText("No ScreenShot");

        setContentView(mContent);
    }
}
