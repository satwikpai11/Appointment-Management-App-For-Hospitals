package com.example.aapointagain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MakeAppointmentOptions extends AppCompatActivity {

    private FirebaseFirestore fstore;

    public ImageView hiddenCally, home1, back1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_make_appointment_options);

        home1=(ImageView)findViewById(R.id.home1);
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
            }
        });

        back1=(ImageView)findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
            }
        });

        FirebaseApp.initializeApp(this);

        fstore=FirebaseFirestore.getInstance();

//        hiddenCally=(ImageView)findViewById(R.id.cally2);
//
//        hiddenCally.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Calendar cal=Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog=new DatePickerDialog(
//                        MakeAppointmentOptions.this,
//                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,
//                        year, month, day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
//                dialog.show();
//            }
//        });
//
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
//                Calendar cal = Calendar.getInstance();
//                cal.set(mYear,mMonth,mDay);
//            }
//        };


        Button docappointment = (Button)findViewById(R.id.DoctorAppointment);
        docappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("AppointmentType", "Doctors");

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                                 //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                            }
                        });


                startActivity(new Intent(getApplicationContext(), DoctorsList.class));

            }
        });

        Button diagnosisappointment = (Button)findViewById(R.id.DiagnosisAppointment);
        diagnosisappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("AppointmentType", "Diagnosis");

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Diagnosis added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DiagnosisList.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), OptionsMain.class));

    }
}
