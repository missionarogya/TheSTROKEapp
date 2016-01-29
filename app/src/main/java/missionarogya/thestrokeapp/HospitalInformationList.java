package missionarogya.thestrokeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
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

        TableLayout table = (TableLayout)findViewById(R.id.hospitalTable);

        String info = hospitalInformation.getHospitalInformation();
        if(info!=null && info.length()>0) {
            try {
                JSONArray hospitalArray = new JSONArray(info);
                for (int n = 0; n < hospitalArray.length(); n++) {

                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT);
                    row.setLayoutParams(lp);
                    row.setPadding(5,4,4,4);
                    row.setBackgroundColor(Color.WHITE);

                    JSONObject hospital = hospitalArray.getJSONObject(n);
                    final String name = hospital.getString("name");
                    final String address = hospital.getString("address");
                    final int phone = hospital.getInt("phone");
                    int rating = 0;
                    String services = "";
                    int cityScan = hospital.getInt("cityScan");
                    if(cityScan == 1){
                        rating = rating + 1;
                        services = services + "City Scan\n";
                    }
                    int intravenousThrombolysis = hospital.getInt("intravenousThrombolysis");
                    if(intravenousThrombolysis == 1){
                        rating = rating + 1;
                        services = services + "Intravenous Thrombolysis\n";
                    }
                    int strokeTeamNuerosurgeon = hospital.getInt("strokeTeamNuerosurgeon");
                    if(strokeTeamNuerosurgeon == 1){
                        rating = rating + 1;
                        services = services + "Stroke Team and Nuerosurgeon\n";
                    }
                    int dedicatedStrokeUnit = hospital.getInt("dedicatedStrokeUnit");
                    if(dedicatedStrokeUnit == 1){
                        rating = rating + 1;
                        services = services + "Dedicated Stroke Unit\n";
                    }
                    int cathLab = hospital.getInt("cathLab");
                    if(cathLab == 1){
                        rating = rating + 1;
                        services = services + "Vascular Interventiom / Intra-aerial Throbolysis and CATH Lab\n";
                    }
                    TextView txtHospitalName = new TextView(this);
                    txtHospitalName.setText(" " + name.toUpperCase() + " ");
                    txtHospitalName.setTextColor(Color.WHITE);
                    txtHospitalName.setBackgroundColor(Color.BLACK);
                    txtHospitalName.setTypeface(Typeface.DEFAULT);
                    txtHospitalName.setLayoutParams(new TableRow.LayoutParams(1));
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)txtHospitalName.getLayoutParams();
                    params.setMargins(0, 0, 4, 0);
                    txtHospitalName.setLayoutParams(params);
                    row.addView(txtHospitalName);

                    TextView txtPhone = new TextView(this);
                    txtPhone.setText(" " + Integer.toString(phone) + " ");
                    txtPhone.setTextColor(Color.WHITE);
                    txtPhone.setBackgroundColor(Color.BLACK);
                    txtPhone.setTypeface(Typeface.DEFAULT);
                    txtPhone.setLayoutParams(new TableRow.LayoutParams(2));
                    LinearLayout.LayoutParams paramsPhone = (LinearLayout.LayoutParams)txtHospitalName.getLayoutParams();
                    paramsPhone.setMargins(0, 0, 4, 0);
                    txtPhone.setLayoutParams(paramsPhone);
                    row.addView(txtPhone);

                    TextView txtRating = new TextView(this);
                    txtRating.setText(" " + Integer.toString(rating) + " ");
                    txtRating.setTextColor(Color.WHITE);
                    txtRating.setBackgroundColor(Color.BLACK);
                    txtRating.setTypeface(Typeface.DEFAULT);
                    txtRating.setLayoutParams(new TableRow.LayoutParams(3));
                    LinearLayout.LayoutParams paramsRating = (LinearLayout.LayoutParams)txtHospitalName.getLayoutParams();
                    paramsRating.setMargins(0, 0, 4, 0);
                    txtRating.setLayoutParams(paramsRating);
                    row.addView(txtRating);

                    ImageView imageRating = new ImageView(this);
                    imageRating.setPadding(0, 0, 2, 0);
                    if(rating == 1){
                        imageRating.setImageResource(R.drawable.r1);
                    }else if(rating == 2){
                        imageRating.setImageResource(R.drawable.r2);
                    }else if(rating == 3){
                        imageRating.setImageResource(R.drawable.r3);
                    }else if(rating == 4){
                        imageRating.setImageResource(R.drawable.r4);
                    }else if(rating == 5){
                        imageRating.setImageResource(R.drawable.r5);
                    }
                    imageRating.setBackgroundColor(Color.BLACK);
                    imageRating.setLayoutParams(new TableRow.LayoutParams(4));
                    row.addView(imageRating);
                    final String popUpTxtServices = services;

                    table.addView(row);

                    imageRating.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HospitalInformationList.this);
                            builder.setTitle("Services available in "+ name.toUpperCase() +" 24*7");
                            builder.setMessage(popUpTxtServices)
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

                    txtHospitalName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            String map = "http://maps.google.co.in/maps?q=" + address ;
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
                            builder.setTitle("Address").setMessage(address).setPositiveButton("Show in Google Maps", dialogClickListener)
                                    .setNegativeButton("OK", dialogClickListener).show();
                        }
                    });

                    txtPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            call(Integer.toString(phone));
                        }
                    });
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

        final TextView servicesHeading = (TextView)findViewById(R.id.textViewStars);
        servicesHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HospitalInformationList.this);
                builder.setTitle("Services");
                builder.setMessage("Static Text")
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
    }

    private void call(String phoneNumber) {
        Intent in=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
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
