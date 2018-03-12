package com.tool.flashcard.flashcardtool;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

public class DeckSelect extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select);

        ListView list = findViewById(R.id.mobile_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent = new Intent(DeckSelect.this, DeckSelectSubMenu.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.NewDeckButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelect.this, DeckSelectNewDeck.class);
                startActivity(intent);
            }
        });

    }
}
