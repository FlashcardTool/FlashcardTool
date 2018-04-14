package com.tool.flashcard.flashcardtool.studymode;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.DeckSelect;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.R;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class StudyMode extends FragmentActivity
{
    private Deck        m_CurrentDeck;

    private TextView    m_DeckName;
    private TextView    m_CardNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);

        m_CurrentDeck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);

        m_DeckName = findViewById(R.id.deckName);
        m_DeckName.setText(m_CurrentDeck.GetName());

        m_CardNumber = findViewById(R.id.cardNumber);

        ViewPager pager = findViewById(R.id.cardViewPager);
        pager.setAdapter(new FlashcardPagerAdapter(getSupportFragmentManager() , m_CurrentDeck));

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                m_CardNumber.setText((position + 1) + "/" + m_CurrentDeck.getNumberOfCards());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //Update the cardNumber textView for the first card
        m_CardNumber.setText("1/" + m_CurrentDeck.getNumberOfCards());

        //remove

    }

    private class FlashcardPagerAdapter extends FragmentStatePagerAdapter
    {
        Deck deck;

        public FlashcardPagerAdapter(FragmentManager fm, Deck deck_using)
        {
            super(fm);
            this.deck = deck_using;
        }

        @Override
        public Fragment getItem(int position)
        {
            FlashCardFragment fragment = new FlashCardFragment();
            fragment.card = deck.getFlashcard(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return this.deck.getNumberOfCards();
        }
    }
}