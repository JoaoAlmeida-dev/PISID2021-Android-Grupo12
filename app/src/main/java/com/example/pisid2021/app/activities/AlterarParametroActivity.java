package com.example.pisid2021.app.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pisid2021.R;
import com.example.pisid2021.app.connection.ConnectionHandler;
import com.example.pisid2021.app.database.DatabaseConfig;
import com.example.pisid2021.app.database.DatabaseHandler;
import com.example.pisid2021.app.database.DatabaseReader;
import com.example.pisid2021.app.helper.UserLogin;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.example.pisid2021.app.activities.VerCulturaActivity.CULTURA_ID_EXTRA;

public class AlterarParametroActivity extends AppCompatActivity {

    private static final String IP = UserLogin.getInstance().getIp();
    private static final String PORT = UserLogin.getInstance().getPort();
    private static final String username= UserLogin.getInstance().getUsername();
    private static final String password = UserLogin.getInstance().getPassword();

    String getParametros = "http://" + IP + ":" + PORT + "/scripts/getParametroCultura.php";
    DatabaseHandler db = new DatabaseHandler(this);
    int culturaID;

    Handler h = new Handler();
    Runnable runnable;
    int delay = 1000; //1 second=1000 milisecond

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt(CULTURA_ID_EXTRA);
        culturaID=value;

        updateParametros();
        drawTextBoxes();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_parametro);
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible
        h.postDelayed( runnable = new Runnable() {
            public void run() {
                updateParametros();
                drawTextBoxes();
                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    private void drawTextBoxes() {
        EditText MinHumidade = findViewById(R.id.MinHumidade);
        EditText MaxHumidade = findViewById(R.id.MaxHumidade);
        EditText MinTemperatura = findViewById(R.id.MinTemperatura);
        EditText MaxTemperatura = findViewById(R.id.MaxTemperatura);
        EditText MinLuz = findViewById(R.id.MinLuz);
        EditText MaxLuz = findViewById(R.id.MaxLuz);
        EditText DangerZoneMinHumidade = findViewById(R.id.DangerZoneMinHumidade);
        EditText DangerZoneMaxHumidade = findViewById(R.id.DangerZoneMaxHumidade);
        EditText DangerZoneMinTemperatura = findViewById(R.id.DangerZoneMinTemperatura);
        EditText DangerZoneMaxTemperatura = findViewById(R.id.DangerZoneMaxTemperatura);
        EditText DangerZoneMinLuz = findViewById(R.id.DangerZoneMinLuz);
        EditText DangerZoneMaxLuz = findViewById(R.id.DangerZoneMaxLuz);

        int helper=0;
        Date currentTimestamp = new Date();
        long currentLong = currentTimestamp.getTime();
        DatabaseReader dbReader = new DatabaseReader(db);

        Cursor cursorParametros = dbReader.readParametroCulturas(culturaID);
        cursorParametros.moveToFirst();
        if (cursorParametros!=null && cursorParametros.moveToFirst()) {
            double MinHumidadeValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_MinHumidade));
            double MaxHumidadeValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_MaxHumidade));
            double MinTemperaturaValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_MinTemperatura));
            double MaxTemperaturaValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_MaxTemperatura));
            double MinLuzValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_MinLuz));
            double MaxLuzValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_MaxLuz));
            double DangerZoneMinHumidadeValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_DangerZoneMinHumidade));
            double DangerZoneMaxHumidadeValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_DangerZoneMaxHumidade));
            double DangerZoneMinTemperaturaValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_DangerZoneMinTemperatura));
            double DangerZoneMaxTemperaturaValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_DangerZoneMaxTemperatura));
            double DangerZoneMinLuzValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_DangerZoneMinLuz));
            double DangerZoneMaxLuzValue = cursorParametros.getDouble(cursorParametros.getColumnIndex(DatabaseConfig.ParametroCultura.COLUMN_NAME_DangerZoneMaxLuz));
            cursorParametros.close();
            MinHumidade.setText((int) MinHumidadeValue);
            MaxHumidade.setText((int) MaxHumidadeValue);
            MinTemperatura.setText((int) MinTemperaturaValue);
            MaxTemperatura.setText((int) MaxTemperaturaValue);
            MinLuz.setText((int) MinLuzValue);
            MaxLuz.setText((int) MaxLuzValue);
            DangerZoneMinHumidade.setText((int) DangerZoneMinHumidadeValue);
            DangerZoneMaxHumidade.setText((int) DangerZoneMaxHumidadeValue);
            DangerZoneMinTemperatura.setText((int) DangerZoneMinTemperaturaValue);
            DangerZoneMaxTemperatura.setText((int) DangerZoneMaxTemperaturaValue);
            DangerZoneMinLuz.setText((int) DangerZoneMinLuzValue);
            DangerZoneMaxLuz.setText((int) DangerZoneMaxLuzValue);
        }

    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    private void updateParametros(){
        db.clearMedicoes();
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("culturaID", String.valueOf(culturaID));
        ConnectionHandler jParser = new ConnectionHandler();
        JSONArray medicoes = jParser.getJSONFromUrl(getParametros, params);
        try {
            if (medicoes != null){
                for (int i=0;i< medicoes.length();i++){
                    JSONObject c = medicoes.getJSONObject(i);
                    int IdParametroCultura;
                    try {
                        IdParametroCultura = c.getInt("IdParametroCultura");
                    } catch (Exception e) {
                        IdParametroCultura = 0;
                    }
                    int IdCultura;
                    try {
                        IdCultura = c.getInt("IdCultura");
                    } catch (Exception e) {
                        IdCultura = 0;
                    }

                    double MinHumidade=updateMedicaoTry(c,"MinHumidade");
                    double MaxHumidade=updateMedicaoTry(c,"MaxHumidade");
                    double MinTemperatura=updateMedicaoTry(c,"MinTemperatura");
                    double MaxTemperatura=updateMedicaoTry(c,"MaxTemperatura");
                    double MinLuz=updateMedicaoTry(c,"MinLuz");
                    double MaxLuz=updateMedicaoTry(c,"MaxLuz");
                    double DangerZoneMinHumidade=updateMedicaoTry(c,"DangerZoneMinHumidade");
                    double DangerZoneMaxHumidade=updateMedicaoTry(c,"DangerZoneMaxHumidade");
                    double DangerZoneMinTemperatura=updateMedicaoTry(c,"DangerZoneMinTemperatura");
                    double DangerZoneMaxTemperatura=updateMedicaoTry(c,"DangerZoneMaxTemperatura");
                    double DangerZoneMinLuz=updateMedicaoTry(c,"DangerZoneMinLuz");
                    double DangerZoneMaxLuz=updateMedicaoTry(c,"DangerZoneMaxLuz");




                    db.insertParametroCultura(IdParametroCultura,IdCultura,
                            MinHumidade,MaxHumidade,
                            MinTemperatura,MaxTemperatura,
                            MinLuz,MaxLuz,
                            DangerZoneMinHumidade,DangerZoneMaxHumidade,
                            DangerZoneMinTemperatura,DangerZoneMaxTemperatura,
                            DangerZoneMinLuz,DangerZoneMaxLuz);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

private double updateMedicaoTry(JSONObject c,String value){
    try {
        return c.getDouble(value);
    } catch (Exception e) {
        return 0;
    }
}

    public void irMedicoes(View v){
        Intent i = new Intent(this, MedicoesActivity.class);
        i.putExtra(CULTURA_ID_EXTRA,culturaID);
        startActivity(i);
    }

    public void salvarParametros(View v){


    }

}