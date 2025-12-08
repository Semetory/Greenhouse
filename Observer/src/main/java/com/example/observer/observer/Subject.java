package com.example.observer.observer;

import java.util.List;

public interface Subject {
    void notifyAllObservers(); // увед all набл
    void attach(Observer obs); // + набл
    void detach(Observer obs); // - набл
    int getState();
    void setState(int time);
}
