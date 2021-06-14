package com.example.phamxuanthinh_b17dcat175_btl;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddActivity extends AppCompatActivity {
    private EditText addtitle,adddes,adddate,addhour;
    private Button btnCancel,btnSave,btnPick,btnPick2;
    private int mnam, mthang, mngay,mgio,mphut;
    private FirebaseDatabase database;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();


        database= FirebaseDatabase.getInstance();

        btnPick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mngay=calendar.get(Calendar.DAY_OF_MONTH);
                mthang=calendar.get(Calendar.MONTH);
                mnam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        calendar.set(year,month,day);
                        SimpleDateFormat dinhDangNgay=new SimpleDateFormat("dd/MM/yyyy");
                        adddate.setText(dinhDangNgay.format(calendar.getTime()));
                    }
                },mnam,mthang,mngay);
                datePickerDialog.show();

            }
        });
        btnPick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mgio=calendar.get(Calendar.HOUR_OF_DAY);
                mphut=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=database.getReference().push().getKey();
                reference = database.getReference().
                        child("congviec").
                        child(id);

                Map<String, Object> data= new HashMap<>();
                data.put("ten", addtitle.getText().toString());
                data.put("ngay", adddate.getText().toString());
                data.put("gio",addhour.getText().toString());
                data.put("mota", adddes.getText().toString());
                data.put("id", id);

                reference.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),
                                "Thêm thành công",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
    private void initView() {
        addtitle = findViewById(R.id.addtitle);
        adddes = findViewById(R.id.adddes);
        adddate = findViewById(R.id.adddate);
        addhour=findViewById(R.id.addhour);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnPick=findViewById(R.id.btnPick);
        btnPick2=findViewById(R.id.btnPick2);
    }
}