package cn.vikingden.notes.ui.drawables;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import cn.vikingden.notes.R;

/**
 * Created by Viking Den on 2017/12/24.
 */

public class VectorDrawableActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawable_vector);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        getWindow().setAttributes( lp );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_BLUR_BEHIND );
    }
}
