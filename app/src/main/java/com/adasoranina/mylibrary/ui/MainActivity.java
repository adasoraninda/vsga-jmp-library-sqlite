package com.adasoranina.mylibrary.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adasoranina.mylibrary.R;
import com.adasoranina.mylibrary.database.DatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputName = findViewById(R.id.input_name);
        Button buttonStore = findViewById(R.id.button_store);
        Button buttonGetNames = findViewById(R.id.button_get_names);
        Button buttonDelNames = findViewById(R.id.button_del_names);
        TextView textResultNames = findViewById(R.id.text_result_names);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        buttonStore.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();

            if (!name.isEmpty()) {
                long result = dbHelper.addStudentDetail(name);
                if (result <= 0) {
                    Toast.makeText(
                            this,
                            "Stored Unsuccessfully!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                inputName.setText(null);
                Toast.makeText(
                        this,
                        "Stored Successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        buttonGetNames.setOnClickListener(v -> {
            textResultNames.setText("");
            inputName.setText(null);

            List<String> names = dbHelper.getAllStudents();
            StringBuilder strNames = new StringBuilder();

            for (int i = 0; i < names.size(); i++) {
                if (i > 0) strNames.append(", ");
                strNames.append(names.get(i));
            }

            if (strNames.toString().isEmpty()) {
                Toast.makeText(
                        this,
                        "Get Unsuccessfully!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            textResultNames.setText(strNames.toString());
        });

        buttonDelNames.setOnClickListener(v -> {
            textResultNames.setText("");
            inputName.setText(null);

            int result = dbHelper.deleteAllStudents();
            if (result <= 0) {
                Toast.makeText(
                        this,
                        "Deleted Unsuccessfully!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(
                    this,
                    "Deleted Successfully!",
                    Toast.LENGTH_SHORT).show();
        });

    }
}