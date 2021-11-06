package com.example.landview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextUsername,editTextEmail,editTextPass,editTextTypePass;
    private CheckBox checkBoxPolicy;
    private Button buttonCreate;
    private FirebaseAuth mAuth;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        //ánh xạ view
        editTextUsername = findViewById(R.id.EdtUser);
        editTextEmail = findViewById(R.id.Edtemail);
        editTextPass = findViewById(R.id.EdtPassword);
        editTextTypePass = findViewById(R.id.EdtTypePass);
        checkBoxPolicy = findViewById(R.id.cbPolicy);
        buttonCreate = findViewById(R.id.btnCreateAccount);

        //gọi instance của class FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //thay đổi label action bar
        getSupportActionBar().setTitle("Register");

        //xử lý sự kiện
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData()){
                    createUser();
                }
            }
        });
    }

    private void createUser() {
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Sign Up completed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkData() {
        String textUser = editTextUsername.getText().toString();
        String textEmail = editTextEmail.getText().toString();
        String textPass = editTextPass.getText().toString();
        String textTypePass = editTextTypePass.getText().toString();
        Boolean isCheck = checkBoxPolicy.isChecked();
//        if(textUser == null || textEmail==null||textPass==null||textTypePass==null||isCheck==false)
//        {
//           Toast.makeText(this,"Please enter full information",Toast.LENGTH_LONG).show();
//        }
//        if(!checkValidEmail()||!checkPassword())
//        {
//            Toast.makeText(this,"Invalid Email or Password!Type Again",Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//           Toast.makeText(this,"Sign up successful",Toast.LENGTH_LONG).show();
//           //chuyển đến Main Activity
//            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
        if(TextUtils.isEmpty(textUser)){
            editTextUsername.setError("Username section can't be empty");
            editTextUsername.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(textEmail)){
            editTextEmail.setError("Email section can't be empty");
            editTextEmail.requestFocus();
            return false;
        }else if(!checkValidEmail()) {
            editTextEmail.setError("Wrong email pattern!!");
            editTextEmail.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(textPass)){
            editTextPass.setError("Password section can't be empty");
            editTextPass.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(textTypePass)){
            editTextPass.setError("Password section can't be empty");
            editTextPass.requestFocus();
            return false;
        }else if(!checkPassword()) {
            editTextTypePass.setError("Password doesn't match!!");
            editTextTypePass.requestFocus();
            return false;
        }else if(!isCheck) {
            Toast.makeText(SignUpActivity.this, "You have to accept our policies create an account!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkPassword() {
        String textPass = editTextPass.getText().toString();
        String textTypePass = editTextTypePass.getText().toString();
        if(textPass.equals(textTypePass))
        {
            return true;
        }
        return false;
    }

    private boolean checkValidEmail() {
        String textEmail = editTextEmail.getText().toString();
        String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(textEmail.matches(emailPattern))
        {
            return true;
        }
        return false;
    }
}
