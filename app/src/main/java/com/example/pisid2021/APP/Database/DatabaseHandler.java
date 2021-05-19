package com.example.pisid2021.APP.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "PISID.db";
    DatabaseConfig config = new DatabaseConfig();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        dropAndCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        dropAndCreate(sqLiteDatabase);
    }

    private void dropAndCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseConfig.SQL_CREATE_DROP_MEDICAO_IFEXISTS);
        sqLiteDatabase.execSQL(DatabaseConfig.SQL_CREATE_MEDICAO);

        sqLiteDatabase.execSQL(DatabaseConfig.SQL_CREATE_DROP_ALERTA_IFEXISTS);
        sqLiteDatabase.execSQL(DatabaseConfig.SQL_CREATE_ALERTA);

        sqLiteDatabase.execSQL(DatabaseConfig.SQL_CREATE_DROP_CULTURA_IFEXISTS);
        sqLiteDatabase.execSQL(DatabaseConfig.SQL_CREATE_CULTURA);
    }

    public void insertMedicao(String hora, double leitura) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConfig.Medicao.COLUMN_NAME_HORA, hora);
        values.put(DatabaseConfig.Medicao.COLUMN_NAME_LEITURA, leitura);
        getWritableDatabase().insert(DatabaseConfig.Medicao.TABLE_NAME,null, values);
    }

    public void insertAlerta(String zona, String sensor, String hora, double leitura, String tipoAlerta, String cultura, int idUtilizador, String horaEscrita, String NivelAlerta, int IdParametroCultura) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_ZONA, zona);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_SENSOR, sensor);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_HORA, hora);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_LEITURA, leitura);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_TIPO_ALERTA, tipoAlerta);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_CULTURA, cultura);
        //values.put(DatabaseConfig.Alerta.COLUMN_NAME_MENSAGEM, mensagem);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_ID_UTILIZADOR, idUtilizador);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_HORA_ESCRITA, horaEscrita);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_NIVELALERTA, NivelAlerta);
        values.put(DatabaseConfig.Alerta.COLUMN_NAME_ID_PARAMETROCULTURA, IdParametroCultura);
        getWritableDatabase().insert(DatabaseConfig.Alerta.TABLE_NAME,null, values);
    }


    public void insertCultura(String nomecultura, int idUtilizador, int estado, int idZona) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConfig.Cultura.COLUMN_NAME_NOMECULTURA, nomecultura);
        values.put(DatabaseConfig.Cultura.COLUMN_NAME_ID_UTILIZADOR, idUtilizador);
        values.put(DatabaseConfig.Cultura.COLUMN_NAME_ESTADO, estado);
        values.put(DatabaseConfig.Cultura.COLUMN_NAME_ID_ZONA, idZona);
        getWritableDatabase().insert(DatabaseConfig.Cultura.TABLE_NAME,null, values);
    }

    public void clearAlertas() { getWritableDatabase().execSQL(DatabaseConfig.SQL_DELETE_ALERTA_DATA); }

    public void clearCulturas() { getWritableDatabase().execSQL(DatabaseConfig.SQL_DELETE_CULTURA_DATA); }

    public void clearMedicoes() {
        getWritableDatabase().execSQL(DatabaseConfig.SQL_DELETE_MEDICAO_DATA);
    }

}
