package com.example.observer;

import com.example.observer.observer.effects.CombinedEffects;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.observer.observer.TimeServer;
import com.example.observer.observer.components.ComponentOne;
import com.example.observer.observer.components.ComponentTwo;
import com.example.observer.observer.components.ComponentThree;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.observer.observer.effects.PerspectiveEffectManager;
import javafx.scene.effect.PerspectiveTransform;

public class HelloController implements Initializable {

    private TimeServer timeServer;
    private ComponentOne componentOne;
    private ComponentTwo componentTwo;
    private ComponentThree componentThree;

    @FXML private Label timeLabel;
    @FXML private Label serverStatusLabel;
    @FXML private Label currentTimeLabel;
    @FXML private Label comp2IntervalLabel;
    @FXML private Label comp3IntervalLabel;

    @FXML private Button serverStartBtn;
    @FXML private Button serverStopBtn;
    @FXML private Button serverResetBtn;

    @FXML private Button comp1StartBtn;
    @FXML private Button comp1StopBtn;

    @FXML private Button comp2StartBtn;
    @FXML private Button comp2StopBtn;

    @FXML private Button comp3StartBtn;
    @FXML private Button comp3StopBtn;

    @FXML private Circle animationCircle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //иниц сервера времени
        timeServer = new TimeServer();

        //иниц компонентов
        componentOne = new ComponentOne(timeLabel);
        componentTwo = new ComponentTwo();
        componentThree = new ComponentThree(animationCircle);

        //привязка компонентов к серверу
        componentOne.setSubject(timeServer);
        componentTwo.setSubject(timeServer);
        componentThree.setSubject(timeServer);

        //применение Lighting к кнопкам
        applyPerspectiveEffects(); //новый метод
        applyLightingEffects();

        //настройка обработчиков событий
        setupEventHandlers();

        //обновление интерфейса
        updateUI();
    }

    private void applyLightingEffects() {
        //Создаем источник света Point
        Light.Point light = new Light.Point();
        light.setX(70); //Позиция источника света по X
        light.setY(50); //позиция источника света по Y
        light.setZ(30); //высота источника света

        //эффект Lighting для кнопки старта сервера (зеленый свет)
        Lighting startLighting = new Lighting();
        startLighting.setLight(light);
        startLighting.setSurfaceScale(5.0); //интенсивность эффекта
        startLighting.setDiffuseConstant(2.0); //коф-цент диффузного отражения
        startLighting.setSpecularConstant(0.3); //коф-цент зеркального отражения
        startLighting.setSpecularExponent(20.0); // Экспонента зеркального отражения
        startLighting.setLight(new Light.Point(150, 100, 50, Color.LIMEGREEN));
        serverStartBtn.setEffect(startLighting);

        //эффект Lighting остановки сервера red
        Lighting stopLighting = new Lighting();
        stopLighting.setLight(new Light.Point(150, 100, 50, Color.INDIANRED));
        stopLighting.setSurfaceScale(5.0);
        stopLighting.setDiffuseConstant(1.8);
        serverStopBtn.setEffect(stopLighting);

        //эффект Lighting для кнопки сброса blue
        Lighting resetLighting = new Lighting();
        resetLighting.setLight(new Light.Point(150, 100, 50, Color.DODGERBLUE));
        resetLighting.setSurfaceScale(4.0);
        resetLighting.setDiffuseConstant(1.5);
        serverResetBtn.setEffect(resetLighting);

        //эффект Lighting для круга анимации
        Lighting circleLighting = new Lighting();
        Light.Distant distantLight = new Light.Distant();
        distantLight.setAzimuth(45.0); //горизонтальный угол
        distantLight.setElevation(30.0); //угол возвышения
        distantLight.setColor(Color.WHITE);

        circleLighting.setLight(distantLight);
        circleLighting.setSurfaceScale(8.0);
        circleLighting.setDiffuseConstant(2.0);
        circleLighting.setSpecularConstant(0.5);
        circleLighting.setSpecularExponent(40.0);

        //применяем Lighting к кругу только если он не null
        if (animationCircle != null) {
            animationCircle.setEffect(circleLighting);
        }
    }

    private void applyPerspectiveEffects() {
        //применяем анимированный PerspectiveTransform к основным кнопкам
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(serverStartBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(serverStopBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(serverResetBtn);

        //применяем PerspectiveTransform к кнопкам компонентов
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(comp1StartBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(comp1StopBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(comp2StartBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(comp2StopBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(comp3StartBtn);
        PerspectiveEffectManager.applyAnimatedPerspectiveEffect(comp3StopBtn);
    }

    private void setupEventHandlers() {
        //обработчики сервера времени
        serverStartBtn.setOnAction(e -> {
            timeServer.start();
            updateUI();
        });

        serverStopBtn.setOnAction(e -> {
            timeServer.stop();
            updateUI();
        });

        serverResetBtn.setOnAction(e -> {
            timeServer.reset();
            updateUI();
        });

        //компонент 1
        comp1StartBtn.setOnAction(e -> {
            componentOne.setSubject(timeServer);
            updateUI();
        });

        comp1StopBtn.setOnAction(e -> {
            //стоп доделать
            updateUI();
        });

        // Компонент 2
        comp2StartBtn.setOnAction(e -> {
            componentTwo.start();
            updateUI();
        });

        comp2StopBtn.setOnAction(e -> {
            componentTwo.stop();
            updateUI();
        });

        // Компонент 3
        comp3StartBtn.setOnAction(e -> {
            componentThree.start();
            updateUI();
        });

        comp3StopBtn.setOnAction(e -> {
            componentThree.stop();
            updateUI();
        });
    }

    private void updateUI() {
        //обновление статуса сервера
        if (timeServer.isActive()) {
            serverStatusLabel.setText("СЕРВЕР АКТИВЕН");
            serverStatusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #4CAF50; -fx-font-size: 14pt;");

            //применяем комбинированный эффект при активном сервере
            serverStatusLabel.setEffect(CombinedEffects.createActiveServerEffect());

            //эффект пульсации для времени
            currentTimeLabel.setEffect(CombinedEffects.createPulsatingEffect());

            serverStartBtn.setDisable(true);
            serverStopBtn.setDisable(false);
        } else {
            serverStatusLabel.setText("Сервер не активен");
            serverStatusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #757575;");
            serverStatusLabel.setEffect(null); //кбираем эффект

            currentTimeLabel.setEffect(null); //убираем пульсацию

            serverStartBtn.setDisable(false);
            serverStopBtn.setDisable(true);
        }

        //обновление текущего времени
        currentTimeLabel.setText("Время: " + timeServer.getState() + " сек");
    }
}
