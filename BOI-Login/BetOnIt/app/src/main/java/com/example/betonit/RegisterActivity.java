package com.example.betonit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
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
import com.parse.SignUpCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegisterActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText passwordView;
    private EditText passCheckView;
    private EditText email;
    private EditText user_First;
    private EditText user_Last;
    private EditText user_DOB;
    private EditText user_Phone;
    private EditText user_Addr;
    private EditText user_Addr2;
    private EditText user_City;
    private EditText user_State;
    private EditText user_ZIP;
    private EditText user_Country;
    private EditText user_Bet_Favorite_Types;
    Date dateObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameView = findViewById(R.id.etUsername);
        passwordView = findViewById(R.id.etPasscode);
        passCheckView = findViewById(R.id.etPasscodeCheck);
        user_First = findViewById(R.id.etFirstName);
        user_Last = findViewById(R.id.etLastName);
        email = findViewById(R.id.etEmail);
        user_DOB = findViewById(R.id.etDOB);
        user_Phone = findViewById(R.id.etPhone);
        user_Addr = findViewById(R.id.etAddr);
        user_Addr2 = findViewById(R.id.etAddr2);
        user_City = findViewById(R.id.etCity);
        user_State = findViewById(R.id.etState);
        user_ZIP = findViewById(R.id.etZIP);
        user_Country = findViewById(R.id.etCountry);

        final Button btnSignUp = findViewById(R.id.btnRegister);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validating the log in data
                boolean validationError = false;

                StringBuilder validationErrorMessage = new StringBuilder("Please, insert ");
                if (usernameView.getText().toString().isEmpty()) {
                    validationError = true;
                    validationErrorMessage.append("an username");
                }
                if (passwordView.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("a password");
                }
                if (passCheckView.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("your password again");
                }

                if (email.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("your email");
                }

                if (user_DOB.getText().toString().isEmpty()) {
                    if (validationError) {
                        validationErrorMessage.append(" and ");
                    }
                    validationError = true;
                    validationErrorMessage.append("your date of birth");
                }

                else {
                    if (!(passwordView.getText().toString().matches(passCheckView.getText().toString()))) {
                        if (validationError) {
                            validationErrorMessage.append(" and ");
                        }
                        validationError = true;
                        validationErrorMessage.append("the same password twice.");
                    }
                }
                validationErrorMessage.append(".");

                if (validationError) {
                    Toast.makeText(RegisterActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                //Setting up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(RegisterActivity.this);
                dlg.setTitle("Please, wait a moment.");
                dlg.setMessage("Signing up...");
                dlg.show();

                ParseUser user = new ParseUser();
                user.setUsername(usernameView.getText().toString());
                user.setPassword(passwordView.getText().toString());
                user.setEmail(email.getText().toString());
                user.put("user_First", user_First.getText().toString());
                user.put("user_Last", user_Last.getText().toString());

                try {
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    dateObject = formatter.parse(user_DOB.getText().toString());
                    user.put("user_DOB", dateObject);

                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                user.put("user_Phone", user_Phone.getText().toString());
                user.put("user_Addr", user_Addr.getText().toString());
                user.put("user_Addr2", user_Addr2.getText().toString());
                user.put("user_City", user_City.getText().toString());
                user.put("user_State", user_State.getText().toString());
                user.put("user_ZIP", user_ZIP.getText().toString());
                user.put("user_Country", user_Country.getText().toString());
                user.put("user_Status", "Active");

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            dlg.dismiss();
                            alertDisplayer("Successful Login","Welcome " + usernameView.getText().toString() + "!");

                        } else {
                            dlg.dismiss();
                            ParseUser.logOut();
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(RegisterActivity.this, BettorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

}