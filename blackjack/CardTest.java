

public class CardTest
{
    
    public static void main(String args[]){
        
        Deck deck = new Deck();
        deck.makeShuffledDeck();
        deck.outputDeck();
        System.out.println("" + deck.deal().getCard());
        System.out.println(deck.deal().getStatus());
        
        System.exit(255); // Ends the process. 
        /* Note: Exit codes go from 0-255, indicating 16 bits of allocated space. */
        
    }
    
}