package com.example.phamxuanthinh_b17dcat175_btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    private EditText addtitle,adddes,adddate,addhour;
    private Button btnDelete,btnEdit,btnPick,btnPick2;
    private int mnam, mthang, mngay,mgio,mphut;

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        btnPick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mngay = calendar.get(Calendar.DAY_OF_MONTH);
                mthang = calendar.get(Calendar.MONTH);
                mnam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        calendar.set(year, month, day);
                        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
                        adddate.setText(dinhDangNgay.format(calendar.getTime()));
                    }
                }, mnam, mthang, mngay);
                datePickerDialog.show();

            }
        });
        btnPick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mgio=calendar.get(Calendar.HOUR_OF_DAY);
                mphut=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(0,0,0,hourOfDay,minute);
                        SimpleDateFormat dinhDangGio=new SimpleDateFormat("HH:mm");
                        addhour.setText(dinhDangGio.format(calendar.getTime()));
                    }
                },mgio,mphut,true);
                timePickerDialog.show();
            }
        });
        final Intent intent = getIntent();

        if (intent.getSerializableExtra("cv") != null) {
            final CongViec congviec = (CongViec) intent.getSerializableExtra("cv");
            addtitle.setText(congviec.getTen());
            adddate.setText(congviec.getNgay());
            addhour.setText(congviec.getGio());
            adddes.setText(congviec.getMota());
            reference = FirebaseDatabase.getInstance().getReference().child("congviec").
                    child(congviec.getId());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CongViec editCongviec=new CongViec();
                    editCongviec.setTen(addtitle.getText().toString());
                    editCongviec.setNgay(adddate.getText().toString());
                    editCongviec.setGio(addhour.getText().toString());
                    editCongviec.setMota(adddes.getText().toString());

                    editCongviec.setId(congviec.getId());
                    reference.setValue(editCongviec).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),
                                    "Cập nhật thành công",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),
                                    "Xóa thành công",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Xóa thất bại",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
    private void initView() {
        addtitle = findViewById(R.id.addtitle);
        adddes = findViewById(R.id.adddes);
        adddate = findViewById(R.id.adddate);
        addhour=findViewById(R.id.addhour);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnPick=findViewById(R.id.btnPick);
        btnPick2=findViewById(R.id.btnPick2);
    }
}