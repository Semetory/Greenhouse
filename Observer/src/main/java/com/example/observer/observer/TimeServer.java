package com.example.observer.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer implements Subject {
    private int timeState = 0;
    private Timer timer;
    private TimerTask task;
    private boolean isActive = false;
    private final List<Observer> observers = new ArrayList<>();

    private static final int DELAY = 0; // начальная задержка (мс)
    private static final int PERIOD = 1000; // период обновления (1 секунда)



    public TimeServer() {
        // Конструктор без автоматического запуска
    }

    public void start() {
        if (!isActive) {
            this.timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            };
            timer.schedule(task, DELAY, PERIOD);
            isActive = true;
        }
    }

    public void stop() {
        if (isActive && timer != null) {
            timer.cancel();
            timer = null;
            isActive = false;
        }
    }

    public void reset() {
        timeState = 0;
        notifyAllObservers();
    }

    public boolean isActive() {
        return isActive;
    }

    private void tick() {
        timeState++;
        notifyAllObservers();
    }

    @Override
    public int getState() {
        return timeState;
    }

    @Override
    public void setState(int time) {
        this.timeState = time;
        notifyAllObservers();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
