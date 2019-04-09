package com.example.calculatorito.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.example.calculatorito.R;
import com.example.calculatorito.databinding.ActivityMainBinding;
import com.example.calculatorito.model.CalculatorModel;
import com.example.calculatorito.presenter.MainActivityPresenter;
import com.example.calculatorito.viewmodel.MainActivityViewModel;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements MainActivityPresenter {

    private FragmentManager manager;
    private CalculatorModel calculatorModel;
    private String equation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            equation = savedInstanceState.getString("equation");
        }
        calculatorModel = new CalculatorModel(equation);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(this);
        activityMainBinding.setModel(calculatorModel);
        activityMainBinding.setViewModel(mainActivityViewModel);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("equation", equation);
    }

    @Override
    public void showCalculation(String value) {

            if (value.equals(MainActivity.this.getResources().getString(R.string.equal))) {
                try {
                    if (validateExpression(equation)){
                    Expression expression = new ExpressionBuilder(equation).build();
                    double result = expression.evaluate();
                    //padaro tik du skaitmenis po kablelio
                    result = Double.parseDouble(new DecimalFormat("##.##").format(result));
                    equation = Double.toString(result);
                    //istrina nulius po kablelio
                    if (equation.endsWith(".0"))
                    equation = String.valueOf(equation).replaceAll("[0]*$", "").replaceAll(".$", "");
                    calculatorModel.setValue(equation);
                    }
                    else {
                        equation = "";
                        calculatorModel.setValue("ERROR");
                    }
                } catch (Exception ex) {
                    equation = "";
                    calculatorModel.setValue("ERROR");
                }
            }
            else if(value.equals("√"))
            {
                try {
                    Expression expression = new ExpressionBuilder(equation).build();
                    double result = expression.evaluate();
                    double finalResult = Math.sqrt(result);
                    //padaro tik du skaitmenis po kablelio
                    finalResult = Double.parseDouble(new DecimalFormat("##.##").format(finalResult));
                    equation = Double.toString(finalResult);
                    //istrina nulius po kablelio
                    if (equation.endsWith(".0"))
                    equation = String.valueOf(equation).replaceAll("[0]*$", "").replaceAll(".$", "");
                    calculatorModel.setValue(equation);
                }
                catch (Exception ex)
                {
                    equation="";
                    calculatorModel.setValue("ERROR");
                }

            }
            else if (value.equals(MainActivity.this.getResources().getString(R.string.delete))) {
                equation = "";
                calculatorModel.setValue("0");
            } else {
                equation += value;
                calculatorModel.setValue(equation);
            }

    }

    //padeda patikrint ar teisinga israiska
    public boolean validateExpression(String expression) {
        if (expression.endsWith("*") ||
                expression.endsWith("/") ||
                expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith(".") ||
                expression.endsWith("(") ||
                expression.endsWith("√")) {
            return false;
        } else if (expression.length() > 18) {
            return false;
        } else {
            return true;
        }
    }
}
