package com.appstone.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StudentListAdapter.StudenListClickListener {

    private EditText mEtStudentName, mEtStudentRollNo, mEtDepartment, mEtBookBorrowed;

    private Button btnEnterRegistry;
    private Button updateRegistry;

    private DatabaseHelper dbHelper;

    private RecyclerView mRcStudentList;


    /**
     * Recycler view is of three types
     * 1. LinearLayout Type
     * 2. GridLayout Type
     * 3. StaggeredGrid Layout Type
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtStudentName = findViewById(R.id.et_student_name);
        mEtStudentRollNo = findViewById(R.id.et_student_rollno);
        mEtDepartment = findViewById(R.id.et_student_dept);
        mEtBookBorrowed = findViewById(R.id.et_student_book);

        btnEnterRegistry = findViewById(R.id.btn_enter_registry);
        updateRegistry = findViewById(R.id.btn_update_registry);
        Button btnViewData = findViewById(R.id.btn_get_values);


        dbHelper = new DatabaseHelper(MainActivity.this);

        mRcStudentList = findViewById(R.id.rc_studentlist);
        mRcStudentList.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

//        mRcStudentList.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));

//        mRcStudentList.setLayoutManager(new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));


        btnEnterRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student studentDetail = new Student();
                studentDetail.studentName = mEtStudentName.getText().toString();
                studentDetail.rollNo = Integer.parseInt(mEtStudentRollNo.getText().toString());
                studentDetail.department = mEtDepartment.getText().toString();
                studentDetail.bookBorrowed = mEtBookBorrowed.getText().toString();

                dbHelper.insertDataToDatabase(dbHelper.getWritableDatabase(), studentDetail);

                mEtStudentName.setText("");
                mEtStudentRollNo.setText("");
                mEtBookBorrowed.setText("");
                mEtDepartment.setText("");
            }
        });

        updateRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student studentDetail = new Student();
                studentDetail.studentName = mEtStudentName.getText().toString();
                studentDetail.rollNo = Integer.parseInt(mEtStudentRollNo.getText().toString());
                studentDetail.department = mEtDepartment.getText().toString();
                studentDetail.bookBorrowed = mEtBookBorrowed.getText().toString();

                dbHelper.updateDataInDatabase(dbHelper.getWritableDatabase(), studentDetail);

                mEtStudentName.setText("");
                mEtStudentRollNo.setText("");
                mEtBookBorrowed.setText("");
                mEtDepartment.setText("");

                mEtStudentRollNo.setEnabled(true);
                mEtStudentRollNo.setFocusableInTouchMode(true);

                getDataFromDatabase();

                updateRegistry.setVisibility(View.GONE);
                btnEnterRegistry.setVisibility(View.VISIBLE);
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromDatabase();
            }
        });

    }

    private void getDataFromDatabase() {
        ArrayList<Student> studentList = dbHelper.getDataFromDatabase(dbHelper.getReadableDatabase());

        StudentListAdapter adapter = new StudentListAdapter(MainActivity.this, studentList);
        adapter.setListener(MainActivity.this);
        mRcStudentList.setAdapter(adapter);
    }

    @Override
    public void onStudentEditClicked(Student student) {
        mEtStudentName.setText(student.studentName);
        mEtBookBorrowed.setText(student.bookBorrowed);
        mEtStudentRollNo.setText(String.valueOf(student.rollNo));
        mEtDepartment.setText(student.department);

        //SetFocusableInTouchMode will disable entry
        mEtStudentRollNo.setEnabled(false);
        mEtStudentRollNo.setFocusableInTouchMode(false);

        btnEnterRegistry.setVisibility(View.GONE);
        updateRegistry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStudentDeleteClicked(Student student) {
        dbHelper.deleteDataFromDatabase(dbHelper.getWritableDatabase(), student);
        getDataFromDatabase();
    }
}
