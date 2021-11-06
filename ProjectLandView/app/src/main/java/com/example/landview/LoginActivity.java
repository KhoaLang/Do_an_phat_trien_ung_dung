package com.example.landview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextemailLogin,editTextpassLogin;
    private TextView txtForgot,txtSignupnow;
    private Button btnLogin;
    private ImageView fbImage,ggImage;
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //ánh xạ view
        editTextemailLogin = findViewById(R.id.edtEmail_login);
        editTextpassLogin = findViewById(R.id.edtPass_login);
        txtForgot = findViewById(R.id.txtForgot_password);
        txtSignupnow = findViewById(R.id.txtSignupNow);
        btnLogin = findViewById(R.id.btn_Login);
        fbImage = findViewById(R.id.facebook_login);
        ggImage = findViewById(R.id.gg_login);

        //gọi instance của class FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("MODE_LOGIN",MODE_PRIVATE);
        //lấy ra
        editTextemailLogin.setText(sharedPreferences.getString("Email",""));
        editTextpassLogin.setText(sharedPreferences.getString("Password",""));

        //thay đổi label action bar
        getSupportActionBar().setTitle("Login");

        //sét sự kiện button
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CheckData();
//            }
//        });
        //sự kiện textview sign up now
        txtSignupnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //sự kiện quên mật khẩu:
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(intent);
            }
        });
        //sự kiện đăng nhập fb,gg chưa làm.
        //đăng nhập chay
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckData()){
                    login();
                }
            }
        });
    }

    private void login() {
        String email = editTextemailLogin.getText().toString();
        String password = editTextpassLogin.getText().toString();

        if(TextUtils.isEmpty(email)){
            editTextemailLogin.setError("Email section can't be empty");
            editTextemailLogin.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            editTextpassLogin.setError("Password section can't be empty");
            editTextpassLogin.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credential", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean CheckData() {
        //kiểm tra input người dùng nhập vào
        String textEmail = editTextemailLogin.getText().toString();
        String textPass = editTextpassLogin.getText().toString();
//        if(textEmail.length()==0 || textPass.length() ==0)
//        {
//            Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            Toast.makeText(this,"Login succesfull",Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
//            ///SHAREREFERENCES
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("Email",textEmail);
//            editor.putString("Password",textPass);
//            editor.apply();
//        }
        if(TextUtils.isEmpty(textEmail)){
            editTextemailLogin.setError("Email section can't be empty");
            editTextemailLogin.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(textPass)){
            editTextpassLogin.setError("Password section can't be empty");
            editTextpassLogin.requestFocus();
            return false;
        }
        return true;

    }

}
