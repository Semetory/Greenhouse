package com.example.observer.observer.effects;

import javafx.animation.*;
import javafx.scene.effect.PerspectiveTransform;
import javafx.util.Duration;

public class PerspectiveEffectManager {

    public static void applyAnimatedPerspectiveEffect(javafx.scene.control.Button button) {
        if (button == null) return;

        //исходный PerspectiveTransform без искажения
        PerspectiveTransform perspective = new PerspectiveTransform();
        perspective.setUlx(0);    //верхний левый X
        perspective.setUly(0);    //верхний левый Y
        perspective.setUrx(button.getPrefWidth());  // Верхний правый X
        perspective.setUry(0);    //верхний правый Y
        perspective.setLrx(button.getPrefWidth());  //нижний правый X
        perspective.setLry(button.getPrefHeight()); //нижний правый Y
        perspective.setLlx(0);    //нижний левый X
        perspective.setLly(button.getPrefHeight()); //нижний левый Y

        button.setEffect(perspective);

        //анимация при наведении мыши
        button.setOnMouseEntered(e -> {
            animatePerspective(perspective,
                    0, 0,                            //UL
                    button.getPrefWidth() + 20, 0,   //UR (растягиваем вправо)
                    button.getPrefWidth() - 10, button.getPrefHeight(), //LR
                    -10, button.getPrefHeight());    //LL (сдвигаем влево)
        });

        //возврат к исходному состоянию при уходе мыши
        button.setOnMouseExited(e -> {
            animatePerspective(perspective,
                    0, 0,                            //UL
                    button.getPrefWidth(), 0,        //UR
                    button.getPrefWidth(), button.getPrefHeight(), //LR
                    0, button.getPrefHeight());      //LL
        });
    }

    private static void animatePerspective(PerspectiveTransform perspective,
                                           double ulx, double uly,
                                           double urx, double ury,
                                           double lrx, double lry,
                                           double llx, double lly) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(perspective.ulxProperty(), perspective.getUlx()),
                        new KeyValue(perspective.ulyProperty(), perspective.getUly()),
                        new KeyValue(perspective.urxProperty(), perspective.getUrx()),
                        new KeyValue(perspective.uryProperty(), perspective.getUry()),
                        new KeyValue(perspective.lrxProperty(), perspective.getLrx()),
                        new KeyValue(perspective.lryProperty(), perspective.getLry()),
                        new KeyValue(perspective.llxProperty(), perspective.getLlx()),
                        new KeyValue(perspective.llyProperty(), perspective.getLly())
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(perspective.ulxProperty(), ulx),
                        new KeyValue(perspective.ulyProperty(), uly),
                        new KeyValue(perspective.urxProperty(), urx),
                        new KeyValue(perspective.uryProperty(), ury),
                        new KeyValue(perspective.lrxProperty(), lrx),
                        new KeyValue(perspective.lryProperty(), lry),
                        new KeyValue(perspective.llxProperty(), llx),
                        new KeyValue(perspective.llyProperty(), lly)
                )
        );
        timeline.play();
    }

    public static PerspectiveTransform createTitlePerspective() {
        PerspectiveTransform perspective = new PerspectiveTransform();
        perspective.setUlx(0);      // Верхний левый X
        perspective.setUly(-5);     // Верхний левый Y (немного выше)
        perspective.setUrx(400);    // Верхний правый X
        perspective.setUry(5);      // Верхний правый Y (немного ниже)
        perspective.setLrx(380);    // Нижний правый X (уже)
        perspective.setLry(40);     // Нижний правый Y
        perspective.setLlx(20);     // Нижний левый X (сдвиг вправо)
        perspective.setLly(30);     // Нижний левый Y
        return perspective;
    }
}