package io.github.gersonsr.tinyappproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    String extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        txtJson = (TextView) findViewById(R.id.Answer);
        new JsonTask().execute();
    }

    protected class JsonTask extends AsyncTask<Void, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(Void... params)
        {
            try {
                extra = getIntent().getStringExtra("zip");
            } catch (Exception e) {

            }
            try {
                extra = getIntent().getStringExtra("city");
            } catch (Exception e) {

            }
            String str =  String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=imperial&appid=2eb186d94a8951c1b7506734f3473c0c", extra);
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
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
                    int result = Integer.parseInt(response.getJSONArray("weather").getJSONObject(0).getString("id"));
                    if ((result > 199) && (result < 623)){
                        txtJson.setText("Yes");
                    }
                    else {
                        txtJson.setText("No");
                    }
                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                    txtJson.setText("Error, my dude.");
                }
            }
        }
    }
}
