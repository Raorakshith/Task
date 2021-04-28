package com.example.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RunningAdapter1 extends RecyclerView.Adapter<com.example.tasks.RunningAdapter1.ViewHolder> {
    private List<RunningItem> listItems;
    private Context context;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    public RunningAdapter1(Context context, List<RunningItem> listItems) {
        this.listItems = listItems;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mAuth= FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final RunningItem listItem=listItems.get(position);
        holder.shopnames.setText(listItem.getShopname());
        holder.shopratings.setText(listItem.getShopratings());
        holder.shoptypes.setText(listItem.getShoptype());
        holder.shopaddress.setText(listItem.getShopaddress());
        Picasso.with(context).load(listItem.getShopimages()).into(holder.shopimg);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("shop code",listItem.getShopcode());
                editor.commit();
                context.startActivity(new Intent(context,ShopDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView shopimg;
        public TextView shopnames;
        public TextView shopratings;
        public TextView shoptypes;
        public TextView shopaddress;
        public ImageView relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopnames=itemView.findViewById(R.id.shopname);
            shoptypes=itemView.findViewById(R.id.shoptype);
            shopaddress=itemView.findViewById(R.id.shopaddresses);
            shopratings=itemView.findViewById(R.id.ratings);
            shopimg = itemView.findViewById(R.id.shopimage);
            relativeLayout=itemView.findViewById(R.id.clkimg);
        }
    }
}
