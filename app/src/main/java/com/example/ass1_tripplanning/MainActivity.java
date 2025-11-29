package com.example.ass1_tripplanning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<TripTask> allTasks;
    private SearchView searchView;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTasks);
        searchView = findViewById(R.id.searchView);
        fabAdd = findViewById(R.id.fabAddTask);


        allTasks = TaskStorage.loadTasks(this);

        adapter = new TaskAdapter(allTasks, this, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onUpdateClick(TripTask task) {

                Intent i = new Intent(MainActivity.this, AddEditTaskActivity.class);
                i.putExtra("taskId", task.getId());
                startActivity(i);
            }

            @Override
            public void onDeleteClick(TripTask task) {
                showDeleteConfirmDialog(task);
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {

            Intent i = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivity(i);
        });


        setupSearch();
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void showDeleteConfirmDialog(TripTask task) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    allTasks.remove(task);
                    TaskStorage.saveTasks(MainActivity.this, allTasks);
                    adapter.setTasks(allTasks);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        allTasks = TaskStorage.loadTasks(this);
        adapter.setTasks(allTasks);
    }
}