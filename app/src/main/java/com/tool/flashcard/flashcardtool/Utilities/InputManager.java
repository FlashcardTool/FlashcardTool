package com.tool.flashcard.flashcardtool.Utilities;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/**
 * Created by j on 3/31/18.
 */

public class InputManager extends Activity
{
    private static final float  MIN_DISTANCE = 300.0f;

    private float               m_StartTouhPositionY;
    private float               m_EndTouchPositionY;
    private float               m_StartTouhPositionX;
    private float               m_EndTouchPositionX;

    final public boolean onTouchEvent(MotionEvent event)
    {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action)
        {
            case (MotionEvent.ACTION_DOWN):
                m_StartTouhPositionY = event.getY();
                m_StartTouhPositionX = event.getX();
                break;
            case (MotionEvent.ACTION_UP):
                m_EndTouchPositionY = event.getY();
                m_EndTouchPositionX = event.getX();
                if(Math.abs(m_EndTouchPositionY - m_StartTouhPositionY) > MIN_DISTANCE)
                {
                    OnSwipeVertical(m_EndTouchPositionY);

                    if(m_EndTouchPositionY - m_StartTouhPositionY > 0)
                        OnSwipeDown(m_EndTouchPositionY);
                    else
                        OnSwipeUp(m_EndTouchPositionY);
                }
                else if(Math.abs(m_EndTouchPositionX - m_StartTouhPositionX) > MIN_DISTANCE)
                {
                    OnSwipeHorizontal(m_EndTouchPositionX);

                    if(m_EndTouchPositionX - m_StartTouhPositionX > 0)
                        OnSwipeRight(m_EndTouchPositionX);
                    else
                        OnSwipeLeft(m_EndTouchPositionX);
                }
                else if((Math.abs(m_EndTouchPositionX - m_StartTouhPositionX) < MIN_DISTANCE) && (Math.abs(m_EndTouchPositionY - m_StartTouhPositionY) < MIN_DISTANCE))
                {
                    OnTouch(m_EndTouchPositionX, m_EndTouchPositionY);
                }
                break;
            case (MotionEvent.ACTION_MOVE):
                OnDrag(event.getX(), event.getY());
        }

        return super.onTouchEvent(event);
    }

    public void OnSwipeHorizontal(float _position) { }
    public void OnSwipeRight(float _position) { }
    public void OnSwipeLeft(float _position) { }
    public void OnSwipeVertical(float _position) { }
    public void OnSwipeUp(float _position) { }
    public void OnSwipeDown(float _position) { }
    public void OnTouch(float _xPosition, float _yPosition) { }
    public void OnDrag(float _xPosition, float _yPosition) { }

}
