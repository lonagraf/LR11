package com.example.lr11;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_EMPLOYEE_REQUEST = 1;

    private EmployeeViewModel employeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddEmployee = findViewById(R.id.add_btn);
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivityForResult(intent, ADD_EMPLOYEE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        EmployeeAdapter adapter = new EmployeeAdapter();
        recyclerView.setAdapter(adapter);

        employeeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(EmployeeViewModel.class);
        employeeViewModel.getAllEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EMPLOYEE_REQUEST && resultCode == RESULT_OK) {
            String fullname = data.getStringExtra(AddEmployeeActivity.EXTRA_NAME);
            String department = data.getStringExtra(AddEmployeeActivity.EXTRA_DEPARTMENT);
            String position = data.getStringExtra(AddEmployeeActivity.EXTRA_POSITION);

            Employee employee = new Employee(fullname, department, position);
            employeeViewModel.insert(employee);

            Toast.makeText(this, "Сотрудник сохранен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Сотрудник не сохранен", Toast.LENGTH_SHORT).show();
        }
    }
}