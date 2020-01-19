package com.appstone.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListHolder> {


    //Steps to create a RecyclerView Adapter

    /**
     * 1. Create a class for the adapter
     * 2. Create a inner class for the view created in the layout and extend the class to RecyclerView.ViewHolder
     * 3. Implement the Constructor matching super class for the view holder
     * 4. Declare all the views designed and find its viewById
     * 5. Extend the top class to RecyclerView.Adapter and pass the viewHolder name in the <>
     * 6. Implement the three override methods onCreateView, onBindView and getItemCount
     * 7. Create a constructor to get the context and the arraylist
     * 8. Return the arraylist.size() in the getItemCount
     * 9. Create a view using layout inflater in the onCreateView pass the view to the viewHolder object and return it
     * 10. OnBindView get the data of the current position and display it.
     * <p>
     * <p>
     * <p>
     * Interface Steps
     * 1. Create a interface
     * 2. Declare methods for edit and delete action
     * 3. Create a local variable in the adapter for the interface
     * 4. Create a setter method for the listener to make sure the callback is gone to the correct class.
     * 5. Create clickListener for the buttons and call the appropriate trigger method.
     */


    private ArrayList<Student> studentList;
    private Context context;
    private StudenListClickListener listener;

    public StudentListAdapter(Context context, ArrayList<Student> studentList) {
        this.studentList = studentList;
        this.context = context;
    }


    //Method to make sure the callback is routed or directed towards the correct calling Activity
    public void setListener(StudenListClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_student_detail, parent, false);
        StudentListHolder holder = new StudentListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListHolder holder, int position) {
        final Student singleData = studentList.get(position);

        holder.tvRollno.setText(String.valueOf(singleData.rollNo));
        holder.tvStudentName.setText(singleData.studentName);
        holder.tvDepartment.setText(singleData.department);
        holder.tvBookBorrowed.setText(singleData.bookBorrowed);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onStudentDeleteClicked(singleData);
                }
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onStudentEditClicked(singleData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }


    //Inner class for the view designed in the layout
    class StudentListHolder extends RecyclerView.ViewHolder {


        TextView tvStudentName, tvRollno, tvDepartment, tvBookBorrowed;
        ImageView ivDelete, ivEdit;

        public StudentListHolder(@NonNull View itemView) {
            super(itemView);

            tvStudentName = itemView.findViewById(R.id.tv_student_studentname);
            tvRollno = itemView.findViewById(R.id.tv_student_rollno);
            tvDepartment = itemView.findViewById(R.id.tv_student_dept);
            tvBookBorrowed = itemView.findViewById(R.id.tv_student_bookborrowd);

            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivEdit = itemView.findViewById(R.id.iv_edit);
        }
    }

    public interface StudenListClickListener {
        void onStudentEditClicked(Student student);

        void onStudentDeleteClicked(Student student);
    }
}
