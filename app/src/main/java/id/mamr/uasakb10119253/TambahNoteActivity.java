package id.mamr.uasakb10119253;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class TambahNoteActivity extends AppCompatActivity {

    private EditText etNama, etTanggalBuat, etIsiCatatan;
    private NotesDB database;
    private long id;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_note);

        intent = getIntent();
        id = intent.getLongExtra(NotesDB.id_notes,0);

        if(intent.hasExtra(NotesDB.id_notes)){
            setTitle("Edit Notes");
        }else{
            setTitle("Tambah Biodata");
        }

        etNama = findViewById(R.id.etJudul);
        etTanggalBuat = findViewById(R.id.etTanggalBuat);
        etIsiCatatan = findViewById(R.id.etIsiCatatan);

        Button btnSimpan = findViewById(R.id.btnSimpan);
        Button btnBatal = findViewById(R.id.btnBatal);

        database = new NotesDB(this);

        //SET TANGGAL LAHIR
        etTanggalBuat.setOnClickListener(v -> {
            Calendar getCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(TambahNoteActivity.this, (view, year, month, dayOfMonth) -> {
                Calendar setCalendar = Calendar.getInstance();
                setCalendar.set(Calendar.YEAR, year);
                setCalendar.set(Calendar.MONTH, month);
                setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String setCurrentDate = DateFormat.getDateInstance().format(setCalendar.getTime());
                etTanggalBuat.setText(setCurrentDate);
            },getCalendar.get(Calendar.YEAR), getCalendar.get(Calendar.MONTH), getCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        //SET BTNSIMPAN
        btnSimpan.setOnClickListener(v -> prosesSimpan());

        //set btnBatal
        btnBatal.setOnClickListener(v -> finish());
        getNotes();
    }

    private void getNotes(){
        Cursor cursor = database.getNotes(id);
        if(cursor.moveToFirst()){
            String nama = cursor.getString(cursor.getColumnIndexOrThrow(NotesDB.judul));
            String tanggalbuat = cursor.getString(cursor.getColumnIndexOrThrow(NotesDB.tanggal_buat));
            String isi_catatan = cursor.getString(cursor.getColumnIndexOrThrow(NotesDB.isi_catatan));

            etNama.setText(nama);
            etTanggalBuat.setText(tanggalbuat);
            etIsiCatatan.setText(isi_catatan);
        }
    }

    private void prosesSimpan(){
        String nama = etNama.getText().toString().trim();
        String tanggalbuat = etTanggalBuat.getText().toString().trim();
        String isicatatan = etIsiCatatan.getText().toString().trim();

        if (nama.isEmpty()){
            etNama.setError("Judul tidak boleh kosong");
        }else if(tanggalbuat.isEmpty()){
            etTanggalBuat.setError("Tanggal tidak boleh kosong");
        }else if (isicatatan.isEmpty()){
            etIsiCatatan.setError("Isi catatan tidak boleh kosong");
        }else{
            ContentValues values = new ContentValues();
            values.put(NotesDB.judul, nama);
            values.put(NotesDB.tanggal_buat, tanggalbuat);
            values.put(NotesDB.isi_catatan, isicatatan);
            if (intent.hasExtra(NotesDB.id_notes)){
                database.updateNotes(values, id);
            }else{
                database.insertNotes(values);
            }
            finish();
        }
    }
}