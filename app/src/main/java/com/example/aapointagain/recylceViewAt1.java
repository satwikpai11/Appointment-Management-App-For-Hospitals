package com.example.aapointagain;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class recylceViewAt1 extends AppCompatActivity {

    public RecyclerView mFirestoreList;
    public FirebaseFirestore firebaseFirestore;

    public FirestoreRecyclerAdapter adapter;

    public String ui;
    public TextView text11, hiddenNumber, text123;

    private FirebaseFirestore fstore, f1store;

    public String text1, text2, text3;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_recylce_view_at1);

        Intent intent = getIntent();
        text1 = intent.getStringExtra(ViewAppointmentSearch.EXTRA_TEXT1);
        text2 = intent.getStringExtra(ViewAppointmentSearch.EXTRA_TEXT2);
        text3 = intent.getStringExtra(ViewAppointmentSearch.EXTRA_TEXT3);



//        Toast.makeText(getApplicationContext(), "AppointmentType- "+text1,  Toast.LENGTH_SHORT).show();

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);


        FirebaseApp.initializeApp(this);

        fstore = FirebaseFirestore.getInstance();
        f1store = FirebaseFirestore.getInstance();

        text11 = (TextView)findViewById(R.id.text11);
        text123 = (TextView)findViewById(R.id.text123);
        hiddenNumber = (TextView)findViewById(R.id.number);

        final Button makeappy = (Button)findViewById(R.id.makeappy);
        final Button goBack = (Button)findViewById(R.id.goBack);




    if (text1.equals("Doctors"))
    {


        //Toast.makeText(getApplicationContext(), "text3 - " + text3, Toast.LENGTH_SHORT).show();

        if (text3.equals(""))
        {

            final List<String> list = new ArrayList<>();
            fstore.collection("Appointments").document("Via")
                    .collection(text1).document(text2)
                    .collection("Patients")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                            list.add(document.getId());
                        }
                        if (list.size()==0) {
                            //Toast.makeText(getApplicationContext(), "list is empty- " + list.size(), Toast.LENGTH_SHORT).show();
                            text123.setVisibility(View.VISIBLE);
                            hiddenNumber.setVisibility(View.INVISIBLE);
                            makeappy.setVisibility(View.VISIBLE);
                            goBack.setVisibility(View.VISIBLE);

                            goBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
                                    finish();
                                }
                            });
                            makeappy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
                                    finish();
                                }
                            });
                            return;

                        }
                        else {
                            hiddenNumber.setText("Number of appointments to be shown- "+list.size());
                            hiddenNumber.setVisibility(View.VISIBLE);
                        }

                    }
                }
            });



//            Toast.makeText(getApplicationContext(), "nothin was picked-" + text3, Toast.LENGTH_SHORT).show();

            //Query
            Query query = firebaseFirestore.collection("Appointments").document("Via").collection(text1)
                    .document(text2).collection("Patients");
            //Recycler Options
            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query, ProductsModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                    holder.list_name.setText(model.getName());
                    holder.list_age.setText(model.getAge());
                    holder.list_sex.setText(model.getSex());
                    holder.list_phoneNumber.setText(model.getPhoneNumber());
                    holder.list_doctor.setText(model.getDoctor());
                    holder.list_diagnosis.setText(model.getDiagnosis());
                    holder.list_datePicked.setText(model.getDatePicked());
                    holder.list_hospitalNumber.setText(model.getHospitalNumber());
                    holder.list_otherDetails.setText(model.getOtherDetails());

                }
            };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
            mFirestoreList.setAdapter(adapter);




        }

        //date is given as filter for DOCTOR
        else {

            final List<String> list1 = new ArrayList<>();
            fstore.collection("AppByDocDate").document(text2)
                    .collection(text3)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                            list1.add(document.getId());
                        }
                        if (list1.size()==0) {
                            //Toast.makeText(getApplicationContext(), "list is empty- " + list.size(), Toast.LENGTH_SHORT).show();
                            text123.setVisibility(View.VISIBLE);
                            hiddenNumber.setVisibility(View.INVISIBLE);
                            makeappy.setVisibility(View.VISIBLE);
                            goBack.setVisibility(View.VISIBLE);

                            goBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
                                    finish();
                                }
                            });
                            makeappy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
                                    finish();
                                }
                            });
                            return;

                        }
                        else {
                            hiddenNumber.setText("Number of appointments to be shown- "+list1.size());
                            hiddenNumber.setVisibility(View.VISIBLE);
                        }

                    }
                }
            });


