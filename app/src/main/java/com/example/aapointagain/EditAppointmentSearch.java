package com.example.aapointagain;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditAppointmentSearch extends AppCompatActivity {

    private FirebaseFirestore fstore, f1store;
    public EditText itemSearched;

    private ProgressBar progressBar, progressBar3;

    public TextView areyousure;

    public int count=0;

    public String checkerType, checkerSub1, checkerSub2, checkerDate, checkerHospno, checkerAge, checkerSex, checkerPhoneNumber, checkerOtherDeets;


    public TextView datess;

    public String text1="";

    public ImageView hiddenCally, home1, back1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_edit_appointment_search);

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

        progressBar=(ProgressBar)findViewById(R.id.progressbar1);
        progressBar3=(ProgressBar)findViewById(R.id.progressbar3);

        fstore= FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

        itemSearched=(EditText)findViewById(R.id.searchedItem);

        Intent intent = getIntent();
        text1 = intent.getStringExtra(EditAppointmentEnterDetails.EXTRA_TEXT1);
        //Toast.makeText(getApplicationContext(), "text1-"+text1, Toast.LENGTH_SHORT).show();

        itemSearched.setText(text1);

        itemSearched=(EditText)findViewById(R.id.searchedItem);

        datess=(TextView)findViewById(R.id.datess);

        areyousure=(TextView)findViewById(R.id.areyousure);
        final Button noDelete = (Button)findViewById(R.id.noDelete);
        final Button yesDelete = (Button)findViewById(R.id.yesDelete);


        final Button confirm = (Button)findViewById(R.id.confirmSearchButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                final String oncl_itemSearched=itemSearched.getText().toString().trim();

                if (TextUtils.isEmpty(oncl_itemSearched))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                final List<String> list = new ArrayList<>();
                fstore.collection("Appointments").document("Via")
                        .collection("Patient Name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {



                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                                list.add(document.getId());
                            }
                            if (list.contains(oncl_itemSearched)) {

                                DocumentReference documentReference=fstore.collection("Extras")
                                        .document("Stuff");

                                Map<String, Object> user = new HashMap<>();
                                user.put("EditItem", oncl_itemSearched);



                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                fstore.collection("Appointments").document("Via")
                                        .collection("Patient Name").document(oncl_itemSearched)
                                        .collection("Patients").document(oncl_itemSearched)
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            DocumentSnapshot documentSnapshot = task.getResult();

                                            datess.setText(documentSnapshot.getString("datesss"));



                                            final String date2=documentSnapshot.getString("datesss");

                                            Calendar tomorrowCalendar = Calendar.getInstance();
                                            tomorrowCalendar.add(Calendar.DAY_OF_MONTH,-1);
                                            Date date1 = tomorrowCalendar.getTime();

                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                                            Date strDate = null;
                                            try {
                                                strDate = sdf.parse(date2);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            if (date1.after(strDate)) {
                                                Toast.makeText(getApplicationContext(), "Appointment cannot be edited as the date that has already passed!  You may view it.", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.INVISIBLE);
                                                return;
                                            }
                                            else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                startActivity(new Intent(getApplicationContext(), EditAppointmentEnterDetails.class));
                                            }



                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


//                                Toast.makeText(getApplicationContext(), "date2 -"+date2 ,  Toast.LENGTH_SHORT).show();




                            }
                            else {

                                //Toast.makeText(getApplicationContext(), "list - " + list, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "No Appointment made under this name. Please check the spelling or add initials.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                return;

                            }

                        }
//
                        else {

                        }
                    }
                });






            }
        });

