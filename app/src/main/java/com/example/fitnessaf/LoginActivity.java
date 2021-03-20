package com.example.fitnessaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText number;
    Button otp;
    CountryCodePicker countryCodePicker;
    FirebaseFirestore fstore;
    String phoneNumbers;
    private List<String> pn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        number= findViewById(R.id.number);
        countryCodePicker= findViewById(R.id.countryCodePicker);
        otp= findViewById(R.id.otp);
        fstore= FirebaseFirestore.getInstance();

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference= fstore.collection("phone").document("phone");
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String n="+" + countryCodePicker.getFullNumber() + number.getText().toString();
                        phoneNumbers= documentSnapshot.getString("phone");
                        pn= Arrays.asList(phoneNumbers.split(",", 0));
                        if (!pn.contains(n))
                        {
                            Toast.makeText(LoginActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent= new Intent(getApplicationContext(), OTPforLogin.class);
                            intent.putExtra("number", n);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}