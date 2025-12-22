package com.example.observer.observer.components;

import com.example.observer.observer.IObserver;
import com.example.observer.observer.Subject;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class ComponentOne implements IObserver {
    private final Label timeLabel;
    private Subject subject;

    public ComponentOne(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        if (subject != null) {
            subject.attach(this);
        }
    }

    @Override
    public void update(Subject subject) {
        Platform.runLater(() -> {
            timeLabel.setText("Прошло " + subject.getState() + " секунд");
        });
    }
}