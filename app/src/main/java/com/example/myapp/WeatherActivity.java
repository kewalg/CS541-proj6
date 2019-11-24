package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    EditText edt_weather;
    TextView tv_main, tv_desc, tv_temp, tv_pressure, tv_humidity;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        edt_weather = findViewById(R.id.edt_cityname);
        tv_main = findViewById(R.id.tv_main);
        tv_desc = findViewById(R.id.tv_desc);
        tv_temp = findViewById(R.id.tv_temp);
        tv_pressure = findViewById(R.id.tv_pressure);
        tv_humidity = findViewById(R.id.tv_humidity);
        btn_back = findViewById(R.id.btn_back_weather);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WeatherActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void getWeather(View view) {
        DownloadTask task = new DownloadTask();
        task.execute("https://openweathermap.org/data/2.5/weather?q=" + edt_weather.getText().toString() + "&appid=b6907d289e10d714a6e88b30761fae22");
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result = result + current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = new JSONObject(s);

                String weatherinfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherinfo);

                JSONArray arr = new JSONArray(weatherinfo);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String desc = jsonPart.getString("description");
                    if (!main.equals("")) {
                        tv_main.setText("Forecast: " + main);
                    }
                    if (!desc.equals("")) {
                        tv_desc.setText("Description: " + desc);
                    }
                }

                JSONObject jsonObject3 = jsonObject1.getJSONObject("main");
                String temp = jsonObject3.getString("temp");
                String pressure = jsonObject3.getString("pressure");
                String humidity = jsonObject3.getString("humidity");

                if (!temp.equals("")) {
                    tv_temp.setText("Temperature: " + temp + " °C");
                }
                if (!pressure.equals("")) {
                    tv_pressure.setText("Pressure: " + pressure + " in");
                }
                if (!humidity.equals("")) {
                    tv_humidity.setText("Humidity: " + humidity + " %");
                }


                /*for (int j = 0; j < ja.length(); j++) {
                    JSONObject jsonPart1 = ja.getJSONObject(j);
                    String temp = jsonPart1.getString("temp");
                    String pressure = jsonPart1.getString("pressure");
                    String humidity = jsonPart1.getString("humidity");
                    if (!temp.equals("")) {
                        tv_temp.setText(temp);
                    }
                    if (!pressure.equals("")) {
                        tv_pressure.setText(pressure);
                    }
                    if (!humidity.equals("")) {
                        tv_humidity.setText(humidity);
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
