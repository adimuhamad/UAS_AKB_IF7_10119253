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
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private Button logout_btn;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_about);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext()
                            , MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_note:
                    startActivity(new Intent(getApplicationContext()
                            , NoteActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_about:
                    return true;
            }
            return false;
        });

        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mauth.getCurrentUser();
//        if (currentUser == null) {
//            startActivity(new Intent(AboutActivity.this,LoginActivity.class));
//            finish();
//        }
//    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(AboutActivity.this,LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AboutActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}