package com.user.firebasedatabase.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.firebasedatabase.Adapter.ArgoAdvisor.ArgoAdapter;
import com.user.firebasedatabase.Adapter.CropDetails.ViewCropMainAdapter;
import com.user.firebasedatabase.Pojo.AddCropPojo;
import com.user.firebasedatabase.Pojo.AdvisorList;
import com.user.firebasedatabase.Pojo.AdvisorPojo;
import com.user.firebasedatabase.Pojo.CropList;
import com.user.firebasedatabase.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAdvisor extends AppCompatActivity {
    private List<AdvisorPojo> addCropPojoList = new ArrayList<>();
    private List<String> addKeyList = new ArrayList<>();
    ArgoAdapter mAdapter;
    String TAG = ViewAdvisor.class.getSimpleName();
    String type = "user";
    private List<AdvisorList> cropLists = new ArrayList<>();

    RecyclerView recyclerView;
    private View view;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advisor);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("ArgoAdvisor");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("View Advisor");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        FloatingActionButton floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ViewAdvisor.this,AddAdvisor.class));
            }
        });
        if (getIntent().getStringExtra("type") != null) {
            type = getIntent().getStringExtra("type");

        } else initSwipe();


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                if (addCropPojoList.size() > 0) {
//                    addCropPojoList.clear();
//                    addKeyList.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {


                    AdvisorPojo cAd = post.getValue(AdvisorPojo.class);
                    Log.e(TAG, "Error trying to get classified ad for update " +
                            "" + cAd.getAddress());
                    addCropPojoList.add(cAd);

                    addKeyList.add(post.getKey());

                    Log.e(TAG, "Iniyan" + addCropPojoList.size() + "----" + post.getKey());
                    AdvisorList cropList = new AdvisorList(addCropPojoList, addKeyList);
                    cropLists.add(cropList);
                    Log.e(TAG, "" + cropLists.size());
                }


                runLayoutAnimation(recyclerView);

            }


            //}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error trying to get classified ad for update " +
                        "" + databaseError);
            }
        });


    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_left);

        mAdapter = new ArgoAdapter(ViewAdvisor.this, cropLists, type);
        recyclerView.setAdapter(mAdapter);

        // mAdapter.notifyDataSetChanged();


        //   adapter.notifyDataSetChanged();
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    mAdapter.removeItem(position);
                } else if (direction == ItemTouchHelper.RIGHT) {

//                    edit_position = position;
//                    alertDialog.setTitle("Edit Country");
//                    et_country.setText(countries.get(position));
//                    alertDialog.show();
                    mAdapter.addItem(cropLists.get(position),position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);


                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
