package com.tool.flashcard.flashcardtool;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.Utilities.InputManager;

public class EditMode_cardView extends InputManager {

     private Deck m_Deck;

     private EditText m_Top;
     private EditText m_Bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        m_Deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);

        m_Top = findViewById(R.id.editModeTopEditText);
        m_Top.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                m_Deck.UpdateCardValues(s.toString(), m_Deck.GetCurrentCardBack());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        m_Bottom = findViewById(R.id.editModeBottomEditText);
        m_Bottom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                m_Deck.UpdateCardValues(m_Deck.GetCurrentCardFront(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.imageButton4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Deck.CreateNewCard("Card Front", "Card Back");
                m_Deck.NextCard();
                DisplayCard();
            }
        });

        button = findViewById(R.id.imageButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Deck.DeleteFlashcard();
                m_Deck.PreviousCard();
                DisplayCard();
            }
        });

        button = findViewById(R.id.imageButton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Deck.PreviousCard();
                DisplayCard();
            }
        });

        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Deck.NextCard();
                DisplayCard();
            }
        });

    }

    protected void onResume()
    {
        super.onResume();

        DisplayCard();
    }

    protected void onPause()
    {
        super.onPause();

        m_Deck.Reset();
    }

    private void DisplayCard()
    {
        m_Top.setText(m_Deck.GetCurrentCardFront());
        m_Bottom.setText(m_Deck.GetCurrentCardBack());
    }

    final public void OnSwipeLeft(float _position)
    {
        m_Deck.NextCard();
        DisplayCard();
    }

    final public void OnSwipeRight(float _position)
    {
        m_Deck.PreviousCard();
        DisplayCard();
    }
}
