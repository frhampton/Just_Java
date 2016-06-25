package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

   private String isWipped;
    private String isChocolate;
   public int quantity= 0;

    int addOnTopping;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayQuantity(quantity);


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        if(quantity >0) {
            displayPrice(quantity * 5);
            EditText editText = (EditText) findViewById(R.id.editText);
            TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
            Intent i = new Intent(getIntent().ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Java Order for " + editText.getText().toString());
            i.putExtra(Intent.EXTRA_TEXT, priceTextView.getText().toString());

            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {

            }
        }
    }

    public void isChecked(View view){

        seeIfChecked();
    }

    public void increment(View view){
        quantity+=1;
        displayQuantity(quantity);
    }

    public void decrement(View view){
        if(quantity > 0) {
            quantity -= 1;
            displayQuantity(quantity);
            return;
        }
        Toast.makeText(getApplication(),"Can't have negative orders",Toast.LENGTH_SHORT).show();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        seeIfChecked();
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        if(number >0) {
            priceTextView.setText("Name: " + editText.getText().toString() +"\nAdd whipped cream? "+  isWipped + "\nAdd Chocolate? " + isChocolate +"\nQuantity: " + quantity + "\nTotal: " + NumberFormat.getCurrencyInstance().format(number+ addOnTopping) + "\nThank you");
            return;
            //NumberFormat adds currency to format
        }
         priceTextView.setText("Total: " + NumberFormat.getCurrencyInstance().format(number) + "\nBoo you");
    }

    private void seeIfChecked() {
        int wippedPrice =1;
        int chocolatePrice=2;

        final CheckBox checkBoxCream = (CheckBox) findViewById(R.id.checkBoxCream);
        final CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.checkBoxChocolate);

        if(checkBoxChocolate.isChecked() && checkBoxCream.isChecked()){
            isWipped ="Yes";
            isChocolate ="Yes";
            addOnTopping =(wippedPrice+chocolatePrice*quantity);
        }else if(checkBoxCream.isChecked()){
            isWipped ="Yes";
            isChocolate ="No";
            addOnTopping =(wippedPrice * quantity);

        }else if(checkBoxChocolate.isChecked()){
            isChocolate="Yes";
            isWipped ="No";
            addOnTopping =(chocolatePrice * quantity);
        }else{
            isWipped ="No";
            isChocolate ="No";
            addOnTopping = 0;
        }
    }
}