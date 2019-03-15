package com.example.ayush.finalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateandReview extends AppCompatActivity {
    RatingBar ratenegotiator;
    float rateval;
    EditText review;
    Button submitrate;
    TextView ratetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateand_review);
        ratenegotiator = (RatingBar)findViewById(R.id.ratenego);
        ratetext= (TextView)findViewById(R.id.ratetextview) ;
        review = (EditText)findViewById(R.id.reviewnego);

        ratenegotiator.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateval=rating;
//                Toast.makeText(MainActivity.this,"Rating" + String.valueOf(rating),Toast.LENGTH_SHORT).show();
                ratetext.setText(String.valueOf(rateval));
            }
        });

        // contentreview = review.getText().toString();


        submitrate=(Button)findViewById(R.id.submitreview);
        submitrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submitrate.setText(review.getText().toString()  );
                //Toast.makeText(RateandReview.this,"Rating" + String.valueOf(rateval),Toast.LENGTH_LONG).show();
                ratetext.setText(String.valueOf(rateval));
            }
        });
    }
}
