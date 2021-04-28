package com.example.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RunningAdapter2 extends RecyclerView.Adapter<RunningAdapter2.ViewHolder> {
    private List<RunningItem2> listItems;
    private Context context;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    public RunningAdapter2(Context context, List<RunningItem2> listItems) {
        this.listItems = listItems;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mAuth= FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final RunningItem2 listItem=listItems.get(position);
        Picasso.with(context).load(listItem.getOfferimg()).into(holder.offerimg);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView offerimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerimg = itemView.findViewById(R.id.offersimg);
        }
    }
}
