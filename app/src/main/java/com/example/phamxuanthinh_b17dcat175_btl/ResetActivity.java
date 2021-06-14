package com.example.phamxuanthinh_b17dcat175_btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
    private EditText edtEmail;

    private Button btnReset;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        mAuth=FirebaseAuth.getInstance();
        initView();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=edtEmail.getText().toString();
                if(e.isEmpty()){
                    edtEmail.setError("Email không được để trống");
                    return;
                }
                mAuth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetActivity.this,
                                    "Kiểm tra email để reset mật khẩu", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            String error=task.getException().toString();
                            Toast.makeText(ResetActivity.this,
                                    "Reset thất bại: "+error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void initView() {
        btnReset=findViewById(R.id.btnReset);

        edtEmail=findViewById(R.id.edtEmail);

    }
}