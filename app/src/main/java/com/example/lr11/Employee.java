package com.example.lr11;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String full_name;

    private String department;

    private String position;

    public Employee(String full_name, String department, String position) {
        this.full_name = full_name;
        this.department = department;
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }
}
