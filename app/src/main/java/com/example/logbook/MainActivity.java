package com.example.logbook;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText editUrl;
    Button buttonAdd;

    ImageView imageView;
    List<String> imageList = new ArrayList<String>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        editUrl = findViewById(R.id.editUrl);
        imageList.add("https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_3x2.jpg");
        imageList.add("https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/322868_1100-800x825.jpg");
        imageList.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg");
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLink = editUrl.getText().toString();
                linkUrl linkUrl = new linkUrl();
                Thread newThread = new Thread(linkUrl);
                newThread.start();
                try {
                    newThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean isImage = linkUrl.isImage();

                if (urlLink.equals("") || !isImage) {
                    Toast.makeText(getApplicationContext(), "Wrong Url Image", Toast.LENGTH_SHORT).show();
                }else {
                    imageList.add(urlLink);

                    loadImage(urlLink);

                }
            }
        });
        loadImage(imageList.get(0));

    }



    class linkUrl extends Thread {

    boolean isImage;

        @Override
        public void run() {
            try {
                String urlLink = editUrl.getText().toString();
                URLConnection connection = new URL(urlLink).openConnection();
                String contentType = connection.getHeaderField("Content-Type");
                isImage = contentType.startsWith("image/"); //true if image

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean isImage(){
            return isImage;
        }
    }

    private void loadImage(String url) {
        Glide.with(MainActivity.this)
                .load(url)
                .centerCrop()
                .into(imageView);
    }


    public void nextImage(View v){
        index++;

        if(index <= imageList.size() -1){
            loadImage(imageList.get(index));
        } else
            index = imageList.size() -1;
    }

    public void previousImage(View v){
        index--;
        if(index >= 0){
            loadImage(imageList.get(index));
        } else
            index = 0;

    }




}