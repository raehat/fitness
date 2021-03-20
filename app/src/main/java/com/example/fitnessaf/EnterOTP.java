package com.example.fitnessaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EnterOTP extends AppCompatActivity {

    String name, number, email;
    Button receiveOtp;
    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    private String userID;
    private String codeSent;
    PinView editTextCode;
    private String phoneTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        name= getIntent().getExtras().getString("name");
        number= getIntent().getExtras().getString("temp");
        phoneTemp= number;
        email= getIntent().getExtras().getString("email");
        mAuth= FirebaseAuth.getInstance();
        receiveOtp= findViewById(R.id.button2);
        editTextCode= findViewById(R.id.kk);
        fstore= FirebaseFirestore.getInstance();

        new AlertDialog.Builder(EnterOTP.this)
                .setTitle("OTP VERIFICATION!")
                .setMessage("You'll recieve OTP on "+ number +", so enter it ASAP!")
                .setNeutralButton("OK", null)
                .show();

        sendVerificationCode();

        receiveOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
    }
    private void verifySignInCode() {
        final String code = editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Getting values to store
                            //here you can open new activity
                            Toast.makeText(getApplicationContext(),
                                    "Login Successful", Toast.LENGTH_LONG).show();

                            userID= mAuth.getCurrentUser().getUid();
                            final DocumentReference documentReference= fstore.collection("users").document(userID);
                            Map<String, Object> user= new HashMap<>();
                            user.put("fname", name);
                            user.put("number", phoneTemp);
                            user.put("email", email);
                            dialog.dismiss();
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EnterOTP.this, "User profile created", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                            final DocumentReference documentReference1= fstore.collection("phone")
                                    .document("phone");
                            documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String kk= documentSnapshot.getString("phone");
                                    Map<String, Object> user1= new HashMap<>();
                                    user1.put("phone", kk + "," + phoneTemp);
                                    documentReference1.set(user1);
                                }
                            });



                            Toast.makeText(EnterOTP.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            /*SaveSharedPreference.setLoggedIn(getApplicationContext(), true);*/
                            Intent intent=new Intent(EnterOTP.this, FirstPage.class);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    public void sendVerificationCode() {


        String phone = phoneTemp;
        Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_SHORT).show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                EnterOTP.this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(EnterOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            Toast.makeText(EnterOTP.this, "Hey", Toast.LENGTH_SHORT).show();
            codeSent = s;

        }
    };
}