package com.example.fitnessaf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class Intro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        @SuppressLint("WrongConstant") SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_APPEND);
        int tf = sh.getInt("tf", 0);

        if (tf==1)
        {
            startActivity(new Intent(getApplicationContext(), LoginOrRegister.class));
        }

        new AlertDialog.Builder(Intro.this)
                .setTitle("Copyright©2021- VIT pop champz")
                .setMessage("All rights reserved. This app was developed by " +
                        "VIT pop champs so No part of this publication may be reproduced, distributed," +
                        " or transmitted in any form or by any means, including photocopying, recording, " +
                        "or other electronic or mechanical methods, without the prior written permission of " +
                        "the publisher, except in the case of" +
                        " brief quotations embodied in critical reviews and certain " +
                        "other noncommercial uses permitted by copyright law.")
                .setNeutralButton("Ok, I understand", null).show();


        addSlide(AppIntroFragment.newInstance(
                "Welcome...",
                "We are really thankful for you to download this app. You can either skip this intro or bother" +
                        " yourself for this lol!"
        ));
        addSlide(AppIntroFragment.newInstance(
                "...Let's get started!",
                "Well! You can buy or sell stuff using this app! Very nycccc great success!!!"
        ));
        addSlide(AppIntroFragment.newInstance(
                "Last slide lmaoo",
                "You're all done!! Now you can either login or" +
                        " create an account if you're new ehehe)"
        ));
    }

    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent= new Intent(Intro.this, LoginOrRegister.class);
        startActivity(intent);
        // Decide what to do when the user clicks on "Skip"
        finish();
    }

    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Decide what to do when the user clicks on "Done"
        Intent intent= new Intent(Intro.this, LoginOrRegister.class);
        startActivity(intent);
        finish();
    }
}