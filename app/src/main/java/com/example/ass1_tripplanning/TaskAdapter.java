package com.example.ass1_tripplanning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.widget.Button;



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface OnTaskClickListener {
        void onUpdateClick(TripTask task);
        void onDeleteClick(TripTask task);

    }


    private ArrayList<TripTask> tasks;
    private ArrayList<TripTask> fullList;
    private Context context;
    private OnTaskClickListener listener;

    public TaskAdapter(ArrayList<TripTask> tasks, Context context, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.fullList = new ArrayList<>(tasks);
        this.context = context;
        this.listener = listener;
    }

    public void setTasks(ArrayList<TripTask> newTasks) {
        this.tasks = newTasks;
        this.fullList = new ArrayList<>(newTasks);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        tasks.clear();
        if (text == null || text.trim().isEmpty()) {
            tasks.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (TripTask t : fullList) {
                if (t.getTitle().toLowerCase().contains(text)) {
                    tasks.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TripTask task = tasks.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvDate.setText(task.getDate());
        holder.tvType.setText(task.getType());
        holder.cbDone.setChecked(task.isDone());


        if ("Flight".equals(task.getType())) {
            holder.imgType.setImageResource(android.R.drawable.ic_menu_compass);
        } else if ("Hotel".equals(task.getType())) {
            holder.imgType.setImageResource(android.R.drawable.ic_menu_mylocation);
        } else {
            holder.imgType.setImageResource(android.R.drawable.ic_menu_myplaces);
        }

        holder.btnUpdate.setOnClickListener(v -> listener.onUpdateClick(task));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(task));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvType;
        CheckBox cbDone;
        ImageView imgType;
        Button btnUpdate, btnDelete;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvType);
            cbDone = itemView.findViewById(R.id.cbDone);
            imgType = itemView.findViewById(R.id.imgType);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

}