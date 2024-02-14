package com.example.lr11;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_EMPLOYEE_REQUEST = 1;
    public static final int EDIT_EMPLOYEE_REQUEST = 2;

    private EmployeeViewModel employeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddEmployee = findViewById(R.id.add_btn);
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditEmployeeActivity.class);
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                employeeViewModel.delete(adapter.getEmployeeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Сотрудник удален", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        
        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Employee employee) {
                Intent intent = new Intent(MainActivity.this, AddEditEmployeeActivity.class);
                intent.putExtra(AddEditEmployeeActivity.EXTRA_ID, employee.getId());
                intent.putExtra(AddEditEmployeeActivity.EXTRA_NAME, employee.getFull_name());
                intent.putExtra(AddEditEmployeeActivity.EXTRA_DEPARTMENT, employee.getDepartment());
                intent.putExtra(AddEditEmployeeActivity.EXTRA_POSITION, employee.getPosition());
                startActivityForResult(intent, EDIT_EMPLOYEE_REQUEST);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EMPLOYEE_REQUEST && resultCode == RESULT_OK) {
            String fullname = data.getStringExtra(AddEditEmployeeActivity.EXTRA_NAME);
            String department = data.getStringExtra(AddEditEmployeeActivity.EXTRA_DEPARTMENT);
            String position = data.getStringExtra(AddEditEmployeeActivity.EXTRA_POSITION);

            Employee employee = new Employee(fullname, department, position);
            employeeViewModel.insert(employee);

            Toast.makeText(this, "Сотрудник сохранен", Toast.LENGTH_SHORT).show();
        } else  if (requestCode == EDIT_EMPLOYEE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditEmployeeActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Сотрудник не может быть изменен", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullname = data.getStringExtra(AddEditEmployeeActivity.EXTRA_NAME);
            String department = data.getStringExtra(AddEditEmployeeActivity.EXTRA_DEPARTMENT);
            String position = data.getStringExtra(AddEditEmployeeActivity.EXTRA_POSITION);

            Employee employee = new Employee(fullname, department, position);
            employee.setId(id);
            employeeViewModel.update(employee);
            Toast.makeText(this, "Сотрудник изменен", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Сотрудник не сохранен", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_employees) {
            employeeViewModel.deleteAllEmployees();
            Toast.makeText(this, "Все сотрудники удалены", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
        }

    }


