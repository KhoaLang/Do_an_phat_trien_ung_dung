package com.example.landview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextUsername,editTextEmail,editTextPass,editTextTypePass;
    private CheckBox checkBoxPolicy;
    private Button buttonCreate;
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
        //xử lý sự kiện
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEmpty();
            }
        });
    }

    private void checkDataEmpty() {
        String textUser = editTextUsername.getText().toString();
        String textEmail = editTextEmail.getText().toString();
        String textPass = editTextPass.getText().toString();
        String textTypePass = editTextTypePass.getText().toString();
        Boolean isCheck = checkBoxPolicy.isChecked();
        if(textUser == null || textEmail==null||textPass==null||textTypePass==null||isCheck==false)
        {
            Toast.makeText(this,"Please enter full information",Toast.LENGTH_LONG).show();
        }
        if(!checkValidEmail()||!checkPassword())
        {
            Toast.makeText(this,"Invalid Email or Password!Type Again",Toast.LENGTH_LONG).show();
        }
        else
        {
           Toast.makeText(this,"Sign up successful",Toast.LENGTH_LONG).show();
           //chuyển đến Main Activity
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
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
