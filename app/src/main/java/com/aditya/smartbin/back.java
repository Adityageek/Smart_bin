package com.aditya.smartbin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import com.google.firebase.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class back extends Service {
    NotificationCompat.Builder mBuilder;
    int notificationID = 3;
   // MainActivity m;
    String v;



    FirebaseDatabase database2 = FirebaseDatabase.getInstance();

    DatabaseReference notify = database2.getReference("NOTIFICATION");;

      public back() {
    }

    public void not(){
        //noinspection deprecation,deprecation,deprecation
        mBuilder = new NotificationCompat.Builder(this);
       mBuilder.setSmallIcon(R.drawable.download);
        mBuilder.setAutoCancel(true);
        mBuilder.setTicker("Bin is full");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle("bin alert");
        mBuilder.setContentText("Dustbin is full empty the bin");

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setSound(uri);
        mBuilder.setDeleteIntent(resultPendingIntent);


        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       // mNotificationManager.cancel(3);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID, mBuilder.build());

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
       //database2 = FirebaseDatabase.getInstance();
       // notify = database2.getReference("NOTIFICATION");

        notify.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {


                //String v = (String)dataSnapshot.getValue(String.class);
                //m.t.setText("hello");
                not();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //not();
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
