package com.example.sqldemo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sqldemo2.databinding.LoginLayoutBinding;
//import com.example.sqldemo2.databinding.SignupLayoutBinding;

public class Login_activity extends AppCompatActivity {

    EditText login_email, login_password;
    Button login;
    TextView to_signup;
    DBHelper2 db_helper;
            ;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        login_email=(EditText)findViewById(R.id.login_email);
        login_password=(EditText)findViewById(R.id.login_password) ;
        login=(Button)findViewById(R.id.login_button);
        to_signup=(TextView) findViewById(R.id.signupRedirectText);
        db_helper=new DBHelper2(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if ((email == "") || (password == ""))
                    Toast.makeText(Login_activity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                else{
                    Boolean check_userpass=db_helper.checkEmailPassword(email,password);
                    if (check_userpass==true){
                        Toast.makeText(Login_activity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login_activity.this, "Invalid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Signup_activity.class);
                startActivity(intent);
            }
        });
    }



    }
