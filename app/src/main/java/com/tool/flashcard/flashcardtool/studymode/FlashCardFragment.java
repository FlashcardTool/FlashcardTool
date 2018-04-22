package com.tool.flashcard.flashcardtool.studymode;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.R;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;

import org.w3c.dom.Text;

public class FlashCardFragment extends Fragment
{
    public Flashcard card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_flashcard, container, false);

        TextView topNumber = rootView.findViewById(R.id.cardNumber);
        TextView topText = rootView.findViewById(R.id.cardTextTop);
        final TextView bottomText = rootView.findViewById(R.id.cardTextBottom);

        topText.setText(card.GetCardFront());


        //bottomText.setText(card.GetCardBack());

        bottomText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                bottomText.setText(card.GetCardBack());
            }
        });


        return rootView;
    }


}
