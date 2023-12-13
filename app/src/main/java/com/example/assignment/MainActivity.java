package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    private EditText edtWeight, edtGoldValue;
    private Spinner spinnerGoldType;
    private Button btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setTitle("Zakat Gold Master");
        getSupportActionBar().setTitle("Gold Calculator");
        myToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtWeight = findViewById(R.id.inputGoldGram);
        edtGoldValue = findViewById(R.id.inputGoldValue);
        spinnerGoldType = findViewById(R.id.GoldType);
        btnCalculate = findViewById(R.id.btnCalc);
        btnClear = findViewById(R.id.btnClear);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gold_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoldType.setAdapter(adapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateZakat();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share) {
            // Share functionality
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Please use my application - http://t.co/app");
            startActivity(Intent.createChooser(shareIntent, null));
            return true;
        } else if (item.getItemId() == R.id.item_about){
            // Launch about activity
            Intent aboutIntent = new Intent(this, About_activity.class);
            startActivity(aboutIntent);
            return true;
        }

        if (item.getItemId() == android.R.id.home){
            // Handle back button
            super.onBackPressed();
            return true;
        }

        return false;
    }

    private void clearFields() {
        edtWeight.getText().clear();
        edtGoldValue.getText().clear();
    }

    private void calculateZakat() {
        String weightText = edtWeight.getText().toString();
        String goldValueText = edtGoldValue.getText().toString();

        if (weightText.isEmpty() || goldValueText.isEmpty()) {
            showAlert("Please enter both weight and gold value.");
            return;
        }

        double weight = Double.parseDouble(weightText);
        double goldValuePerGram = Double.parseDouble(goldValueText);
        String goldType = spinnerGoldType.getSelectedItem().toString().toLowerCase();

        double totalGoldValue = weight * goldValuePerGram;
        double X = (goldType.equals("keep")) ? 200.0 : 85.0;
        double zakatPayableValue = (weight - X) * goldValuePerGram;

        if (zakatPayableValue < 0) {
            zakatPayableValue = 0;
        }

        double totalZakat = 0.025 * zakatPayableValue;

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("totalGoldValue", totalGoldValue);
        intent.putExtra("zakatPayableValue", zakatPayableValue);
        intent.putExtra("totalZakat", totalZakat);
        startActivity(intent);
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Input Required")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
