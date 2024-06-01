package com.example.calculator;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    TextView inputArea;
    private MyDatabaseManager dbManager;

    // functions that turn the inerFix to postFix
    private List<String> turnStringExpressionToList(String expression) {
        StringBuilder tempElement = new StringBuilder();
        List<String> result = new ArrayList<>();
        for(int i=0;i<expression.length();i++) {
            tempElement.append(expression.charAt(i));

            if(i <= expression.length() - 2)
                if(Character.isDigit(expression.charAt(i+1)) && Character.isDigit(expression.charAt(i)))
                    continue;

            result.add(tempElement.toString());
            tempElement.setLength(0);
        }
        return result;
    }

    private int precedence(String ch) {
        switch (ch) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
        }
        return -1;
    }

    public List<String> infixToPostfix(List<String> expression) {
        List<String> result = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : expression) {
            if (token.matches("\\d+")) {
                result.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                    result.add(stack.pop());
                }
                stack.push(token);
            }
        }


        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Invalid Expression"); // Biểu thức không hợp lệ
            }
            result.add(stack.pop());
        }

        return result;
    }

    //input and delete the expression
    private void putTextIntoInputArea(String s){
        //TextView inputArea = findViewById(R.id.inputArea);
        inputArea = findViewById(R.id.input);
        inputArea.append(s);

    }

    public void deleteLastCharacter(View view) {
        //TextView inputArea = findViewById(R.id.inputArea);
        inputArea = findViewById(R.id.input);
        String currentText = inputArea.getText().toString();
        if (!currentText.isEmpty()) {
            // Xóa ký tự cuối cùng
            String newText = currentText.substring(0, currentText.length() - 1);
            inputArea.setText(newText);
        } else {
            Toast.makeText(this, "Nothing to delete", Toast.LENGTH_SHORT).show();
        }
    }

    // calculate the postFix expression
    public int evaluatePostfix(List<String> expression) {
        Stack<Integer> stack = new Stack<>();

        for (String token : expression) {
            if (isOperand(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(token, operand1, operand2);
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private boolean isOperand(String token) {
        return Character.isDigit(token.charAt(0));
    }

    private int performOperation(String operator, int operand1, int operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new MyDatabaseManager(this);
        dbManager.open();
        dbManager.close();
    }

    // input onclick
    public void handleOperateButton(View view){
        if(view.getId() == R.id.bt1){
            putTextIntoInputArea("1");
        }else if (view.getId() == R.id.bt2){
            putTextIntoInputArea("2");
        }else if (view.getId() == R.id.bt3){
            putTextIntoInputArea("3");
        }else if (view.getId() == R.id.bt4){
            putTextIntoInputArea("4");
        }else if (view.getId() == R.id.bt5){
            putTextIntoInputArea("5");
        }else if (view.getId() == R.id.bt6){
            putTextIntoInputArea("6");
        }else if (view.getId() == R.id.bt7){
            putTextIntoInputArea("7");
        }else if (view.getId() == R.id.bt8){
            putTextIntoInputArea("8");
        }else if (view.getId() == R.id.bt9){
            putTextIntoInputArea("9");
        }else if (view.getId() == R.id.bt0){
            putTextIntoInputArea("0");
        }else if (view.getId() == R.id.btCong){
            putTextIntoInputArea("+");
        }else if (view.getId() == R.id.btTru){
            putTextIntoInputArea("-");
        }else if (view.getId() == R.id.btNhan){
            putTextIntoInputArea("*");
        }else if (view.getId() == R.id.btChia){
            putTextIntoInputArea("/");
        }else if (view.getId() == R.id.btMoNgoac){
            putTextIntoInputArea("(");
        }else if (view.getId() == R.id.btDongNgoac){
            putTextIntoInputArea(")");
        }
    }

    // output onclick, caculating the expression and save to the database

    public void calculate(View view){
        inputArea = findViewById(R.id.input);
        TextView outputArea = findViewById(R.id.output);
        String inputString = inputArea.getText().toString();
        List<String> innerFix = turnStringExpressionToList(inputString);
        List<String> posFix = infixToPostfix(innerFix);
        int result = evaluatePostfix(posFix);
        outputArea.setText(String.valueOf(result));

        // save result to the database
        dbManager.open();
        dbManager.insertCalculation(inputString, (float)result);
        dbManager.close();
    }
    // db switch onclick
    public void switch_to_kq(View v){
        Intent IManHinhKQ = new Intent(this, MainDBActivity.class);
        startActivity(IManHinhKQ);
    }
}