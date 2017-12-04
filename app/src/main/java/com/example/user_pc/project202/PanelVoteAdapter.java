package com.example.user_pc.project202;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User-PC on 12/4/2017.
 */

public class PanelVoteAdapter extends RecyclerView.Adapter<PanelVoteAdapter.PanelVoteHolder>{

    private Context ctx;
    private ItemClickListener ClickListener;
    private List<Panels> Data;

    public PanelVoteAdapter(List<Panels> Data, Context ctx){
        this.ctx = ctx;
        this.Data = Data;
    }

    @Override
    public PanelVoteAdapter.PanelVoteHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.vote_panel_row, parent, false);
        PanelVoteHolder vh = new PanelVoteHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(PanelVoteHolder holder, final int position){
        Panels pn = Data.get(position);
        holder.BindCandidate(pn);
    }

    @Override
    public int getItemCount(){
        return Data.size();
    }

    public void SetClickListener(ItemClickListener clickListener){
        this.ClickListener = clickListener;
    }

    public class PanelVoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View layout;
        private TextView txt_ttl;
        private TextView txt_desc;


        public PanelVoteHolder(View v){
            super(v);
            this.layout = v;
            txt_ttl = (TextView) v.findViewById(R.id.vote_panel_ttl);
            txt_desc = (TextView) v.findViewById(R.id.vote_panel_desc);
            v.setOnClickListener(this);
        }

        public void BindCandidate(final Panels panel){
            txt_ttl.setText(Config.capitalize(panel.getCandidate_ID()) + " - " + panel.getCandidate_Name());
            txt_desc.setText("Programme: " + panel.getCandidate_Prog() + ", Description: " + panel.getExplanation());
        }

        @Override
        public void onClick(View v){
            if(ClickListener != null) ClickListener.onClick(v, getAdapterPosition());
        }

    }

}
