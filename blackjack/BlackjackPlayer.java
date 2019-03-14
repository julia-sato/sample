import java.util.ArrayList; // ArrayList is used as the player's hand, due to its dynamic nature.

public class BlackjackPlayer
{
    
    /* 
    
    Class "BlackjackPlayer" Description:
    
    Holds methods and data for individual player behaviour. It tracks the player's balance, hand, and 
    identifies the player by number (playerID). 'cashedOut' is a boolean that tracks whether the player
    has chosen to exit the game. It implements 
    
    Instance Data Summary:  
    
    int initialMoney; 
    Stores the amount of money the player started with, so that when the player withdraws from the game,
    an accurate summary of their net gain/loss can be provided.
    
    int money;
    Stores the amount of money the player has at the time.
    
    int playerID;
    An integer starting at 1 identifying the player. 
    
    boolean cashedOut;
    Tracks whether or not the player has chosen to exit the game or not. Only necessary because I didn't 
    want to import arrayList and therefore could not simply remove a player from the game. Thus, the
    variable indicates inactivity and the player can be ignored.
    
    int currentBet;
    Stores how much the user has chosen to bet in the 'bet' function. Stored as instance data so that all
    methods can access the player's current bet. 
    
    ArrayList<Card> hand;
    Stores the cards in the player's hand. Declared as an arraylist to accommodate resizing of the hand. 
    
    
    Method Summary: 
    
    getHand() returns the entirety of the hand in [(suit)(value)] form as well as the total, all in a handy string! 
    
    getHandList() returns the hand as a raw ArrayList.
    
    handTotal() returns the total of the hand with all aces optimized for staying under 21. 
    
    clearHand() replaces the old hand with a new empty ArrayList. 
    
    getPlayerInfo() returns the player's ID, money, and hand. 
    
    getID() returns the player's assigned ID. 
    
    getBalance() returns how much money the player has.

    isInactive() returns true if the player has cashed out.     
    
    
    The following functions return true or false as an indication of whether the user can keep playing. 
        
        hit(Deck deck) gives the player one more card and returns whether or not they have busted.
        
        stay() returns false. 
        
        doubleDown(Deck deck) returns false. 
        
    
    checkBust() returns true or false depending on their hand's total. 
    
    bet(int bet) stores the given bet in the player's instance data. 
 
    givePlayerBet(double multiplier) issues the player money multiplied according to a specified double. 
    
    cashOut() prints out an ending statement for the player and changes cashedOut to true. 

    obtainCard(Card card) adds a single given card to the hand. 
    
    dealerReveal(Deck deck) gives the dealer one more card and then prints the hand. Returns false for reasons unclear to me. 
    
    */
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    private int initialMoney; // The money the player started with.
    private int money; // The amount of money each player has. 
    private int playerID; // Identification number for each player. 
    private boolean cashedOut = false; // Specifies activity status of player. 
    private int currentBet; // How much money the player has currently bet. 
    
    private ArrayList<Card> hand = new ArrayList<Card>(); // Holds the player's card objects. 

    /////////////////////////////////////////////////////////////////////////////////////
    
    public BlackjackPlayer(int initialMoney, int playerNum) // Constructor for a new player. 
    {
        this.initialMoney = initialMoney; // Stores the money the player began with. 
        money = initialMoney; // Assigns the player the starting amount of money. 
        playerID = playerNum; // Gives the player a specified identification number. 
    }
    
    /////////////////////////////////////////////////////////////////////////////////////    
    
    public String getHand() // Returns a string containing the player's hand and total. 
    {
        String handOutput = "";
        
        for(int i = 0, length = hand.size(); i < length; i++)
        {
            handOutput = handOutput + hand.get(i).getCard() + " ";
        }
        
        return handOutput + "\nTotal: " + handTotal();
        
    }
    
    public ArrayList<Card> getHandList() // Returns the player's hand as a raw ArrayList.
    {
        return hand;
    }
    
