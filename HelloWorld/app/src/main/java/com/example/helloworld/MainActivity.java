package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //Declaration
    Button clear, delete, equal, lightMode, darkMode;
    LinearLayout numbersBackground;
    ConstraintLayout body;
    int [] id = {R.id.btnOne,R.id.btnTwo,R.id.btnThree,R.id.btnFour,R.id.btnFive,R.id.btnSix,R.id.btnSeven,R.id.btnEight,R.id.btnNine, R.id.btnZero, R.id.btnPoint};
    Button [] number = new Button[id.length];
    int [] op_id = {R.id.btnDivide,R.id.btnMultiply,R.id.btnSub,R.id.btnPlus,R.id.btnFact};
    Button [] op = new Button[op_id.length];
    char operator;
    char DEFAULT__OPERATOR = '\u0000';
    double value__i, value__ii, result;
    String currentInput__String = "\0", btnValue, __operationString, inputMonitor;
    TextView __txtDisplayInput, txtAnswer;
    EditText __txtGetInputs;
    boolean onOperatorChange = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_main);

        //initialization
        __txtGetInputs =  findViewById(R.id.screen);
        __txtDisplayInput = findViewById(R.id.txtInput);
        txtAnswer =  findViewById(R.id.txtResult);
        equal =  findViewById(R.id.btnEqual);
        clear =  findViewById(R.id.btnClr);
        delete =  findViewById(R.id.btnDelete);
        operator = DEFAULT__OPERATOR;
        numbersBackground = findViewById(R.id.layoutKeys);
        body = findViewById(R.id.bodyLayout);
        lightMode = findViewById(R.id.btnLightMode);
        darkMode = findViewById(R.id.btnDarkMode);

        for (int i = 0; i < id.length; i++){
            final int index = i;
            number[index] =  findViewById(id[index]);
            number[index].setOnClickListener(v ->{
                btnValue = number[index].getText().toString();
                if (btnValue.equals(".")){
                    if(!currentInput__String.contains(btnValue)){
                        currentInput__String = (currentInput__String.equals("\0")) ? __txtGetInputs.getText().toString().concat("0".concat(btnValue)) : __txtGetInputs.getText().toString().concat(btnValue);
                    }
                    else btnValue = "";
                }else
                     currentInput__String = __txtGetInputs.getText().toString().concat(btnValue);

                __txtGetInputs.setText(currentInput__String);
                String input = __txtDisplayInput.getText().toString();
                __txtDisplayInput.setText(input.concat(btnValue));
            });
        }

        for(int i = 0;i < op_id.length;i++){
            final int index = i;
            op[index]= findViewById(op_id[index]);
            op[index].setOnClickListener(v ->{
                __operationString = op[index].getText().toString();
                operator = __operationString.charAt(0);
                String input = __txtDisplayInput.getText().toString();
                __txtDisplayInput.setText(input.concat(__operationString));
                inputMonitor = input;
                currentInput__String = "\0";
                __txtGetInputs.setText(currentInput__String);
                if(onOperatorChange){
                    value__i = result;
                }
                onOperatorChange = true;
            });
        }
      __txtGetInputs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentInput__String = __txtGetInputs.getText().toString();
                __autoCompute(currentInput__String);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        lightMode.setOnClickListener(v ->{
            __lightMode();
        });
        darkMode.setOnClickListener(v ->{
            __darkMode();
        });
        equal.setOnClickListener(v ->{
            txtAnswer.setTextSize(36);
            __clearScreen__ResetValues();
        });
        clear.setOnClickListener(v ->
        {
            txtAnswer.setTextSize(28);
            __txtGetInputs.setText(__clearScreen__ResetValues());
            __txtDisplayInput.setText(__clearScreen__ResetValues());
            txtAnswer.setText(__clearScreen__ResetValues());
        });
        delete.setOnClickListener(v ->
        {
            String __stringOnScreen = __txtGetInputs.getText().toString();
            __txtGetInputs.setText(__charDelete(__stringOnScreen));
            __txtDisplayInput.setText(__charDelete(__stringOnScreen));
        });
    }
    public  String __clearScreen__ResetValues(){
        currentInput__String = "\0";
        value__i = value__ii = result = 0;
        operator = DEFAULT__OPERATOR;
        onOperatorChange = false;

        return currentInput__String;
    }
    public String __charDelete(String str){
        try {
            if(!(str.equals(""))){
                StringBuilder eraser = new StringBuilder(str);
                eraser.reverse();
                eraser.deleteCharAt(0);
                eraser.reverse();
                return new String(eraser);
            }
        }catch (Exception e){
            __txtGetInputs.setText(e.toString());
        }

        return " ";
    }
    public double __computeResult(double val__i, double val__ii){
        try {
            switch(operator){
                case '+':
                    result = val__i + val__ii;
                    break;
                case '×':
                    result = val__i * val__ii;
                    break;
                case '-':
                    result = val__i - val__ii;
                    break;
                case '÷':
                    result = val__i / val__ii;
                    break;
                case '^':
                    result = Math.pow(val__i, val__ii);
                    break;
                default:
                    txtAnswer.setText("Math Error!");
            }
            return result;
        }catch (Exception e){
            __txtGetInputs.setText(e.toString());
        }
        return 0;
    }
    public double __computeFactorial(double __num__to__compute){
        try {
            if(__num__to__compute <= 1)
                return 1;
            return __num__to__compute * __computeFactorial(__num__to__compute - 1);
        }
        catch (Exception e){
            __txtGetInputs.setText(e.toString());
        }
        return 0;
    }
    public double __autoCompute(String currentInput__String){
        if(!(currentInput__String.equals("\0"))){
            if(!onOperatorChange){
                value__i = Double.parseDouble(currentInput__String);
            }
            else{
                if(operator != '!')
                    value__ii = Double.parseDouble(currentInput__String);
            }
        }
        if(!(currentInput__String.equals("\0")) && onOperatorChange) {
            if (operator != '!'){
                result = __computeResult(value__i, value__ii);
                txtAnswer.setText(String.valueOf(result));
            }
        }
        if(currentInput__String.equals("\0") && operator == '!') {
            result = __computeFactorial(value__i);
            txtAnswer.setText((!inputMonitor.contains(".")) ? String.valueOf(result) : "Math Error!");
        }
        return result;
    }
    public  void __darkMode(){

        numbersBackground.setBackgroundResource(R.color.darker_gray);
        body.setBackgroundResource(R.color.black);
        for (int i = 0; i < id.length; i++){
            final int index = i;
            number[index] =  findViewById(id[index]);
            number[index].setTextColor(getResources().getColor(R.color.wheat));

        }
        __txtDisplayInput.setTextColor(getResources().getColor(R.color.wheat));
        txtAnswer.setTextColor(getResources().getColor(R.color.wheat));
    }
    public  void __lightMode(){
        numbersBackground.setBackgroundResource(R.color.wheat);
        body.setBackgroundResource(R.color.light_mode);
        for (int i = 0; i < id.length; i++){
            final int index = i;
            number[index] =  findViewById(id[index]);
            number[index].setTextColor(getResources().getColor(R.color.darker_gray));

        }
        __txtDisplayInput.setTextColor(getResources().getColor(R.color.darker_gray));
        txtAnswer.setTextColor(getResources().getColor(R.color.darker_gray));
    }
}