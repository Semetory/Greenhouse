package com.example.observer.observer.components;

import com.example.observer.observer.Observer;
import com.example.observer.observer.Subject;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ComponentTwo implements Observer {
    private Subject subject;
    private int playInterval = 10; //кд 10 сек
    private int lastPlayTime = 0;
    private MediaPlayer mediaPlayer;
    private boolean isActive = false;

    public ComponentTwo() {
        //Запуск/иниц медиаплеера
        try {
            String musicFile = "src/main/resources/music.mp3";
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки музыкального файла: " + e.getMessage());
        }
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
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setPlayInterval(int interval) {
        this.playInterval = interval;
    }

    @Override
    public void update(Subject subject) {
        if (!isActive || mediaPlayer == null) return;

        int currentTime = subject.getState();

        if (currentTime >= lastPlayTime + playInterval) {
            mediaPlayer.stop();
            mediaPlayer.play();
            lastPlayTime = currentTime;
        }
    }
}
