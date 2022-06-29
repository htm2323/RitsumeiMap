package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.nifcloud.mbaas.core.NCMB;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        NCMB.initialize(this.getApplicationContext(),
                "242065d2599016f30f6c72b88582430b94e97efe9b9aacdfe55875e16f740f9f",
                "da8cbfbb576f6c3ef0205be62528c7effe0fb5b9b7464d4604ccff0b004a67aa");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this, MapsActivity.class);
                startActivity(intent);

                finish();
            }
        }, 1000);
    }
}