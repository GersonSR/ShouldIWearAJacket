package io.github.gersonsr.tinyappproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class resultsActivity extends AppCompatActivity {

    TextView txtJson;
    TextView description;
    TextView temperature;
    ImageView image;
    String zip;
    String city;
    String urlstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        txtJson = (TextView) findViewById(R.id.Answer);
        description = (TextView) findViewById(R.id.description);
        temperature = (TextView) findViewById(R.id.temperature);
        image = (ImageView) findViewById(R.id.returnImg);
        new JsonTask().execute();
    }

    protected class JsonTask extends AsyncTask<Void, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(Void... params)
        {
            try {
                zip = getIntent().getStringExtra("zip");
            } catch (Exception e) {

            }
            try {
                city = getIntent().getStringExtra("city");
            } catch (Exception e) {

            }

            if(zip != null && !zip.isEmpty()) {
                urlstr =  String.format("http://api.openweathermap.org/data/2.5/weather?zip=%s&units=imperial&appid=2eb186d94a8951c1b7506734f3473c0c", zip);
            }
            else if (city != null && !city.isEmpty()) {
                urlstr =  String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=imperial&appid=2eb186d94a8951c1b7506734f3473c0c", city);
            }

            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(urlstr);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response)
        {
            if(response != null)
            {
                try {
                    Log.e("App", "Success: " + response.getJSONArray("weather").getJSONObject(0).getString("id"));
                    int id = Integer.parseInt(response.getJSONArray("weather").getJSONObject(0).getString("id"));
                    String desc = (response.getJSONArray("weather").getJSONObject(0).getString("description"));
                    int temp = (response.getJSONObject("main").getInt("temp"));

                    if ((id > 199) && (id < 623)){
                        txtJson.setText("Yes");
                        description.setText(desc);
                        String degree =  String.format("Current Temp: %d F", temp);
                        image.setImageResource(R.drawable.yes);
                        temperature.setText(degree);

                    }
                    else {
                        txtJson.setText("No");
                        description.setText(desc);
                        String degree =  String.format("Current Temp: %d F", temp);
                        image.setImageResource(R.drawable.no);
                        temperature.setText(degree);

                    }
                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                    txtJson.setText("Error, my dude.");
                }
            }
        }
    }
}
