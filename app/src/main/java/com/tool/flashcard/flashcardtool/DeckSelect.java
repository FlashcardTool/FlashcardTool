package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.DeckListAdapter;

import java.util.ArrayList;
import java.util.List;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuInflater;

public class DeckSelect extends AppCompatActivity
{
    private static DeckSelect instance;

    public static List<Deck> Manager;
    public static int CurrentDeckIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        Manager = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Deck deck = new Deck("Test Deck " + i);
            Manager.add(deck);

            for (int ind = 0; ind < 5; ind++) {
                deck.CreateNewCard("Front of card " + Integer.toString(ind), "Back of card " + Integer.toString(ind));
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select);

        FloatingActionButton fab = findViewById(R.id.NewDeckButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(DeckSelect.this, DeckSelectNewDeck.class);
                //startActivity(intent);
            }
        });

        RecyclerView deck_list = findViewById(R.id.DeckList);
        deck_list.setLayoutManager(new LinearLayoutManager(this));
        deck_list.setAdapter(new DeckListAdapter());

        registerForContextMenu(deck_list);
    }

    public static void onItemClick(View view, int deck_id, boolean long_click)
    {
        DeckSelect.CurrentDeckIndex = deck_id;

        if(!long_click)
        {
            instance.openContextMenu(view);
        }
        /*String string = deck_id + ": ";
        string += long_click ? "Long" : "Short";
        Toast.makeText(instance, string, Toast.LENGTH_SHORT).show();*/

        //Intent intent = new Intent(instance, DeckSelectSubMenu.class);
        //instance.startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        //menu.setHeaderTitle(DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex).GetName());
        /*menu.add(0, v.getId(), 0, "Study");
        menu.add(0, v.getId(), 0, "Quiz");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Export");*/

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deck_select_sub_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        Intent intent;
        int id = item.getItemId();
        switch (id)
        {
            case R.id.sub_menu_study:
                intent = new Intent(this, StudyMode.class);
                startActivity(intent);
                break;
            case R.id.sub_menu_quiz:
                intent = new Intent(this, QuizModeQuestion.class);
                startActivity(intent);
                break;
            case R.id.sub_menu_edit:
                intent = new Intent(this, EditMode.class);
                startActivity(intent);
                break;
            case R.id.sub_menu_export:
                break;
        }

        return true;
    }
}
