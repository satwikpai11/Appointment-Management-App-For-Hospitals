package com.example.aapointagain;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OptionsMain extends AppCompatActivity {

    private long backpressedTime;

    public TextView logout;
    Dialog mydialog;

    private FirebaseFirestore fstore, f1store;

    public ImageView hiddenCally, home1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public TextView titleToDate, DatePt1, St_Nd_Th, DatePt2, titleHiddenComma1, DatePt3, DayPt1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_options_main);

        FirebaseApp.initializeApp(this);
        fstore=FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

        mydialog = new Dialog(this);
        logout=(TextView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mydialog.setContentView(R.layout.logout_popup);

                mydialog.show();

                Button yes=(Button)mydialog.findViewById(R.id.logoutyes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();

                        finish();
                        Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        DocumentReference documentReference=fstore.collection("LoggedIn")
                                .document("Status");

                        Map<String, Object> user = new HashMap<>();
                        user.put("status", "no");

                        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //progressBar.setVisibility(View.INVISIBLE);
                                //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                Button no=(Button)mydialog.findViewById(R.id.logoutno);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydialog.dismiss();
                    }
                });
                /*FirebaseAuth.getInstance().signOut();
                finish();
                Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));*/
            }
        });


        home1=(ImageView)findViewById(R.id.home1);
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
            }
        });

//
//        DatePt1=(TextView)findViewById(R.id.DatePt1);
//        St_Nd_Th=(TextView)findViewById(R.id.St_Nd_Th);
//        DatePt2=(TextView)findViewById(R.id.DatePt2);
//        titleHiddenComma1=(TextView)findViewById(R.id.titleHiddenComma1);
//        DatePt3=(TextView)findViewById(R.id.DatePt3);
//        DayPt1=(TextView)findViewById(R.id.DayPt1);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//        Date d = new Date();
//        String dayOfTheWeek = sdf.format(d);
//        DayPt1.setText(" "+dayOfTheWeek);
//
//        Calendar calendar = Calendar.getInstance();
//
//
//        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
//            //titleToDate.setVisibility(View.VISIBLE);
//            DatePt1.setText(""+thisDay);
//            DatePt1.setVisibility(View.VISIBLE);
//            if (thisDay==1 || thisDay==21 || thisDay==31)
//            {
//                 St_Nd_Th.setText("st of ");
//                 St_Nd_Th.setVisibility(View.VISIBLE);
//            }
//            if (thisDay==2 || thisDay==22)
//            {
//                 St_Nd_Th.setText("nd of ");
//                 St_Nd_Th.setVisibility(View.VISIBLE);
//            }
//            if (thisDay==3 || thisDay==23)
//            {
//                 St_Nd_Th.setText("rd of ");
//                 St_Nd_Th.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                St_Nd_Th.setText("th of ");
//                St_Nd_Th.setVisibility(View.VISIBLE);
//            }
//
//
//        int thisMonth = calendar.get(Calendar.MONTH);
//            if (thisMonth==0)
//            {
//                DatePt2.setText("January");
//                DatePt2.setVisibility(View.VISIBLE);
//            }
//        if (thisMonth==1)
//        {
//            DatePt2.setText("February");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==2)
//        {
//            DatePt2.setText("March");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==3)
//        {
//            DatePt2.setText("April");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==4)
//        {
//            DatePt2.setText("May");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==5)
//        {
//            DatePt2.setText("June");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==6)
//        {
//            DatePt2.setText("July");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==7)
//        {
//            DatePt2.setText("August");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==8)
//        {
//            DatePt2.setText("September");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==9)
//        {
//            DatePt2.setText("October");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==10)
//        {
//            DatePt2.setText("November");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//        if (thisMonth==11)
//        {
//            DatePt2.setText("December");
//            DatePt2.setVisibility(View.VISIBLE);
//        }
//
//
//
//        int thisYear = calendar.get(Calendar.YEAR);
//        titleHiddenComma1.setVisibility(View.VISIBLE);
//        DatePt3.setText(""+thisYear);
//        DatePt3.setVisibility(View.VISIBLE);
//

        hiddenCally=(ImageView)findViewById(R.id.cally2);

        hiddenCally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        OptionsMain.this,
                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                Calendar cal = Calendar.getInstance();
                cal.set(mYear,mMonth,mDay);
            }
        };


        Button makeappointment = (Button) findViewById(R.id.MakeAppointment);
        makeappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
            }
        });

        Button viewappointment = (Button) findViewById(R.id.ViewAppointment);
        viewappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewAppointmentOptions.class));
            }
        });

        Button editappointment = (Button) findViewById(R.id.EditAppointments);
        editappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditAppointmentSearch.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {


        if (backpressedTime + 3000 > System.currentTimeMillis())
        {
            finishAffinity();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return;
        }
        else
        {
            Toast.makeText(getBaseContext(), "Click back again to exit...", Toast.LENGTH_SHORT).show();
        }

        backpressedTime=System.currentTimeMillis();
    }

}
