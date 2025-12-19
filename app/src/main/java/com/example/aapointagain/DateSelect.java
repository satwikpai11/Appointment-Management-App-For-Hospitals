package com.example.aapointagain;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DateSelect extends AppCompatActivity {

    private FirebaseFirestore fstore, f1store;

    public TextView appType;
    public TextView DocName, hiddenColon;
    public TextView DiagnosisName, hiddenA, hiddenCase;

    public ImageView hiddenCally, home1, back1;

    public TextView dateselected, mdisplaydate, daypicked, hiddendatess;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public TextView text2, hiddenFullStop;

    public String checkerSub, checkerType;
    public String day1, day2, day;
    public int count=0;

    public ImageView hiddenQuest;
    Dialog mydialog;


    public TextView text551, againHiddenA, againdiagnosisName, againdocName, againhiddenCase,
            day1docanddiagnosis, againHiddenComma, day2docanddiagnosis, againHiddenCanBe, day1docanddiagnosis1;

    private ProgressBar progressBar, progressBar2;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_date_select);

        progressBar=(ProgressBar)findViewById(R.id.progressbar1);
        progressBar2=(ProgressBar)findViewById(R.id.progressbar2);

        appType=(TextView)findViewById(R.id.AppType);
        DocName=(TextView)findViewById(R.id.DocName);
        hiddenColon=(TextView)findViewById(R.id.hiddenColon);
        DiagnosisName=(TextView)findViewById(R.id.DiagnosisName);
        hiddenA=(TextView)findViewById(R.id.hiddenA);
        hiddenCase=(TextView)findViewById(R.id.hiddenCase);

        mydialog = new Dialog(this);
        hiddenQuest=(ImageView)findViewById(R.id.hiddenQuest);

        text2=(TextView)findViewById(R.id.text2);
        hiddenFullStop=(TextView)findViewById(R.id.hiddenFullStop);

        text551=(TextView)findViewById(R.id.text551);
        againHiddenA=(TextView)findViewById(R.id.againhiddenA);
        againdiagnosisName=(TextView)findViewById(R.id.againdiagnosisName);
        againdocName=(TextView)findViewById(R.id.againdocName);
        againhiddenCase=(TextView)findViewById(R.id.againhiddenCase);
        day1docanddiagnosis=(TextView)findViewById(R.id.day1docanddiagnosis);
        againHiddenComma=(TextView)findViewById(R.id.againHiddenComma);
        day2docanddiagnosis=(TextView)findViewById(R.id.day2docanddiagnosis);
        day1docanddiagnosis1=(TextView)findViewById(R.id.day1docanddiagnosis1);
        againHiddenCanBe=(TextView)findViewById(R.id.againhiddenCanBe);

        FirebaseApp.initializeApp(this);

        fstore=FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

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
                                         fstore.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                             @Override
                                             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                 if (task.isSuccessful()) {

                                                     DocumentSnapshot documentSnapshot = task.getResult();

                                                     if (documentSnapshot.getString("AppointmentType").equals("Doctors")) {
                                                         startActivity(new Intent(getApplicationContext(), DoctorsList.class));
                                                     }
                                                     if (documentSnapshot.getString("AppointmentType").equals("Diagnosis")) {
                                                         startActivity(new Intent(getApplicationContext(), DiagnosisList.class));
                                                     }

                                                 }
                                             }
                                         });
                                     }
                                 });


        fstore.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();

                    appType.setText(documentSnapshot.getString("AppointmentType"));

                    if (documentSnapshot.getString("AppointmentType").equals("Doctors")){


                        checkerType="Doctors";
                        DocName.setText(documentSnapshot.getString("SubSelected"));
                        checkerSub=documentSnapshot.getString("SubSelected");
                        DocName.setVisibility(View.VISIBLE);
                        hiddenColon.setVisibility(View.VISIBLE);


                        checkerSub=DocName.getText().toString().trim();



                        f1store.collection("Doctors").document(checkerSub).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {

                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    day1 = documentSnapshot.getString("day1");
                                    day2 = documentSnapshot.getString("day2");
                                    progressBar.setVisibility(View.INVISIBLE);
                                    hiddenQuest.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                    if (documentSnapshot.getString("AppointmentType").equals("Diagnosis")){


                        checkerType="Diagnosis";
                        DiagnosisName.setText(documentSnapshot.getString("SubSelected"));
                        hiddenA.setVisibility(View.VISIBLE);
                        DiagnosisName.setVisibility(View.VISIBLE);
                        hiddenCase.setVisibility(View.VISIBLE);


                        checkerSub=DiagnosisName.getText().toString().trim();

                        f1store.collection("Diagnosis").document(checkerSub).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {

                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    day1 = documentSnapshot.getString("day1");
                                    day2 = documentSnapshot.getString("day2");
                                    progressBar.setVisibility(View.INVISIBLE);
                                    hiddenQuest.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                }

            }
        });


        hiddenQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                fstore.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();

                            final String chType = documentSnapshot.getString("AppointmentType");
                            final String chSub = documentSnapshot.getString("SubSelected");




                            if (chType.equals("Doctors"))
                            {

                                f1store.collection("Doctors").document(chSub).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            DocumentSnapshot documentSnapshot = task.getResult();

                                            day1 = documentSnapshot.getString("day1");
                                            day2 = documentSnapshot.getString("day2");
                                            //progressBar.setVisibility(View.INVISIBLE);
                                            //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                againdocName.setText(chSub);
                                day1docanddiagnosis1.setText(day1);
                                text551.setVisibility(View.VISIBLE);
                                againdocName.setVisibility(View.VISIBLE);
                                againHiddenCanBe.setVisibility(View.VISIBLE);
                                day1docanddiagnosis1.setVisibility(View.VISIBLE);

                                if (chSub.equals("Dr. Girish"))
                                {
                                    day2docanddiagnosis.setText(day2);
                                    againHiddenComma.setVisibility(View.VISIBLE);
                                    day2docanddiagnosis.setVisibility(View.VISIBLE);

                                }

                                hiddenQuest.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                            if (chType.equals("Diagnosis"))
                            {

                                f1store.collection("Diagnosis").document(chSub).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            DocumentSnapshot documentSnapshot = task.getResult();

                                            day1 = documentSnapshot.getString("day1");
                                            day2 = documentSnapshot.getString("day2");
                                            progressBar.setVisibility(View.INVISIBLE);
                                            //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                againdiagnosisName.setText(chSub);
                                day1docanddiagnosis.setText(day1);
                                text551.setVisibility(View.VISIBLE);
                                againHiddenA.setVisibility(View.VISIBLE);
                                againdiagnosisName.setVisibility(View.VISIBLE);
                                againhiddenCase.setVisibility(View.VISIBLE);
                                day1docanddiagnosis.setVisibility(View.VISIBLE);

                                hiddenQuest.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);

                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        hiddendatess=(TextView)findViewById(R.id.hiddendatess);

        mdisplaydate = (TextView)findViewById(R.id.DateOfAppointment);
        daypicked=(TextView)findViewById(R.id.DayPicked);
        mdisplaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        DateSelect.this,
                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();

                //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();


            }
        });



        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                mdisplaydate.setText(mDay + "." + (mMonth+1) + "." + mYear);
                hiddendatess.setText(mDay + "/" + (mMonth+1) + "/" + mYear);




