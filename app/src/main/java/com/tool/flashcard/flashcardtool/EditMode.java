package com.tool.flashcard.flashcardtool;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;

public class EditMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Deck deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);

        final EditText top = findViewById(R.id.editModeTopEditText);
        top.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deck.UpdateCardValues(s.toString(), deck.GetCurrentCardBack());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final EditText bottom = findViewById(R.id.editModeBottomEditText);
        bottom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deck.UpdateCardValues(deck.GetCurrentCardFront(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.imageButton4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deck.CreateNewCard("test", "back");
                deck.NextCard();
                top.setText(deck.GetCurrentCardFront());
                bottom.setText(deck.GetCurrentCardBack());
            }
        });

        button = findViewById(R.id.imageButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deck.DeleteFlashcard();
                deck.PreviousCard();
                top.setText(deck.GetCurrentCardFront());
                bottom.setText(deck.GetCurrentCardBack());
            }
        });

        button = findViewById(R.id.imageButton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deck.PreviousCard();
                top.setText(deck.GetCurrentCardFront());
                bottom.setText(deck.GetCurrentCardBack());
            }
        });

        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deck.NextCard();
                top.setText(deck.GetCurrentCardFront());
                bottom.setText(deck.GetCurrentCardBack());
            }
        });

    }
}
