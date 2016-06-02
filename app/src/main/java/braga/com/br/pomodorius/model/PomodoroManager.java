package braga.com.br.pomodorius.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yann.braga on 28/05/2016.
 */
public class PomodoroManager {
    private Task task;
    private List<Integer> timer;
    private int runningItem;

    public PomodoroManager(Task t){
        this.task = t;
        this.runningItem=-1;
        timer = new ArrayList<Integer>();
        this.fillTimeArray();
    }

    private void fillTimeArray() {
        int pomodoro = task.getPomodoro();
        int counter=0;
        for (int i=0;i<pomodoro;i++){
            timer.add(Task.POMODORO_TIME);
            counter++;
            if (counter % 4 == 0){
                timer.add(task.getLongBreak());
                counter=0;
            }else {
                timer.add(task.getShortBreak());
            }
        }
    }
    public List<Integer> getTimer(){
        return this.timer;
    }
    public int getRunningItem(){
        return this.runningItem;
    }

    public Task getTask(){
        return this.task;
    }

    public void updateRunningItem(){
        this.runningItem++;
    }

}
