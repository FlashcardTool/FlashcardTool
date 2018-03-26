package com.tool.flashcard.flashcardtool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JBuckley15 on 3/25/18.
 */

public class FlashcardManager
{
    enum CardFaceViewed
    {
        CARD_FRONT_VIEWED,
        CARD_BACK_VIEWED
    }
    private List<Flashcard> m_Cards;
    private int             m_CardsViewed;
    private CardFaceViewed  m_CurrentFace;
    private String          m_DeckName;

    public FlashcardManager()
    {
        this("This is the front", "This is the back");
    }

    public FlashcardManager(String _frontString)
    {
        this(_frontString, "This is the back");
    }

    public FlashcardManager(String _frontString, String _backString)
    {
        m_Cards = new ArrayList<>();
        m_Cards.add(new Flashcard());
        m_Cards.get(0).SetBothSides(_frontString, _backString);

        m_CardsViewed = 0;
        m_CurrentFace = CardFaceViewed.CARD_FRONT_VIEWED;
    }

    public void NextCard()
    {
        if(m_CardsViewed < m_Cards.size())
            m_CardsViewed++;
    }

    public void PreviousCard()
    {
        if(m_CardsViewed > 0)
            m_CardsViewed--;
    }

    public String GetCurrentCardFront()
    {
        return m_Cards.get(m_CardsViewed).GetCardFront();
    }

    public String GetCurrentCardBack()
    {
        return m_Cards.get(m_CardsViewed).GetCardBack();
    }

    public void UpdateCardValues(String _newFront, String _newBack)
    {
        m_Cards.get(m_CardsViewed).SetBothSides(_newFront, _newBack);
    }

    public void CreateNewCard(String _newFront, String _newBack)
    {
        Flashcard card = new Flashcard();
        card.SetBothSides(_newFront, _newBack);
        m_Cards.add(card);
    }

    public void DeleteFlashcard()
    {
        m_Cards.remove(m_CardsViewed);
    }
}
