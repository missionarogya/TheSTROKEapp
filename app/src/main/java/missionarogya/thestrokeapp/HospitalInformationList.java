package missionarogya.thestrokeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

public class HospitalInformationList extends AppCompatActivity {
    HospitalInformation hospitalInformation = HospitalInformation.getOurInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_information_list);

        String info = hospitalInformation.getHospitalInformation();
        if(info!=null && info.length()>0) {
            try {
                JSONArray hospitalArray = new JSONArray(info);
                for (int n = 0; n < hospitalArray.length(); n++) {
                    JSONObject hospital = hospitalArray.getJSONObject(n);
                    String name = hospital.getString("name");
                    String address = hospital.getString("address");
                    int phone = hospital.getInt("phone");
                    boolean cityScan = hospital.getBoolean("cityScan");
                    boolean intravenousThrombolysis = hospital.getBoolean("intravenousThrombolysis");
                    boolean strokeTeamNuerosurgeon = hospital.getBoolean("strokeTeamNuerosurgeon");
                    boolean dedicatedStrokeUnit = hospital.getBoolean("dedicatedStrokeUnit");
                    boolean cathLab = hospital.getBoolean("cathLab");
                    Toast.makeText(HospitalInformationList.this, name + address + phone + cityScan + intravenousThrombolysis + strokeTeamNuerosurgeon + dedicatedStrokeUnit + cathLab, Toast.LENGTH_LONG).show();
                }
            }catch(Exception e){
                Toast.makeText(HospitalInformationList.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        final ImageButton kmes = (ImageButton) findViewById(R.id.kmes);
        kmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HospitalInformationList.this, "You are being redirected to the KMES mobile website!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://m.kmes.in"));
                startActivity(intent);
                HospitalInformationList.this.finish();
            }
        });

        final ImageButton stroke = (ImageButton) findViewById(R.id.stroke);
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HospitalInformationList.this, "Take the STROKE test again!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HospitalInformationList.this, EvaluateSymptoms.class);
                HospitalInformationList.this.startActivity(intent);
                HospitalInformationList.this.finish();
            }
        });

        final Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HospitalInformationList.this, "You are exiting from this app!", Toast.LENGTH_SHORT).show();
                HospitalInformationList.this.finish();
            }
        });

        final TextView starRatingHeading = (TextView) findViewById(R.id.textViewStarRatingHeading);
        starRatingHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HospitalInformationList.this);
                builder.setTitle("Star Rating Details");
                builder.setMessage("Static text")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        final TextView phoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        final TextView hospName = (TextView) findViewById(R.id.textViewHospital);
        hospName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                String map = "http://maps.google.co.in/maps?q=" + "6515 boulevard east, west new york, new jersey 07093";
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                                startActivity(i);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(HospitalInformationList.this);
                builder.setTitle("Address").setMessage("6515 boulevard east, west new york, new jersey 07093").setPositiveButton("Show in Google Maps", dialogClickListener)
                        .setNegativeButton("OK", dialogClickListener).show();
            }
        });

    }

    private void call() {
        Intent in=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"+12016829255"));
        try{
            startActivity(in);
        }
        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"Error : "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hospital_information_list, menu);
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
