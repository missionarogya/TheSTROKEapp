package missionarogya.thestrokeapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Diagnosis extends AppCompatActivity {
    HospitalInformation hospitalInformation = HospitalInformation.getOurInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        final TextView error = (TextView)findViewById(R.id.error);
        final ImageButton refresh = (ImageButton)findViewById(R.id.refresh);
        final ImageButton home = (ImageButton)findViewById(R.id.home);

        getHospitalInformation(error, refresh, home);

       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Diagnosis.this, Icons.class);
               Diagnosis.this.startActivity(intent);
               Diagnosis.this.finish();
           }
       });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHospitalInformation(error, refresh, home);
            }
        });

        final Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Diagnosis.this, "You are exiting from this app!", Toast.LENGTH_SHORT).show();
                Diagnosis.this.finish();
            }
        });


    }

    private void getHospitalInformation(TextView error, ImageButton home, ImageButton refresh){
        try{
            JSONParser mJSONParser = new JSONParser(Diagnosis.this, hospitalInformation, error, refresh, home);
            mJSONParser.execute("");
        }catch(Exception e){
            Toast.makeText(Diagnosis.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diagnosis, menu);
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

class JSONParser extends AsyncTask<String, Void, String> {
    Activity activity;
    ProgressDialog progressDialog;
    String output = null;
    StringBuilder message = new StringBuilder();
    HospitalInformation hospitalInformation;
    TextView error;
    ImageButton refresh;
    ImageButton home;

    public JSONParser(Activity activity, HospitalInformation hospitalInformation, TextView error, ImageButton home, ImageButton refresh) {
        this.activity = activity;
        this.hospitalInformation = hospitalInformation;
        this.error = error;
        this.refresh = refresh;
        this.home = home;
    }

    @Override
    public String doInBackground(String... params) {
        try {
            URL url = new URL("http://kmes.in/stroke");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "*");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                message.append(output.toString());
            }
            os.close();
            conn.getInputStream().close();
            conn.disconnect();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
        return message.toString();
    }

    @Override
    protected void onPreExecute() {
        try {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Retrieving STROKE hospital information...");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressPercentFormat(null);
            progressDialog.setProgressNumberFormat(null);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }catch(Exception e){
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try{
            super.onPostExecute(result);
            progressDialog.dismiss();
            progressDialog = null;
            if(message.toString().length() > 0) {
                hospitalInformation.setHospitalInformation(message.toString());
                HospitalInformation.setOurInstance(hospitalInformation);
                Intent intent = new Intent(activity, HospitalInformationList.class);
                activity.startActivity(intent);
            }else{
                error.setVisibility(View.VISIBLE);
                home.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.VISIBLE);
                home.setClickable(true);
                refresh.setClickable(true);
            }
        }catch(Exception e){
        }
    }
}