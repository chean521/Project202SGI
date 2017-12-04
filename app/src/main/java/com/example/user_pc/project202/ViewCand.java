package com.example.user_pc.project202;

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

/**
 * Created by User-PC on 12/2/2017.
 */

public class ViewCand extends AppCompatActivity {

    private RecyclerView recView;
    private ViewCandAdapter adpt;
    private List<Candidates> Cand;
    private Button btn_back;
    private String Act_PID;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.candidate_details_recycler);

        Bundle extras = getIntent().getExtras();
        if(extras == null)
            finish();
        else
            Act_PID = extras.getString("activity_id");

        this.setTitle("Candidate Result (Act: "+Act_PID+")");

        btn_back = (Button) findViewById(R.id.cand_details_back);
        recView = (RecyclerView) findViewById(R.id.cand_details_recycler_view);

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_grey));
        recView.addItemDecoration(divider);

        Candidate_Lab c_lab = new Candidate_Lab(Integer.parseInt(Act_PID));

        if(c_lab.getCandidates()==null){
            Toast.makeText(this, "No Candidates!", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Cand = c_lab.getCandidates();
        adpt = new ViewCandAdapter(Cand, this, c_lab.getTotalVote());
        recView.setAdapter(adpt);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButtonClicked(view);
            }
        });
    }


    private void BackButtonClicked(View v){
        finish();
    }
}



