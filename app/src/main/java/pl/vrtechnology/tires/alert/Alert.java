package pl.vrtechnology.tires.alert;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import pl.vrtechnology.tires.R;

public class Alert extends AppCompatTextView {

    private Handler handler;
    private Type alertType = Type.INFO;
    private Runnable delayedTask;

    public Alert(Context context) {
        super(context);
        init();
    }

    public Alert(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Alert(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        handler = new Handler();
        setTextSize(18);
        setPadding(0, 2, 0, 2);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setVisibility(INVISIBLE);
    }

    public void show(@NonNull String text, @NonNull Type type, long duration) {
        if (delayedTask != null) {
            handler.removeCallbacks(delayedTask);
        }

        setVisibility(VISIBLE);
        setText(text);
        setType(type);
        setDuration(duration);
        animateSlideIn();
    }

    public void show(@StringRes int textRes, @NonNull Type type, long duration) {
        show(getContext().getString(textRes), type, duration);
    }

    private void setDuration(long duration) {
        if (duration > 0) {
            delayedTask = this::hide;
            handler.postDelayed(delayedTask, duration);
        }
    }

    public void hide() {
        animateSlideOut();
    }

    private void setType(Type type) {
        this.alertType = type;
        updateAppearance();
    }

    private void updateAppearance() {
        switch (alertType) {
            case SUCCESS:
                setBackgroundColor(getResources().getColor(R.color.alert_success_background, getContext().getTheme()));
                setTextColor(getResources().getColor(R.color.alert_success_text, getContext().getTheme()));
                break;
            case ERROR:
                setBackgroundColor(getResources().getColor(R.color.alert_error_background, getContext().getTheme()));
                setTextColor(getResources().getColor(R.color.alert_error_text, getContext().getTheme()));
                break;
            case INFO:
            default:
                setBackgroundColor(getResources().getColor(R.color.alert_info_background, getContext().getTheme()));
                setTextColor(getResources().getColor(R.color.alert_info_text, getContext().getTheme()));
                break;
        }
    }

    private void animateSlideIn() {
        TranslateAnimation slideIn = new TranslateAnimation(0, 0, -getHeight(), 0);
        slideIn.setDuration(500);
        slideIn.setFillAfter(true);
        startAnimation(slideIn);
    }

    private void animateSlideOut() {
        TranslateAnimation slideOut = new TranslateAnimation(0, 0, 0, -getHeight());
        slideOut.setDuration(500);
        slideOut.setFillAfter(true);
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(slideOut);
    }

    public enum Type {
        INFO,
        SUCCESS,
        ERROR
    }
}
