package missionarogya.thestrokeapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Icons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icons);

        final ImageButton kmes = (ImageButton) findViewById(R.id.kmes);
        kmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Icons.this, "You are being redirected to the KMES mobile website!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://m.kmes.in"));
                startActivity(intent);
            }
        });

        final ImageButton doctor = (ImageButton) findViewById(R.id.doctor);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Icons.this, "You are being redirected to the 'Request a DOCTOR app'!", Toast.LENGTH_SHORT).show();
            }
        });

        final Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Icons.this, "You are exiting from this app!", Toast.LENGTH_SHORT).show();
                Icons.this.finish();
            }
        });

        final ImageButton stroke = (ImageButton) findViewById(R.id.stroke);
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Icons.this, "Plaese take the STROKE test!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Icons.this, EvaluateSymptoms.class);
                Icons.this.startActivity(intent);
                Icons.this.finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Icons.this, MainActivity.class);
        Icons.this.startActivity(intent);
        Icons.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
