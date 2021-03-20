package com.example.fitnessaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginOrRegister extends AppCompatActivity {

    Button login, account;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        fAuth= FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!=null)
        {
            Intent intent= new Intent(LoginOrRegister.this, FirstPage.class);
            startActivity(intent);
        }


        getSupportActionBar().hide();
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putInt("tf",
                Integer.parseInt(
                        String.valueOf(1)));
        myEdit.commit();

        login= findViewById(R.id.login);
        account= findViewById(R.id.account);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginOrRegister.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginOrRegister.this, Register.class);
                startActivity(intent);
            }
        });

    }
}