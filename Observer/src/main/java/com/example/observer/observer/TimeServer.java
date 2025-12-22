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
    private final List<IObserver> observers = new ArrayList<>();

    private static final int DELAY = 0; //Начальная задержка (мс)
    private static final int PERIOD = 1000; //Период обновления (1 секунда)

    public TimeServer() { }

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
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }
}
