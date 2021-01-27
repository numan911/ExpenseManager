package SplashScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.expensemanager.R;
import com.example.expensemanager.UI.SignInActivity;

public class SplashScreen extends AppCompatActivity {

    ImageView iv;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iv = findViewById(R.id.imageView);
        getWindow().setStatusBarColor(Color.parseColor("#19678b"));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,SignInActivity.class));
                finish();
            }
        }, 2000);

    }
}