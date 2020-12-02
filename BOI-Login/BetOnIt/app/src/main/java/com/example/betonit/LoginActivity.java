package com.example.betonit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.LogInCallback;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null)
        {
            checkAdminUser();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPasscode);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Login User to Bet On It
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Validating the log in data
                boolean validationError = false;

                StringBuilder validationErrorMessage = new StringBuilder("Please, insert ");
                if (username.isEmpty()) {
                    validationError = true;
                    validationErrorMessage.append("an username");
                }
                if (password.isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("a password");
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                // If the fields are valid, login
                loginUser(username, password);
            }
        });
        
        // Send User To Registration Screen
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterActivity();
            }
        });
    }

    private void loginUser(String username, String password)
    {
        ParseUser.logInInBackground(username, password, new LogInCallback()
        {
            @Override
            public void done(ParseUser parseUser, ParseException e)
            {
                if (e != null)
                {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Unable to log in. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check to see if the user if an admin or standard bettor
                checkAdminUser();
            }
        });
    }

    private void checkAdminUser() {
        final ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("isAdmin", true);
        query.whereEqualTo("username", parseUser.getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e == null)
                {
                    if(users.isEmpty())
                    {
                        Log.d(TAG, "There is no admin user with the username " + parseUser.getUsername().toString());
                        goBettorActivity();
                    }
                    else
                    {
                        Log.d(TAG, "Admin user: " + parseUser.getUsername().toString() + " : FOUND");
                        goAdminActivity();
                    }
                }
            }
        }
        );
    }

    private void goBettorActivity() {
        Intent i = new Intent(this, BettorActivity.class);
        startActivity(i);
        finish();
    }

    private void goRegisterActivity() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void goAdminActivity() {
        Intent i = new Intent(this, AdminActivity.class);
        startActivity(i);
        finish();
    }

//    private void alertDisplayer(String title,String message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        // don't forget to change the line below with the names of your Activities
////                        Intent intent = new Intent(SignUpActivity.this, LogoutActivity.class);
//                        Intent intent = new Intent(LoginActivity.this, fck.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                });
//        AlertDialog ok = builder.create();
//        ok.show();
//    }

}