package com.user.firebasedatabase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.user.firebasedatabase.Common.Common;
import com.user.firebasedatabase.UserModule.UserDashboard;
import com.user.firebasedatabase.admin.AdminDashboard;


public class splashscreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        sharedpreferences = getSharedPreferences("login",
                Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                String uname = sharedpreferences.getString(Common.u_name, "");
                String password = sharedpreferences.getString(Common.password, "");
                String type = sharedpreferences.getString(Common.getType, "");


                Common.mobileno= sharedpreferences.getString(Common.mobile, "");
              //  Toast.makeText(getApplicationContext(),Common.mobileno,Toast.LENGTH_LONG).show();
//                if (!uname.isEmpty() && !password.isEmpty() && !type.isEmpty()) {
//
//
//                    if (Common.Type.equalsIgnoreCase("User")) {
//
//                        startActivity(new Intent(splashscreen.this, UserDashboard.class));
//                        finish();
//                    } else {
//
//                        Intent i = new Intent(splashscreen.this, AdminDashboard.class);
//                        startActivity(i);
//                        finish();
//
//                    }
//
//                } else {

                    Intent i = new Intent(splashscreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
          //  }
}

            // close this activity

        }, SPLASH_TIME_OUT);
    }

}