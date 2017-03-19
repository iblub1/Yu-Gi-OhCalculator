package com.example.android.yu_gi_ohcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CharacterSelectActivity extends AppCompatActivity {

    String image_titles[];

    Integer image_ids[] = {
            R.drawable.akiza,
            R.drawable.bakura,
            R.drawable.bandit,
            R.drawable.bonz,
            R.drawable.chazz,
            R.drawable.dartz,
            R.drawable.ishizu,
            R.drawable.jack,
            R.drawable.jaden,
            R.drawable.joey,
            R.drawable.kaiba,
            R.drawable.mai,
            R.drawable.marik,
            R.drawable.pagasus,
            R.drawable.rex,
            R.drawable.solomon,
            R.drawable.syrus,
            R.drawable.tea,
            R.drawable.tristan,
            R.drawable.weevil,
            R.drawable.yugi,
            R.drawable.yugi_small,
            R.drawable.yuma,
            R.drawable.yusei,
            R.drawable.yuya
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        Intent intent = getIntent();
        image_titles = intent.getStringArrayExtra("strings");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imageGallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

    }


    public ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }

}
