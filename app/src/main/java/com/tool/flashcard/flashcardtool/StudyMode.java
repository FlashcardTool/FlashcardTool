package com.tool.flashcard.flashcardtool;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.Utilities.InputManager;

import org.w3c.dom.Text;

public class StudyMode extends InputManager
{
    private Deck        m_CurrentDeck;
    private Boolean     m_FrontOfCard;

    private TextView    m_CardQuestion;
    private TextView    m_DeckName;
    private TextView    m_CardNumber;
    private TextView    m_SideOfCard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);

        m_CardQuestion = findViewById(R.id.textView2);
        m_DeckName = findViewById(R.id.textView);
        m_CardNumber = findViewById(R.id.textView3);
        m_SideOfCard = findViewById(R.id.textView4);
    }

    final public void OnSwipeRight(float _position)
    {
        m_CurrentDeck.PreviousCard();
        DisplayCard();
    }

    final public void OnSwipeLeft(float _position)
    {
        m_CurrentDeck.NextCard();
        DisplayCard();
    }

    final public void OnSwipeVertical(float _position)
    {
        FlipCard();
    }

    final public void OnTouch(float _xPosition, float _yPosition)
    {
        FlipCard();
    }


    protected void onResume()
    {
        super.onResume();

        m_FrontOfCard = true;
        m_CurrentDeck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);

        m_CardQuestion.setText(m_CurrentDeck.GetCurrentCardFront());
        m_DeckName.setText(m_CurrentDeck.GetName());
        UpdateCurrentCardIndex();
    }

    protected void OnPause()
    {
        m_CurrentDeck.Reset();
    }

    private void FlipCard()
    {
        m_FrontOfCard = !m_FrontOfCard;

        if(m_FrontOfCard)
        {
            m_SideOfCard.setText("Top");
        }
        else
        {
            m_SideOfCard.setText("Bottom");
        }


        m_CardQuestion.setText(m_CurrentDeck.GetCardString(m_FrontOfCard));
    }

    private void UpdateCurrentCardIndex()
    {
        m_CardNumber.setText(m_CurrentDeck.GetCardNumber());
    }

    private void DisplayCard()
    {
        m_FrontOfCard = true;

        m_SideOfCard.setText("Top");
        m_CardQuestion.setText(m_CurrentDeck.GetCurrentCardFront());
        UpdateCurrentCardIndex();
    }
}