//            Toast.makeText(getApplicationContext(), "date was picked-" + text3, Toast.LENGTH_SHORT).show();

            //Query
            Query query = firebaseFirestore.collection("AppByDocDate")
                    .document(text2).collection(text3);
            //Recycler Options
            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query, ProductsModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                    holder.list_name.setText(model.getName());
                    holder.list_age.setText(model.getAge());
                    holder.list_sex.setText(model.getSex());
                    holder.list_phoneNumber.setText(model.getPhoneNumber());
                    holder.list_doctor.setText(model.getDoctor());
                    holder.list_diagnosis.setText(model.getDiagnosis());
                    holder.list_datePicked.setText(model.getDatePicked());
                    holder.list_hospitalNumber.setText(model.getHospitalNumber());
                    holder.list_otherDetails.setText(model.getOtherDetails());

                }
            };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(recylceViewAt1.this));
            mFirestoreList.setAdapter(adapter);





        }



    }


    if (text1.equals("Diagnosis"))
    {

        //Toast.makeText(getApplicationContext(), "text3 - " + text3, Toast.LENGTH_SHORT).show();

        if (text3.equals(""))
        {

            final List<String> list = new ArrayList<>();
            fstore.collection("Appointments").document("Via")
                    .collection(text1).document(text2)
                    .collection("Patients")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                            list.add(document.getId());
                        }
                        if (list.size()==0) {
                            //Toast.makeText(getApplicationContext(), "list is empty- " + list.size(), Toast.LENGTH_SHORT).show();
                            text123.setVisibility(View.VISIBLE);
                            hiddenNumber.setVisibility(View.INVISIBLE);
                            makeappy.setVisibility(View.VISIBLE);
                            goBack.setVisibility(View.VISIBLE);

                            goBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
                                    finish();
                                }
                            });
                            makeappy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
                                    finish();
                                }
                            });
                            return;

                        }
                        else {
                            hiddenNumber.setText("Number of appointments to be shown- "+list.size());
                            hiddenNumber.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });



//            Toast.makeText(getApplicationContext(), "nothin was picked-" + text3, Toast.LENGTH_SHORT).show();

            //Query
            Query query = firebaseFirestore.collection("Appointments").document("Via").collection(text1)
                    .document(text2).collection("Patients");
            //Recycler Options
            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query, ProductsModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                    holder.list_name.setText(model.getName());
                    holder.list_age.setText(model.getAge());
                    holder.list_sex.setText(model.getSex());
                    holder.list_phoneNumber.setText(model.getPhoneNumber());
                    holder.list_doctor.setText(model.getDoctor());
                    holder.list_diagnosis.setText(model.getDiagnosis());
                    holder.list_datePicked.setText(model.getDatePicked());
                    holder.list_hospitalNumber.setText(model.getHospitalNumber());
                    holder.list_otherDetails.setText(model.getOtherDetails());

                }
            };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
            mFirestoreList.setAdapter(adapter);




        }


        else {

            final List<String> list1 = new ArrayList<>();
            fstore.collection("AppByDiagDate").document(text2)
                    .collection(text3)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                            list1.add(document.getId());
                        }
                        if (list1.size()==0) {
                            //Toast.makeText(getApplicationContext(), "list is empty- " + list.size(), Toast.LENGTH_SHORT).show();
                            text123.setVisibility(View.VISIBLE);
                            hiddenNumber.setVisibility(View.INVISIBLE);
                            makeappy.setVisibility(View.VISIBLE);
                            goBack.setVisibility(View.VISIBLE);

                            goBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
                                    finish();
                                }
                            });
                            makeappy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
                                    finish();
                                }
                            });
                            return;

                        }
                        else {
                            hiddenNumber.setText("Number of appointments to be shown- "+list1.size());
                            hiddenNumber.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });



//            Toast.makeText(getApplicationContext(), "date was picked-" + text3, Toast.LENGTH_SHORT).show();

            //Query
            Query query = firebaseFirestore.collection("AppByDiagDate")
                    .document(text2).collection(text3);
            //Recycler Options
            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query, ProductsModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                    holder.list_name.setText(model.getName());
                    holder.list_age.setText(model.getAge());
                    holder.list_sex.setText(model.getSex());
                    holder.list_phoneNumber.setText(model.getPhoneNumber());
                    holder.list_doctor.setText(model.getDoctor());
                    holder.list_diagnosis.setText(model.getDiagnosis());
                    holder.list_datePicked.setText(model.getDatePicked());
                    holder.list_hospitalNumber.setText(model.getHospitalNumber());
                    holder.list_otherDetails.setText(model.getOtherDetails());

                }
            };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
            mFirestoreList.setAdapter(adapter);


        }



    }

    //if it's patientname, date, hospitalnumber,
    if (text1.equals("Date") || text1.equals("Hospital Number")){


        final List<String> list = new ArrayList<>();
        fstore.collection("Appointments").document("Via")
                .collection(text1).document(text2)
                .collection("Patients")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                        list.add(document.getId());
                    }
                    if (list.size()==0) {
                        //Toast.makeText(getApplicationContext(), "list is empty- " + list.size(), Toast.LENGTH_SHORT).show();
                        text123.setVisibility(View.VISIBLE);
                        hiddenNumber.setVisibility(View.INVISIBLE);
                        makeappy.setVisibility(View.VISIBLE);
                        goBack.setVisibility(View.VISIBLE);

                        goBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
                                finish();
                            }
                        });
                        makeappy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
                                finish();
                            }
                        });
                        return;

                    }
                    else {
                        hiddenNumber.setText("Number of appointments to be shown- "+list.size());
                        hiddenNumber.setVisibility(View.VISIBLE);
                    }
                }
            }
        });



