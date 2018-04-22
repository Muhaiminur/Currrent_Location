package com.example.abir.currrent_location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karan.churi.PermissionManager.PermissionManager;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    Context mContext;
    double longitude;
    double latitude;
    TextView lat;
    TextView lon;
    PermissionManager permissionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionManager=new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);
        if(getLocationMode() != 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogMaterialStyle);
            builder.setTitle("Select High Accuracy Mood")
                    .setMessage("You Must select high Accuracy to send report")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .show();

        }
        mContext=this;
        /*locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                    2000,
                    10, locationListenerGPS);
            isLocationEnabled();
        }catch (SecurityException s){
            //dialogGPS(this.getContext());
        }*/

        Button up=(Button)findViewById(R.id.update);
        lat=(TextView)findViewById(R.id.lat);
        lon=(TextView)findViewById(R.id.lon);
        up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker(getApplication());
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                lat.setText(""+latitude);
                lon.setText(""+longitude);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions, grantResults);
    }
    public int getLocationMode() {
        try {
            return Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        }catch (Exception e){

        }
        return 11;
    }
}
