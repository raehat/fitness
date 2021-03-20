package com.example.fitnessaf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.Arrays;
import java.util.List;

public class Register extends AppCompatActivity {

    EditText editTextPhone;
    CountryCodePicker ccp;
    String number;
    EditText name, email;
    TextView city, pinn;
    public String codeSent;
    String CITY, PIN;
    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    List<String> phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("REGISTER");
        fstore= FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        Button button3 = findViewById(R.id.button4);
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        editTextPhone = findViewById(R.id.no);
        ccp = findViewById(R.id.ccp);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtp();
            }
        });
        /*overridePendingTransition(R.anim.slide_down, R.anim.slide_down1);*/
    }

    private void getOtp() {
        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Just a moment");
        progressDialog.show();
        number = "+" + ccp.getFullNumber() + editTextPhone.getText().toString();

        final DocumentReference documentReference= fstore.collection("phone").document("phone");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String mobile=documentSnapshot.getString("phone");
                phones= Arrays.asList(mobile.split(",",0));
                if (phones.contains(number))
                {
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), EnterOTP.class);
                    intent.putExtra("temp", number);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}