package com.example.appintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListner {
    private RecyclerView recyclerView;
    private List<Employee2> list;
    private Adapter ad;
    private DataBase db;
    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DataBase(this);
//        db.additems(new Employee2(1,"anc","dfs"));
        db.opendb();
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ad = new Adapter(db,this);

        ad.setTasks(list);
        recyclerView.setAdapter(ad);

//        Employee2 em = new Employee2();
//        em.setName("this is test text");
//        em.setStatus(0);
//        em.setID(1);
//        db.additems(em);
//        list.add(em);
//        list.add(em);
//        list.add(em);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelp(ad));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        fb = findViewById(R.id.floatingActionButton);
        list = db.getGetAll();
        ad.setTasks(list);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNew.addNew().show(getSupportFragmentManager(), AddNew.TAG);
            }
        });
    }
        @Override
        public void handleDialogClose (DialogInterface dialog){
            list = db.getGetAll();
            ad.setTasks(list);
            ad.notifyDataSetChanged();
        }
    }
