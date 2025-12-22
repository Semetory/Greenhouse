package com.example.observer.observer.components;

import com.example.observer.observer.IObserver;
import com.example.observer.observer.Subject;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ComponentThree implements IObserver {
    private Subject subject;
    private Circle animationCircle;
    private int animationInterval = 20; //Повторение анимациии каждые 20 сек
    private int lastAnimationTime = 0;
    private boolean isActive = false;

    public ComponentThree(Circle circle) {
        this.animationCircle = circle;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        if (subject != null) {
            subject.attach(this);
        }
    }

    public void start() {
        isActive = true;
    }

    public void stop() {
        isActive = false;
    }

    public void setAnimationInterval(int interval) {
        this.animationInterval = interval;
    }

    @Override
    public void update(Subject subject) {
        if (!isActive) return;

        int currentTime = subject.getState();

        //Настало ли ... время для анимации
        if (currentTime >= lastAnimationTime + animationInterval) {
            Platform.runLater(this::playAnimation);
            lastAnimationTime = currentTime;
        }
    }

    private void playAnimation() {
        if (animationCircle == null) return;

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setNode(animationCircle);
        transition.setFromX(0);
        transition.setToX(200);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.play();
    }
}