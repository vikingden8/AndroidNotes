package cn.vikingden.notes.ui.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.vikingden.notes.MainActivity;
import cn.vikingden.notes.R;
import cn.vikingden.notes.intent.ResolveActivity;

public class NotificationCompatActivity extends AppCompatActivity {

    /**
     * A numeric value that identifies the notification that we'll be sending.
     * This value needs to be unique within this app, but it doesn't need to be
     * unique system-wide.
     */
    public static final int NOTIFICATION_NORMAL_ID = 1;
    public static final int NOTIFICATION_ACTION_ID = 2;
    public static final int NOTIFICATION_BIGVIEW_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notication_compat);
    }

    /**
     * Send a sample notification using the NotificationCompat API.
     */
    public void sendNormalNotification(View view) {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commonly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("BasicNotifications Sample");
        builder.setContentText("Time to learn about notifications!");
        builder.setSubText("Tap to view documentation about notifications.");

        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_NORMAL_ID, builder.build());
        // END_INCLUDE(send_notification)
    }

    public void sendActionNotification(View view) {
        //create normal notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Title");
        builder.setContentText("Description");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true) ;
        builder.setPriority(NotificationCompat.PRIORITY_LOW) ;

        //add action
        Intent buttonActionIntent = new Intent(this, ResolveActivity.class);
        PendingIntent pendingIntentButton = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), buttonActionIntent, 0);
        NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher, "Open Action", pendingIntentButton);
        builder.addAction(action);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        //show notification
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ACTION_ID, builder.build());
    }

    public void sendBigViewNotification(View view) {
        //create normal notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Title");
        builder.setContentText("Description");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true) ;
        builder.setPriority(NotificationCompat.PRIORITY_MAX) ;

        //Create big view
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        events[0] = new String("First line....");
        events[1] = new String("Second line...");
        events[2] = new String("Third line...");
        events[3] = new String("4th line...");
        events[4] = new String("5th line...");
        events[5] = new String("6th line...");

        // Sets a title for the big view
        inboxStyle.setBigContentTitle("Big Title");
        // Add events
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        builder.setStyle(inboxStyle);
        //show notification
        NotificationManagerCompat.from(this).notify(NOTIFICATION_BIGVIEW_ID, builder.build());
    }

}
