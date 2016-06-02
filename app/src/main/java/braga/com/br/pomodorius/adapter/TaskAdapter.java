package braga.com.br.pomodorius.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import braga.com.br.pomodorius.R;
import braga.com.br.pomodorius.model.Task;
import braga.com.br.pomodorius.util.RecyclerViewListener;

/**
 * Created by yann.braga on 19/05/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private LayoutInflater layoutInflater;
    private List<Task> tasks;
    private RecyclerViewListener listener;
    private Context context;

    public TaskAdapter(Context context,List<Task> tasks){
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.tasks = tasks;
    }

    public void setListener(RecyclerViewListener listener) {
        this.listener = listener;
    }


    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.task_item, parent, false);
        TaskHolder taskHolder = new TaskHolder(view);
        return taskHolder;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.id.setText("" + task.getId());
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.pomodoro.setText("" + task.getPomodoro());
        holder.shortBreak.setText("" + task.getShortBreak());
        holder.longBreak.setText("" + task.getLongBreak());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id;
        private TextView title;
        private TextView description;
        private TextView pomodoro;
        private TextView shortBreak;
        private TextView longBreak;

        public TaskHolder(View itemView) {

            super(itemView);
            //this.context = ctx;
            id = (TextView) itemView.findViewById(R.id.taskIdDetail);
            title = (TextView) itemView.findViewById(R.id.taskTitleDetail);
            description = (TextView) itemView.findViewById(R.id.taskDescriptionDetail);
            pomodoro = (TextView) itemView.findViewById(R.id.taskPomodoroDetail);
            shortBreak = (TextView) itemView.findViewById(R.id.taskShortBreakDetail);
            longBreak = (TextView) itemView.findViewById(R.id.taskLongBreakDetail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = Integer.parseInt(((TextView) v.findViewById(R.id.taskIdDetail)).getText().toString());
            listener.onClick(id);
        }
    }
}
