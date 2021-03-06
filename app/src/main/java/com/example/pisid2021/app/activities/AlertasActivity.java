package com.example.pisid2021.app.activities;

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

import com.example.pisid2021.app.connection.ConnectionHandler;
import com.example.pisid2021.app.database.DatabaseConfig;
import com.example.pisid2021.app.database.DatabaseHandler;
import com.example.pisid2021.app.database.DatabaseReader;
import com.example.pisid2021.app.helper.UserLogin;
import com.example.pisid2021.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.pisid2021.app.database.DatabaseConfig.Alerta.*;

public class AlertasActivity extends AppCompatActivity {

    private static String IP = UserLogin.getInstance().getIp();
    private static String PORT = UserLogin.getInstance().getPort();
    private static String username = UserLogin.getInstance().getUsername();
    private static String password = UserLogin.getInstance().getPassword();

    DatabaseHandler db = new DatabaseHandler(this);
    String getAlertas = "http://" + IP + ":" + PORT + "/scripts/getAlertasGlobais.php";
    int year;
    int month;
    int day;
    String date;

    Handler h = new Handler();
    int delay = 1000; //1 second=1000 milisecond
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        refreshLogin();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("appPref", MODE_PRIVATE);
        sp.edit().clear().commit();

        setContentView(R.layout.activity_alertas);

