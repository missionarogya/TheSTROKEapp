package missionarogya.thestrokeapp;

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

        try{
            JSONParser mJSONParser = new JSONParser(Diagnosis.this, hospitalInformation);
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

    public JSONParser(Activity activity, HospitalInformation hospitalInformation) {
        this.activity = activity;
        this.hospitalInformation = hospitalInformation;
    }

    @Override
    public String doInBackground(String... params) {
        try {
            URL url = new URL("http://192.168.1.3/strokeapp");
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
                Intent intent = new Intent(activity, Icons.class);
                activity.startActivity(intent);
            }
        }catch(Exception e){
        }
    }
}