package com.example.landview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextemailLogin,editTextpassLogin;
    private TextView txtForgot,txtSignupnow;
    private Button btnLogin;
    private ImageView fbImage,ggImage;
    SharedPreferences sharedPreferences;

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

        sharedPreferences = getSharedPreferences("MODE_LOGIN",MODE_PRIVATE);
        //lấy ra
        editTextemailLogin.setText(sharedPreferences.getString("Email",""));
        editTextpassLogin.setText(sharedPreferences.getString("Password",""));

        //sét sự kiện button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckData();
            }
        });
        //sự kiện textview sign up now
        txtSignupnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //sự kiện quên mật khẩu: chưa làm design.
        //sự kiện đăng nhập fb,gg chưa làm.
    }

    private void CheckData() {
        //kiểm tra input người dùng nhập vào
        String textEmail = editTextemailLogin.getText().toString();
        String textPass = editTextpassLogin.getText().toString();
        if(textEmail.length()==0 || textPass.length() ==0)
        {
            Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Login succesfull",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            ///SHAREREFERENCES
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Email",textEmail);
            editor.putString("Password",textPass);
            editor.apply();
        }
        ///kiểm tra dữ liệu trên server
    }
}
