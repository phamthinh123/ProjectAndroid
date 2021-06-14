package com.example.phamxuanthinh_b17dcat175_btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordActivity extends AppCompatActivity {
    private EditText edtPassword,edtPasswordConfirm;

    private Button btnUpdate;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        initView();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=edtPassword.getText().toString();
                String pc=edtPasswordConfirm.getText().toString();
                if(p.isEmpty()){
                    edtPassword.setError("Password không được để trống");
                    return;
                }
                if(p.length()<6){
                    edtPassword.setError("Password phải lớn hơn hoặc bằng 6 ký tự");
                    return;
                }
                if(pc.isEmpty()){
                    edtPasswordConfirm.setError("PasswordConfirm không được để trống");
                    return;
                }
                if(pc.length()<6){
                    edtPasswordConfirm.setError("PasswordConfirm phải lớn hơn hoặc bằng 6 ký tự");
                    return;
                }
                if(!pc.equals(p)){
                    edtPasswordConfirm.setError("Hai mật khẩu cần phải đồng nhất");
                    return;
                }
                muser.updatePassword(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordActivity.this,
                                    "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            String error=task.getException().toString();
                            Toast.makeText(PasswordActivity.this,
                                    "Đổi mật khẩu thất bại: "+error, Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }
    private void initView() {
        btnUpdate=findViewById(R.id.btnUpdate);

        edtPassword=findViewById(R.id.edtPassword);
        edtPasswordConfirm=findViewById(R.id.edtPasswordConfirm);

    }
}


