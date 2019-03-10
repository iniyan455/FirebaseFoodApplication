package com.user.firebasedatabase.UserModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.user.firebasedatabase.Adapter.Producer.ProducerViewDetails;
import com.user.firebasedatabase.Common.Common;
import com.user.firebasedatabase.LoginActivity;
import com.user.firebasedatabase.PaymentActivity;
import com.user.firebasedatabase.Pojo.User;
import com.user.firebasedatabase.Profile;
import com.user.firebasedatabase.R;
import com.user.firebasedatabase.admin.AddAdvisor;
import com.user.firebasedatabase.admin.AdminDashboard;
import com.user.firebasedatabase.admin.ViewAdvisor;
import com.user.firebasedatabase.admin.ViewCropDetails;


public class UserDashboard extends AppCompatActivity {

    private Menu menu;
    SharedPreferences sharedpreferences;
    CardView argodetails, consumer, producerview, weather, producer, profile;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);

        sharedpreferences = getSharedPreferences("private",
                Context.MODE_PRIVATE);

        editor = sharedpreferences.edit();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("User Dashboard");

        argodetails = findViewById(R.id.agrodetails);

        consumer = findViewById(R.id.consumer);
        weather = findViewById(R.id.weather);
        profile = findViewById(R.id.profile);

        producerview = findViewById(R.id.producerview);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, Profile.class);

                startActivity(i);
            }
        });

        CardView viewCrop = findViewById(R.id.viewCrop);


        viewCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserDashboard.this, ViewCropDetails.class));

            }
        });
        // viewCrop


        producerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserDashboard.this, com.user.firebasedatabase.UserModule.ProducerViewDetails.class);
                i.putExtra("type", "User");
                startActivity(i);

            }
        });
        producer = findViewById(R.id.producer);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, com.user.firebasedatabase.UserModule.Weather.MainActivity.class));
            }
        });
        sharedpreferences = getSharedPreferences("private",
                Context.MODE_PRIVATE);

        argodetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(UserDashboard.this, ViewAdvisor.class);
                i.putExtra("type", "Admin");
                startActivity(i);
            }
        });

        consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, com.user.firebasedatabase.UserModule.ProducerViewDetails.class);
                i.putExtra("type", "consumer");
                startActivity(i);
            }
        });
        producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View view = LayoutInflater.from(UserDashboard.this).inflate(R.layout.pinlock, null);
                MaterialEditText username = (MaterialEditText) view.findViewById(R.id.username);
                MaterialEditText pin = (MaterialEditText) view.findViewById(R.id.pin);
                Button submit = view.findViewById(R.id.submit);


                String paasword_check = sharedpreferences.getString(Common.epassword, "");

                if (paasword_check.equals("") || paasword_check.equals("epass")) {
                    pin.setFloatingLabelText("Create  Your 4 digit Pin");
                } else pin.setFloatingLabelText("Enter Your 4 digit Pin");
                username.setText(Common.Username);

                AlertDialog.Builder dialog = new AlertDialog.Builder(UserDashboard.this);
                dialog.setView(view);
                //  dialog.show();
                AlertDialog dialog1 = dialog.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!pin.getText().toString().isEmpty()) {

                            if (paasword_check.equals(pin.getText().toString())){
                                Intent i = new Intent(UserDashboard.this, Consumer.class);
                                startActivity(i);
                                dialog1.dismiss();

                            }
                            else   if (paasword_check.equals("") || paasword_check.equals("epass")) {

                                editor.putString(Common.epassword, pin.getText().toString());
                                editor.apply();
                                Intent i = new Intent(UserDashboard.this, Consumer.class);
                                startActivity(i);
                                dialog1.dismiss();
                            }else{
                                Toast.makeText(getApplicationContext(),"Pin Wrong",Toast.LENGTH_SHORT).show();
                            }

                                                 }
                    }
                });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        this.menu = menu;
        //hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {


            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void hideOption(int id) {
//        MenuItem item = menu.findItem(id);
//        item.setVisible(false);
//    }
//
//    private void showOption(int id) {
//        MenuItem item = menu.findItem(id);
//        item.setVisible(true);
//    }
}
