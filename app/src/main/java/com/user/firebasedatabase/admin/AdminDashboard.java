package com.user.firebasedatabase.admin;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.user.firebasedatabase.Profile;
import com.user.firebasedatabase.R;
import com.user.firebasedatabase.UserModule.UserDashboard;

public class AdminDashboard extends AppCompatActivity {

    private Menu menu;
    CardView advisor, cropdetails, viewArgo, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Admin Dashboard");
        profile = findViewById(R.id.profile);
        advisor = findViewById(R.id.advisor);
        CardView viewCrop = findViewById(R.id.viewCrop);
        CardView viewArgo = findViewById(R.id.viewArgo);
        viewArgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, ViewAdvisor.class));

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminDashboard.this, Profile.class);

                startActivity(i);
            }
        });

        viewCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminDashboard.this, ViewCropDetails.class));

            }
        });
        cropdetails = findViewById(R.id.cropdetails);
        cropdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, AddCrop.class));
            }
        });
        advisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, AddAdvisor.class));
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
       //. hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       if (id == R.id.action_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }
}
