package id.mamr.uasakb10119253;

// NIM : 10119253
// NAMA : Mochamad Adi Maulia Rahman
// KELAS : IF-7

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteActivity extends AppCompatActivity {

    private NotesAdapter notesAdapter;
    private NotesDB database;
    private LinearLayout linearEditNotes, linearDeleteNotes;
    private Button btnBatalPopup;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_note);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext()
                            , MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_note:
                    return true;
                case R.id.nav_about:
                    startActivity(new Intent(getApplicationContext()
                            , AboutActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
            }
            return false;
        });

        //db inisialisasi
        database = new NotesDB(this);

        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        FloatingActionButton fabAddNotes = findViewById(R.id.fabAddNotes);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(this, database.getAllNotes());
        rvNotes.setAdapter(notesAdapter);
        notesAdapter.swapCursor(database.getAllNotes());

        fabAddNotes.setOnClickListener(v -> startActivity(new Intent(NoteActivity.this, TambahNoteActivity.class)));

        notesAdapter.setOnClickListenerNotes(id -> {
            LayoutInflater inflater = LayoutInflater.from(NoteActivity.this);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.popup_notes, null);
            linearEditNotes = view.findViewById(R.id.linearEditNotes);
            linearDeleteNotes = view.findViewById(R.id.linearDeleteNotes);
            btnBatalPopup = view.findViewById(R.id.btnBatalPopup);

            Dialog popupNotes = new Dialog(NoteActivity.this);
            popupNotes.setContentView(view);
            popupNotes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupNotes.setOnShowListener(dialog -> {
                linearEditNotes.setOnClickListener(v -> {
                    Intent editNotes = new Intent(NoteActivity.this, TambahNoteActivity.class);
                    editNotes.putExtra(NotesDB.id_notes, id);
                    startActivity(editNotes);
                    popupNotes.dismiss();
                });
                linearDeleteNotes.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                    builder.setTitle("Konfirmasi");
                    builder.setMessage("Apakah anda yakin ingin menghapus data ini?");
                    builder.setPositiveButton("Ya", (dialog1, which) -> {
                        database.deleteNotes(id);
                        popupNotes.dismiss();
                        notesAdapter.swapCursor(database.getAllNotes());
                    }).setNegativeButton("Tidak", (dialog1, which) -> popupNotes.dismiss());
                    AlertDialog popupKonfirmasi = builder.create();
                    popupKonfirmasi.show();
                });
                btnBatalPopup.setOnClickListener(v -> popupNotes.dismiss());
            });
            popupNotes.show();
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mauth.getCurrentUser();
//        if (currentUser == null) {
//            startActivity(new Intent(NoteActivity.this,LoginActivity.class));
//            finish();
//        }
//    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NoteActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}