//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                Date strDate = sdf.parse(mdisplaydate.getText().toString().trim());
//                if (new Date().after(strDate)) {
//                    catalog_outdated = 1;
//                }

                Calendar cal = Calendar.getInstance();

                cal.set(mYear,mMonth,mDay);


                if (cal.get(Calendar.DAY_OF_WEEK)==1) {
                    //daypickedtitle.setVisibility(View.VISIBLE);
                    day="Sunday";
                }
                if (cal.get(Calendar.DAY_OF_WEEK)==2) {
                    //daypickedtitle.setVisibility(View.VISIBLE);
                    day="Monday";
                }
                if (cal.get(Calendar.DAY_OF_WEEK)==3) {
                    day="Tuesday";
                }
                if (cal.get(Calendar.DAY_OF_WEEK)==4) {
                    day="Wednesday";
                }
                if (cal.get(Calendar.DAY_OF_WEEK)==5) {
                    day="Thursday";
                }
                if (cal.get(Calendar.DAY_OF_WEEK)==6) {
                    day="Friday";
                }
                if (cal.get(Calendar.DAY_OF_WEEK)==7) {
                    day="Saturday";
                }


                if(day1.equals(day) || day2.equals(day)) {

                    count=1;

                    daypicked.setText(day);
                    daypicked.setTextColor(Color.rgb(56,178,20));
                    text2.setVisibility(View.VISIBLE);
                    daypicked.setVisibility(View.VISIBLE);
                    hiddenFullStop.setVisibility(View.VISIBLE);

                }
                else {

                    count=0;

                    daypicked.setText(day);
                    daypicked.setTextColor(Color.rgb(199,0,57));
                    text2.setVisibility(View.VISIBLE);
                    daypicked.setVisibility(View.VISIBLE);
                    hiddenFullStop.setVisibility(View.VISIBLE);

                }





            }
        };

        Button confirmDate=(Button)findViewById(R.id.DocViewAppointments);
        confirmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String oncl_date_value=mdisplaydate.getText().toString().trim();
                if (TextUtils.isEmpty(oncl_date_value))
                {
                    Toast.makeText(getApplicationContext(), "Please select a Date for the appointment.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);


                final String date2 = hiddendatess.getText().toString().trim();

                Calendar tomorrowCalendar = Calendar.getInstance();
                tomorrowCalendar.add(Calendar.DAY_OF_MONTH,-1);
                Date date1 = tomorrowCalendar.getTime();

                //if (date1.compareTo(date2) < 0)
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date strDate = null;
                try {
                    strDate = sdf.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1.after(strDate)) {
                    Toast.makeText(getApplicationContext(), "Appointment cannot be made for a date that has already passed!", Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.INVISIBLE);
                    return;
                }


                final List<String> list = new ArrayList<>();
                fstore.collection("Dates").document(oncl_date_value)
                        .collection(checkerSub).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                                list.add(document.getId());
                            }
//                            Toast.makeText(getApplicationContext(), "list-" + list, Toast.LENGTH_SHORT).show();

                            if (list.size()>=25) {
                                Toast.makeText(getApplicationContext(), "The maximum number of appointments has been reached for this date.", Toast.LENGTH_LONG).show();
                                progressBar2.setVisibility(View.INVISIBLE);
                                return;
                            }
                            else
                            {

                                if (count==1)
                                {

                                    DocumentReference documentReference=fstore.collection("Extras")
                                            .document("Stuff");

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("DatePicked", oncl_date_value);
                                    user.put("datesss0", date2);

                                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    progressBar2.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(getApplicationContext(), EnterDetailsForAppointment.class));
                                }
                                else
                                {

                                    if (checkerType.equals("Doctors"))
                                    {

                                        againdocName.setText(checkerSub);
                                        day1docanddiagnosis1.setText(day1);
                                        text551.setVisibility(View.VISIBLE);
                                        againdocName.setVisibility(View.VISIBLE);
                                        againHiddenCanBe.setVisibility(View.VISIBLE);
                                        day1docanddiagnosis1.setVisibility(View.VISIBLE);
                                        if (checkerSub.equals("Dr. Girish"))
                                        {
                                            day2docanddiagnosis.setText(day2);
                                            againHiddenComma.setVisibility(View.VISIBLE);
                                            day2docanddiagnosis.setVisibility(View.VISIBLE);
                                        }

                                        progressBar2.setVisibility(View.INVISIBLE);

                                    }
                                    if (checkerType.equals("Diagnosis"))
                                    {

                                        againdiagnosisName.setText(checkerSub);
                                        day1docanddiagnosis.setText(day1);
                                        text551.setVisibility(View.VISIBLE);
                                        againHiddenA.setVisibility(View.VISIBLE);
                                        againdiagnosisName.setVisibility(View.VISIBLE);
                                        againhiddenCase.setVisibility(View.VISIBLE);
                                        day1docanddiagnosis.setVisibility(View.VISIBLE);

                                        progressBar2.setVisibility(View.INVISIBLE);

                                    }

                                }

                            }//end of else for if(list.size()>=2)



                        }//end of is(task.isSuccessful)
//
                        else {

                        }
                    }
                });





            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
    }

}
