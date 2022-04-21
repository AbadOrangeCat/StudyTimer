package com.example.studytimer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class TimeAdp extends RecyclerView.Adapter<TimeAdp.Holder> {
    private Context context;
    private List<TimerModel> taskModelList;

    public TimeAdp(Context context, List<TimerModel> taskModelList) {
        this.context = context;
        this.taskModelList = taskModelList;
        Collections.reverse(taskModelList);
    }

    public void setTaskModelList(List<TimerModel> taskModelList) {
        this.taskModelList = taskModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context)
                .inflate(R.layout.layout_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.d("sth", "TimerModel: " + taskModelList);
        holder.txtTaskName.setText(taskModelList.get(position).getTaskName());
        holder.txtTaskAddTime.setText(String.valueOf(taskModelList.get(position).getTaskAddedTime()));
        holder.textSlot.setText(String.valueOf(taskModelList.get(position).getTaskSlotName()));
    }

    @Override
    public int getItemCount() {
        return taskModelList != null ? taskModelList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtTaskName, txtTaskAddTime, textSlot;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.txt_task_name);
            txtTaskAddTime = itemView.findViewById(R.id.txt_date);
            textSlot = itemView.findViewById(R.id.t1);
        }
    }
}
