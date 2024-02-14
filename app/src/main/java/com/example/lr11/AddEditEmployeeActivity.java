package com.example.lr11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditEmployeeActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.lr11.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.lr11.EXTRA_NAME";
    public static final String EXTRA_DEPARTMENT = "com.example.lr11.EXTRA_DEPARTMENT";
    public static final String EXTRA_POSITION = "com.example.lr11.EXTRA_POSITION";


    EditText fullnameEdit;
    EditText departmentEdit;
    EditText positionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        fullnameEdit = findViewById(R.id.fullname_edit);
        departmentEdit = findViewById(R.id.department_edit);
        positionEdit = findViewById(R.id.position_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Вы можете настроить ActionBar, например, установить кастомный значок
            actionBar.setHomeAsUpIndicator(R.drawable.close);

            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_ID)) {
                setTitle("Редактирование сотрудника");
                fullnameEdit.setText(intent.getStringExtra(EXTRA_NAME));
                departmentEdit.setText(intent.getStringExtra(EXTRA_DEPARTMENT));
                positionEdit.setText(intent.getStringExtra(EXTRA_POSITION));
            } else {
                actionBar.setTitle("Добавление сотрудника");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_employee_menu, menu);
        return true;
    }

    void saveEmployee() {
        String fullname = (fullnameEdit != null) ? fullnameEdit.getText().toString() : "";
        String department = (departmentEdit != null) ? departmentEdit.getText().toString() : "";
        String position = (positionEdit != null) ? positionEdit.getText().toString() : "";

        if (fullname == null || department == null || position == null || fullname.trim().isEmpty() || department.trim().isEmpty() || position.trim().isEmpty()) {
            Toast.makeText(this, "Введите все данные", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, fullname);
        data.putExtra(EXTRA_DEPARTMENT, department);
        data.putExtra(EXTRA_POSITION, position);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_employee) {
            saveEmployee();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}