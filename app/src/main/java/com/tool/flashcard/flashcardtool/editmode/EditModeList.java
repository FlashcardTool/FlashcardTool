package com.tool.flashcard.flashcardtool.editmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import com.tool.flashcard.flashcardtool.DeckSelect;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.DynamicListView;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.StableArrayAdapter;
import com.tool.flashcard.flashcardtool.R;

public class EditModeList extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode_list);

        Deck deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
        List<Flashcard> flashcards = deck.getAllCards();

        DynamicListView listView = findViewById(R.id.flashcard_list);
        StableArrayAdapter<Flashcard> adapter = new StableArrayAdapter<>(this, 0, flashcards);
        listView.setAdapter(adapter);
    }
}
