

public class Card
{
    
    /* 
    
    Class "Card" Description:
    
    Card contains the following instance data- used, suit and value, the last two stored in integer values.
    The integer suit maps to a correspondent character, listed as follows:
    Spade: 0  Heart: 1  Diamond: 2  Club: 3
    Values are stored as integers, 1 ~ 11-14 representing face cards. 
    
    The variable 'used' is a boolean indicating whether the card is available to be used, thus allowing 
    tracking of which cards have been burned and are no longer playable. 
    
    
    Instance Data Summary:  

    int suit; 
    Tracks the suit of the card as an integer from 0-3. Maps to an array, with the following correspondence:
    Spade: 0  Heart: 1  Diamond: 2  Club: 3
    
    int value;
    Holds the value as an integer. 
    
    boolean used;
    A variable that tracks whether or not the card has been used or not. It can be changed based on the
    nature of the game it is being used for, allowing cards to be burned and prevented from reuse. 
    
    
    Method Summary: 
    
    getCard() returns the card in a String, [(suit)(value)] form.
    
    getSuit() returns the suit for the purpose of printing it to the console.
    
    getStatus() returns whether the card has been used/burned.
    
    useCard() changes the status of the card to indicate it is unavailable for use. 
    
    There are two methods for retrieving the value of the card. 
        
        getValueNumerical() returns the value of the card in pure integer form. 
        
        getValueString() returns values converted to strings for the purpose of printing to console. 
        
    
    */
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    private int suit; // Stores the suit numerically.
    private int value; // Stores integer value of card.
    
    private boolean used = false; // Tracks whether the card has been used.
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public Card(int suit, int value) // Constructor accepting and assigning values to card.
    {
        this.suit = suit;
        this.value = value;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////    
    
    
    private char[] suits = {'♠','♡','♢','♣'}; // Maps suit value to character.

    
    public void useCard() // Changes availability of card. 
    {
        used = true;
    }

    public boolean getStatus() // Returns availability of card. 
    {
        return used; 
    }
    
    public String getCard()
    {
        return "[" + getSuit() + "" + getValueString() + "]";
    }
    
    
    
    public char getSuit() // Retrieves the character version of the suit.
    {
        return suits[suit];
    }    
    
    
    
    public int getValueNumerical() // Retrives the numerical value of the card. 
    {
        return value; 
    }
    
    public String getValueString() // Retrieves String value of card accounting for face cards. 
    {
        if (value < 11 && value > 1) // If the value should be an integer, do not change. 
        {
            return Integer.toString(value);
        }
        else // Otherwise, convert to correspondent face card. 
        {
            switch(value) // Accepts integer values to convert to face card representations. 
            {
                case 1:
                    return "A";
                
                case 11: 
                    return "J";
                
                case 12:
                    return "Q";
                
                case 13:
                    return "K";
            }
        }
        
        return "error"; // If this statement has been reached, there is something very wrong. 
        
    }    

}