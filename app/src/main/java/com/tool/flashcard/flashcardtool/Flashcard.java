package com.tool.flashcard.flashcardtool;

/**
 * Created by JBuckley15
 */

public class Flashcard
{
    private String m_FrontOfCard;
    private String m_BackOfCard;

    public void SetCardFront(String _cardFront)
    {
        m_FrontOfCard = _cardFront;
    }

    public void SetCardBack(String _cardBack)
    {
        m_BackOfCard = _cardBack;
    }

    public void SetBothSides(String _cardFront, String _cardBack)
    {
        m_FrontOfCard = _cardFront;
        m_BackOfCard = _cardBack;
    }

    public String GetCardFront()
    {
        return m_FrontOfCard;
    }

    public String GetCardBack()
    {
        return m_BackOfCard;
    }

    public String GetCardForWrite()
    {
        //TODO: Return the correct string that can be bit packed and parsed back out into a card correctly
        return (m_FrontOfCard + " " + m_BackOfCard + " Still to be implemented for returning a correct string for bit packing");
    }
}
