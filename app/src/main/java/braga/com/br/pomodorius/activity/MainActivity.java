package braga.com.br.pomodorius.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import braga.com.br.pomodorius.R;
import braga.com.br.pomodorius.adapter.TaskAdapter;
import braga.com.br.pomodorius.dao.TaskDao;
import braga.com.br.pomodorius.model.Task;
import braga.com.br.pomodorius.util.RecyclerViewListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewListener {

    private TaskAdapter taskAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFloatingActionButton;
    private TaskDao taskDao;
    private List<Task> tasks;
    private TextView mTimerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao = new TaskDao(this);

        tasks = taskDao.findAll();
        if (tasks == null){
            tasks = new ArrayList<Task>();
        }
        taskAdapter = new TaskAdapter(this, tasks);
        taskAdapter.setListener(MainActivity.this);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.addButton);
        mFloatingActionButton.setOnClickListener(this);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyvlerView);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(taskAdapter);

        mTimerTextView = (TextView) findViewById(R.id.timerTextView);
    }

    @Override
    protected void onStart() {
        super.onStart();


        tasks = taskDao.findAll();
        if (tasks == null){
            tasks = new ArrayList<Task>();
        }
        taskAdapter = new TaskAdapter(this, tasks);
        taskAdapter.setListener(MainActivity.this);
        mRecyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent it;

        switch (v.getId()) {
            case R.id.addButton:
                it = new Intent(this, EditActivity.class);
                startActivity(it);
                break;
        }

    }

    @Override
    public void onClick(int id) {
        Intent it = new Intent(this,TaskActivity.class);
        it.putExtra("task",id);
        startActivity(it);
    }

}
