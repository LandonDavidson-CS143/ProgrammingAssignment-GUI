package com.example.programmingassignmentgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;

public class Calculator {
  private final StringProperty input = new SimpleStringProperty("");
  private final TilePane buttonPane = new TilePane();
  public Calculator(char[] buttonContents, double buttonSize) {
    buttonPane.setPrefRows(4);
    buttonPane.setPrefColumns(4);
    buttonPane.setPrefTileHeight(buttonSize + 10.0);
    buttonPane.setPrefTileWidth(buttonSize + 10.0);
    for (char content : buttonContents) {
      Button button = createButton(content);
      button.setPrefSize(buttonSize, buttonSize);
      buttonPane.getChildren().add(button);
    }
  }

  private boolean isLastCharOperator() {
      char lastChar = input.get().length() > 0 ?
          input.get().charAt(input.get().length() - 1) : ' ';
      return lastChar == '+' || lastChar == '-' ||
          lastChar == '*' || lastChar == '/';
    }
  private Button createButton (char content) {
    Button button = new Button(Character.toString(content));
    if (content == 'C') {
      button.setOnAction(event -> input.set(""));
    } else if (content == '=') {
      button.setOnAction(event -> {
        if (isLastCharOperator() || input.get().length() <= 1) {return;}
        input.set(calculateInput());
      });
    } else if (content == '+' || content == '-' || content == '/' || content == '*') {
      button.setOnAction(event -> {
        if (isLastCharOperator()) {return;}
        input.set(input.get() + content);
        });
    } else {
      button.setOnAction(event -> input.set(input.get() + content));
    }
    return button;
  }

  private String calculateInput() {
    String[] ops = input.get().replaceAll("\\d", "").replaceAll("\\.", "").split("");
    String[] nums = input.get().split("[-+/*]");
    ArrayList<ArrayList<Double>> sortedNums = new ArrayList<>();
    sortedNums.add(new ArrayList<>());
    sortedNums.get(0).add(Double.parseDouble(nums[0]));
    for (int i = 0; i < ops.length; i++) {
      boolean subtraction = false;
      switch (ops[i]) {
        case "-": subtraction = true;
        case "+": sortedNums.add(new ArrayList<>());
        default: sortedNums.get(sortedNums.size() - 1).add(Double.parseDouble(nums[i + 1])); break;
        case "/": sortedNums.get(sortedNums.size() - 1).add(1 / Double.parseDouble(nums[i + 1])); break;
      }
      if (subtraction) {
        sortedNums.get(sortedNums.size() - 1).add(-1.0);
      }
    }
    double total = 0.0;
    for (ArrayList<Double> p : sortedNums) {
      double result = p.get(0);
      for (int j = 1; j < p.size(); j++) {
        result *= p.get(j);
      }
      total += result;
    }
    return String.format(total % 1.0 > 0.0001 && total % 1.0 < 0.9999 ? "%s" : "%.0f", total);
  }

  public TilePane getPane() {return buttonPane;}

  public StringProperty getInputProperty() {return input;}
}
