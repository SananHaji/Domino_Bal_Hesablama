package com.example.dominobalhesablama;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Telefon extends AppCompatActivity {
    private Context context;
    private int AdamSayi;
    private FloatingActionButton floatingActionButtonAdamElaveEt, floatingActionButtonAdamAzalt;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerViewTelefon;
    private int qayitsinMi;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefon);

        qayitsinMi = getIntent().getExtras().getInt("qayitsinMi");

        floatingActionButtonAdamElaveEt = findViewById(R.id.floatingActionButtonAdamElaveEt);
        floatingActionButtonAdamAzalt = findViewById(R.id.floatingActionButtonAdamAzalt);
        recyclerViewTelefon = findViewById(R.id.recyclerViewTelefon);

        sp = getSharedPreferences("AdamSayi", MODE_PRIVATE);
        editor = sp.edit();

        AdamSayi = sp.getInt("AdamSayiInt", 0);

        recyclerViewTelefon.setHasFixedSize(true);
        recyclerViewTelefon.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTelefon.addItemDecoration(new SimpleDividerItemDecoration(this));

        RVAdapter rvAdapter = new RVAdapter(this, AdamSayi, qayitsinMi);

        recyclerViewTelefon.setAdapter(rvAdapter);


        floatingActionButtonAdamElaveEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Yeni Adam Əlavə Olundu", Toast.LENGTH_SHORT).show();
                AdamSayi+=1;
                editor.putInt("AdamSayiInt", AdamSayi);
                editor.commit();
                qayitsinMi = 1;
                RVAdapter rvAdapter = new RVAdapter(Telefon.this, AdamSayi, qayitsinMi);
                recyclerViewTelefon.setAdapter(rvAdapter);

            }
        });

        floatingActionButtonAdamAzalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdamSayi <= 0){
                    Toast.makeText(getApplicationContext(), "0 Oyunçu var!", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "1 Adam Silindi", Toast.LENGTH_SHORT).show();
                    AdamSayi -= 1;
                    editor.putInt("AdamSayiInt", AdamSayi);
                    editor.commit();
                    qayitsinMi = 1;
                    RVAdapter rvAdapter = new RVAdapter(Telefon.this, AdamSayi, qayitsinMi);
                    recyclerViewTelefon.setAdapter(rvAdapter);
                }
            }
        });

    }

}
