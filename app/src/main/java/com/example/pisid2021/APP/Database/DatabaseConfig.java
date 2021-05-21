package com.example.pisid2021.APP.Database;

import android.provider.BaseColumns;

public class DatabaseConfig {

    public static class Medicao implements BaseColumns {
        public static final String TABLE_NAME="Medicao";
        public static final String COLUMN_NAME_ID_MEDICAO ="IDMedicao";
        public static final String COLUMN_NAME_HORA ="Hora";
        public static final String COLUMN_NAME_LEITURA ="Leitura";
        public static final String COLUMN_NAME_TIPOLEITURA ="TipoLeitura";
        public static final String COLUMN_NAME_NOMECULTURA =Cultura.COLUMN_NAME_NOMECULTURA;
        //also has cultura name
    }

    public static class Cultura implements BaseColumns {
        public static final String TABLE_NAME="Cultura";
        public static final String COLUMN_NAME_ID_CULTURA ="IdCultura";
        public static final String COLUMN_NAME_NOMECULTURA ="NomeCultura";
        public static final String COLUMN_NAME_ID_UTILIZADOR ="IdUtilizador";
        public static final String COLUMN_NAME_ESTADO ="Estado";
        public static final String COLUMN_NAME_ID_ZONA ="IdZona";
    }

    public static class Alerta implements BaseColumns {
        public static final String TABLE_NAME                       ="Alerta";
        public static final String COLUMN_NAME_ID_ALERTA            ="IdAlerta";
        public static final String COLUMN_NAME_ZONA                 ="IdZona";
        public static final String COLUMN_NAME_SENSOR               ="IdSensor";
        public static final String COLUMN_NAME_HORA                 ="Hora";
        public static final String COLUMN_NAME_LEITURA              ="Leitura";
        public static final String COLUMN_NAME_TIPO_ALERTA          ="TipoAlerta";
        public static final String COLUMN_NAME_CULTURA              ="Cultura";
        public static final String COLUMN_NAME_ID_UTILIZADOR        ="IdUtilizador";
        public static final String COLUMN_NAME_HORA_ESCRITA         ="HoraEscrita";
        public static final String COLUMN_NAME_NIVELALERTA          ="NivelAlerta";
        public static final String COLUMN_NAME_ID_PARAMETROCULTURA  ="IdParametroCultura";
    }

    protected static final String SQL_CREATE_MEDICAO =
            "CREATE TABLE " + Medicao.TABLE_NAME +
                    " (" + Medicao.COLUMN_NAME_ID_MEDICAO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Medicao.COLUMN_NAME_HORA + " TIMESTAMP," +
                    Medicao.COLUMN_NAME_LEITURA + " DOUBLE,"+
                    Medicao.COLUMN_NAME_TIPOLEITURA + " VARCHAR(1)," +
                    Medicao.COLUMN_NAME_NOMECULTURA + " TEXT )";

    protected static final String SQL_DELETE_MEDICAO_DATA =
            "DELETE FROM " + Medicao.TABLE_NAME;

    protected static final String SQL_CREATE_CULTURA =
            "CREATE TABLE " + Cultura.TABLE_NAME +
                    " (" + Cultura.COLUMN_NAME_ID_CULTURA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Cultura.COLUMN_NAME_NOMECULTURA + " TEXT," +
                    Cultura.COLUMN_NAME_ID_UTILIZADOR + " INTEGER," +
                    Cultura.COLUMN_NAME_ESTADO + " INTEGER," +
                    Cultura.COLUMN_NAME_ID_ZONA + " INTEGER )";

    protected static final String SQL_DELETE_CULTURA_DATA =
            "DELETE FROM " + Cultura.TABLE_NAME;


    protected static final String SQL_CREATE_ALERTA =
            "CREATE TABLE " + Alerta.TABLE_NAME +
                    " (" + Alerta.COLUMN_NAME_ID_ALERTA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Alerta.COLUMN_NAME_ZONA                + " TEXT,"      +
                    Alerta.COLUMN_NAME_SENSOR              + " TEXT, "     +
                    Alerta.COLUMN_NAME_HORA                + " TIMESTAMP," +
                    Alerta.COLUMN_NAME_LEITURA             + " DOUBLE,"    +
                    Alerta.COLUMN_NAME_TIPO_ALERTA         + " TEXT,"      +
                    Alerta.COLUMN_NAME_CULTURA             + " TEXT,"      +
                    Alerta.COLUMN_NAME_ID_UTILIZADOR       + " INTEGER,"   +
                    Alerta.COLUMN_NAME_HORA_ESCRITA        + " TIMESTAMP," +
                    Alerta.COLUMN_NAME_NIVELALERTA         + " TEXT,"      +
                    Alerta.COLUMN_NAME_ID_PARAMETROCULTURA + " INTEGER"   +
                    ")";

    protected static final String SQL_DELETE_ALERTA_DATA =
            "DELETE FROM " + Alerta.TABLE_NAME;

    protected static final String SQL_CREATE_DROP_ALERTA_IFEXISTS=("DROP TABLE IF EXISTS ") + Alerta.TABLE_NAME;
    protected static final String SQL_CREATE_DROP_MEDICAO_IFEXISTS =("DROP TABLE IF EXISTS ") + Medicao.TABLE_NAME;
    protected static final String SQL_CREATE_DROP_CULTURA_IFEXISTS =("DROP TABLE IF EXISTS ") + Cultura.TABLE_NAME;

}
