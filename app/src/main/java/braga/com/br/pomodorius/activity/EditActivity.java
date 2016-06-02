package braga.com.br.pomodorius.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import braga.com.br.pomodorius.R;
import braga.com.br.pomodorius.dao.TaskDao;
import braga.com.br.pomodorius.model.Task;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private TaskDao taskDao;

    private TextView mId;
    private EditText mTitle;
    private EditText mDescription;
    private EditText mPomodoro;
    private EditText mShortBreak;
    private EditText mLongBreak;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        taskDao = new TaskDao(this);

        mId = (TextView) findViewById(R.id.taskIdEdit);
        mTitle = (EditText) findViewById(R.id.taskTitleEdit);
        mDescription = (EditText) findViewById(R.id.taskDescriptionEdit);
        mPomodoro = (EditText) findViewById(R.id.taskPomodoroEdit);
        mShortBreak = (EditText) findViewById(R.id.taskShortBreakEdit);
        mLongBreak = (EditText) findViewById(R.id.taskLongBreakEdit);

        mButton = (Button) findViewById(R.id.taskSaveButton);
        mButton.setOnClickListener(this);

        //Caso seja passado uma task
        Task task = null;
        Intent it = getIntent();
        int taskId = it.getIntExtra("task",-1);
        if (taskId != -1){
            task = taskDao.find(taskId);

            mId.setText("" + task.getId());
            mTitle.setText(task.getTitle());
            mDescription.setText(task.getDescription());
            mPomodoro.setText("" + task.getPomodoro());
            mShortBreak.setText("" + task.getShortBreak());
            mLongBreak.setText("" + task.getLongBreak());
            mButton.setText("Atualizar");
        }
    }

    @Override
    public void onClick(View v) {

        boolean save=false;

        Task task = new Task();

        String title = mTitle.getText().toString();
        if(!title.isEmpty()){
            task.setTitle(title);
            save=true;
        }else {
            mTitle.setError("Este campo está vazio.");
            save=false;
        }

        task.setDescription(mDescription.getText().toString());

        int pomodoro = Integer.parseInt(mPomodoro.getText().toString());

        if (pomodoro != 0){
            task.setPomodoro(pomodoro);
            save=true;
        }else {
            mPomodoro.setError("Este valor precisa ser maior que zero!");
            save=false;
        }

        int shortBreak = Integer.parseInt(mShortBreak.getText().toString());
        if (shortBreak != 0){
            task.setShortBreak(shortBreak);
            save=true;
        }else {
            mShortBreak.setError("Este valor precisa ser maior que zero!");
            save=false;
        }

        int longBreak = Integer.parseInt(mLongBreak.getText().toString());
        if (longBreak != 0){
            task.setLongBreak(longBreak);
            save=true;
        }else {
            mLongBreak.setError("Este valor precisa ser maior que zero!");
            save=false;
        }

        if(save){
            if (mButton.getText().toString().equalsIgnoreCase("Atualizar")){
                task.setId(Integer.parseInt(mId.getText().toString()));
                taskDao.update(task);
            }else {
                taskDao.insert(task);
            }
            finish();
        }else {
            Snackbar.make(v,"Formulário inválido",Snackbar.LENGTH_SHORT).show();
        }

    }
}
