package com.example.pisid2021.app.database;

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
    public static class ParametroCultura implements BaseColumns {
        public static final String TABLE_NAME="ParametroCultura";
        public static final String COLUMN_NAME_IdParametroCultura ="IdParametroCultura";
        public static final String COLUMN_NAME_IdCultura ="IdCultura";
        public static final String COLUMN_NAME_MinHumidade ="MinHumidade";
        public static final String COLUMN_NAME_MaxHumidade ="MaxHumidade";
        public static final String COLUMN_NAME_MinTemperatura ="MinTemperatura";
        public static final String COLUMN_NAME_MaxTemperatura ="MaxTemperatura";
        public static final String COLUMN_NAME_MinLuz ="MinLuz";
        public static final String COLUMN_NAME_MaxLuz ="MaxLuz";
        public static final String COLUMN_NAME_DangerZoneMinHumidade ="DangerZoneMinHumidade";
        public static final String COLUMN_NAME_DangerZoneMaxHumidade ="DangerZoneMaxHumidade";
        public static final String COLUMN_NAME_DangerZoneMinTemperatura ="DangerZoneMinTemperatura";
        public static final String COLUMN_NAME_DangerZoneMaxTemperatura ="DangerZoneMaxTemperatura";
        public static final String COLUMN_NAME_DangerZoneMinLuz ="DangerZoneMinLuz";
        public static final String COLUMN_NAME_DangerZoneMaxLuz ="DangerZoneMaxLuz";
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
            "DELETE FROM " + Medicao.TABLE_NAME + " WHERE " + Medicao.TABLE_NAME+"."+Medicao.COLUMN_NAME_HORA + " <= now() - interval 1 day";

    protected static final String SQL_CREATE_CULTURA =
            "CREATE TABLE " + Cultura.TABLE_NAME +
                    " (" + Cultura.COLUMN_NAME_ID_CULTURA + " INTEGER PRIMARY KEY," +
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

    protected static final String SQL_CREATE_PARAMETROCULTURA =
            "CREATE TABLE " + ParametroCultura.TABLE_NAME +
                    " (" + ParametroCultura.COLUMN_NAME_IdParametroCultura  + " INTEGER PRIMARY KEY," +
                    ParametroCultura.COLUMN_NAME_IdCultura                  + " INTEGER NOT NULL,"  +
                    ParametroCultura.COLUMN_NAME_MinHumidade                + " INTEGER NOT NULL,"  +
                    ParametroCultura.COLUMN_NAME_MaxHumidade                + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_MinTemperatura             + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_MaxTemperatura             + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_MinLuz                     + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_MaxLuz                     + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_DangerZoneMinHumidade      + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_DangerZoneMaxHumidade      + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_DangerZoneMinTemperatura   + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_DangerZoneMaxTemperatura   + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_DangerZoneMinLuz           + " DECIMAL(5,2) NOT NULL," +
                    ParametroCultura.COLUMN_NAME_DangerZoneMaxLuz           + " DECIMAL(5,2) NOT NULL" +
                    ")";

    protected static final String SQL_DELETE_PARAMETROCULTURA_DATA =
            "DELETE FROM " + ParametroCultura.TABLE_NAME;

    protected static final String SQL_CREATE_DROP_ALERTA_IFEXISTS=("DROP TABLE IF EXISTS ") + Alerta.TABLE_NAME;
    protected static final String SQL_CREATE_DROP_MEDICAO_IFEXISTS =("DROP TABLE IF EXISTS ") + Medicao.TABLE_NAME;
    protected static final String SQL_CREATE_DROP_CULTURA_IFEXISTS =("DROP TABLE IF EXISTS ") + Cultura.TABLE_NAME;
    protected static final String SQL_CREATE_DROP_PARAMETROCULTURA_IFEXISTS =("DROP TABLE IF EXISTS ") + ParametroCultura.TABLE_NAME;

}
