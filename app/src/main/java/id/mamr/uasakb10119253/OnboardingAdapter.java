package id.mamr.uasakb10119253;

// NIM : 10119253
// NAMA : Mochamad Adi Maulia Rahman
// KELAS : IF-7

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_onboarding_item, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {

        private final TextView textJudulOnboarding;
        private final TextView textDeskripsiOnboarding;
        private final ImageView imageOnboarding;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textJudulOnboarding = itemView.findViewById(R.id.textJudulOnboarding);
            textDeskripsiOnboarding = itemView.findViewById(R.id.textDeskripsiOnboarding);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }

        void setOnboardingData(OnboardingItem onboardingItem) {
            textJudulOnboarding.setText(onboardingItem.getJudulOnboarding());
            textDeskripsiOnboarding.setText(onboardingItem.getDeskripsiOnboarding());
            imageOnboarding.setImageResource(onboardingItem.getImageOnboarding());
        }
    }
}
