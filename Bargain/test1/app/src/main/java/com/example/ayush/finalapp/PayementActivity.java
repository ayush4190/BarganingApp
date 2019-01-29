package com.example.ayush.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PayementActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.payment_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);



        Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QrCode.class);
                v.getContext().startActivity(intent);
            }
        });


      /*  Button button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PaymentActivity.this);
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
        });*/

        Button button3 = (Button) view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PreviousTransactions.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}




