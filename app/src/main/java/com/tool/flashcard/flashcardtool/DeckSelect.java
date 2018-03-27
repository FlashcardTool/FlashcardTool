package com.tool.flashcard.flashcardtool;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;

import java.util.ArrayList;
import java.util.List;

public class DeckSelect extends AppCompatActivity
{
    public static List<Deck> Manager;
    public static int        CurrentDeckIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Manager = new ArrayList<>();
        Deck deck = new Deck("Test Deck");
        Manager.add(deck);

        for(int ind = 0; ind < 45; ind++)
        {
            deck.CreateNewCard("Front of card " + Integer.toString(ind), "Back of card " + Integer.toString(ind));
        }

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
