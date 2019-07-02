package com.example.pingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputDataActivity extends AppCompatActivity {
    EditText et_hostname,et_address;
    Button btn_upload;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        et_hostname = findViewById(R.id.et_hostname);
        et_address = findViewById(R.id.et_address);
        btn_upload = findViewById(R.id.btn_upload);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_upload.setEnabled(false);
                btn_upload.setText("Loading ...");

                //saving to firebase
                reference = FirebaseDatabase.getInstance().getReference().child("Network").child(et_hostname.getText().toString());
                reference.getRef().child("hostname").setValue(et_hostname.getText().toString());
                reference.getRef().child("address").setValue(et_address.getText().toString());

                startActivity(new Intent(InputDataActivity.this,MainActivity.class));

            }
        });
    }
}
