package michalm.loginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michał on 2016-12-30.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;
    public static int ostZlecenie;
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "s4bi.db";

    private static final String TABLE_NAME_LOGIN = "login";
    private static final String COLUMN_ID_LOGIN = "id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASS = "password";


    public static final String TABLE_NAME_OGLOSZENIA = "ogloszenia";
    private static final String COLUMN_ID_OGLOSZENIA = "id_ogloszenia";
    private static final String COLUMN_TEMAT = "temat";
    private static final String COLUMN_KOMUNIKAT = "komunikat";
    private static final String COLUMN_DATA_OGLOSZENIA = "data_waznosci";



    //tabela do zakładki paszport techniczny
    public static final String TABLE_NAME_PASZPORT = "paszport_techniczny2";
    private static final String COLUMN_ID_PASZPORT = "id_paszport";
    private static final String COLUMN_NAZWA_PASZPORT = "nazwa";
    private static final String COLUMN_NAZWA_OSRODEK = "osrodek";
    private static final String COLUMN_STATUS_PASZPORT = "status";
    private static final String COLUMN_ELEMENT_PASZPORT = "element_nadrzedny";
    private static final String COLUMN_TYP_PASZPORT= "typ_model";
    private static final String COLUMN_LOKALIZACJA_PASZPORT = "lokalizacja_paszport";

    // tworzy tabele paszport techniczny
    private static final String CREATE_TABLE_PASZPORT = "create table paszport_techniczny2(id_paszport integer primary key not null , nazwa text not null,  osrodek text not null, " +
            "status text not null, element_nadrzedny text not null, typ_model text not null, lokalizacja_paszport text not null )";

    //tabela do zakładki lista zadan
    public static final String TABLE_NAME_LISTA_ZADAN = "lista_zadan";
    public static final String COLUMN_ID_ZADANIA = "id_zadania";
    private static final String COLUMN_OPIS_ZADANIA = "opis_zadania";
    private static final String COLUMN_LOKALIZACJA_ZADANIA = "zadanie_lokalizacja";
    private static final String COLUMN_STATUS_ZADANIA = "status_zadania";
    private static final String COLUMN_ZASOB_ZADANIA = "zasob_zadania";
    private static final String COLUMN_IMAGE_ZADANIA = "zdjecie";
    private static final String COLUMN_KOMENTARZ_ZADANIA = "komentarz";
    private static final String COLUMN_PODSUMOWANIE = "podsumowanie";
    private static final String COLUMN_SZCZEGOLY = "szczegoly";

    // tworzy tabele lista zadan
    private static final String CREATE_TABLE_LISTA_ZADAN = "create table lista_zadan(id_zadania integer primary key not null , opis_zadania text not null, " +
            "zadanie_lokalizacja text not null, status_zadania text not null, zasob_zadania text not null, zdjecie blob, komentarz text, podsumowanie text, szczegoly text)";

    private static final String CREATE_TABLE_OGLOSZENIA = "create table ogloszenia(id_ogloszenia integer primary key not null , temat text not null, komunikat text not null, data_waznosci text not null)";
    private static final String CREATE_TABLE_LOGIN = "create table login(id integer primary key not null , login text not null, password text not null )";

    public static final String DELETE_TABLE_OGLOSZENIA="DROP TABLE IF EXISTS " + TABLE_NAME_OGLOSZENIA;
    public static final String DELETE_TABLE_LOGIN="DROP TABLE IF EXISTS " + TABLE_NAME_LOGIN;
    public static final String DELETE_TABLE_PASZPORT="DROP TABLE IF EXISTS " + TABLE_NAME_PASZPORT;




    public DatabaseHelper(Context context){

    super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_LOGIN);
    db.execSQL(CREATE_TABLE_OGLOSZENIA);
    db.execSQL(CREATE_TABLE_PASZPORT);
    db.execSQL(CREATE_TABLE_LISTA_ZADAN);
        db.execSQL("INSERT INTO " + TABLE_NAME_LOGIN + "(id, login, password ) VALUES (1, 'user', 'user')");
        db.execSQL("INSERT INTO " + TABLE_NAME_LOGIN + "(id, login, password ) VALUES (2, 'admin', 'admin')");
        db.execSQL("INSERT INTO " + TABLE_NAME_PASZPORT + "(id_paszport, nazwa, osrodek, status, element_nadrzedny, typ_model, lokalizacja_paszport ) VALUES (1, 'Slup przelotowy nr 32','Krakow', 'Aktywny','Linia 110V Skibowki - Kamieniec', '-', 'Skibowki dzialka 159/3')");
        db.execSQL("INSERT INTO " + TABLE_NAME_PASZPORT + "(id_paszport, nazwa, osrodek, status, element_nadrzedny, typ_model, lokalizacja_paszport ) VALUES (2, 'Slup przelotowy nr 12','Katowice', 'Aktywny','Linia 110V Katowice - Tychy', '-', 'Katowice dzialka 166/4')");
        db.execSQL("INSERT INTO " + TABLE_NAME_PASZPORT + "(id_paszport, nazwa, osrodek, status, element_nadrzedny, typ_model, lokalizacja_paszport ) VALUES (3, 'Slup przelotowy nr 6','Tychy', 'Aktywny','Linia 110V Tychy - Lędziny', '-', 'Tychy dzialka 67/8')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (1, 'Awaria sieci', 'Sosnowiec, ul. Jagielońska', '01-03-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (2, 'Awaria sieci', 'Katowice, ul. 3 maja', '01-03-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (3, 'Awaria sieci', 'Bytom, ul. Franciszkanska', '10-04-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (4, 'Awaria sieci', 'Katowice, ul. Bażantów', '01-05-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (5, 'Awaria sieci', 'Tychy, ul. Słowików', '01-06-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (6, 'Awaria sieci', 'Gliwice, ul. Powstańców', '01-09-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (7, 'Awaria sieci', 'Katowice, ul. Jankego', '21-12-2017')");
        db.execSQL("INSERT INTO " + TABLE_NAME_OGLOSZENIA + "(id_ogloszenia, temat, komunikat, data_waznosci ) VALUES (8, 'Awaria sieci', 'Tychy, ul. Przemysłowa', '22-12-2017')");


    this.db =db;
            }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String query = "DROP TABLE IF EXISTS "+TABLE_NAME_LOGIN;
    String query2 = "DROP TABLE IF EXISTS "+TABLE_NAME_OGLOSZENIA;
    String query3 = "DROP TABLE IF EXISTS "+TABLE_NAME_PASZPORT;
    String query4 = "DROP TABLE IF EXISTS "+TABLE_NAME_LISTA_ZADAN;
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);

        this.onCreate(db);
    }

    public void insertUser(){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query="select * from login";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID_LOGIN, count);
        values.put(COLUMN_LOGIN, "user");
        values.put(COLUMN_PASS, "user");

        db.insert(TABLE_NAME_LOGIN, null, values);
        db.close();
    }


    public void insertOgloszenie(String temat, String komunikat, String data_ogloszenia){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query="select * from ogloszenia";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount() + 1;

        values.put(COLUMN_ID_OGLOSZENIA, count);
        values.put(COLUMN_TEMAT, temat);
        values.put(COLUMN_KOMUNIKAT, komunikat);
        values.put(COLUMN_DATA_OGLOSZENIA, data_ogloszenia);
        db.insert(TABLE_NAME_OGLOSZENIA, null, values);
        db.close();
    }

    public void insertPaszport(String nazwa, String osrodek, String status, String elemNad, String typ, String lok){
        db = this.getWritableDatabase();
        db.execSQL(DELETE_TABLE_PASZPORT);
        db.execSQL(CREATE_TABLE_PASZPORT);
        ContentValues values = new ContentValues();
        String query="select * from " + TABLE_NAME_PASZPORT;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount() + 1;

        values.put(COLUMN_ID_PASZPORT, count);
        values.put(COLUMN_NAZWA_PASZPORT, nazwa);
        values.put(COLUMN_NAZWA_OSRODEK, osrodek);
        values.put(COLUMN_STATUS_PASZPORT , status);
        values.put(COLUMN_ELEMENT_PASZPORT , elemNad);
        values.put(COLUMN_TYP_PASZPORT, typ);
        values.put(COLUMN_LOKALIZACJA_PASZPORT , lok);
        db.insert(TABLE_NAME_PASZPORT, null, values);
        db.close();
    }

    public String searchPass(String login){

    db = this.getReadableDatabase();
    String query ="select login, password from "+TABLE_NAME_LOGIN;
    Cursor cursor = db.rawQuery(query, null);
    String a, b;
        b= "nie znaleziono";
        if(cursor.moveToFirst())
    {
        do{
            a = cursor.getString(0);

            if(a.equals(login)){
                b = cursor.getString(1);
                break;
            }

        }
        while(cursor.moveToNext());
    }
    db.close();
    return b;
    }


    public boolean insertZlecenie(String opis_zadania, String zadanie_lokalizacja, String zasob_zadania, byte[] image_zadania, String podsumowanie, String szczegoly){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query="select id_zadania from lista_zadan";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount() + 1;
        ostZlecenie = count;
        values.put(COLUMN_ID_ZADANIA, count);
        values.put(COLUMN_OPIS_ZADANIA, opis_zadania);
        values.put(COLUMN_LOKALIZACJA_ZADANIA, zadanie_lokalizacja);
        values.put(COLUMN_STATUS_ZADANIA, "NOWE");
        values.put(COLUMN_ZASOB_ZADANIA, zasob_zadania);
        values.put(COLUMN_IMAGE_ZADANIA, image_zadania);
        values.put(COLUMN_PODSUMOWANIE, podsumowanie);
        values.put(COLUMN_SZCZEGOLY, szczegoly);
        long result = db.insert(TABLE_NAME_LISTA_ZADAN, null, values);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public void updatePhoto(int id ,byte[] image){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_ZADANIA, image);

        db.update(TABLE_NAME_LISTA_ZADAN, values, "id_zadania=" + id, null );
        db.close();
    }

    public void updateStatusZlecenia(int id ,String status, String komentarz){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_ZADANIA, status);
        values.put(COLUMN_KOMENTARZ_ZADANIA, komentarz);
        db.update(TABLE_NAME_LISTA_ZADAN, values, "id_zadania=" + id, null );
        db.close();
    }

}
