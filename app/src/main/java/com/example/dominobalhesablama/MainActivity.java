package com.example.dominobalhesablama;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonTelefon;
    private EditText editTextSay;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private int qayitsinMi = 0;
    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********** Qayitsin Mi
//        qayitsinMiFunc();
//        promptForResult(new PromptRunnable(){
//            @Override
//            public void run() {
//                qayitsinMi = this.getValue();
//                Log.d("PromptRunngetValue()", String.valueOf(qayitsinMi));
//            }
//        });
//        Log.d("PromptRunngetValue()22", String.valueOf(qayitsinMi));
//        //***********
//        Log.d("qayitsinMi", String.valueOf(qayitsinMi));

        promptForResult(new DialogInputInterface() {
            @Override
            public View onBuildDialog() {
                return getLayoutInflater().inflate(R.layout.alert_yaddas_qayitsinmi, null);
            }

            @Override
            public void onCancel() {
                qayitsinMi = 0;
            }

            @Override
            public void onResult(View v) {
                qayitsinMi = 1;
                Intent intent = new Intent(MainActivity.this, Telefon.class);
                intent.putExtra("qayitsinMi", qayitsinMi);
                startActivity(intent);
                finish();

            }
        });


        buttonTelefon = findViewById(R.id.buttonTelefon);
        editTextSay = findViewById(R.id.editTextSay);

        sp = getSharedPreferences("AdamSayi", MODE_PRIVATE);
        editor = sp.edit();
//        editor.putInt("AdamSayiInt", sp.getInt("AdamSayiInt", 0));



//        editor.commit();
        buttonTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextSay.getText().toString().equals("")){
                Intent intent = new Intent(MainActivity.this, Telefon.class);
                intent.putExtra("qayitsinMi", 0);
                startActivity(intent);
                editor.putInt("AdamSayiInt", Integer.parseInt(editTextSay.getText().toString().trim()));
                editor.commit();
                finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "please fill fields", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    interface DialogInputInterface {
        // onBuildDialog() is called when the dialog builder is ready to accept a view to insert
        // into the dialog
        View onBuildDialog();
        // onCancel() is called when the user clicks on 'Cancel'
        void onCancel();
        // onResult(View v) is called when the user clicks on 'Ok'
        void onResult(View v);
    }
    void promptForResult(final DialogInputInterface dlg) {
        // replace "MyClass.this" with a Context object. If inserting into a class extending Activity,
        // using just "this" is perfectly ok.
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//        alert.setTitle(dlgTitle);
//        alert.setMessage(dlgMessage);
        // build the dialog
        final View v = dlg.onBuildDialog();
        // put the view obtained from the interface into the dialog
        if (v != null) { alert.setView(v);}
        // procedure for when the ok button is clicked.
        alert.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ** HERE IS WHERE THE MAGIC HAPPENS! **


                dlg.onResult(v);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dlg.onCancel();
                dialog.dismiss();
            }
        });
        alert.show();
    }




}
