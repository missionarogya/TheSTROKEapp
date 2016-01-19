package missionarogya.thestrokeapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        final ImageButton go =(ImageButton)findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "Please select an option before you proceed.", Toast.LENGTH_SHORT).show();
                } else if (selectedId == R.id.yes) {
                    Toast.makeText(MainActivity.this, "Please take the STROKE test.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, EvaluateSymptoms.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                } else if (selectedId == R.id.no) {
                    Intent intent = new Intent(MainActivity.this, Icons.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                }
                // RadioButton radioButton = (RadioButton) findViewById(selectedId);
                //Toast.makeText(MainActivity.this, Integer.toString(radioButton.getId()), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
