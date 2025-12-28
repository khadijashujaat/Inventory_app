package com.example.sqldemo2;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Signup_activity extends AppCompatActivity {

    EditText su_email, su_password, su_confirm;
    Button signup;
    TextView to_login;
    DBHelper2 db_helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_layout);

        su_email=(EditText)findViewById(R.id.signup_email);
        su_password=(EditText)findViewById(R.id.signup_password);
        su_confirm=(EditText) findViewById(R.id.signup_confirm);
        to_login=(TextView) findViewById(R.id.loginRedirectText);
        signup=(Button) findViewById(R.id.signup_button);
        db_helper=new DBHelper2(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = su_email.getText().toString();
                String password = su_password.getText().toString();
                String confirm = su_confirm.getText().toString();

                if ((email == "") || (password == "") || (confirm == ""))
                    Toast.makeText(Signup_activity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(confirm)) {
                        Boolean checkuser = db_helper.checkEmail(email);
                         if (checkuser == false) {
                            Boolean insert = db_helper.insertData(email, password);
                            if (insert == true) {
                                Toast.makeText(Signup_activity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                            }else{
                                Toast.makeText(Signup_activity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                             Toast.makeText(Signup_activity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                         }

                    } else    {
                        Toast.makeText(Signup_activity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), Login_activity.class);
                startActivity(intent);
            }

        });

        }
    }