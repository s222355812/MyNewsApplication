package com.example.mynewsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title,description,content,image,url;
    private TextView titleTV,subDescTV,contentTV;
    private ImageView newsIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        description=getIntent().getStringExtra("description");
        image=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");
        titleTV= findViewById(R.id.idTVTitle);
        subDescTV= findViewById(R.id.idTVSubDesc);
        contentTV=findViewById(R.id.idTVContent);
        newsIV = findViewById(R.id.idIVNews);

        titleTV.setText(title);
        subDescTV.setText(description);
        contentTV.setText(content);
        Picasso.get().load(image).into(newsIV);
    }
}