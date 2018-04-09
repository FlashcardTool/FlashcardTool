package com.tool.flashcard.flashcardtool.studymode;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.R;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;

public class FlashCardFragment extends Fragment
{
    public Flashcard card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_flashcard, container, false);
        TextView mainText = rootView.findViewById(R.id.cardText);
        mainText.setText(card.GetCardFront());

        //TODO add OnTouchListener to reveal back of card
        //rootView.setOnTouchListener();

        return rootView;
    }

}
