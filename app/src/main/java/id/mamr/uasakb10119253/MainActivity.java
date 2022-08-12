package id.mamr.uasakb10119253;

// NIM : 10119253
// NAMA : Mochamad Adi Maulia Rahman
// KELAS : IF-7

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mauth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    return true;
                case R.id.nav_note:
                    startActivity(new Intent(getApplicationContext()
                            , NoteActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
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
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mauth.getCurrentUser();
//        if (currentUser == null) {
//            startActivity(new Intent(MainActivity.this,LoginActivity.class));
//            finish();
//        }
//    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}