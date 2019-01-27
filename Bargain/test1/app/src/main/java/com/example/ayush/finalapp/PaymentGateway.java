package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentGateway extends AppCompatActivity {
   /* EditText editText = (EditText)findViewById(R.id.editAmount);
    String string;
    TextView textView = (TextView)findViewById(R.id.PayAmount);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

      // string = editText.getText().toString();
        Button button = (Button)findViewById(R.id.PayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  textView.setText(string);
                Intent intent = new Intent(v.getContext(),PaymentActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}
