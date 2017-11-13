package com.example.bartosz.databaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPreferencesManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewUsername = (TextView) findViewById(R.id.Username);
        textViewEmail = (TextView) findViewById(R.id.Email);

        textViewEmail.setText(SharedPreferencesManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPreferencesManager.getInstance(this).getUsername());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.menuLogout:
                 SharedPreferencesManager.getInstance(this).logOut();
                 finish();
                 startActivity(new Intent(this, LoginActivity.class));
             break;
        }
        return true;
    }
}
