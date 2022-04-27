package com.example.tecseb42ise1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    lateinit var orient:String;
    lateinit var tv:TextView;
    var count:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orientation = this.resources.configuration.orientation
        val currOrientation = this.resources.configuration.orientation
        var b = findViewById<Button>(R.id.button);
        tv = findViewById<TextView>(R.id.textView);
//set orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("MyMessage","potriat");
            orient = "Potrait";
            tv.append("Potrait");

        } else {
            Log.i("MyMessage","landscape");
            tv.append("Landscape");
            orient = "Landscape";
        }
        tv.append("\nThe count of rotation is ${count}");

        b.setOnClickListener(View.OnClickListener{
            tv.append("\nLocked Rotation");
            if (currOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT)
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            }

            val builder = NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Screen Rotation")
                .setContentText("Locked")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(2, builder.build())
            }


        })
        createNotificationChannel()


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = "Channel 1 notifcations"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        // Built a notifcation object
        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Screen Rotation")
            .setContentText(orient)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            //.setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

    }

//restoring button value

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        count += 1
        Log.i("MyMessege","value of count variable is ${count}")
        outState.putInt("countValue",count)
        Log.i("MyMessege","onSaveInstanceState One Arg")

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("MyMessege","onRestoreInstanceState")
        count = savedInstanceState.getInt("countValue")
        Log.i("MyMessege","value of count variable restored is ${count}")
        tv.append("\nThe count of rotation is $count");


    }

}