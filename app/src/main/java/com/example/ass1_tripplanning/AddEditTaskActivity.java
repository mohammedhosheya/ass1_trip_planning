package com.example.ass1_tripplanning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {

    private EditText etTitle, etNotes;
    private TextView tvDate;
    private Button btnPickDate, btnSave;
    private RadioGroup rgType;
    private RadioButton rbFlight, rbHotel, rbActivity;
    private CheckBox cbDoneEdit;

    private ArrayList<TripTask> allTasks;
    private TripTask currentTask = null;
    private int currentTaskId = -1;
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);


        etTitle = findViewById(R.id.etTitle);
        etNotes = findViewById(R.id.etNotes);
        tvDate = findViewById(R.id.tvDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnSave = findViewById(R.id.btnSave);
        rgType = findViewById(R.id.rgType);
        rbFlight = findViewById(R.id.rbFlight);
        rbHotel = findViewById(R.id.rbHotel);
        rbActivity = findViewById(R.id.rbActivity);
        cbDoneEdit = findViewById(R.id.cbDoneEdit);


        allTasks = TaskStorage.loadTasks(this);


        currentTaskId = getIntent().getIntExtra("taskId", -1);
        if (currentTaskId != -1) {

            currentTask = TaskStorage.findTaskById(allTasks, currentTaskId);
            if (currentTask != null) {
                fillFormWithTask(currentTask);
            }
        }

        btnPickDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveTask());
    }

    private void fillFormWithTask(TripTask task) {
        etTitle.setText(task.getTitle());
        tvDate.setText(task.getDate());
        selectedDate = task.getDate();
        etNotes.setText(task.getNotes());
        cbDoneEdit.setChecked(task.isDone());


        String type = task.getType();
        if ("Flight".equals(type)) {
            rbFlight.setChecked(true);
        } else if ("Hotel".equals(type)) {
            rbHotel.setChecked(true);
        } else {
            rbActivity.setChecked(true);
        }
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                (DatePicker view, int y, int m, int d) -> {
                    selectedDate = y + "-" + (m + 1) + "-" + d;
                    tvDate.setText(selectedDate);
                }, year, month, day);
        dpd.show();
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }


        String type = "Activity";
        int checkedId = rgType.getCheckedRadioButtonId();
        if (checkedId == R.id.rbFlight) {
            type = "Flight";
        } else if (checkedId == R.id.rbHotel) {
            type = "Hotel";
        }

        boolean isDone = cbDoneEdit.isChecked();

        if (currentTask == null) {

            int newId = TaskStorage.getNextId(allTasks);
            TripTask newTask = new TripTask(
                    newId,
                    title,
                    selectedDate,
                    type,
                    notes,
                    isDone
            );
            allTasks.add(newTask);
        } else {

            currentTask.setTitle(title);
            currentTask.setDate(selectedDate);
            currentTask.setType(type);
            currentTask.setNotes(notes);
            currentTask.setDone(isDone);
        }


        TaskStorage.saveTasks(this, allTasks);
        Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
