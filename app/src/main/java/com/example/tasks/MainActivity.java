package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter,adapter1;
    private List<RunningItem> listItems,myList;
    DatabaseReference mRef;
    private EditText search_edt_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        search_edt_txt = findViewById(R.id.search_data);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        myList = new ArrayList<>();
        mRef= FirebaseDatabase.getInstance().getReference("Shops");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RunningItem com = postSnapshot.getValue(RunningItem.class);
                    listItems.add(com);
                }
                adapter = new RunningAdapter1(MainActivity.this, listItems);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        search_edt_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(String.valueOf(charSequence));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void search(String string){

        ArrayList<RunningItem> myList= new ArrayList<>();
        for (RunningItem object:listItems){
            String[] words = object.getTags().split(",");
            for(int i=0;i<words.length;i++){
                if(words[i].toLowerCase().contains(string.toLowerCase())){
                    myList.add(object);
                }
            }
//            if(object.getTags().toLowerCase().contains(string.toLowerCase())){
//                myList.add(object);
//            }
        }
        adapter1=new RunningAdapter1(MainActivity.this,myList);
        recyclerView.setAdapter(adapter1);
    }
}