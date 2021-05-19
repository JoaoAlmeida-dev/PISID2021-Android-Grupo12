package com.example.pisid2021.APP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pisid2021.APP.Connection.ConnectionHandler;
import com.example.pisid2021.APP.Database.DatabaseConfig;
import com.example.pisid2021.APP.Database.DatabaseHandler;
import com.example.pisid2021.APP.Database.DatabaseReader;
import com.example.pisid2021.APP.Helper.UserLogin;
import com.example.pisid2021.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_CULTURA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_HORA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_HORA_ESCRITA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_ID_PARAMETROCULTURA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_ID_UTILIZADOR;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_LEITURA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_NIVELALERTA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_SENSOR;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_TIPO_ALERTA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Alerta.COLUMN_NAME_ZONA;
import static com.example.pisid2021.APP.Database.DatabaseConfig.Cultura.*;
import java.util.Arrays;
import java.util.List;

public class VerCulturaActivity extends AppCompatActivity {

    private static final String IP = UserLogin.getInstance().getIp();
    private static final String PORT = UserLogin.getInstance().getPort();
    private static final String username= UserLogin.getInstance().getUsername();
    private static final String password = UserLogin.getInstance().getPassword();
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
        //params.put("date",date);
        //params.put("idUtilizador","1");
        ConnectionHandler jParser = new ConnectionHandler();
        JSONArray culturas = jParser.getJSONFromUrl(getCulturas, params);
        try{
            if(culturas!=null){
                for (int i=0;i< culturas.length();i++){
                    JSONObject c = culturas.getJSONObject(i);
                    String nomecultura = c.getString(COLUMN_NAME_NOMECULTURA);
                    int idUtilizador;
                    try {
                        idUtilizador = c.getInt(COLUMN_NAME_ID_UTILIZADOR);
                    } catch (Exception e) {
                        idUtilizador = 0;
                    }
                    int estado = c.getInt(COLUMN_NAME_ESTADO);
                    int idZona;
                    try {
                        idZona = c.getInt(COLUMN_NAME_ID_ZONA);
                    } catch (Exception e) {
                        idZona = 0;
                    }

                    db.insertCultura(nomecultura,idUtilizador,estado,idZona);

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
                COLUMN_NAME_NOMECULTURA
                , COLUMN_NAME_ID_UTILIZADOR
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

            TextView nomecultura = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    cursorCulturas.getString(cursorCulturas.getColumnIndex(COLUMN_NAME_NOMECULTURA)));

            TextView idUtilizador = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    Integer.toString(cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ID_UTILIZADOR))));

            TextView estado = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    Integer.toString(cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ESTADO))));

            TextView idZona = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    Integer.toString(cursorCulturas.getInt(cursorCulturas.getColumnIndex(COLUMN_NAME_ID_ZONA))));

            row.addView(nomecultura);
            row.addView(idUtilizador);
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
}