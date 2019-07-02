package com.example.pingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn_plus;
    RecyclerView recyclerView;
    ArrayList<NetworkCard> list;
    NetworkAdapterCard networkAdapterCard;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvData);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        list = new ArrayList<NetworkCard>();

        btn_plus = findViewById(R.id.btn_add_menu);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InputDataActivity.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Network");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    NetworkCard card = dataSnapshot1.getValue(NetworkCard.class);
                    list.add(card);
                }
                networkAdapterCard = new NetworkAdapterCard(list);
                recyclerView.setAdapter(networkAdapterCard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
