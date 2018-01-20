package cn.vikingden.notes.performance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.vikingden.notes.R;

/**
 * Created by Viking Den on 2018/1/20.
 */

public class OverDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_overdraw);
    }
}
