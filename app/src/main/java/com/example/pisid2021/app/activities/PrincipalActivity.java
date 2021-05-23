package com.example.pisid2021.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pisid2021.R;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }
    public void TemplAlertas(View view){
        Intent i = new Intent(this, AlertasActivity.class);
        startActivity(i);
        finish();
    }
    public void irMedicao(View view){
        Intent i = new Intent(this, MedicoesActivity.class);
        startActivity(i);
        finish();
    }
    public void verCultura(View view){
        Intent i = new Intent(this, VerCulturaActivity.class);
        startActivity(i);
        finish();
    }
    public void logoutIn(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }


}