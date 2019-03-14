import java.util.*; // Imported for the use of Math.Random() in makeShuffledDeck().

public class Deck
{
    
    /* 
    
    Class "Deck" Description:
    
    Deck represents a single standard 52 card deck. The only instance data is the deck, an array
    with space for 52 "Card" objects (see Card.java).
    There are two methods for filling the empty deck with cards, for the conservation of resources. 
    One creates an unshuffled deck, and the other shuffles the deck in its creation. 
    
    
    Instance Data Summary:  
    
    Card[] deck;
    Holds 52 card objects. 
    
    int cardIndex; 
    When the cards are being iterated through methodically, sometimes it is useful to know what the next
    usused card is. That index is stored here. 

    
    Method Summary: 
    
    makeDeck() fills the empty array with cards, unshuffled. 
    
    makeShuffledDeck() fills the array with cards placed randomly. 
    
    outputDeck() prints the entire deck out to console, with each card in the format (suit)(value). 
    
    deal() returns a card from the top of the deck and marks it as used. 
    
    */


    /////////////////////////////////////////////////////////////////////////////////////

    private Card[] deck = new Card[52]; // Initiates a new 'deck' with space for 52 Card objects.
    private int cardIndex = 0; // Tracks where in the deck to take cards from. 
    
    /////////////////////////////////////////////////////////////////////////////////////
 
    public void makeDeck() // Fills deck with cards, unshuffled. 
    {   
        int index = 0; // Accesses correct spot in 'deck'. 
        
        for (int suit = 0; suit < 4; suit++) // Loops through each suit.
        {
            for (int value = 1; value < 14; value++) // Loops through each value, numerically including face cards.
            {
                deck[index] = new Card(suit, value); // Assigns each card a value.
                index++; // Moves to next slot in array. 
            }
        }
    }
    
    public void makeShuffledDeck() // Fills deck with cards, shuffled. 
    {   
        int index = (int)(Math.random()*52); // Randomizes spot in 'deck'. 
        
        for (int suit = 0; suit < 4; suit++) // Loops through each suit.
        {
            for (int value = 1; value < 14; value++) // Loops through each value, numerically including face cards.
            {
                while (deck[index] != null)
                {
                    index = (int)(Math.random()*52); // Places card in random empty slot. 
                }
                
                deck[index] = new Card(suit, value); // Assigns each card a value.
            }
        }
    }    
    
    
    public void outputDeck() // Loops through deck and prints the cards out in order.
    {
        for (int i = 0; i < 52; i++)
        {
            System.out.print(deck[i].getCard() + " ");
        }
    }

    public Card deal() // Deals a card from the top of the deck. 
    {
        if (cardIndex >= 52) // If all the cards have been used, re shuffle the deck.
        {
            makeShuffledDeck();
            cardIndex = 0;
            System.out.println("Shuffled Deck!");
        }
        
        deck[cardIndex].useCard(); // Mark card as used.
        cardIndex++; // Increment index to next unused card. 
        return deck[cardIndex - 1]; // Return the card that was just marked as used. 
    }
    
}