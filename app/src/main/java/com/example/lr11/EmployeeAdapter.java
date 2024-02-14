package com.example.lr11;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    private List<Employee> employees = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("EmployeeAdapter", "onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item, parent, false);
        return new EmployeeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        Employee currentEmployee = employees.get(position);

        Log.d("EmployeeAdapter", "onBindViewHolder: " + currentEmployee.getFull_name());


        holder.fullName.setText(currentEmployee.getFull_name());
        holder.department.setText(currentEmployee.getDepartment());
        holder.position.setText(currentEmployee.getPosition());
    }

    @Override
    public int getItemCount() {
        Log.d("EmployeeAdapter", "getItemCount: " + employees.size());
        return employees.size();
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    public Employee getEmployeeAt(int position) {
        return employees.get(position);
    }

    class EmployeeHolder extends RecyclerView.ViewHolder {
        private TextView fullName;
        private TextView department;
        private TextView position;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullname);
            department = itemView.findViewById(R.id.department);
            position = itemView.findViewById(R.id.position);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(employees.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
