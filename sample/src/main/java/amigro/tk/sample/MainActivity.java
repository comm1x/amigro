package amigro.tk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import amigro.tk.amigro.Amigro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyMigrations();
    }

    private void applyMigrations() {
        Amigro.getInstance()
                .addMigration(1, new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Amigro", "1");
                    }
                })
                .addMigration(2, new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Amigro", "3");
                    }
                })
                .addMigration(3, new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Amigro", "6");
                    }
                })
                .apply(this);
    }
}
