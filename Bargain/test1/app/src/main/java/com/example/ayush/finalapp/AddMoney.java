package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddMoney extends AppCompatActivity {
    Integer integer;
    EditText editText;
    TextView textView;
    Editable Amount;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

//         editText = (EditText)findViewById(R.id.add);
//         Amount = editText.getText();
//          s = Amount.toString();




   /* Button button4 = (Button)findViewById(R.id.button4);
    button4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textView = (TextView)findViewById(R.id.amount);
            textView.setText("" + s);

            Intent intent = new Intent(v.getContext(),PaymentActivity.class);
            v.getContext().startActivity(intent);
        }
    });*/
    }
}
