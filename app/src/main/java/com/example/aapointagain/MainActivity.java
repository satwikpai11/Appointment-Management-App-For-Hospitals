package com.example.aapointagain;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private long backpressedTime;

    private ProgressBar progressBar, progressBar2;

    public EditText UserID, Password;

    private FirebaseFirestore fstore, f1store;

    public ImageView eye1;
    public int count;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);

        UserID=(EditText)findViewById(R.id.UserID);
        Password=(EditText)findViewById(R.id.Password);

        progressBar2=(ProgressBar)findViewById(R.id.progressbar2);

        FirebaseApp.initializeApp(this);
        fstore=FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

        count=0;
        eye1=(ImageView)findViewById(R.id.eye1);
        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                //Toast.makeText(getApplicationContext(), "count- "+count, Toast.LENGTH_SHORT).show();

                if (count%2!=0)
                {
                    Password.setInputType(
                            InputType.TYPE_CLASS_TEXT|
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                }
                if (count%2==0)
                {
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });




        f1store.collection("LoggedIn").document("Status")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot.getString("status").equals("yes")) {
                                    progressBar2.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(getApplicationContext(), OptionsMain.class));
                                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                }
                                if (documentSnapshot.getString("status").equals("no")) {
                                    progressBar2.setVisibility(View.INVISIBLE);
                                }


//                            final String date2=documentSnapshot.getString("today date");
//
//                            Calendar tomorrowCalendar = Calendar.getInstance();
//                            tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 0);
//                            Date date1 = tomorrowCalendar.getTime();
//                            //Toast.makeText(getApplicationContext(), "Appointment cannot be made for a date that has already passed!  "+date1, Toast.LENGTH_LONG).show();
//
//                            //if (date1.compareTo(date2) < 0)
//                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//                            Date strDate = null;
//                            try {
//                                strDate = (Date) sdf.parse(String.valueOf(date1));
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(getApplicationContext(), "Appointment cannot be made for a date that has already passed!  "+strDate, Toast.LENGTH_LONG).show();
//
////                            if (date1.after(strDate)) {
////                                DocumentReference documentReference=fstore.collection("LoggedIn")
////                                        .document("Status");
//
//                                Map<String, Object> user = new HashMap<>();
//                                user.put("status", "no");
//                                user.put("today date", strDate);
//
//                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        //progressBar.setVisibility(View.INVISIBLE);
//                                        //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
//                                            }
//                                });
//                                progressBar2.setVisibility(View.INVISIBLE);
//                            }
//                            else
//                            {
//                                if (documentSnapshot.getString("status").equals("yes")) {
//                                    progressBar2.setVisibility(View.INVISIBLE);
//                                    startActivity(new Intent(getApplicationContext(), OptionsMain.class));
//                                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
//                                }
//                                if (documentSnapshot.getString("status").equals("no")) {
//                                    progressBar2.setVisibility(View.INVISIBLE);
//                                }
//                            }

                        }
                    }
                });


        //final String date2 = hiddendatess.getText().toString().trim();






        Button login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar2.setVisibility(View.VISIBLE);

                final String oncl_userid=UserID.getText().toString().trim();
                final String oncl_password=Password.getText().toString().trim();

                if (TextUtils.isEmpty(oncl_userid))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter a User ID", Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.INVISIBLE);
                    UserID.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(oncl_password))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter the Password.", Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.INVISIBLE);
                    Password.requestFocus();
                    return;
                }

                fstore.collection("LoggedIn").document("Status")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (oncl_userid.equals(documentSnapshot.getString("id")))
                            {
                                if (oncl_password.equals(documentSnapshot.getString("password")))
                                {

                                    DocumentReference documentReference=fstore.collection("LoggedIn")
                                            .document("Status");

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("status", "yes");

                                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    progressBar2.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(getApplicationContext(), OptionsMain.class));
                                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Please check your password", Toast.LENGTH_LONG).show();
                                    progressBar2.setVisibility(View.INVISIBLE);
                                    Password.requestFocus();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "This user id does not exist.", Toast.LENGTH_LONG).show();
                                progressBar2.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_LONG).show();
                        progressBar2.setVisibility(View.INVISIBLE);
                    }
                });

                //startActivity(new Intent(getApplicationContext(), OptionsMain.class));
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
