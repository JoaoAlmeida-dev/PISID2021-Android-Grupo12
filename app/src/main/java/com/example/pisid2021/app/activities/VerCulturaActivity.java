package com.example.pisid2021.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pisid2021.app.connection.ConnectionHandler;
import com.example.pisid2021.app.database.DatabaseHandler;
import com.example.pisid2021.app.database.DatabaseReader;
import com.example.pisid2021.app.helper.UserLogin;
import com.example.pisid2021.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.example.pisid2021.app.database.DatabaseConfig.Alerta.COLUMN_NAME_ID_UTILIZADOR;
import static com.example.pisid2021.app.database.DatabaseConfig.Cultura.*;

public class VerCulturaActivity extends AppCompatActivity {

    private static final String IP = UserLogin.getInstance().getIp();
    private static final String PORT = UserLogin.getInstance().getPort();
    private static final String username= UserLogin.getInstance().getUsername();
    private static final String password = UserLogin.getInstance().getPassword();
    public static final String CULTURA_ID_EXTRA = "culturaID";
    DatabaseHandler db = new DatabaseHandler(this);
    String getCulturas = "http://" + IP + ":" + PORT + "/scripts/getCulturas.php";
    Handler h = new Handler();
    int delay = 1000; //1 second=1000 milisecond
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cultura);

        getCulturas();
        listCulturas();
    }

    private void getCulturas() {
        db.clearCulturas();
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        ConnectionHandler jParser = new ConnectionHandler();
        JSONArray culturas = jParser.getJSONFromUrl(getCulturas, params);
        try{
            if(culturas!=null){
                for (int i=0;i< culturas.length();i++){
                    JSONObject c = culturas.getJSONObject(i);

                    int idCultura;
                    try {
                        idCultura = c.getInt(COLUMN_NAME_ID_CULTURA);
                    } catch (Exception e) {
                        idCultura = 0;
                    }

                    String nomecultura = c.getString(COLUMN_NAME_NOMECULTURA);

                    int idUtilizador;
                    try {
                        idUtilizador = c.getInt(COLUMN_NAME_ID_UTILIZADOR);
                    } catch (Exception e) {
                        idUtilizador = 0;
                    }

                    int estado;
                    try {
                        estado = c.getInt(COLUMN_NAME_ESTADO);
                    } catch (Exception e) {
                        estado = 0;
                    }

                    int idZona;
                    try {
                        idZona = c.getInt(COLUMN_NAME_ID_ZONA);
                    } catch (Exception e) {
                        idZona = 0;
                    }

                    db.insertCultura(idCultura,nomecultura,idUtilizador,estado,idZona);

                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void listCulturas() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("appPref", MODE_PRIVATE);
        int mostRecentEntry = 0;

        TableLayout table = findViewById(R.id.tableCulturas);

        DatabaseReader dbReader = new DatabaseReader(db);
        Cursor cursorCulturas = dbReader.readCulturas();
        table.removeAllViewsInLayout();
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        List<String> culturaFields = Arrays.asList(
                "Medições"
                ,COLUMN_NAME_NOMECULTURA
                , COLUMN_NAME_ESTADO
                , COLUMN_NAME_ID_ZONA
        );

        for (String field: culturaFields) {
            TextView header = new TextView(this);
            header.setText(field);
            header.setTextSize(16);
            header.setPadding(dpAsPixels(16),dpAsPixels(50),0,10);

            headerRow.addView(header);
        }

        table.addView(headerRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        while (cursorCulturas.moveToNext()){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            int left=       dpAsPixels(16);
            int top =       dpAsPixels(5);
            int right =     dpAsPixels(5);
            int bottom =    0;
            Button medicoes = new Button(this);
            medicoes.setText("medicoes");
            final String culturaName = cursorCulturas.getString(cursorCulturas.getColumnIndex(COLUMN_NAME_NOMECULTURA));
            int culturaID ;
            try {
                culturaID=cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ID_CULTURA));
            }catch(Exception e){
                culturaID = 0;
            }
            final int finalCulturaID = culturaID;
            medicoes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(VerCulturaActivity.this, MedicoesActivity.class);
                    i.putExtra(CULTURA_ID_EXTRA, finalCulturaID);
                    startActivity(i);
                    finish();
                }
            });

            TextView nomecultura = getTextView(left, top, right, bottom,
                    culturaName);

            //TextView idUtilizador = getTextView(left, top, right, bottom,
            //        Integer.toString(cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ID_UTILIZADOR))));

            TextView estado = getTextView(left, top, right, bottom,
                    Integer.toString(cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ESTADO))));

            TextView idZona = getTextView(left, top, right, bottom,
                    Integer.toString(cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ID_ZONA))));

            row.addView(medicoes);
            row.addView(nomecultura);
            //row.addView(idUtilizador);
            row.addView(estado);
            row.addView(idZona);

            table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        SharedPreferences.Editor editor = sp.edit().putInt("timePref", mostRecentEntry);
        editor.apply();
        SharedPreferences.Editor editor2 = sp.edit().putInt("refreshPref", 0);
        editor2.apply();
    }

    @NonNull
    private TextView getTextView(int left, int top, int right, int bottom, String valor) {
        TextView zona = new TextView(this);
        if (valor == null || valor.equals("null") || valor.equals("-1000.0")) valor = "";
        zona.setText(valor);
        zona.setPadding(left, top, right, bottom);
        return zona;
    }

    private int dpAsPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible
        h.postDelayed( runnable = new Runnable() {
            public void run() {
                getCulturas();
                listCulturas();

                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    public void voltarInicio(View v){
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

}