package com.example.tasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;


public class SplahScreen extends AppCompatActivity {
FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah_screen);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplahScreen.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
             new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(SplahScreen.this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                // Get deep link from result (may be null if no link is found)
                                Uri deepLink = null;
                                if (pendingDynamicLinkData != null) {
                                    deepLink = pendingDynamicLinkData.getLink();
                                    Log.e("TAG","my referlink"+deepLink.toString());
                                    String referlink=deepLink.toString();
                                    try {
                                        referlink=referlink.substring(referlink.lastIndexOf("=")+1);
                                        Log.e("TAG","substring"+referlink);
                                        String custid=referlink.substring(0,referlink.indexOf("-"));
                                        editor.putString("shop code",custid);
                                        editor.commit();
                                        Intent intent = new Intent(com.example.tasks.SplahScreen.this, ShopDetails.class);
                                        com.example.tasks.SplahScreen.this.startActivity(intent);
                                        com.example.tasks.SplahScreen.this.finish();
                                        Log.e("TAG","custid"+custid);
                                    }catch (Exception e){
                                        Log.e("TAG","error"+e.toString());
                                        // Toast.makeText(getContext(), "You faced", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Intent intent = new Intent(com.example.tasks.SplahScreen.this, MainActivity.class);
                                    com.example.tasks.SplahScreen.this.startActivity(intent);
                                    com.example.tasks.SplahScreen.this.finish();
                                }


                                // Handle the deep link. For example, open the linked
                                // content, or apply promotional credit to the user's
                                // account.
                                // ...

                                // ...
                            }
                        })
                        .addOnFailureListener(SplahScreen.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "getDynamicLink:onFailure", e);
                            }
                        });
//                    Intent intent = new Intent(com.example.tasks.SplahScreen.this, MainActivity.class);
//                    com.example.tasks.SplahScreen.this.startActivity(intent);
//                    com.example.tasks.SplahScreen.this.finish();

            }
        },1000);
    }

}
