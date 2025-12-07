package com.example.observer.observer;

import java.util.List;

public interface Subject {
    void notifyAllObservers(); // уведомить всех наблюдателей
    void attach(Observer obs); // добавить наблюдателя
    void detach(Observer obs); // удалить наблюдателя
    int getState(); // получить текущее состояние
    void setState(int time); // установить состояние
}
