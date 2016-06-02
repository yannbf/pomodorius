package braga.com.br.pomodorius.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import braga.com.br.pomodorius.R;
import braga.com.br.pomodorius.model.Task;
import braga.com.br.pomodorius.notification.Notification;

/**
 * Created by aluno-r17 on 11/05/16.
 */

// Thread do contador
public class PomodoroWorker extends IntentService {

    // Para valores reais, é só descomentar as linhas..
    Task task;
    int count = 3;
    //int count = 25 * 60; //<- essa
    int shortBreakCount;
    int longBreakCount;
    int spentPomodoros = 0;

    public PomodoroWorker() {
        super("PomodoroWorker");
    }

    public String calculateRemainingTime(int value){
        int minutes = value / 60;
        int seconds = value % 60;
        return String.format("%02d:%02d",
                minutes,
                seconds);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("APP", "iniciando..");

        this.task = (Task) intent.getSerializableExtra("currentTask");
        shortBreakCount = this.task.getShortBreak()*2;
        longBreakCount = this.task.getLongBreak()*2;

        //shortBreakCount = this.task.getShortBreak() * 60; // <- essa
        //longBreakCount = this.task.getLongBreak()* 60; // <- essa

        while(spentPomodoros <= task.getPomodoro()){
            try {
                if(count == 0){
                    //count = 25 * 60; // <- essa

                    if(spentPomodoros == task.getPomodoro()){
                        break;
                    }
                    if(spentPomodoros == 4){
                        Toast.makeText(getApplicationContext(),"Starting long break..", Toast.LENGTH_SHORT).show();
                        startBreak(longBreakCount);
                    } else {
                        Toast.makeText(getBaseContext(),"Starting short break..", Toast.LENGTH_SHORT).show();
                        startBreak(shortBreakCount);
                    }

                    count = 10;
                } else {
                    String t = calculateRemainingTime(count);
                    Log.v("Pomodoro", t);
                    Notification.updateNotification(getApplicationContext(), t);
                    RxBUS.send(t);
                    Thread.sleep(1000);
                    count--;

                    if(count == 0) {
                        spentPomodoros++;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(getApplicationContext(),"Your task finished!..", Toast.LENGTH_SHORT).show();
        Notification.updateNotification(getApplicationContext(), "Your task finished!");
        RxBUS.send(0);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.notification);
        mp.start();

    }

    public void startBreak(int count){

        while(count >= 0){
            try {
                String t = calculateRemainingTime(count);
                Log.v("Pomodoro", t);
                Notification.updateNotification(getApplicationContext(),"BREAK " + t );
                RxBUS.send(t);
                Thread.sleep(1000);
                count--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
