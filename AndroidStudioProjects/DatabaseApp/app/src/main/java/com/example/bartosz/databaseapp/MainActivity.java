package com.example.bartosz.databaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword, editTextConfirmEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;

    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding logo



        /////

        //if the user is already logged in, then go straight t
        if(SharedPreferencesManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        editTextEmail = (EditText) findViewById(R.id.Email);

        if( editTextEmail.getText().toString().length() == 0 ) {
            editTextEmail.setError("Email is required!");
        }

        editTextConfirmEmail = (EditText) findViewById(R.id.cEmail);

        if(editTextConfirmEmail.getText().toString().length() == 0){
            editTextConfirmEmail.setError("Please confirm your Email");
        }

        editTextUsername = (EditText) findViewById(R.id.Username);

        if( editTextUsername.getText().toString().length() == 0 ) {
            editTextUsername.setError("Username is required!, Has to be at least 6 characters");
        }

        editTextPassword = (EditText) findViewById(R.id.Password);

        if( editTextPassword.getText().toString().length() == 0 ) {
            editTextPassword.setError("Password is required!, Has to be at least 6 characters ");
        }

        editTextConfirmPassword = (EditText) findViewById(R.id.cPassword);
        if(editTextConfirmPassword.getText().toString().length() == 0)
        {
            editTextConfirmPassword.setError("Please confirm your password");
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);

        textViewLogin =(TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(this);
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        /*"http://192.168.216.157/Android/v1/registerUser.php"*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),
                                    jsonObject.getString("message"),
                                    Toast.LENGTH_LONG).show();

                        }catch (JSONException e){
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password",password);
                return params;

            }

        };

       NetworkRequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void validateAndRegister(){

        //editTextEmail = (EditText) findViewById(R.id.Email);
        final String email = editTextEmail.getText().toString().trim();

        final String password = editTextPassword.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern))
        {
            //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            if( editTextUsername.getText().toString().length() <= 5 )
            {
                editTextUsername.setError("The username has to be at least 6 characters!");

            }else if(editTextPassword.getText().toString().length() <=5)
            {
                editTextPassword.setError("The password has to be at least 6 characters!");

            }else if(!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()))
            {
                editTextConfirmPassword.setError("both passwords must match!!!");

            } else if(!editTextEmail.getText().toString().equals(editTextConfirmEmail.getText().toString()))
            {
                editTextConfirmEmail.setError("The emails must match!!!");
            }

            else{

                registerUser();
                resetTextFields();
            }
        }
        else if(!email.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }



    }//end of Validate and Reg

    public void resetTextFields() {
        editTextUsername.setText("");
        editTextEmail.setText("");
        editTextConfirmEmail.setText("");
        editTextConfirmPassword.setText("");
        editTextPassword.setText("");
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){

           validateAndRegister();


        }
        if (v == textViewLogin){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
