package com.tool.flashcard.flashcardtool.FlashCardUtilities;

import android.app.Activity;
import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XML {
    private static final String DATA_RELATIVE_FILE_PATH = "/data";
    private static final String DECK_TAG = "deck";
    private static final String CARD_TAG = "card";
    private static final String DECK_NAME_ATTRIBUTE = "name";
    private static final String CARD_FRONT_ATTRIBUTE = "front";
    private static final String CARD_BACK_ATTRIBUTE = "back";

    public static List<Deck> load(Context context){
        List<Deck> loadedDecks = new ArrayList<>();

        try{
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();

            String path = context.getFilesDir().getPath() + DATA_RELATIVE_FILE_PATH;
            parser.setInput(new FileInputStream(new File(path)), null);

            int event = parser.getEventType();
            Deck deck = null;// = new Deck(); //initial deck is a throw away to stop compiler error
            while(event != XmlPullParser.END_DOCUMENT){
                String tagName = parser.getName();

                switch(event){
                    case XmlPullParser.START_TAG :
                        if(tagName.equalsIgnoreCase(DECK_TAG)){
                            //start of new deck
                            String deckName = parser.getAttributeValue(null, DECK_NAME_ATTRIBUTE);
                            // TODO: 2018-04-03 put subject attribute here once it's implemented
                            deck = new Deck();
                            deck.SetName(deckName);

                        }else if(tagName.equalsIgnoreCase(CARD_TAG)){
                            //new card for current deck
                            String cardFront = parser.getAttributeValue(null, CARD_FRONT_ATTRIBUTE);
                            String cardBack = parser.getAttributeValue(null, CARD_BACK_ATTRIBUTE);

                            // TODO: 4/4/18 check if deck is null, XML malformed exception
                            deck.CreateNewCard(cardFront, cardBack);
                        }
                        break;
                    case XmlPullParser.END_TAG :
                        if(tagName.equalsIgnoreCase(DECK_TAG)){
                            //current deck complete
                            // TODO: 4/4/18 check if deck is null, XML malformed exception
                            deck.Reset();
                            loadedDecks.add(deck);
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e){
            // TODO: 2018-04-03 deal with file not found more gracefully
            System.out.println("data file does not exist");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return loadedDecks;
    }

    public static void save(List<Deck> source, Context context){
        try{
            // Previously crashed without this bit, seems to work fine now
            // String path = context.getFilesDir().getPath() + DATA_RELATIVE_FILE_PATH;
            // FileOutputStream fos = new FileOutputStream(path);
            FileOutputStream fileos = context.openFileOutput("data", Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            StringWriter writer = new StringWriter();

            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            for(Deck deck : source){
                List<Flashcard> flashcards = deck.getAllCards();

                xmlSerializer.startTag(null, DECK_TAG);
                xmlSerializer.attribute(null, DECK_NAME_ATTRIBUTE, deck.GetName());
                // TODO: 2018-04-03 put subject attribute here once it's implemented

                for(Flashcard card : flashcards){
                    xmlSerializer.startTag(null, CARD_TAG);
                    xmlSerializer.attribute(null, CARD_FRONT_ATTRIBUTE, card.GetCardFront());
                    xmlSerializer.attribute(null, CARD_BACK_ATTRIBUTE, card.GetCardBack());
                    xmlSerializer.endTag(null, CARD_TAG);
                }

                xmlSerializer.endTag(null, DECK_TAG);
            }

            xmlSerializer.endDocument();
            xmlSerializer.flush();

            String dataToWrite = writer.toString();
            fileos.write(dataToWrite.getBytes());
            fileos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
