package com.example.aapointagain;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewAppointmentSearch extends AppCompatActivity {

    private FirebaseFirestore fstore, f1store;

    public static final String EXTRA_TEXT1 = "com.example.aapointagain.EXTRA_TEXT1";
    public static final String EXTRA_TEXT2 = "com.example.aapointagain.EXTRA_TEXT2";
    public static final String EXTRA_TEXT3 = "com.example.aapointagain.EXTRA_TEXT3";

    public static final String EXTRA_TEXT1i = "com.example.aapointagain.EXTRA_TEXT1i";
    public static final String EXTRA_TEXT2i = "com.example.aapointagain.EXTRA_TEXT2i";

    public EditText itemSearched, hiddenSerachAllButValue;
    public TextView itemSearched2, itemSearched2sssss;
    public EditText itemSearched5;
    public String searchOption;
    public TextView searchCat, hiddenColon;
    public EditText hiddenHelp, hiddenHelp2, hiddenHel3, hiddenHel4;

    public TextView titlesearchesssss;

    public TextView dateselected, mdisplaydate, daypicked;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public RelativeLayout layout_PatientName, layout_Date, layout_Doctor, layout_Diagnosis, layout_hospno, layout_Datesssss;

    private ProgressBar progressBar, progressBar2;

    public ImageView hiddenCally, home1, back1;

    public int count=0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_view_appointment_search);



        FirebaseApp.initializeApp(this);

        fstore= FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

        hiddenSerachAllButValue=(EditText)findViewById(R.id.hiddenSearchAllButValue);

        itemSearched=(EditText)findViewById(R.id.searchedItem);
        itemSearched2=(TextView) findViewById(R.id.searchedItem2);
        itemSearched5=(EditText) findViewById(R.id.searchedItem5);

        titlesearchesssss=(TextView)findViewById(R.id.titlesearchsssss);
        itemSearched2sssss=(TextView) findViewById(R.id.searchedItem2sssss);


        hiddenHelp = (EditText)findViewById(R.id.hiddenHELP);
        hiddenHelp2 = (EditText)findViewById(R.id.hiddenHELP2);
        hiddenHel3 = (EditText)findViewById(R.id.hiddenHELP3);
        hiddenHel4 = (EditText)findViewById(R.id.hiddenHELP4);

        searchCat=(TextView)findViewById(R.id.searchCategory);
        hiddenColon=(TextView)findViewById(R.id.hiddenColon);

        layout_PatientName = (RelativeLayout)findViewById(R.id.layout_PatientName);
        layout_Date = (RelativeLayout)findViewById(R.id.layout_Date);
        layout_Doctor = (RelativeLayout)findViewById(R.id.layout_Doctor);
        layout_Diagnosis = (RelativeLayout)findViewById(R.id.layout_Diagnosis);
        layout_hospno = (RelativeLayout)findViewById(R.id.layout_hospno);
        layout_Datesssss=(RelativeLayout)findViewById(R.id.layout_Datesssss);

        progressBar=(ProgressBar)findViewById(R.id.progressbar1);
        progressBar2=(ProgressBar)findViewById(R.id.progressbar2);

        final Button searchAll = (Button)findViewById(R.id.searchAll);

        mdisplaydate = (TextView)findViewById(R.id.searchedItem2);
        mdisplaydate.setEnabled(false);

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
                startActivity(new Intent(getApplicationContext(), ViewAppointmentOptions.class));
            }
        });


        f1store.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();

                    searchCat.setText(documentSnapshot.getString("SearchOption"));
                    hiddenHelp.setText(documentSnapshot.getString("SearchOption"));


                    //patient name layout view
                    if (documentSnapshot.getString("SearchOption").equals("Patient Name")) {
                        layout_PatientName.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);
                        searchAll.setVisibility(View.VISIBLE);

                        searchAll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                count=1;
                                hiddenHelp2.setText("Patient Name");
                                hiddenSerachAllButValue.setText("picked");
                                openViewActivity1();
                            }
                        });
                    }

                    //date layout view
                    if (documentSnapshot.getString("SearchOption").equals("Date")) {

                        layout_Date.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);




                        mdisplaydate.setEnabled(true);
                        mdisplaydate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Calendar cal=Calendar.getInstance();
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog dialog=new DatePickerDialog(
                                        ViewAppointmentSearch.this,
                                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,
                                        year, month, day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                                dialog.show();


                            }
                        });

                        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                                mdisplaydate.setText(mDay + "." + (mMonth+1) + "." + mYear);

                                Calendar cal = Calendar.getInstance();

                                cal.set(mYear,mMonth,mDay);
                            }
                        };

                    }

                    //doctor layout view
                    if (documentSnapshot.getString("SearchOption").equals("Doctors")) {

                        layout_Doctor.setVisibility(View.VISIBLE);
                        titlesearchesssss.setVisibility(View.VISIBLE);
                        layout_Datesssss.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);

                        //dropdown list to select doctor
                        final Spinner levelsp = (Spinner) findViewById(R.id.searchedItem3);
                        ArrayAdapter<String> levelad = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.doctors));
                        levelad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        levelsp.setAdapter(levelad);

                        levelsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String levelpicked=adapterView.getItemAtPosition(i).toString();
                                hiddenHel3.setText(levelpicked);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText(getApplicationContext(), "Please select a level.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

                        //date pickin
                        mdisplaydate = (TextView)findViewById(R.id.searchedItem2sssss);
                        mdisplaydate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Calendar cal=Calendar.getInstance();
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog dialog=new DatePickerDialog(
                                        ViewAppointmentSearch.this,
                                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,
                                        year, month, day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                                dialog.show();


                            }
                        });
                        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                                mdisplaydate.setText(mDay + "." + (mMonth+1) + "." + mYear);
                                String date=mdisplaydate.getText().toString().trim();
                                hiddenHel4.setText(date);

                                Calendar cal = Calendar.getInstance();

                                cal.set(mYear,mMonth,mDay);
                            }
                        };



                    }


                    //diagnosis layout view
                    if (documentSnapshot.getString("SearchOption").equals("Diagnosis")) {

                        layout_Diagnosis.setVisibility(View.VISIBLE);
                        titlesearchesssss.setVisibility(View.VISIBLE);
                        layout_Datesssss.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);

                        //date pickin
                        mdisplaydate = (TextView)findViewById(R.id.searchedItem2sssss);
                        mdisplaydate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Calendar cal=Calendar.getInstance();
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog dialog=new DatePickerDialog(
                                        ViewAppointmentSearch.this,
                                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth, mDateSetListener,
                                        year, month, day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                                dialog.show();


                            }
                        });
                        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                                mdisplaydate.setText(mDay + "." + (mMonth+1) + "." + mYear);
                                String date=mdisplaydate.getText().toString().trim();
                                hiddenHel4.setText(date);

                                Calendar cal = Calendar.getInstance();

                                cal.set(mYear,mMonth,mDay);
                            }
                        };



                        //dropdown list to select diagnosis
                        final Spinner levelsp = (Spinner) findViewById(R.id.searchedItem4);
                        ArrayAdapter<String> levelad = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.diagnosis));
                        levelad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        levelsp.setAdapter(levelad);

                        levelsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String levelpicked=adapterView.getItemAtPosition(i).toString();
                                hiddenHel3.setText(levelpicked);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText(getApplicationContext(), "Please select a level.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

                    }

                    if (documentSnapshot.getString("SearchOption").equals("Hospital Number"))
                    {
                        layout_hospno.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);
                    }


                    searchCat.setVisibility(View.VISIBLE);
                    hiddenColon.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                }

            }
        });




        Button confirmSearch=(Button)findViewById(R.id.confirmSearchButton);
        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                final String oncl_searchCategory=searchCat.getText().toString().trim();


                if (oncl_searchCategory.equals("Patient Name")) {
                    final String oncl_itemSearched = itemSearched.getText().toString().trim();
                    hiddenHelp2.setText(oncl_itemSearched);
                    if (TextUtils.isEmpty(oncl_itemSearched)) {
                        Toast.makeText(getApplicationContext(), "Please enter patient's name in the search bar.", Toast.LENGTH_SHORT).show();
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
                                //Toast.makeText(getApplicationContext(), "list - " + itemSearched, Toast.LENGTH_SHORT).show();
                                if (list.contains(oncl_itemSearched)) {
//                                    Toast.makeText(getApplicationContext(), "list - " + list, Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    openViewActivity();
                                    return;
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No appointment has been made under this name.", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }

                        }
                    });
                }


                if (oncl_searchCategory.equals("Date"))
                {
                    final String oncl_itemSearched=mdisplaydate.getText().toString().trim();
                    hiddenHelp2.setText(oncl_itemSearched);

                    if (TextUtils.isEmpty(oncl_itemSearched))
                    {
                        Toast.makeText(getApplicationContext(), "Please select a date.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }
                    openViewActivity();
                }

                if (oncl_searchCategory.equals("Doctors"))
                {
                    final String oncl_itemSearched=hiddenHel3.getText().toString().trim();
                    hiddenHelp2.setText(oncl_itemSearched);
                    if (TextUtils.isEmpty(oncl_itemSearched))
                    {
                        Toast.makeText(getApplicationContext(), "Please select a Doctor.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }
                    openViewActivity();
                }

                if (oncl_searchCategory.equals("Diagnosis"))
                {
                    final String oncl_itemSearched=hiddenHel3.getText().toString().trim();
                    hiddenHelp2.setText(oncl_itemSearched);
                    if (TextUtils.isEmpty(oncl_itemSearched))
                    {
                        Toast.makeText(getApplicationContext(), "Please select a Diagnosis.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }
                    openViewActivity();
                }

                if (oncl_searchCategory.equals("Hospital Number"))
                {
                    final String oncl_itemSearched=itemSearched5.getText().toString().trim();
                    hiddenHelp2.setText(oncl_itemSearched);
                    if (TextUtils.isEmpty(oncl_itemSearched))
                    {
                        Toast.makeText(getApplicationContext(), "Please enter hospital number in the search bar.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }
                    openViewActivity();
                }




//                DocumentReference documentReference=fstore.collection("Extras")
//                        .document("Stuff");
//
//                Map<String, Object> user = new HashMap<>();
//                user.put("ItemSearched", oncl_itemSearched);
//
//                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });

    }

    public void openViewActivity() {

        EditText editText1 = (EditText)findViewById(R.id.hiddenHELP);
        String text1 = editText1.getText().toString();

        EditText editText2 = (EditText)findViewById(R.id.hiddenHELP2);
        String text2 = editText2.getText().toString();

        EditText editText3 = (EditText)findViewById(R.id.hiddenHELP4);
        String text3 = editText3.getText().toString();

        Intent intent = new Intent(getApplicationContext(), recylceViewAt1.class);
        intent.putExtra(EXTRA_TEXT1, text1);
        intent.putExtra(EXTRA_TEXT2, text2);
        intent.putExtra(EXTRA_TEXT3, text3);
        startActivity(intent);
        finish();
    }

    public void openViewActivity1() {

        EditText editText1 = (EditText)findViewById(R.id.hiddenHELP);
        String text1 = editText1.getText().toString();

        EditText editText2 = (EditText)findViewById(R.id.hiddenSearchAllButValue);
        String text2 = editText2.getText().toString();

        Intent intent = new Intent(getApplicationContext(), recylceViewAt1.class);
        intent.putExtra(EXTRA_TEXT1, text1);
        intent.putExtra(EXTRA_TEXT2, text2);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ViewAppointmentOptions.class));
    }
}
