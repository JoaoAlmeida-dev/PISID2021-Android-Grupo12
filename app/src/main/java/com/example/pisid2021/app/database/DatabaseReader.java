package com.example.pisid2021.app.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseReader {
    SQLiteDatabase db;

    public DatabaseReader(DatabaseHandler dbHandler){
        db = dbHandler.getReadableDatabase();
    }

    public Cursor readMedicoes(char tipoMedicao){
        Cursor cursor = db.query(
                DatabaseConfig.Medicao.TABLE_NAME,
                null,
                DatabaseConfig.Medicao.COLUMN_NAME_TIPOLEITURA+"='"+tipoMedicao+"'",
                null,
                null,
                null,
                DatabaseConfig.Medicao.COLUMN_NAME_HORA + " ASC"
        );

        return cursor;
    }

    public Cursor readAlertas(){
        Cursor cursor = db.query(
                DatabaseConfig.Alerta.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseConfig.Alerta.COLUMN_NAME_HORA + " DESC LIMIT 100"
        );
        return cursor;
    }

    public Cursor readCulturas(){
        Cursor cursor = db.query(
                DatabaseConfig.Cultura.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseConfig.Cultura.COLUMN_NAME_NOMECULTURA + " DESC"
        );
        return cursor;
    }
    public Cursor readParametroCulturas(int id){
        Cursor cursor = db.query(
                DatabaseConfig.ParametroCultura.TABLE_NAME,
                null,
                DatabaseConfig.ParametroCultura.COLUMN_NAME_IdCultura + "='" + id+"'",
                null,
                null,
                null,
                DatabaseConfig.ParametroCultura.COLUMN_NAME_IdParametroCultura + " DESC LIMIT 1"
        );
        return cursor;
    }
}
