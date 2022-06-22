package com.test.v042_158_065_uas_ga.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.test.v042_158_065_uas_ga.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class Tambah_Lokasi extends AppCompatActivity {

   private EditText un,latitude,longitude,notelpon;
   private GifImageView gifImageView;
   TextView tv_address;
   Button otomatis;
   private FusedLocationProviderClient locationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lokais);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(Tambah_Lokasi.this);
        un = (EditText) findViewById(R.id.ET_USERNAME);
        latitude = (EditText) findViewById(R.id.ET_LATITUDE);
        longitude = (EditText) findViewById(R.id.ET_LONGITUDE);
        notelpon = (EditText) findViewById(R.id.ET_NOTELPON);
        tv_address =(TextView) findViewById(R.id.TV_lokasi);
        otomatis = (Button) findViewById(R.id.BTN_OTOMATIS);
        gifImageView = (GifImageView) findViewById(R.id.fire_gif);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(Tambah_Lokasi.this);


        otomatis.setOnClickListener(v -> {
            getLocation();
            gifImageView.setVisibility(View.VISIBLE);
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10){
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Izin lokasi tidak di aktifkan!", Toast.LENGTH_SHORT).show();
            }else{
                getLocation();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // get Permission
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 10);
        } else {
            // get Location
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude.setText(String.valueOf(location.getLatitude()));
                        longitude.setText(String.valueOf(location.getLongitude()));

                        try {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String address = addresses.get(0).getAddressLine(0);
                            tv_address.setText(address);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Lokasi tidak aktif!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //dapat lokasi
        //


        //
    }
    public void BTN_MANUAL(View view) {
        final String nama = un.getText().toString().trim();
        final String alamat = tv_address.getText().toString().trim();
        final String lt = latitude.getText().toString().trim();
        final String longitudee = longitude.getText().toString().trim();
        final String notelponn = notelpon.getText().toString().trim();

        class TambahRegis extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Tambah_Lokasi.this, "Add","Wait",
                        false,false);
            }
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put("nama",nama);
                params.put("alamat",alamat);
                params.put("latitude",lt);
                params.put("longitude",longitudee);
                params.put("notelpon",notelponn);

                /// 	nama	latitude	longitude
                // username,latitude,longitude

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://192.168.1.15/GIS/register.php",params);
                return res;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equals("berhasil")) {
                    Toast.makeText(Tambah_Lokasi.this,"LOKASI TELAH DIDAPAT", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Tambah_Lokasi.this,"Data tidak falid", Toast.LENGTH_LONG).show();
                }
            }
        }
        TambahRegis ae = new TambahRegis();
        ae.execute();

    }
}