package com.example.lr11;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Employee.class, version = 1)
public abstract class EmployeeDatabase extends RoomDatabase {

    private static EmployeeDatabase instance;

    public abstract EmployeeDao employeeDao();

    public static synchronized  EmployeeDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EmployeeDatabase.class, "employee_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EmployeeDao employeeDao;

        private PopulateDbAsyncTask(EmployeeDatabase db) {
            employeeDao = db.employeeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            employeeDao.insert(new Employee("Иванов Иван", "Бухгалтерия", "Бухгалтер"));
            employeeDao.insert(new Employee("Петрова Ирина", "Бухгалтерия", "Главбух"));
            employeeDao.insert(new Employee("Киш Миш", "Столовка", "Повар"));
            return null;
        }
    }
}
