package com.example.observer.observer.effects;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.paint.Color;

public class CombinedEffects {

    /*комбинированный эффект для активного состояния*/

    public static javafx.scene.effect.Effect createActiveServerEffect() {

        //DropShadow внешняя тень
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 150, 255, 0.7));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setSpread(0.5);

        //InnerShadow внутренняя тень
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.rgb(0, 100, 200, 0.5));
        innerShadow.setRadius(5);
        innerShadow.setOffsetX(2);
        innerShadow.setOffsetY(2);

        //Lighting освещение
        Lighting lighting = new Lighting();
        lighting.setLight(new javafx.scene.effect.Light.Distant(45, 45, Color.WHITE));
        lighting.setSurfaceScale(5.0);
        lighting.setDiffuseConstant(1.5);

        //PerspectiveTransform перспектива
        PerspectiveTransform perspective = new PerspectiveTransform();
        perspective.setUlx(0);
        perspective.setUly(0);
        perspective.setUrx(120);
        perspective.setUry(5);
        perspective.setLrx(110);
        perspective.setLry(35);
        perspective.setLlx(10);
        perspective.setLly(30);

        //Комбинируем эффекты через Blend
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);
        blend.setTopInput(perspective);
        blend.setBottomInput(lighting);

        //Цепочка эффектов
        perspective.setInput(innerShadow);
        innerShadow.setInput(dropShadow);

        return blend;
    }

    /*Эффект пульсации для активного элемента*/

    public static javafx.scene.effect.Effect createPulsatingEffect() {
        DropShadow pulsatingShadow = new DropShadow();
        pulsatingShadow.setColor(Color.rgb(255, 100, 100, 0.8));
        pulsatingShadow.setRadius(15);

        //Анимация пульсации
        javafx.animation.Timeline pulse = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.ZERO,
                        new javafx.animation.KeyValue(pulsatingShadow.radiusProperty(), 10),
                        new javafx.animation.KeyValue(pulsatingShadow.colorProperty(),
                                Color.rgb(255, 100, 100, 0.5))
                ),
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(1000),
                        new javafx.animation.KeyValue(pulsatingShadow.radiusProperty(), 20),
                        new javafx.animation.KeyValue(pulsatingShadow.colorProperty(),
                                Color.rgb(255, 150, 150, 0.8))
                ),
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(2000),
                        new javafx.animation.KeyValue(pulsatingShadow.radiusProperty(), 10),
                        new javafx.animation.KeyValue(pulsatingShadow.colorProperty(),
                                Color.rgb(255, 100, 100, 0.5))
                )
        );
        pulse.setCycleCount(javafx.animation.Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        return pulsatingShadow;
    }
}
