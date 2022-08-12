package id.mamr.uasakb10119253;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotesDB extends SQLiteOpenHelper {

    public static final String database_name = "Notes";
    public static final int database_version = 4;

    public static final String tabel_notes = "tabel_notes";
    public static final String id_notes = "id_notes";
    public static final String judul = "nama";
    public static final String tanggal_buat = "tanggal_buat";
    public static final String isi_catatan = "isi_catatan";
    public static final String created = "created";

    private final SQLiteDatabase db;

    public NotesDB(@Nullable Context context) {
        super(context, database_name, null, database_version);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tabel_notes + "("
                + id_notes + " integer primary key autoincrement, "
                + judul + " text not null, "
                + tanggal_buat + " text not null, "
                + isi_catatan + " text not null, "
                + created + " timestamp default (datetime('now', 'localtime')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tabel_notes");
    }

    public void insertNotes(ContentValues values){
        db.insert(tabel_notes, null, values);
    }

    public void updateNotes(ContentValues values, long id){
        db.update(tabel_notes, values, id_notes + "=" + id, null);
    }
    public void deleteNotes(long id){
        db.delete(tabel_notes, id_notes + "=" + id, null);
    }

    public Cursor getAllNotes(){
        return db.query(tabel_notes, null, null, null, null, null, judul + " ASC");
    }

    public Cursor getNotes(long id){
        return db.rawQuery("select * from " + tabel_notes + " where " + id_notes + "=" + id, null);
    }

}
