package com.tool.flashcard.flashcardtool.editmode;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tool.flashcard.flashcardtool.DeckSelect;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.R;

public class EditMode extends AppCompatActivity {

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


        FloatingActionButton fab = findViewById(R.id.DeleteFlashcardButton);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Deck deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
                deck.DeleteFlashcard();
                Intent intent = new Intent(EditMode.this, EditModeList.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(EditMode.this, EditModeList.class);
        startActivity(intent);
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
}
