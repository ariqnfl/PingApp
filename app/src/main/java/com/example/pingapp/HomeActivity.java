package com.example.pingapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    TextView tv_hostname_dp,tv_address_dp;
    Button btn_ping;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_address_dp = findViewById(R.id.tv_address_dp);
        tv_hostname_dp = findViewById(R.id.tv_hostname_dp);
        btn_ping = findViewById(R.id.btn_ping);

        Bundle bundle = getIntent().getExtras();
        final String nama_hostname = bundle.getString("nama_hostname");

        reference = FirebaseDatabase.getInstance().getReference().child("Network").child(nama_hostname);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv_address_dp.setText(dataSnapshot.child("address").getValue().toString());
                tv_hostname_dp.setText(dataSnapshot.child("hostname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
