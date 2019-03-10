package com.user.firebasedatabase.UserModule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.user.firebasedatabase.R;
import com.user.firebasedatabase.admin.AddCrop;
import com.user.firebasedatabase.admin.ViewCropDetails;

public class Consumer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producerdashboard);







        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Producer Dashboard");

        CardView viewCrop = findViewById(R.id.viewCrop);
      CardView  producer = findViewById(R.id.producer);
       CardView producerview = findViewById(R.id.producerview);
        producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Consumer.this, ViewCropDetails.class);
                i.putExtra("type", "NoAction");
                startActivity(i);
                //startActivity(new Intent(UserDashboard.this, ProducerAdd.class));
            }
        });
      CardView  cropdetails = findViewById(R.id.cropdetails);
        cropdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Consumer.this, AddCrop.class));
            }
        });
        viewCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Consumer.this, ViewCropDetails.class));

            }
        });
        producerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Consumer.this, com.user.firebasedatabase.UserModule.ProducerViewDetails.class);
                i.putExtra("type", "User");
                startActivity(i);

            }
        });
    }
}