//        itemSearched.setHint("haiiii");



        Button delete = (Button)findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                final String oncl_itemSearched=itemSearched.getText().toString().trim();

                if (TextUtils.isEmpty(oncl_itemSearched))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                final List<String> list = new ArrayList<>();
                fstore.collection("Appointments").document("Via")
                        .collection("Patient Name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {



                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                                list.add(document.getId());
                            }
                            if (list.contains(oncl_itemSearched)) {

                                final DocumentReference documentReference=fstore.collection("Extras")
                                        .document("Stuff");

                                Map<String, Object> user = new HashMap<>();
                                user.put("EditItem", oncl_itemSearched);

                                progressBar.setVisibility(View.INVISIBLE);

                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                //,,,,, DELETING BETWEEN THIS AND

                                confirm.setVisibility(View.INVISIBLE);
                                areyousure.setVisibility(View.VISIBLE);
                                noDelete.setVisibility(View.VISIBLE);
                                yesDelete.setVisibility(View.VISIBLE);
                                itemSearched.setEnabled(false);

                                count=0;

                                yesDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(getApplicationContext(), "NO was cliked!", Toast.LENGTH_SHORT).show();

                                        progressBar3.setVisibility(View.VISIBLE);


                                        f1store.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    final DocumentSnapshot documentSnapshot = task.getResult();

//                                                    NameofPatient.setText(documentSnapshot.getString("EditItem"));
//                                                    patientNameRetrieval.setText(documentSnapshot.getString("EditItem"));
//                                                    hiddenEditin.setVisibility(View.VISIBLE);
//                                                    patientNameRetrieval.setVisibility(View.VISIBLE);
//                                                    hiddenAppo.setVisibility(View.VISIBLE);



//                    final int count=1;
                                                    final String retr_patientName = itemSearched.getText().toString().trim();

                                                    fstore.collection("Appointments").document("Via")
                                                            .collection("Patient Name").document(retr_patientName)
                                                            .collection("Patients").document(retr_patientName)
                                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {



                                                                final DocumentSnapshot documentSnapshot = task.getResult();

                                                                //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Age"),  Toast.LENGTH_SHORT).show();

                                                                checkerType=documentSnapshot.getString("AppointmentType");
                                                                checkerSub1=documentSnapshot.getString("Doctor");
                                                                checkerSub2=documentSnapshot.getString("Diagnosis");
                                                                checkerDate=documentSnapshot.getString("DatePicked");
                                                                checkerHospno=documentSnapshot.getString("HospitalNumber");
                                                                checkerAge=documentSnapshot.getString("Age");
                                                                checkerSex=documentSnapshot.getString("Sex");
                                                                checkerPhoneNumber=documentSnapshot.getString("PhoneNumber");
                                                                checkerOtherDeets=documentSnapshot.getString("OtherDetails");

                                                                //deleting from Appointments/Via/PatientName/.. path
                                                                f1store.collection("Appointments").document("Via")
                                                                        .collection("Patient Name").document(retr_patientName)
                                                                        .delete()
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                count=count+1;
                                                                                //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                f1store.collection("Appointments").document("Via")
                                                                        .collection("Patient Name").document(retr_patientName)
                                                                        .collection("Patients").document(retr_patientName)
                                                                        .delete()
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                count=count+1;
                                                                                //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });




                                                                if (checkerType.equals("Doctors")) {
                                                                    //deleting from Appointments/Via/Doctors/*docname*/Patients/.. path
                                                                    f1store.collection("Appointments").document("Via")
                                                                            .collection("Doctors").document(checkerSub1)
                                                                            .collection("Patients").document(retr_patientName)
                                                                            .delete()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                                    count=count+1;
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                    //deleting from Dates/*datepicked*/*docname*/.. path
                                                                    f1store.collection("Dates").document(checkerDate)
                                                                            .collection(checkerSub1).document(retr_patientName)
                                                                            .delete()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                                    count=count+1;
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                    //deleting from AppByDocDate/*docname*/*date*/.. path
                                                                    f1store.collection("AppByDocDate").document(checkerSub1)
                                                                            .collection(checkerDate).document(retr_patientName)
                                                                            .delete()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                                    count=count+1;
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }

                                                                if (checkerType.equals("Diagnosis")) {
                                                                    //deleting from Appointments/Via/Diagnosis/*diagnosisname*/Patients/.. path
                                                                    f1store.collection("Appointments").document("Via")
                                                                            .collection("Diagnosis").document(checkerSub2)
                                                                            .collection("Patients").document(retr_patientName)
                                                                            .delete()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    count=count+2;
                                                                                    //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                    //deleting from AppByDiagDate/*docname*/*date*/.. path
                                                                    f1store.collection("AppByDiagDate").document(checkerSub2)
                                                                            .collection(checkerDate).document(retr_patientName)
                                                                            .delete()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                                    count=count+1;
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }


                                                                //deleting from Appointments/Via/HospitalNumber/*hospno*/Patients/.. path
                                                                f1store.collection("Appointments").document("Via")
                                                                        .collection("Hospital Number").document(checkerHospno)
                                                                        .collection("Patients").document(retr_patientName)
                                                                        .delete()
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                count=count+1;
                                                                                //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });


                                                                //deleting from Appointments/Via/Date/*datepicked*/Patients/.. path
                                                                f1store.collection("Appointments").document("Via")
                                                                        .collection("Date").document(checkerDate)
                                                                        .collection("Patients").document(retr_patientName)
                                                                        .delete()
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                //Toast.makeText(getApplicationContext(), "" + documentSnapshot.getString("Doctor"),  Toast.LENGTH_SHORT).show();
                                                                                count=count+1;
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(getApplicationContext(), "failed.",  Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });


                                                                Toast.makeText(getApplicationContext(), "Appointment has been deleted successfully.",  Toast.LENGTH_SHORT).show();
                                                                progressBar3.setVisibility(View.INVISIBLE);
                                                                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
                                                                finish();
//                                                                if (count==6) {
//                                                                    progressBar3.setVisibility(View.INVISIBLE);
//                                                                    Toast.makeText(getApplicationContext(), "Appointment has been deleted successfully.",  Toast.LENGTH_SHORT).show();
//                                                                }
//                                                                else {
//                                                                    progressBar3.setVisibility(View.INVISIBLE);
//                                                                    Toast.makeText(getApplicationContext(), "Appointment deletion was unsuccessful",  Toast.LENGTH_SHORT).show();
//                                                                }






                                                            }
                                                            else {
                                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });





                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                                    }
                                });




                                noDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
//                                        Toast.makeText(getApplicationContext(), "NO was cliked!", Toast.LENGTH_SHORT).show();

                                        confirm.setVisibility(View.VISIBLE);
                                        areyousure.setVisibility(View.INVISIBLE);
                                        noDelete.setVisibility(View.INVISIBLE);
                                        yesDelete.setVisibility(View.INVISIBLE);
                                        itemSearched.setEnabled(true);

                                    }
                                });



                                //^^^^^ DELETING BETWEEN THISSSSS

                                //Toast.makeText(getApplicationContext(), "are you sure blah blah - " + oncl_itemSearched, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), EditAppointmentEnterDetails.class));

                            }
                            else {

                                //Toast.makeText(getApplicationContext(), "list - " + list, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "No Appointment made under this name. Please check the spelling or add initials.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                return;

                            }

                        }
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
        startActivity(new Intent(getApplicationContext(), OptionsMain.class));
    }
}
