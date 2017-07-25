package cn.vikingden.notes.intent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.vikingden.notes.R;

/**
 * Author : Viking Den<dengwj@gionee.com>
 * Time : 7/25/17 3:22 PM
 */

public class ResolveActivity extends AppCompatActivity {

    private static final String WEB_URL = "https://www.baidu.com" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intent_resolve);
    }

    private boolean launchUrlInDefaultBrowser(Context context, String url) {
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        browserIntent.setData(Uri.parse(url));

        // 1: Try to find the default browser and launch the URL with it
        final ResolveInfo defaultResolution = context.getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (defaultResolution != null) {
            final ActivityInfo activity = defaultResolution.activityInfo;
            if (!activity.name.equals("com.android.internal.app.ResolverActivity")) {
                browserIntent.setClassName(activity.applicationInfo.packageName, activity.name);
                context.startActivity(browserIntent);
                return true;
            }
        }
        // 2: Try to find anything that we can launch the URL with. Pick up the first one that can.
        final List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (!resolveInfoList.isEmpty()) {
            browserIntent.setClassName(resolveInfoList.get(0).activityInfo.packageName, resolveInfoList.get(0).activityInfo.name);
            context.startActivity(browserIntent);
            return true;
        }
        return false;
    }

    public void onResolveButtonClicked(View view) {
        if (!launchUrlInDefaultBrowser(getApplicationContext(), WEB_URL)) {
            Toast.makeText(getApplicationContext(), "no application can handle web url" , Toast.LENGTH_SHORT).show();
        }
    }
}
