package com.example.phamxuanthinh_b17dcat175_btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        initView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=edtEmail.getText().toString();
                String p=edtPassword.getText().toString();
                if(e.isEmpty()){
                    edtEmail.setError("Email không được để trống");
                    return;
                }
                if(p.isEmpty()){
                    edtPassword.setError("Password không được để trống");
                    return;
                }
                if(p.length()<6){
                    edtPassword.setError("Password phải lớn hơn hoặc bằng 6 ký tự");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                            startActivity(intent);
                        } else {
                            String error=task.getException().toString();
                            Toast.makeText(RegisterActivity.this,
                                    "Đăng ký thất bại: "+error, Toast.LENGTH_SHORT).show()  ;
                        }
                    }
                });
            }
        });
    }
    private void initView() {
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
    }
}