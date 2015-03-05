package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.negocio.template.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created on 11/02/2015
 *
 * @author I7
 */
public class CanvasController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(Token.DISPLAY_WIDTH, Token.DISPLAY_HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //autorizar transação
//        new Header(Color.NAVY, Color.WHITE, "Banco Avixy").render(gc);
//        new Text(60, Color.BLACK, null, "Utilize este código\npara Autorizar sua\nTransação", Text.Size.SMALL, Text.Alignment.LEFT).render(gc);
//        new Stripe(140, 58, Color.rgb(0, 176, 240)).render(gc);
//        new Text(154, Color.BLACK, null, "561239", Text.Size.HUGE, Text.Alignment.CENTER).render(gc);
//        new Footer(Color.rgb(255, 204, 41), Color.BLACK, "Pressione o botão", "para desligar o Token").render(gc);
        //\autorizar transação

        //transação
        new Header(new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_FLAG_BLUE), new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_WHITE), "Banco Avixy").render(gc);
        new Stripe(60, 16, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB, 190, 190, 190)).render(gc);
        new Text(60, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK), null, Text.Size.SMALL, Text.Alignment.LEFT, "DEBITADO").render(gc);
        new Stripe(76, 48, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB,0, 176, 240)).render(gc);
        new Text(76, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK), null, Text.Size.SMALL, Text.Alignment.LEFT, "Cheiroso\nAgência: Belini\nConta: 1234-5").render(gc);
        new Stripe(140, 16, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB,190, 190, 190)).render(gc);
        new Text(140, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK), null, Text.Size.SMALL, Text.Alignment.LEFT, "CREDITADO").render(gc);
        new Stripe(156, 48, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB,0, 176, 240)).render(gc);
        new Text(156, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK), null, Text.Size.SMALL, Text.Alignment.LEFT, "Mary\nAgência: 3475-4\nConta: 26823-2").render(gc);
        new Stripe(220, 32, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB,0, 176, 240)).render(gc);
        new Text(220, new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK), null, Text.Size.SMALL, Text.Alignment.LEFT, "19/12/2014\nValor: R$ 4.000.00").render(gc);
        new Footer(new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB,255, 204, 41), new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK), "Pressione o botão", "para receber o código").render(gc);
        //\transação

        //grid
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        int x = 0;
        while (x < Token.DISPLAY_WIDTH) {
            x += 10;
            gc.strokeLine(x,0,x,Token.DISPLAY_HEIGHT);
        }
        int y = 0;
        while (y < Token.DISPLAY_HEIGHT) {
            y += 10;
            gc.strokeLine(0,y,Token.DISPLAY_WIDTH,y);
        }
        //\grid

//        new Stripe(60, 60, Color.LIGHTBLUE).render(gc);
//        new com.avixy.qrtoken.negocio.template.Text(165, Color.BLACK, null, "123456", Text.Size.HUGE, Text.Alignment.CENTER).render(gc);
//        new Stripe(60, 40, Color.LIGHTBLUE).render(gc);
//        new Text(100, Color.BLACK, null, "1234567890", Text.Size.SMALL, Text.Alignment.CENTER).render(gc);
//        new Text(130, Color.BLACK, null, "1234567890", Text.Size.LARGE, Text.Alignment.CENTER).render(gc);
//        new Text(180, Color.BLACK, null, "1234567890", Text.Size.HUGE, Text.Alignment.CENTER).render(gc);
//        new Footer(Color.rgb(255, 204, 41), Color.WHITE, "Utilize este código", "para fazer login").render(gc);
    }
}

