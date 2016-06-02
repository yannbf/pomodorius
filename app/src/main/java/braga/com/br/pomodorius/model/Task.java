package braga.com.br.pomodorius.model;

import java.io.Serializable;

/**
 * Created by yann.braga on 19/05/2016.
 */
public class Task implements IModel, Serializable {
    private Integer id;
    private String title;
    private String description;
    private int pomodoro;
    public final static int WORKING_TASK=0;
    public final static int CHILLING_TASK=1;
    //Definição do tempo do pomodoro
    public final static int POMODORO_TIME=25;
    private int shortBreak = 5;
    private int longBreak = 15;

    public Task(){
    }

    public Task(String title, String description, int pomodoro, int shortBreak, int longBreak) {
        this(null, title, description,pomodoro, shortBreak, longBreak);
    }

    public Task(Integer id, String title, String description, int pomodoro, int shortBreak, int longBreak) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pomodoro = pomodoro;
        this.shortBreak = shortBreak;
        this.longBreak = longBreak;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPomodoro() {
        return pomodoro;
    }

    public void setPomodoro(int pomodoro) {
        this.pomodoro = pomodoro;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public void setLongBreak(int longBreak) {
        this.longBreak = longBreak;
    }

    public int getShortBreak() {
        return shortBreak;
    }

    public void setShortBreak(int shortBreak) {
        this.shortBreak = shortBreak;
    }
}
