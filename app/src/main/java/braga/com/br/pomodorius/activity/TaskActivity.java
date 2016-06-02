package braga.com.br.pomodorius.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import braga.com.br.pomodorius.R;
import braga.com.br.pomodorius.dao.TaskDao;
import braga.com.br.pomodorius.model.Task;
import braga.com.br.pomodorius.service.PomodoroWorker;
import braga.com.br.pomodorius.service.RxBUS;
import rx.functions.Action1;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {
    private TaskDao taskDao;
    private Task task;
    private TextView mId;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mPomodoro;
    private TextView mTimerTextView;
    private Button mCancelButton;
    private Button mRemoveButton;
    private Button mEditButton;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        subscribe();

        Toast.makeText(getBaseContext(),"Nota: os valores foram trocados para que você possa testar sem esperar tanto. Para calculos corretos, é só descomentar as partes na classe PomodoroWorker!.", Toast.LENGTH_LONG).show();

        taskDao = new TaskDao(this);

        mId = (TextView) findViewById(R.id.taskIdText);
        mTitle = (TextView) findViewById(R.id.taskTitleText);
        mDescription = (TextView) findViewById(R.id.taskDescriptionText);
        mPomodoro = (TextView) findViewById(R.id.taskPomodoroText);
        mTimerTextView = (TextView) findViewById(R.id.taskClockText);

        mCancelButton = (Button) findViewById(R.id.taskCancelButton);
        mRemoveButton = (Button) findViewById(R.id.taskRemoveButton);
        mEditButton = (Button) findViewById(R.id.taskEditButton);
        mStartButton = (Button) findViewById(R.id.taskStartButton);

        //mStartButton.setEnabled(false);

        mCancelButton.setOnClickListener(this);
        mRemoveButton.setOnClickListener(this);
        mEditButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Task enviada via bundle
        Intent it = getIntent();
        int taskId = it.getIntExtra("task",-1);
        if (taskId != -1){
            task = taskDao.find(taskId);
        }
        //Setar valores nos campos
        mId.setText(""+task.getId());
        mTitle.setText(task.getTitle());
        mDescription.setText(task.getDescription());
        mPomodoro.setText("" + task.getPomodoro());
    }

    public void subscribe(){
        Log.v("APP", "executado subs");
        RxBUS.toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.v("APP", "recebi a acao." + o.toString());
                runOnUI(o);

            }
        });
    }

    private void runOnUI(final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mTimerTextView.setText("" + o.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.taskCancelButton:
                finish();
                break;
            case R.id.taskRemoveButton:
               final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Delete task");
                alertDialog.setMessage("Are you sure you wanna do this?");
                alertDialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        taskDao.delete(task);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alertDialog.create();
                alertDialog.show();

                break;
            case R.id.taskEditButton:
                Intent it = new Intent(this, EditActivity.class);
                it.putExtra("task",task.getId());
                startActivity(it);
                break;
            case R.id.taskStartButton:
                Intent startIntent = new Intent(TaskActivity.this, PomodoroWorker.class);
                startIntent.putExtra("currentTask", task);

                startService(startIntent);
                break;
        }
    }
}
