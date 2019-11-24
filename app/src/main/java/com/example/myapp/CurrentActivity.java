package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class CurrentActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        back_btn = findViewById(R.id.btn_back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CurrentActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationinfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationinfo(lastKnownLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void updateLocationinfo(Location location) {
        Log.i("Location", location.toString());
        TextView lat_tv = findViewById(R.id.tv_lat);
        TextView long_tv = findViewById(R.id.tv_long);
        TextView accuracy_tv = findViewById(R.id.tv_accuracy);
        TextView title_tv = findViewById(R.id.tv_current_title);
        TextView altitude_tv = findViewById(R.id.tv_altitude);
        TextView address_tv = findViewById(R.id.tv_address);


        DecimalFormat df = new DecimalFormat("###.##");
        DecimalFormat df1 = new DecimalFormat("###.#####");
        lat_tv.setText("Latitude: " + df.format(location.getLatitude()));
        long_tv.setText("Longitude: " + df.format(location.getLongitude()));
        accuracy_tv.setText("Accuracy: " + df1.format(location.getAccuracy()));
        altitude_tv.setText("Altitude: " + df1.format(location.getAltitude()));


        Geocoder geocoder;
        List<Address> addresses;
        String address = "Could not find address!";
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses != null && addresses.size() > 0) {
                address = "Address: ";

                if (addresses.get(0).getThoroughfare() != null) {
                    address += addresses.get(0).getThoroughfare() + " ";
                }
                if (addresses.get(0).getLocality() != null) {
                    address += addresses.get(0).getLocality() + " ";
                }
                if (addresses.get(0).getPostalCode() != null) {
                    address += addresses.get(0).getPostalCode() + " ";
                }
                if (addresses.get(0).getAdminArea() != null) {
                    address += addresses.get(0).getAdminArea() + " ";
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        address_tv.setText(address);
    }

}