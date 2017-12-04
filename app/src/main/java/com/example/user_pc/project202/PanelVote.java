package com.example.user_pc.project202;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by User-PC on 12/4/2017.
 */

public class PanelVote extends AppCompatActivity implements ItemClickListener{

    private String Act_ID;
    private RecyclerView recView;
    private TextView txt_selected;
    private Button btn_vote;
    private Button btn_back;
    private PanelVoteAdapter adpt;
    private List<Panels> panels;
    private String SelectedID = null;
    private SessionManager Session;
    private String CurrentUserID = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_panel_recycler_view);
        Intent i = getIntent();
        Act_ID = i.getExtras().getString("activity_id");

        setTitle("Candidate Selection (#" + String.valueOf(Act_ID) + ")");

        recView = (RecyclerView) findViewById(R.id.vote_panel_recycler);
        txt_selected = (TextView) findViewById(R.id.voted_cand_view);
        btn_back = (Button) findViewById(R.id.btn_vote_back);
        btn_vote = (Button) findViewById(R.id.btn_vote);

        Session = new SessionManager(getApplicationContext());
        List<String> Current = Session.getUserDetails();
        CurrentUserID = Current.get(0);

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_grey));
        recView.addItemDecoration(divider);

        Panel_Lab panel = new Panel_Lab(Act_ID);
        if(panel.getCandidate_Panel()==null){
            Toast.makeText(this, "Unable get data, connection error!", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        panels = panel.getCandidate_Panel();
        adpt = new PanelVoteAdapter(panels, this);
        recView.setAdapter(adpt);
        adpt.SetClickListener(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackBtnClicked(view);
            }
        });
        btn_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoteBtnClicked(view);
            }
        });
    }

    @Override
    public void onClick(View v, int pos){
        final Panels selected = panels.get(pos);
        this.SelectedID = selected.getCandidate_ID();
        txt_selected.setText("Selected Candidate: " + selected.getCandidate_ID() + " - " + selected.getCandidate_Name());
    }

    private void BackBtnClicked(View v){
        finish();
    }

    private void VoteBtnClicked(View v){
        if(SelectedID == null){
            Toast.makeText(this, "Please select a candidate to vote!", Toast.LENGTH_LONG).show();
        }
        else{
            ConfirmDialog();
        }
    }

    private void ConfirmDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure want to continue? If voted can't be undone.");

        dialog.setCancelable(true);
        dialog.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        PanelsProcess process = new PanelsProcess(Act_ID, CurrentUserID, SelectedID);
                        process.ProcessVote();

                        Context ctx = PanelVote.this;

                        Toast.makeText(ctx, "Candidate Vote Successfully.", Toast.LENGTH_LONG).show();

                        dialog.dismiss();

                        ((Activity) ctx).finish();
                    }
                });

        dialog.setNegativeButton(
                "Please Wait",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog alert = dialog.create();
        alert.show();
    }

}
