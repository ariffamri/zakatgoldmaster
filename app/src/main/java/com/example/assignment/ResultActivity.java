package com.example.assignment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    private TextView txtTotalGoldValue, txtZakatPayableValue, txtTotalZakat, txtAdditionalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setTitle("Zakat Gold Master");
        getSupportActionBar().setTitle("Result");
        myToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtTotalGoldValue = findViewById(R.id.txtTotalGoldValue);
        txtZakatPayableValue = findViewById(R.id.txtZakatPayableValue);
        txtTotalZakat = findViewById(R.id.txtTotalZakat);

        // Retrieve data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double totalGoldValue = extras.getDouble("totalGoldValue");
            double zakatPayableValue = extras.getDouble("zakatPayableValue");
            double totalZakat = extras.getDouble("totalZakat");

            // Display results in TextView elements
            txtTotalGoldValue.setText(String.format("Total Gold Value: RM %.2f", totalGoldValue));
            txtZakatPayableValue.setText(String.format("Zakat Payable Value: RM %.2f", zakatPayableValue));
            txtTotalZakat.setText(String.format("Total Zakat: RM %.2f", totalZakat));
        }

        // Button and OnClickListener moved inside onCreate

    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){

            super.onBackPressed();
        }
        return true;
    }
}

