package io.github.gersonsr.tinyappproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.data;
import static android.R.id.edit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchForWeather(View view) {
        // Find the View that shows the numbers category

        EditText zipcode = (EditText) findViewById(R.id.zip_code_search);
        EditText city = (EditText) findViewById(R.id.city_search);

        String zipCodeText = zipcode.getText().toString();
        String cityText = city.getText().toString();

        if(zipCodeText != null && !zipCodeText.isEmpty()) {
            Intent zipCodeIntent = new Intent(MainActivity.this, resultsActivity.class);
            zipCodeIntent.putExtra("zipCode",zipCodeText);
            startActivity(zipCodeIntent);
        }
        else if (cityText != null && !cityText.isEmpty()) {
            Intent cityIntent = new Intent(MainActivity.this, resultsActivity.class);
            cityIntent.putExtra("city", cityIntent);
            startActivity(cityIntent);
        }
        else {
            Intent errorIntent = new Intent(MainActivity.this, resultsActivity.class);
            errorIntent.putExtra("error","No Input");
            startActivity(errorIntent);
        }
    }
}
