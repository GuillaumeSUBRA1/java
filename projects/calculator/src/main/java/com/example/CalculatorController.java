package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController {
    @FXML
    private Label outputLabel;

    @FXML
    private Label calculationLabel;

    private boolean pressedBinary, pressedEqual, pressedUnary;
    private boolean storedNum1, storedNum2;

    private double num1, num2;
    private String binaryOperator;

    @FXML
    public void handleNumber(ActionEvent e) {
        Button b = (Button) e.getSource();
        String nb = b.getText();
        String output = outputLabel.getText();

        if (canReplace0(output)) {
            outputLabel.setText(nb);
            if (storeNum2()) {
                storedNum2 = true;
            }
            pressedEqual = false;
            pressedUnary = false;
        } else {
            outputLabel.setText(output + nb);
        }
    }

    @FXML
    public void handleUnary(ActionEvent e) {
        Button b = (Button) e.getSource();
        String unary = b.getText();

        double n1 = Double.parseDouble(outputLabel.getText());

        switch (unary) {
            case Constants.PERCENT:
                n1 /= 100;
                calculationLabel.setText(Double.toString(n1));
                break;
            case Constants.RECIPROCAL:
                n1 = 1 / n1;
                calculationLabel.setText("1/" + n1);
                break;
            case Constants.SQUARE:
                n1 = n1 * n1;
                calculationLabel.setText(n1 + "²");
                break;
            case Constants.SQRT:
                n1 = Math.sqrt(n1);
                calculationLabel.setText("sqrt(" + n1 + ")");
                break;
            case Constants.NEGATE:
                n1 *= -1;
                break;
        }

        if (!storedNum1) {
            num1 = n1;
            storedNum1 = true;

        } else if (storeNum2()) {
            num2 = n1;
            storedNum2 = true;
        }

        outputLabel.setText(Double.toString(n1));

        pressedUnary = true;
        pressedEqual = false;
        pressedBinary = false;
    }

    @FXML
    public void handleBinary(ActionEvent e) {
        Button b = (Button) e.getSource();
        String binary = b.getText();

        if (!storedNum1) {
            num1 = Double.parseDouble(outputLabel.getText());
            storedNum1 = true;
        }

        if (storedNum1) {
            updateBinary(binary);
        }

        pressedBinary = true;
        pressedUnary = false;
        pressedEqual = false;
    }

    @FXML
    public void handleDot() {
        if (!outputLabel.getText().contains(".")) {
            outputLabel.setText(outputLabel.getText() + ".");
        }
    }

    @FXML
    public void onPressed(ActionEvent e) {
        Button b = (Button) e.getSource();
        String pressed = b.getText();

        switch (pressed) {
            case Constants.CLEAR_ENTRY:
                outputLabel.setText("0");
                break;
            case Constants.CLEAR:
                reset();
                break;
            case Constants.DELETE:
                if (Double.parseDouble(outputLabel.getText()) != 0) {
                    outputLabel.setText(outputLabel.getText().substring(0,
                            outputLabel.getText().length() - 1));
                }

                if (outputLabel.getText().length() <= 0) {
                    outputLabel.setText("0");
                }
                break;

        }
    }

    @FXML
    public void handleEqual() {
        if (storeNum2()) {
            num2 = num1;
            storedNum2 = true;
        }

        if (canCalculate()) {
            equal();
            pressedEqual = true;
            pressedBinary = false;
            pressedUnary = false;
        }
    }

    private boolean canReplace0(String output) {
        return (storedNum1 && pressedBinary && !storedNum2)
                || pressedEqual || pressedUnary
                || Double.parseDouble(output) == 0;
    }

    private boolean storeNum2() {
        return !storedNum2 && storedNum1 && pressedBinary;
    }

    private void updateBinary(String op) {
        this.binaryOperator = op;

        calculationLabel.setText(num1 + " " + this.binaryOperator);
    }

    private void reset() {
        outputLabel.setText("0");
        calculationLabel.setText("");
        storedNum1 = false;
        storedNum1 = false;
        pressedBinary = false;
        pressedUnary = false;
        pressedEqual = false;
    }

    private boolean canCalculate() {
        return storedNum1 && storedNum2;
    }

    private void equal() {
        num2 = Double.parseDouble(outputLabel.getText());
        storedNum2 = true;

        num1 = performCalculate();
        outputLabel.setText(Double.toString(num1));
    }

    private double performCalculate() {
        double result = 0;

        switch (binaryOperator) {
            case Constants.ADD:
                result = num1 + num2;
                break;
            case Constants.SUBSTRACT:
                result = num1 - num2;
                break;
            case Constants.DIVIDE:
                if (num2 == 0) {
                    outputLabel.setText("Error : can't divide by 0");
                    reset();
                } else {
                    result = num1 / num2;
                }
                break;
            case Constants.MULTIPLY:
                result = num1 * num2;
                break;
        }

        calculationLabel.setText(num1 + " " + binaryOperator + " " + num2 + " = ");

        num2 = 0;
        storedNum2 = false;

        return result;
    }
}