//            Toast.makeText(getApplicationContext(), "nothin was picked-" + text3, Toast.LENGTH_SHORT).show();

        //Query
        Query query = firebaseFirestore.collection("Appointments").document("Via").collection(text1)
                .document(text2).collection("Patients");
        //Recycler Options
        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                holder.list_name.setText(model.getName());
                holder.list_age.setText(model.getAge());
                holder.list_sex.setText(model.getSex());
                holder.list_phoneNumber.setText(model.getPhoneNumber());
                holder.list_doctor.setText(model.getDoctor());
                holder.list_diagnosis.setText(model.getDiagnosis());
                holder.list_datePicked.setText(model.getDatePicked());
                holder.list_hospitalNumber.setText(model.getHospitalNumber());
                holder.list_otherDetails.setText(model.getOtherDetails());

            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);





    }

    if (text1.equals("Patient Name"))
    {

        if (text2.equals("picked"))
        {

            //Query
            Query query = firebaseFirestore.collection("Appointments")
                    .document("Via").collection(text1);
            //Recycler Options
            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query, ProductsModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                    holder.list_name.setText(model.getName());
                    holder.list_age.setText(model.getAge());
                    holder.list_sex.setText(model.getSex());
                    holder.list_phoneNumber.setText(model.getPhoneNumber());
                    holder.list_doctor.setText(model.getDoctor());
                    holder.list_diagnosis.setText(model.getDiagnosis());
                    holder.list_datePicked.setText(model.getDatePicked());
                    holder.list_hospitalNumber.setText(model.getHospitalNumber());
                    holder.list_otherDetails.setText(model.getOtherDetails());

                }
            };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
            mFirestoreList.setAdapter(adapter);

        }
        else {

            hiddenNumber.setText("Number of appointments to be shown- 1");
            hiddenNumber.setVisibility(View.VISIBLE);


            final List<String> list = new ArrayList<>();
            fstore.collection("Appointments").document("Via")
                    .collection(text1).document(text2)
                    .collection("Patients")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                            list.add(document.getId());
                        }
                        if (list.size()==0) {
                            //Toast.makeText(getApplicationContext(), "list is empty- " + list.size(), Toast.LENGTH_SHORT).show();
                            text123.setVisibility(View.VISIBLE);
                            hiddenNumber.setVisibility(View.INVISIBLE);
                            makeappy.setVisibility(View.VISIBLE);
                            goBack.setVisibility(View.VISIBLE);

                            goBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
                                    finish();
                                }
                            });
                            makeappy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
                                    finish();
                                }
                            });
                            return;

                        }
                    }
                }
            });



//            Toast.makeText(getApplicationContext(), "nothin was picked-" + text3, Toast.LENGTH_SHORT).show();

            //Query
            Query query = firebaseFirestore.collection("Appointments").document("Via").collection(text1)
                    .document(text2).collection("Patients");
            //Recycler Options
            FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                    .setQuery(query, ProductsModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
                @NonNull
                @Override
                public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
                    return new ProductsViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {

                    holder.list_name.setText(model.getName());
                    holder.list_age.setText(model.getAge());
                    holder.list_sex.setText(model.getSex());
                    holder.list_phoneNumber.setText(model.getPhoneNumber());
                    holder.list_doctor.setText(model.getDoctor());
                    holder.list_diagnosis.setText(model.getDiagnosis());
                    holder.list_datePicked.setText(model.getDatePicked());
                    holder.list_hospitalNumber.setText(model.getHospitalNumber());
                    holder.list_otherDetails.setText(model.getOtherDetails());

                }
            };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
            mFirestoreList.setAdapter(adapter);




        }

    }






    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_age;
        private TextView list_sex;
        private TextView list_phoneNumber;
        private TextView list_doctor;
        private TextView list_diagnosis;
        private TextView list_datePicked;
        private TextView list_hospitalNumber;
        private TextView list_otherDetails;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
            list_age = itemView.findViewById(R.id.list_age);
            list_sex = itemView.findViewById(R.id.list_sex);
            list_phoneNumber = itemView.findViewById(R.id.list_phoneNumber);
            list_doctor = itemView.findViewById(R.id.list_Doctor);
            list_diagnosis = itemView.findViewById(R.id.list_diagnosis);
            list_datePicked = itemView.findViewById(R.id.list_datePicked);
            list_hospitalNumber = itemView.findViewById(R.id.list_hospitalNumber);
            list_otherDetails = itemView.findViewById(R.id.list_otherDetails);

        }

    }


    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ViewAppointmentSearch.class));
    }
}
