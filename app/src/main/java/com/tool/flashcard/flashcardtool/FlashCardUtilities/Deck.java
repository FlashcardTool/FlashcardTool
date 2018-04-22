package com.tool.flashcard.flashcardtool.FlashCardUtilities;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JBuckley15 on 3/25/18.
 */

public class Deck
{

    private List<Flashcard> m_Cards;
    private int             m_CardsViewed;
    private String          m_DeckName;

    //added by James to facilitate loading decks from XML
    public Deck(){ m_Cards = new ArrayList<>(); }

    public Deck(String _name)
    {
        this(_name,"This is the front", "This is the back");
    }

    public Deck(String _name, String _frontString)
    {
        this(_name, _frontString, "This is the back");
    }

    public Deck(String _name, String _frontString, String _backString)
    {
        m_DeckName = _name;
        m_Cards = new ArrayList<>();
        m_Cards.add(new Flashcard());
        m_Cards.get(0).SetBothSides(_frontString, _backString);

        m_CardsViewed = 0;
    }

    public void setCurrentCard(int i)
    {
        m_CardsViewed = i;
    }

    public void Reset()
    {
        m_CardsViewed = 0;
    }

    public String GetName()
    {
        return m_DeckName;
    }

    public void SetName(String _name)
    {
        m_DeckName = _name;
    }

    public void NextCard()
    {
        if(m_CardsViewed < (m_Cards.size() - 1))
            m_CardsViewed++;
        else
            m_CardsViewed = 0;
    }

    public void PreviousCard()
    {
        if(m_CardsViewed > 0)
            m_CardsViewed--;
        else
            m_CardsViewed = (m_Cards.size() - 1);
    }

    public String GetCurrentCardFront()
    {
        return m_Cards.get(m_CardsViewed).GetCardFront();
    }

    public String GetCurrentCardBack()
    {
        return m_Cards.get(m_CardsViewed).GetCardBack();
    }

    public String GetCardString(Boolean _frontOfCard)
    {
        if(_frontOfCard)
        {
            return m_Cards.get(m_CardsViewed).GetCardFront();
        }
        else
        {
            return m_Cards.get(m_CardsViewed).GetCardBack();
        }
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
        m_CardsViewed = (m_Cards.size() - 1);
    }

    public void DeleteFlashcard()
    {
        m_Cards.remove(m_CardsViewed);
    }

    public String GetCardNumber()
    {
        return Integer.toString(m_CardsViewed + 1) + "/" + Integer.toString(m_Cards.size());
    }

    //added by James to facilitate saving Decks
    public List<Flashcard> getAllCards(){
        return m_Cards;
    }
}
