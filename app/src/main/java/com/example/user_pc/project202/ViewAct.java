package com.example.user_pc.project202;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class ViewAct extends AppCompatActivity implements ItemClickListener{
    private RecyclerView recView;
    private ViewActAdapter adpt;
    private List<Activity> Act;
    private Button back_btn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_activity_recycler);
        setTitle("View Activity (Available)");

        back_btn = (Button) findViewById(R.id.view_act_back_btn);
        recView = (RecyclerView) findViewById(R.id.view_act_data);

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_grey));
        recView.addItemDecoration(divider);

        Activity_Lab ab = new Activity_Lab();
        if(ab.getActivity()==null){
            Toast.makeText(this, "Unable get data, connection error!", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Act = ab.getActivity();

        adpt = new ViewActAdapter(Act, this);
        recView.setAdapter(adpt);
        adpt.SetClickListener(this);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view, int position){
        final Activity Acts = Act.get(position);
        Intent i = new Intent(this, ViewDetail.class);
        i.putExtra("activity_id", Acts.getPid());
        startActivity(i);
    }
}
