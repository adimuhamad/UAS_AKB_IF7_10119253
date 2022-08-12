package id.mamr.uasakb10119253;

// NIM : 10119253
// NAMA : Mochamad Adi Maulia Rahman
// KELAS : IF-7

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_onboarding);

//        checkFirstRun();

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        });
    }

//    private void checkFirstRun() {
//
//        final String PREFS_NAME = "MyPrefsFile";
//        final String PREF_VERSION_CODE_KEY = "version_code";
//        final int DOESNT_EXIST = -1;
//        int currentVersionCode = BuildConfig.VERSION_CODE;
//
//        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);
//        if (currentVersionCode == savedVersionCode) {
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            finish();
//        }
//        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
//    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemInformasi1 = new OnboardingItem();
        itemInformasi1.setJudulOnboarding("Selamat Datang");
        itemInformasi1.setDeskripsiOnboarding("Apakah Anda Siap Menjelajahi Dunia?");
        itemInformasi1.setImageOnboarding(R.drawable.welcome);

        OnboardingItem itemInformasi2 = new OnboardingItem();
        itemInformasi2.setJudulOnboarding("Simpan Catatan");
        itemInformasi2.setDeskripsiOnboarding("Catat Semua Hal Penting Agar Tidak Terlewatkan");
        itemInformasi2.setImageOnboarding(R.drawable.note_taking);

        OnboardingItem itemInformasi3 = new OnboardingItem();
        itemInformasi3.setJudulOnboarding("Bekerja Lebih Efektif");
        itemInformasi3.setDeskripsiOnboarding("Meningkatkan Kualitas Kerja Karena Semua Telah Tercatat");
        itemInformasi3.setImageOnboarding(R.drawable.note_many);

        onboardingItems.add(itemInformasi1);
        onboardingItems.add(itemInformasi2);
        onboardingItems.add(itemInformasi3);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i =0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_active
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Mulai");
        } else {
            buttonOnboardingAction.setText("Lanjut");
        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OnboardingActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}