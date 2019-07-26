package com.example.pingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.Ping;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {
    TextView tv_hostname_dp,tv_address_dp,min_speed;
    private TextView mlog;
    Button btn_ping,btn;

    DatabaseReference reference;
    long[] speed = new long[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_address_dp = findViewById(R.id.tv_address_dp);
        tv_hostname_dp = findViewById(R.id.tv_hostname_dp);
        btn = findViewById(R.id.button);
        min_speed = findViewById(R.id.tv_min_speed);
        btn_ping = findViewById(R.id.btn_ping);
        mlog = findViewById(R.id.log);
        mlog.setMovementMethod(new ScrollingMovementMethod());

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
        btn_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.SERIAL_EXECUTOR.execute(new PingRunnable(tv_address_dp.getText().toString()));
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Arrays.sort(speed);
//                Toast.makeText(getApplicationContext(),Long.toString(speed[0]),Toast.LENGTH_LONG).show();
//            }
//        });


    }
    class PingRunnable implements Runnable {
        final private StringBuilder mSb = new StringBuilder();
        final private String mHost;
        final private Runnable textSetter = new Runnable() {

            @Override
            public void run() {
                mlog.setText(mSb.toString());
//                min_speed.setText(Long.toString(speed[0]));
            }

        };

        public PingRunnable(final String host) {
            mHost = host;
        }

        @Override
        public void run() {
            try{
                final InetAddress dest = InetAddress.getByName(mHost);
                final Ping ping = new Ping(dest, new Ping.PingListener() {
                    @Override
                    public void onPing(long timeMs, int index) {
                        appendMessage("#" + index +" ms: "+timeMs + " ip: "+ dest.getHostAddress(),null);
                        speed[index] = timeMs;
//                        Arrays.sort(speed);
                    }

                    @Override
                    public void onPingException(Exception e, int count) {
                        appendMessage("#" + count + " ip: "+ dest.getHostAddress(),e);
                    }

                });
                ping.run();



            }catch (UnknownHostException e) {
                e.printStackTrace();
            }


        }
        private void appendMessage(final String message,final Exception e){
            Log.d("PING",message,e);
            mSb.append(message);
            if (e != null){
                mSb.append(" Error: ");
                mSb.append(e.getMessage());
            }
            mSb.append('\n');
            runOnUiThread(textSetter);
        }
    }

}
