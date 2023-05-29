/*
Name: Landon Davidson
Section: 32768
Program Name: ProgramLauncher

Description: ProgramLauncher defines the start method, which creates an
instance of Calculator as well as an AnchorPane and a TextField for
the UI. This is also where the Timeline animation for the bouncing
name and fading background is defined.
 */

package com.example.programmingassignmentgui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProgramLauncher extends Application {
  @Override
  public void start(Stage stage) {
    AnchorPane root = new AnchorPane();
    double padding = 10.0;

    char[] buttonContent = {'7', '8', '9', '/',
        '4', '5', '6', '*', '1', '2', '3', '-',
        '0', 'C', '=', '+'};
    double buttonSize = 75.0;
    Calculator calc = new Calculator(buttonContent, buttonSize);
    AnchorPane.setBottomAnchor(calc.getPane(), padding);
    AnchorPane.setRightAnchor(calc.getPane(), padding);
    AnchorPane.setLeftAnchor(calc.getPane(), padding);

    TextField screen = new TextField();
    screen.setEditable(false);
    screen.textProperty().bind(calc.getInputProperty());
    HBox hBox = new HBox(screen);
    HBox.setHgrow(screen, Priority.ALWAYS);
    AnchorPane.setTopAnchor(hBox, padding);
    AnchorPane.setRightAnchor(hBox, padding);
    AnchorPane.setLeftAnchor(hBox, padding);

    root.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    Text name = new Text("Landon Davidson");
    name.setFill(Color.ORANGE);
    name.relocate(100, 100);
    Timeline animation = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<>() {
      private double deltaX = 3.0;
      private double deltaY = 3.0;
      private double deltaOpacity = -0.003;
      private double curOpacity = 1;
      @Override
      public void handle(ActionEvent actionEvent) {
        curOpacity += deltaOpacity;
        Background nextBackground = new Background(new BackgroundFill(Color.web("#6495ED", curOpacity), CornerRadii.EMPTY, Insets.EMPTY));
        root.setBackground(nextBackground);
        if (curOpacity <= 0.5 || curOpacity >= 1.0) {deltaOpacity *= -1;}

        name.setLayoutX(name.getLayoutX() + deltaX);
        name.setLayoutY(name.getLayoutY() + deltaY);
        boolean atRightBorder = name.getLayoutX() >= root.getWidth() - name.getLayoutBounds().getWidth();
        boolean atLeftBorder = name.getLayoutX() <= 0;
        boolean atTopBorder = name.getLayoutY() <= name.getLayoutBounds().getHeight();
        boolean atBottomBorder = name.getLayoutY() >= root.getHeight();
        if (atRightBorder || atLeftBorder) {
          deltaX *= -1;
        } else if (atBottomBorder || atTopBorder) {
          deltaY *= -1;
        }
    }}));
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play();
    root.getChildren().addAll(name, calc.getPane(), hBox);

    Scene scene = new Scene(root, (buttonSize + 10) * 4 + 20.0, 400.0);
    stage.setTitle("Calculator");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}