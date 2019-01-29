package com.example.ayush.finalapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NegotiatorWallet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_negotiator_wallet);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QrCode.class);
                v.getContext().startActivity(intent);
            }
        });


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NegotiatorWallet.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_add_money,null);
                final EditText mAmount  = (EditText) mView.findViewById(R.id.add);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                Button button = (Button) mView.findViewById(R.id.button4);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) findViewById(R.id.amount);
                        textView.setText(mAmount.getText().toString());


                    }
                });
                //   mBuilder.setView(mView);
                //  AlertDialog dialog = mBuilder.create();
                // dialog.show();
                dialog.cancel();

                // Intent intent = new Intent(v.getContext(),AddMoney.class);
                // v.getContext().startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PreviousTransactions.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}



