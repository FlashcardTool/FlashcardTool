package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.DeckListAdapter;
import com.tool.flashcard.flashcardtool.editmode.EditModeList;

import java.util.ArrayList;
import java.util.List;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.Button;

public class DeckSelect extends AppCompatActivity implements View.OnClickListener
{
    private static DeckSelect instance;

    private Button buttonAutoMode;
    private Button buttonDayMode;
    private Button buttonNightMode;

    public static List<Deck> Manager;
    public static int CurrentDeckIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        Manager = new ArrayList<>();


        //Load Flashcards Deck

        for (int i = 0; i < 20; i++) {
            Deck deck = new Deck("Test Deck " + i);
            Manager.add(deck);

            for (int ind = 0; ind < 5; ind++) {
                deck.CreateNewCard("Front of card " + Integer.toString(ind), "Back of card " + Integer.toString(ind));
            }

            deck.Reset();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select);

        initViews();
        initListeners();

        FloatingActionButton fab = findViewById(R.id.NewDeckButton);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
            Deck deck = new Deck("New Deck");
            Manager.add(deck);

            // Update List
            RecyclerView deck_list = findViewById(R.id.DeckList);
            deck_list.getAdapter().notifyItemInserted(Manager.size() - 1);
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


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deck_select_sub_menu, menu);
        menu.setHeaderTitle(DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex).GetName());
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
                intent = new Intent(this, EditModeList.class);
                startActivity(intent);
                break;
            case R.id.sub_menu_export:
                break;
        }

        return true;
    }


    public void onClick(View v)
    {

        switch (v.getId()) {
            case R.id.buttonAutoMode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case R.id.buttonDayMode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.buttonNightMode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }

        Intent intent = new Intent(DeckSelect.this, ModeViewActivity.class);
        startActivity(intent);

    }

    private void initViews() {
        buttonAutoMode = (Button) findViewById(R.id.buttonAutoMode);
        buttonDayMode = (Button) findViewById(R.id.buttonDayMode);
        buttonNightMode = (Button) findViewById(R.id.buttonNightMode);

    }

    private void initListeners() {
        buttonAutoMode.setOnClickListener(this);
        buttonDayMode.setOnClickListener(this);
        buttonNightMode.setOnClickListener(this);
    }
}
