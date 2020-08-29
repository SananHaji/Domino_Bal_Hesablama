package com.example.dominobalhesablama;

import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.cardViewDizaynTutucu>{
    private Context context;
    private int AdamSayi;
    private int qayitsinMi;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public RVAdapter(Context context, int adamSayi, int qayitsinMi) {
        this.context = context;
        this.AdamSayi = adamSayi;
        this.qayitsinMi = qayitsinMi;
        sp = context.getSharedPreferences("AdamSayi", Context.MODE_PRIVATE);
        editor = sp.edit();


    }

    public class cardViewDizaynTutucu extends RecyclerView.ViewHolder{
        public Button buttonArrtir;
        public Button buttonAzalt;
        public TextView textViewBal;
        public Button buttonReset;
        public TextView textViewAdamAdi;
        public CardView cardViewSetir;


        public cardViewDizaynTutucu(@NonNull View itemView) {
            super(itemView);
            buttonArrtir = itemView.findViewById(R.id.buttonArttir);
            buttonAzalt = itemView.findViewById(R.id.buttonAzalt);
            textViewAdamAdi = itemView.findViewById(R.id.textViewAdamAdi);
            buttonReset = itemView.findViewById(R.id.buttonReset);
            textViewBal = itemView.findViewById(R.id.textViewBal);
            cardViewSetir = itemView.findViewById(R.id.cardViewSetir);
        }
    }


    @NonNull
    @Override
    public cardViewDizaynTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_rv_design, parent, false);
        return new cardViewDizaynTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final cardViewDizaynTutucu holder, final int position) {

        if(qayitsinMi==1) {
            holder.textViewBal.setText(String.valueOf(sp.getInt("Bal" + position, 0)));
            holder.textViewAdamAdi.setText(sp.getString(String.valueOf(position), "Ad seçmək üçün toxun")+" :");
        }
        else {
            Toast.makeText(context, "Yeni Adamlar Yaradıldı",Toast.LENGTH_SHORT).show();
        }

        holder.textViewAdamAdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(context).inflate(R.layout.alert_change_adam_adi, null);
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setView(view);
                final EditText editTextAlert = view.findViewById(R.id.editTextAlert);
                ad.setPositiveButton("Yadda saxla", new DialogInterface.OnClickListener() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.textViewAdamAdi.setText(editTextAlert.getText().toString()+" :");

                        editor.putString(String.valueOf(position), editTextAlert.getText().toString());
                        editor.commit();

                    }
                });
                ad.setNegativeButton("Ləğv Et", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Ad Dəyişdirilmədi", Toast.LENGTH_SHORT).show();
                    }
                });
                ad.create().show();
            }
        });

        holder.buttonArrtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewBal.setText(
                        String.valueOf(Integer.parseInt(holder.textViewBal.getText().toString().trim()) + 5)

                );
                editor.putInt("Bal"+ position, Integer.parseInt(holder.textViewBal.getText().toString()));
                editor.commit();

            }
        });

        holder.buttonAzalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewBal.setText(
                        String.valueOf(Integer.parseInt(holder.textViewBal.getText().toString().trim())-5)
                );
                editor.putInt("Bal"+ position, Integer.parseInt(holder.textViewBal.getText().toString()));
                editor.commit();
            }
        });

        holder.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("InflateParams") View tasarim = LayoutInflater.from(context).inflate(R.layout.alertview_sure_question, null);

                androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(context);

                ab.setView(tasarim);
                ab.setPositiveButton("Hə", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.textViewBal.setText("0");
                        editor.putInt("Bal"+ position, Integer.parseInt(holder.textViewBal.getText().toString()));
                        editor.commit();
                    }
                });
                ab.setNegativeButton("Yox", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Silinmədi...", Toast.LENGTH_SHORT).show();
                    }
                });
                ab.create().show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return AdamSayi;
    }




}
