package com.example.pisid2021.APP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.example.pisid2021.APP.Database.DatabaseConfig.Cultura.*;
import java.util.Arrays;
import java.util.List;

public class VerCulturaActivity extends AppCompatActivity {

    private ListView lista;

    List<String> CulturaFields = Arrays.asList(
            COLUMN_NAME_NOMECULTURA,
            COLUMN_NAME_ID_UTILIZADOR,
            COLUMN_NAME_ESTADO
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cultura);

        lista.findViewById(R.id.InvestCulturaTemple);
        ArrayAdapter<String> adap= new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                CulturaFields
        );
        lista.setAdapter(adap);
    }
}