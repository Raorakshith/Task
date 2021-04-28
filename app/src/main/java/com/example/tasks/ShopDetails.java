package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShopDetails extends AppCompatActivity {
private ImageView share,img;
private String custid,lats,longts;
private LinearLayout openmap;
private TextView shname,shtype,shratings,shaddress,shmail,shnumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter,adapter1;
    private List<RunningItem2> listItems;
    DatabaseReference mRef,mRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        img = findViewById(R.id.shopimaged);
        shname = findViewById(R.id.shopnamedet);
        shtype = findViewById(R.id.shoptyped);
        shaddress = findViewById(R.id.shopaddressesd);
        shmail = findViewById(R.id.shopmail);
        shnumber = findViewById(R.id.shopcall);
        openmap = findViewById(R.id.locud);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(ShopDetails.this);
        editor=sharedPreferences.edit();
        custid = sharedPreferences.getString("shop code","ABC");
//        custid = "ABC";
        share = findViewById(R.id.shareshop);
        recyclerView = findViewById(R.id.recyclerviewoffers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        mRef= FirebaseDatabase.getInstance().getReference("Shops").child(custid).child("Offers");
        mRef1= FirebaseDatabase.getInstance().getReference("Shops").child(custid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RunningItem2 com = postSnapshot.getValue(RunningItem2.class);
                    listItems.add(com);
                }
                adapter = new RunningAdapter2(ShopDetails.this, listItems);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShopDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        openmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coor = "geo:"+lats+","+longts;
                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("geo:47.4925,19.0513"));
                intent.setData(Uri.parse(coor));
                Intent chooser = Intent.createChooser(intent,"Launch maps");
                startActivity(chooser);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLink();
            }
        });
        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             String name = snapshot.child("shopname").getValue().toString();
             String type = snapshot.child("shoptype").getValue().toString();
             String ratings = snapshot.child("shopratings").getValue().toString();
             String address = snapshot.child("shopaddress").getValue().toString();
             String shopimage = snapshot.child("shopimages").getValue().toString();
             String shopnumber = snapshot.child("number").getValue().toString();
             String shopmail = snapshot.child("mail").getValue().toString();
              lats = snapshot.child("latitude").getValue().toString();
              longts = snapshot.child("longitude").getValue().toString();
                shname.setText(name);
             shtype.setText(type);
             //shratings.setText(ratings);
             shaddress.setText(address);
             shmail.setText(shopmail);
             shnumber.setText(shopnumber);
                Picasso.with(ShopDetails.this).load(shopimage).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void createLink(){
        Log.e("main","createlink");
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/"))
                .setDomainUriPrefix("exampletask.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e("main","Long refer"+dynamicLink.getUri());
        //customerid to be passed
        createReferLink(custid);
        //manual link

    }
    public void createReferLink(String custid){
        String sharelinktext="https://exampletask.page.link/?"+
                "link=http://www.example.com/myrefer.php?custid="+custid +"-"+
                "&apn="+getPackageName()+
                "&st="+"Look Out this Shop, There are great offers"+
                "&sd="+"Click on the link";
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                //.setLongLink(dynamicLink.getUri())
                .setLongLink(Uri.parse(sharelinktext))
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e("main","short link" + shortLink);

                            Intent intent=new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);
                        } else{
                            Log.e("main","error"+task.getException());
                            // Error
                            // ...
                        }
                    }
                });

    }
}