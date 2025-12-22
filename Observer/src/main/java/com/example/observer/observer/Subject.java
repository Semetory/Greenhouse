package com.example.observer.observer;

public interface Subject {
    void notifyAllObservers(); //Уведомить всех наблюдателей
    void attach(IObserver obs); //Добавить наблюдателя
    void detach(IObserver obs); //Удалить наблюдателя
    int getState(); //Получить текущее состояние
    void setState(int time); //Установить состояние
}