        if (getIntent().hasExtra("date")){
            int[] yearMonthDay = getIntent().getIntArrayExtra("date");
            year = yearMonthDay[0];
            month= yearMonthDay[1];
            day=yearMonthDay[2];
        } else {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH)+1;
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        dateToString();
        getAlertas();
        listAlertas();
    }

    private void dateToString() {
       String yearString = Integer.toString(year);
       String monthString ="";
       String dayString="";
        if (month<10){
            monthString="0"+Integer.toString(month);
        } else {
            monthString=Integer.toString(month);
        }
        if(day<10){
            dayString="0"+Integer.toString(day);
        } else {
            dayString=Integer.toString(day);
        }
        date = yearString+"-"+monthString+"-"+dayString;
        String formatted_date = dayString+"-"+monthString+"-"+yearString;
        TextView stringData = findViewById(R.id.diaSelecionado_tv);
        stringData.setText(formatted_date);

    }

    public void showDatePicker(View v) {
        Intent intent = new Intent(this,DatePickerActivity.class);
        intent.putExtra("global",1);
        startActivity(intent);
        finish();
    }

    private void getAlertas() {
                db.clearAlertas();
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("date",date);

                ConnectionHandler jParser = new ConnectionHandler();
                JSONArray alertas = jParser.getJSONFromUrl(getAlertas, params);
                try{
                    if(alertas!=null ){
                        for (int i=0;i< alertas.length();i++){
                            JSONObject c = alertas.getJSONObject(i);
                            String zona = c.getString(COLUMN_NAME_ZONA);
                            String sensor = c.getString(COLUMN_NAME_SENSOR);
                            String hora = c.getString(COLUMN_NAME_HORA);
                            double leitura;
                            try {
                                leitura = c.getDouble(COLUMN_NAME_LEITURA);
                            } catch (Exception e) {
                                leitura = -1000.0;
                            }
                            String tipoAlerta = c.getString(COLUMN_NAME_TIPO_ALERTA);
                            String cultura = c.getString(COLUMN_NAME_CULTURA);
                            //String mensagem = c.getString("Mensagem");
                            int idUtilizador;
                            try {
                                idUtilizador = c.getInt(COLUMN_NAME_ID_UTILIZADOR);
                            } catch (Exception e) {
                                idUtilizador = 0;
                            }

                            String horaEscrita = c.getString(DatabaseConfig.Alerta.COLUMN_NAME_HORA_ESCRITA);

                            String nivelAlerta = c.getString(DatabaseConfig.Alerta.COLUMN_NAME_NIVELALERTA);

                            int idParametroCultura;
                            try {
                                idParametroCultura = c.getInt(DatabaseConfig.Alerta.COLUMN_NAME_ID_PARAMETROCULTURA);
                            } catch (Exception e) {
                                idParametroCultura = 0;
                            }
                            //db.insertAlerta(zona, sensor, hora, leitura, tipoAlerta, cultura, mensagem, idUtilizador, idCultura, horaEscrita);

                            db.insertAlerta(zona, sensor, hora, leitura, tipoAlerta, cultura, idUtilizador,horaEscrita, nivelAlerta , idParametroCultura);

                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

    private void listAlertas() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("appPref", MODE_PRIVATE);

        long mostRecentEntry = 0L;

        TableLayout table = findViewById(R.id.tableAlertas);

        DatabaseReader dbReader = new DatabaseReader(db);
        Cursor cursorAlertas = dbReader.readAlertas();
        table.removeAllViewsInLayout();
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        List<String> alertaFields = Arrays.asList(
                  COLUMN_NAME_CULTURA

                , COLUMN_NAME_NIVELALERTA
                , COLUMN_NAME_TIPO_ALERTA
                , COLUMN_NAME_LEITURA
                , COLUMN_NAME_HORA

                , COLUMN_NAME_ZONA
                //, COLUMN_NAME_SENSOR
                //, COLUMN_NAME_ID_UTILIZADOR
                //, COLUMN_NAME_HORA_ESCRITA
                //, COLUMN_NAME_ID_PARAMETROCULTURA
        );

        for (String field: alertaFields) {
            TextView header = new TextView(this);
            header.setText(field);
            header.setTextSize(16);
            header.setPadding(dpAsPixels(16),dpAsPixels(50),0,10);

            headerRow.addView(header);
        }

        table.addView(headerRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        while (cursorAlertas.moveToNext()){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView zona = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_ZONA)));

            TextView sensor = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_SENSOR)));

            String horaString = cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_HORA));//.split(" ")[1];
            TextView hora = getTextView(dpAsPixels(16), dpAsPixels(5), 0, 0, horaString);

            TextView leitura = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(0), 0,
                    Double.toString(cursorAlertas.getDouble(cursorAlertas.getColumnIndex(COLUMN_NAME_LEITURA))));

            String tipoAlertaString = cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_TIPO_ALERTA));
            String tipoAlertaStringExtended;
            switch (tipoAlertaString.toUpperCase()){
                case "T":
                    tipoAlertaStringExtended = "Temperatura";
                    break;
                case "H":
                    tipoAlertaStringExtended = "Humidade";
                    break;
                case "L":
                    tipoAlertaStringExtended = "Luz";
                    break;
                default:
                    tipoAlertaStringExtended = "Indefenido";
                    break;
            }
            TextView tipoAlerta = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,tipoAlertaStringExtended);

            TextView cultura = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_CULTURA)));

            //TextView mensagem = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
            // cursorAlertas.getString(cursorAlertas.getColumnIndex("Mensagem")));

            //TextView idUtilizador = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
            //      Integer.toString(cursorAlertas.getInt(cursorAlertas.getColumnIndex(COLUMN_NAME_ID_UTILIZADOR))));

            //TextView idCultura = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
            //      Integer.toString(cursorAlertas.getInt(cursorAlertas.getColumnIndex("IDCultura"))));

            TextView horaEscrita = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_HORA_ESCRITA)));

            String nivelAlertaSring = cursorAlertas.getString(cursorAlertas.getColumnIndex(COLUMN_NAME_NIVELALERTA));
            TextView nivelAlerta = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,nivelAlertaSring);

            TextView parametroCultura = getTextView(dpAsPixels(16), dpAsPixels(5), dpAsPixels(5), 0,
                    Integer.toString(cursorAlertas.getInt(cursorAlertas.getColumnIndex(COLUMN_NAME_ID_PARAMETROCULTURA))));

            String longHora = horaString.replace(":", "").replace("-","").replace(" ","");
            long newHora = Long.parseLong(longHora);
            if (newHora > mostRecentEntry) {
                mostRecentEntry = newHora;
            }
            final long timePref = (long)sp.getLong("timePref", 0L);
            if (newHora > timePref) {
                long refreshPref = sp.getLong("refreshPref", 1L);
                if (refreshPref == 0L) {
                    nivelAlertaSring = nivelAlertaSring.toLowerCase();
                    if(nivelAlertaSring.contains("healthy")){
                        changeTextViewColor(zona, sensor, hora, leitura, tipoAlerta, cultura, horaEscrita, nivelAlerta, parametroCultura, Color.GREEN);
                    }else if(nivelAlertaSring.contains("danger")) {
                        changeTextViewColor(zona, sensor, hora, leitura, tipoAlerta, cultura, horaEscrita, nivelAlerta, parametroCultura, Color.RED);
                    }else if(nivelAlertaSring.contains("death")) {
                        changeTextViewColor(zona, sensor, hora, leitura, tipoAlerta, cultura, horaEscrita, nivelAlerta, parametroCultura, Color.BLACK);
                    }

                }
            }
            row.addView(cultura);

            row.addView(nivelAlerta);
            row.addView(tipoAlerta);
            row.addView(leitura);
            row.addView(hora);

            row.addView(zona);
            //row.addView(sensor);

            //row.addView(mensagem);
            //row.addView(idUtilizador);
            //row.addView(idCultura);
            //row.addView(horaEscrita);
            //row.addView(parametroCultura);

            table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        SharedPreferences.Editor editor = sp.edit().putLong("timePref", mostRecentEntry);
        editor.apply();
        SharedPreferences.Editor editor2 = sp.edit().putLong("refreshPref", 0L);
        editor2.apply();
        cursorAlertas.close();
    }

    private void changeTextViewColor(TextView zona, TextView sensor, TextView hora, TextView leitura, TextView tipoAlerta, TextView cultura, TextView horaEscrita, TextView nivelAlerta, TextView parametroCultura, int color) {
        zona.setTextColor(color);
        sensor.setTextColor(color);
        hora.setTextColor(color);
        leitura.setTextColor(color);
        tipoAlerta.setTextColor(color);
        cultura.setTextColor(color);
        horaEscrita.setTextColor(color);
        nivelAlerta.setTextColor(color);
        parametroCultura.setTextColor(color);
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

    public void medicoes(View v) {
        Intent i = new Intent(this, MedicoesActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        //refreshLogin();

        //start handler as activity become visible
        h.postDelayed( runnable = new Runnable() {
            public void run() {
                getAlertas();
                listAlertas();

                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    private void refreshLogin() {
        IP = UserLogin.getInstance().getIp();
        PORT = UserLogin.getInstance().getPort();
        username = UserLogin.getInstance().getUsername();
        password = UserLogin.getInstance().getPassword();
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }
    public void Principalmenu(View view){
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
        finish();
    }

}
