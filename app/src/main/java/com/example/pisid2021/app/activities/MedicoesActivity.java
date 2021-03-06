package com.example.pisid2021.app.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pisid2021.app.connection.ConnectionHandler;
import com.example.pisid2021.app.database.DatabaseHandler;
import com.example.pisid2021.app.database.DatabaseReader;
import com.example.pisid2021.app.helper.UserLogin;
import com.example.pisid2021.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import android.os.Handler;

import static com.example.pisid2021.app.activities.VerCulturaActivity.CULTURA_ID_EXTRA;

public class MedicoesActivity extends AppCompatActivity {

    private static final String IP = UserLogin.getInstance().getIp();
    private static final String PORT = UserLogin.getInstance().getPort();
    private static final String username= UserLogin.getInstance().getUsername();
    private static final String password = UserLogin.getInstance().getPassword();
    public static final int GRAPHMAXTIMEPERIOD = 5*60;

    String getMedicoes = "http://" + IP + ":" + PORT + "/scripts/getMedicoesTemperatura.php";
    DatabaseHandler db = new DatabaseHandler(this);
    int culturaID;

    Handler h = new Handler();
    int delay = 1000; //1 second=1000 milisecond
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt(CULTURA_ID_EXTRA);
        culturaID=value;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicoes);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        updateMedicoes();
        drawGraphs();
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible
        h.postDelayed( runnable = new Runnable() {
            public void run() {
                updateMedicoes();
                drawGraphs();

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

    public void irCulturas(View v){
        Intent i = new Intent(this, VerCulturaActivity.class);
        startActivity(i);
    }
    public void irParametros(View v){
        Intent i = new Intent(this, AlterarParametroActivity.class);
        i.putExtra(CULTURA_ID_EXTRA,culturaID);
        startActivity(i);
    }

    private void updateMedicoes(){
        //db.clearMedicoes();
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("culturaID", String.valueOf(culturaID));
        ConnectionHandler jParser = new ConnectionHandler();
        JSONArray medicoes = jParser.getJSONFromUrl(getMedicoes, params);
        try {
            if (medicoes != null){
                for (int i=0;i< medicoes.length();i++){
                    JSONObject c = medicoes.getJSONObject(i);
                    String hora = c.getString("Hora");
                    double leitura;
                    try {
                        leitura = c.getDouble("Leitura");
                    } catch (Exception e) {
                        leitura = -1000.0;
                    }
                    char Tipo  = c.getString("Tipo").charAt(0);
                    String NomeCultura = c.getString("NomeCultura");
                    db.insertMedicao(hora, leitura,Tipo,NomeCultura);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void drawGraphs(){
        GraphView graphTemperatura = findViewById(R.id.Temperatura_graph);
        GraphView graphLuz = findViewById(R.id.Luz_graph);
        GraphView graphHumidade = findViewById(R.id.Humidade_graph);
        graphTemperatura.removeAllSeries();
        graphLuz.removeAllSeries();
        graphHumidade.removeAllSeries();


        DataPoint[] datapointsTemperatura = getDataPoints('T');
        LineGraphSeries<DataPoint> seriesTemperatura = new LineGraphSeries<>(datapointsTemperatura);
        seriesTemperatura.setColor(Color.RED);
        seriesTemperatura.setTitle("Temperatura");

        DataPoint[] datapointsLuz= getDataPoints('L');
        LineGraphSeries<DataPoint> seriesLuz = new LineGraphSeries<>(datapointsLuz);
        seriesLuz.setColor(Color.YELLOW);
        seriesLuz.setTitle("Luz");

        DataPoint[] datapointsHumidade = getDataPoints('H');
        LineGraphSeries<DataPoint> seriesHumidade = new LineGraphSeries<>(datapointsHumidade);
        seriesHumidade.setColor(Color.BLUE);
        seriesHumidade.setTitle("Humidade");


        setGraph(graphTemperatura);
        graphTemperatura.addSeries(seriesTemperatura);

        setGraph(graphLuz);
        graphLuz.addSeries(seriesLuz);

        setGraph(graphHumidade);
        graphHumidade.addSeries(seriesHumidade);
    }

    private void setGraph(GraphView graph) {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(300);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {
                   String.valueOf(6*GRAPHMAXTIMEPERIOD/6)
                ,  String.valueOf(5*GRAPHMAXTIMEPERIOD/6)
                ,  String.valueOf(4*GRAPHMAXTIMEPERIOD/6)
                ,  String.valueOf(3*GRAPHMAXTIMEPERIOD/6)
                ,  String.valueOf(2*GRAPHMAXTIMEPERIOD/6)
                , String.valueOf( GRAPHMAXTIMEPERIOD /6)
                , String.valueOf(0)
        });
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setBackgroundColor(Color.alpha(0));
    }

    @NonNull
    private DataPoint[] getDataPoints(char tipoMedicao) {
        int helper=0;
        Date currentTimestamp = new Date();
        long currentLong = currentTimestamp.getTime();
        DatabaseReader dbReader = new DatabaseReader(db);
        Cursor cursorTemperatura = dbReader.readMedicoes(tipoMedicao);
        DataPoint[] datapointsTemperatura = new DataPoint[cursorTemperatura.getCount()];

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (cursorTemperatura.moveToNext()){
            String hora =  cursorTemperatura.getString(cursorTemperatura.getColumnIndex("Hora"));
            double valorMedicao = cursorTemperatura.getDouble(cursorTemperatura.getColumnIndex("Leitura"));
            try {
                Date date = format.parse(hora);
                long pointLong = date.getTime();
                long difference = currentLong - pointLong;
                double seconds = GRAPHMAXTIMEPERIOD - TimeUnit.MILLISECONDS.toSeconds(difference);
                datapointsTemperatura[helper]=new DataPoint(seconds,valorMedicao);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            helper++;
        }
        cursorTemperatura.close();
        return datapointsTemperatura;
    }

}