    public int handTotal() // Returns the total of the hand with all aces optimized to remain under 21. 
    {
        int cardVal; // Stores the numerical value of a card temporarily. 
        int total = 0; // Stores the total of the hand. 
        
        for (int i = 0; i < hand.size(); i++) // Loops through the hand. 
        {
            cardVal = hand.get(i).getValueNumerical();
            
            if (cardVal > 10) // If the card is a face card (but not an ace), add 10. 
            {
                total += 10;
            } 
            else if (cardVal > 1) // If the card is a number value, add its unprocessed value. 
            {
                total += cardVal;
            }
            
        }
        
        for (int i = 0; i < hand.size(); i++) // Loops through hand again and finds all aces. 
        {
            cardVal = hand.get(i).getValueNumerical();
            
            if (cardVal == 1) // If the card is an ace, add 11 only if it won't make the user bust. 
            {
                if (total + 11 <= 21)
                {
                    total += 11;
                } 
                else
                {
                    total += 1;
                }
            }
            
        }
        
        return total; 
    }
    
    public void clearHand() // Empties the player's hand of all cards.
    {
        hand = new ArrayList<Card>();
    }

    /////////////////////////////////////////////////////////////////////////////////////
    
    public String getPlayerInfo() // Returns the stored data about the player. 
    {
        return "Player " + playerID + "\nFunds: " + money + "\nHand: " + getHand();
    }
    
    public int getID() // Returns the player's ID #.
    {
        return playerID;
    }
    
    public int getBalance() // Returns the player's current balance. 
    {
        return money;
    }

    public boolean isInactive() // Returns the activity status of the player. 
    {
        return cashedOut;
    }

    /////////////////////////////////////////////////////////////////////////////////////    

    public boolean hit(Deck deck) // returns true or false based on bust 
    {
        hand.add(deck.deal()); // Adds one card to the user's hand. 
        return checkBust();
    }

    public boolean stay() // If the user chooses to stay, return false to indicate the end of their turn.
    {
        return false; 
    }

    public boolean doubleDown(Deck deck) // Doubles the user's bet and adds a card, then returns false to indicate end of turn. 
    {
        currentBet *= 2; // Doubles the user's bet.
        hand.add(deck.deal()); // Adds another card to the user's deck. 
        return false; // The user cannot hit again. 
    }

    public boolean checkBust() // Returns false if the hand is busted. Subtracts bet from balance when busted. 
    {
        if (handTotal() > 21)
        {
            System.out.println("Player " + playerID + " busted!");
            money -= currentBet; // Subtracts bet from money.
            return false; // Indicates player cannot play anymore. 
        }
        else
        {
            return true; // Indicates player can continue to take their turn. 
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////    
    
    public void bet(int bet) // Links the user's bet to the player's instance data. 
    {
        currentBet = bet;
        money -= bet;
    }
    
    public void givePlayerBet(double multiplier) // Gives the user their monetary return specified by a multiplier. 
    {
        money += (int)(currentBet * multiplier);
    }

    public void cashOut() // Marks player inactive and returns their net performance for the game. 
    {
        cashedOut = true; // Marks player inactive. 
        
        if (money == 0) // If the player went broke, make sure they know. 
        {
            System.out.println("\nPlayer " + playerID + " is broke! They have been launched into the void.");
        }
        else // Otherwise, prints how much they won/lost or if they tied. 
        {
            System.out.println("\nPlayer " + playerID + " cashed out with $" + money + "!");
            
            if (money == initialMoney)
            {
                System.out.println("Tied with house.");
            }
            else if (money < initialMoney)
            {
                System.out.println("Lost $" + (initialMoney - money) + " to house.");
            }
            else
            {
                System.out.println("Won $" + (money - initialMoney) + " from the house!");
            }
            
        }
    }
    
    public void obtainCard(Card card) // Adds a card to the player's hand. 
    {
        hand.add(card);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////    
    
    public boolean dealerReveal(Deck deck)
    {
        hand.add(deck.deal()); // Adds another card to the dealer's deck. 
        
        System.out.println("\nDealer: " + getHand());
        
        return false;
    }

}