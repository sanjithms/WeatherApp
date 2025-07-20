package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout homeRL;
    private ProgressBar progress;
    private TextView cityNameTV,temperatueTV,conditionTV;
    private RecyclerView weatherTV;
    private TextInputEditText cityRdt;
    private ImageView backIV,iconIV,searchIV;
    private ArrayList<WeatherModel> weatherModelArrayList;
    private  WeatherAdapter weatherAdapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE=1;
    private  String cityName;
    private TextView feelsLikeTV, humidityTV, uvIndexTV,sunriseTV, sunsetTV, airQualityTV;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        // Initialize all views
        homeRL = findViewById(R.id.idRLHome);
        progress = findViewById(R.id.progress);
        cityNameTV = findViewById(R.id.idTVCityName);
        temperatueTV = findViewById(R.id.idTVTemperature);
        conditionTV = findViewById(R.id.idTVCondition);
        weatherTV = findViewById(R.id.idRVWeather);
        cityRdt = findViewById(R.id.idEdtCity);
        backIV = findViewById(R.id.idIVBack);
        iconIV = findViewById(R.id.idTVIcon);
        searchIV = findViewById(R.id.idIVSearch);
        feelsLikeTV = findViewById(R.id.idTVFeelsLikeVal);
        humidityTV = findViewById(R.id.idTVHumidityVal);
        uvIndexTV = findViewById(R.id.idTVUVVal);
        sunriseTV = findViewById(R.id.idTVSunriseVal);
        sunsetTV = findViewById(R.id.idTVSunsetVal);
        airQualityTV = findViewById(R.id.idTVAirQualityVal);




        weatherModelArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherModelArrayList);
        weatherTV.setAdapter(weatherAdapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_CODE);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                cityName = getCityname(location.getLongitude(), location.getLatitude());
                getWeatherInfo(cityName);
            } else {
                Toast.makeText(this, "Unable to get location. Loading default city", Toast.LENGTH_LONG).show();
                cityName = "Delhi"; // You can use any default city
                getWeatherInfo(cityName);
            }
        }


        // Handle search button click
        searchIV.setOnClickListener(view -> {
            String city = cityRdt.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter the City Name", Toast.LENGTH_SHORT).show();
            } else {
                cityNameTV.setText(city);
                getWeatherInfo(city);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
        if(requestCode==PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Please Provide the required permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private  String getCityname(double longitutude, double latitue){
        String cityName="Not Found";
        Geocoder gcd =new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses=gcd.getFromLocation(latitue,longitutude,10);
            for (Address adr :addresses){
                if(adr!=null){
                    String city=adr.getLocality();
                    if(city!=null && !city.equals("")){
                        cityName=city;
                    }else {
                        Toast.makeText(this,"User City Not Found.",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }
    private void getWeatherInfo(String cityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=1ddce2887ea14a799af153413251907&q="
                + cityName + "&days=1&aqi=yes&alerts=yes";

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.setVisibility(View.GONE);
                        homeRL.setVisibility(View.VISIBLE);
                        weatherModelArrayList.clear();

                        try {
                            // Location
                            String sr = response.getJSONObject("location").getString("name");
                            cityNameTV.setText(sr);

                            // Current Weather
                            JSONObject current = response.getJSONObject("current");
                            String temperature = current.getString("temp_c");
                            String feelsLike = current.getString("feelslike_c");
                            String humidity = current.getString("humidity");
                            String uvIndex = current.getString("uv");
                            String condition = current.getJSONObject("condition").getString("text");
                            String conditionIcon = current.getJSONObject("condition").getString("icon");
                            int isDay = current.getInt("is_day");

                            // AQI
                            JSONObject airQuality = current.getJSONObject("air_quality");
                            double aqi = airQuality.getDouble("pm2_5"); // PM2.5 as an example

                            // Set values
                            temperatueTV.setText(temperature + "°C");
                            feelsLikeTV.setText(feelsLike + "°C");
                            humidityTV.setText(humidity + "%");
                            uvIndexTV.setText(uvIndex);
                            conditionTV.setText(condition);
                            airQualityTV.setText("PM2.5: " + String.format("%.1f", aqi)); // Ensure `airQualityTV` exists

                            Picasso.get().load("https:" + conditionIcon).into(iconIV);
                            backIV.setImageResource(isDay == 1 ? R.drawable.day1 : R.drawable.night1);

                            // Forecast (for sunrise/sunset and hourly)
                            JSONObject forecast = response.getJSONObject("forecast");
                            JSONObject todayForecast = forecast.getJSONArray("forecastday").getJSONObject(0);

                            String sunrise = todayForecast.getJSONObject("astro").getString("sunrise");
                            String sunset = todayForecast.getJSONObject("astro").getString("sunset");

                            sunriseTV.setText(sunrise);  // Ensure `sunriseTV` and `sunsetTV` exist
                            sunsetTV.setText(sunset);

                            // Hourly forecast
                            JSONArray hourArray = todayForecast.getJSONArray("hour");
                            for (int i = 0; i < hourArray.length(); i++) {
                                JSONObject hourObj = hourArray.getJSONObject(i);
                                String time = hourObj.getString("time");
                                String temp = hourObj.getString("temp_c");
                                String img = hourObj.getJSONObject("condition").getString("icon");
                                String wind = hourObj.getString("wind_kph");

                                weatherModelArrayList.add(new WeatherModel(time, temp, img, wind));
                            }

                            weatherAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Data parsing error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Please Enter Valid City Name", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


}