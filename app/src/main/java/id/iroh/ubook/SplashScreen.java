package id.iroh.ubook;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private final android.os.Handler waitHandler = new android.os.Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();

            //After Opening the MainActivity this Activity will finish

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

    }

    @Override
    protected void onResume() {
        super.onResume();


        waitHandler.postDelayed(waitCallback, 1000);

    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }


}
