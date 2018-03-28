package com.tool.flashcard.flashcardtool;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;

public class StudyMode extends Activity{

    private static final float MIN_DISTANCE = 500.0f;

    private Deck    m_CurrentDeck;
    private Boolean m_FrontOfCard;

    private float   m_StartTouhPositionY;
    private float   m_EndTouchPositionY;
    private float   m_StartTouhPositionX;
    private float   m_EndTouchPositionX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);

        ImageButton button = findViewById(R.id.imageButtonFlip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FlipCard();
            }
        });

        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                m_CurrentDeck.NextCard();
                DisplayCard();
            }
        });

        button = findViewById(R.id.imageButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                m_CurrentDeck.PreviousCard();
                DisplayCard();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                m_StartTouhPositionY = event.getY();
                m_StartTouhPositionX = event.getX();
                break;
            case (MotionEvent.ACTION_UP):
                m_EndTouchPositionY = event.getY();
                m_EndTouchPositionX = event.getX();
                if(Math.abs(m_EndTouchPositionY - m_StartTouhPositionY) > MIN_DISTANCE)
                {
                    FlipCard();
                }
                else if((m_EndTouchPositionX - m_StartTouhPositionX) > MIN_DISTANCE)
                {
                    m_CurrentDeck.PreviousCard();
                    DisplayCard();
                }
                else if(Math.abs(m_EndTouchPositionX - m_StartTouhPositionX) > MIN_DISTANCE)
                {
                    m_CurrentDeck.NextCard();
                    DisplayCard();
                }
        }

        return super.onTouchEvent(event);
    }


    protected void onResume()
    {
        super.onResume();

        m_FrontOfCard = true;
        m_CurrentDeck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);

        ((TextView) findViewById(R.id.textView2)).setText(m_CurrentDeck.GetCurrentCardFront());
        ((TextView) findViewById(R.id.textView)).setText(m_CurrentDeck.GetName());
        UpdateCurrentCardIndex();

    }

    private void FlipCard()
    {
        m_FrontOfCard = !m_FrontOfCard;

        if(m_FrontOfCard)
        {
            ((TextView) findViewById(R.id.textView4)).setText("Top");
        }
        else
        {
            ((TextView) findViewById(R.id.textView4)).setText("Bottom");
        }


        ((TextView) findViewById(R.id.textView2)).setText(m_CurrentDeck.GetCardString(m_FrontOfCard));
    }

    private void UpdateCurrentCardIndex()
    {
        ((TextView) findViewById(R.id.textView3)).setText(m_CurrentDeck.GetCardNumber());
    }

    private void DisplayCard()
    {
        m_FrontOfCard = true;

        ((TextView) findViewById(R.id.textView4)).setText("Top");
        ((TextView) findViewById(R.id.textView2)).setText(m_CurrentDeck.GetCurrentCardFront());
        UpdateCurrentCardIndex();
    }
}