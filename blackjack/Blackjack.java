import java.util.Scanner; import java.util.ArrayList; // For the purpose of accepting user input. 

public class Blackjack
{

    /* 
    
    Class "Blackjack" Description:
    
    Contains the majority of the function of the game "Blackjack".
    
    Instance Data Summary:  

    Scanner scanInt;
    Recieves user input, specifically integers. 
    
    BlackjackPlayer[] players;
    Stores the player objects. It's an array because I didn't want to import ArrayList, but ended up 
    having to import it anyways. 
    
    Deck deck;
    A single deck of cards for the game. Shuffled. When used up, a new shuffled deck is put in. 

    BlackjackPlayer dealer;
    A player object, but with an ID of -1 and a balance of -$1.
    
    
    Method Summary: 
    
    newGame() retrieves data from the user and sets up the game accordingly. It regulates the inputs, 
    placing restrictions on the number of players and amount of money each person begins with. 
    It then fills the array 'players' with the correct number of players, assigning each an ID.
    Also fills the deck with a new set of shuffled cards. 
    
    createPlayers(int playerCount, int startingMoney) initiates and fills an array of players. It accepts
    a number of players and an amount of money each will start with, and adds 'BlackjackPlayer' objects
    to the array. 
    
    listPlayers() prints each player's information to the console. Includes their ID, 
    money remaining, and hand. Primarily for testing. 
    
    peopleArePlaying() returns true or false depending on if there are still people participating 
    in the game.  
    
    getBets() asks each player for a bet and stores it in their instance data. 
    
    everyoneGetsCards() gives every player 2 cards and the dealer 1 card. 
    
    askWhatToDo(BlackjackPlayer player) Asks a specified player to choose from a set of specific options. 
    
    dealerRevealWinCheck() reveals the dealer's other card. If under 18, dealer draws. Determines winners. 
    
    runGame() loops through the actions for a full game of blackjack accordingly. 

    */
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    private Scanner scanInt = new Scanner(System.in); // Initiates scanner with specified type to avoid errors. 
    private BlackjackPlayer[] players; // Partial declaration of array of players. Completed in createPlayers(). 
    private Deck deck = new Deck(); // Declares an empty deck of cards.  
    private BlackjackPlayer dealer = new BlackjackPlayer(-1,-1); // Creates the player representing the dealer. 
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    
    public void newGame() // Sets up game and gathers data necessary to play. Creates players and deck.
    {
        
        int playerCount; // Number of players in game. Up to 5 players allowed. 
        int startingMoney; // Amount of money every player will begin with. 1 million dollar maximum. 
        
        System.out.print("How many players?: "); // Requests number of players (1-5).
        playerCount = scanInt.nextInt();
        
        while (playerCount < 1 || playerCount > 5) // Verifies input. If invalid, re-requests until valid. 
        {
            System.out.print("Invalid Input. 5 players max.\nHow many players?: ");
            playerCount = scanInt.nextInt();
        }
        
        
        System.out.print("And how much money should each person start with?: $"); // Requests amount of money to start with.
        startingMoney = scanInt.nextInt();
        
        while (startingMoney < 1 || startingMoney > 1000000) // Verifies input. If invalid, re-requests until valid. 
        {
            System.out.print("Invalid Input. 1 million dollar maximum. \nHow much money should each person start with?: $");
            startingMoney = scanInt.nextInt();
        }        
        
        createPlayers(playerCount, startingMoney); // Makes an array to hold each of the players with the specified parameters. 
        deck.makeShuffledDeck(); // Fills a deck, shuffles. 
        
    }
    
    
    private void createPlayers(int playerCount, int startingMoney) // Creates a set of players according to specifications.
    {
        players = new BlackjackPlayer[playerCount]; // Initiates array of correct size.
        
        for (int i = 1; i <= playerCount; i++) // Fills array with players. 
        {
            players[i - 1] = new BlackjackPlayer(startingMoney, i); // Assigns 'i' as the player ID (player 1, 2...). 
        }
        
    }
    
    
    public void listPlayers() // Prints the details of each player to the console. 
    {
        for (int i = 0; i < players.length; i++) // Loops through the list of players, retrieving each's info.
        {
            System.out.println(players[i].getPlayerInfo() + "\n");
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////

    private boolean peopleArePlaying() // Checks if everyone has cashed out yet and eliminates the broke. 
    {
        for (int i = 0; i < players.length; i++) 
        {
            if (players[i].isInactive() == false) // If there is still someone playing, continue the game.
            {
                return true;
            }
        }
        return false; // Otherwise, end the game. 
    }
    
    private void getBets()
    {
        
        int bet; // Stores bet input by player. 
        BlackjackPlayer player; // Simplifies naming for player object. 
        
        for (int i = 0; i < players.length; i++) // Asks each player for a bet. 
        {
            
            player = players[i]; // Selects player object and assigns to alias. 
            
            
            if (players[i].isInactive() == false)
            {
                System.out.print("\nPlayer " + player.getID() + ", how much would you like to bet?\nYou have $" + player.getBalance() + ". To cash out, bet nothing.\nBet: $");
                bet = scanInt.nextInt(); // Accepts bet from player. 
                
                while (bet < 0 || bet > player.getBalance()) // Verifies input. 
                {
                    System.out.print("Invalid bet. Try again.\nBet: $");
                    bet = scanInt.nextInt();
                }
                
                if (bet == 0) // If the player bets nothing, they cash out. 
                {
                    player.cashOut();
                }
                else // Otherwise, place the bet. 
                {
                    player.bet(bet);
                }
            }
        }
        
    }
    
    private void everyoneGetsCards() // Deals 1 card to dealer and all active players 2 cards each. 
    {
        
        dealer.clearHand();
        dealer.obtainCard(deck.deal()); // Dealer gets 1 card.
        
        for (int i = 0; i < players.length; i++) // Iterates through list of players and deals new cards.
        {
            
            players[i].clearHand();
            
            for (int j = 0; j < 2; j++)
            {
                if (players[i].isInactive() == false)
                {
                    players[i].obtainCard(deck.deal());
                }
            }
        }
        
    }

    private void askWhatToDo(BlackjackPlayer player) // Requests and processes user input. 
    {
        int canDoubleDown = 0; // Tracks if the user can or cannot double down. 
        boolean playing = true; // Tracks if the user is still taking their turn. 
        
        while (playing == true) // Runs as long as the user can still play. 
        {
            int chosenOption; // Holds the option the user chooses.
            canDoubleDown = 0; // Resets the ability to double down.
            
            System.out.print("\nPlayer " + player.getID() + "\nYour cards: " + player.getHand() + "\nOptions: "); 
            // Shows player what their hand is and what their options are. 
            System.out.print("Hit(0) Stay(1) ");
            
            if (player.handTotal() >= 9 && player.handTotal() <= 11) // Determines if user can double down and offers as choice.
            {
                canDoubleDown = 1; 
                System.out.println("Double Down(2)");
            } 
            
            System.out.print("\nWhat will you do?: "); // Asks user what they want to do.
            chosenOption = scanInt.nextInt(); // Takes the input as an integer. 
            
            while (chosenOption < 0 || chosenOption > 1 + canDoubleDown) // Ensures validity of user's input. 
            { // Adding the double down variable creates a conditional that allows doubling down only when it is 1(valid). 
                System.out.print("Invalid input.\nWhat will you do?: ");
                chosenOption = scanInt.nextInt();
            }
            
            switch(chosenOption) // Acts according to user's input. 
            {
                case 0: // If player chooses to hit. 
                    playing = player.hit(deck);
                    System.out.println(player.getHand());
                    break;
                case 1: // If player chooses to stay. 
                    playing = player.stay();
                    System.out.println(player.getHand());
                    break;
                case 2: // If player chooses double down. 
                    playing = player.doubleDown(deck);
                    System.out.println(player.getHand());
                    break;
            }
            
        }
        
    }
    
    public void dealerRevealWinCheck() // Reveals dealer's card, determines who wins, and, if appropriate, distributes money. 
    {
        dealer.dealerReveal(deck);
        
        for (int i = 0; i < players.length; i++) // Loops through each player. 
        {
            if (players[i].handTotal() <= 21 && players[i].isInactive() == false) // Checks if the player is active and hasn't busted.
            {
                while (dealer.handTotal() < 17) // The dealer keeps pulling cards until they total over 17. 
                {
                    dealer.obtainCard(deck.deal());
                    System.out.println(dealer.getHand() + "\n");
                }
                
                if (dealer.handTotal() > 21) // If the dealer totals over 21, it busts and players recieve money. 
                {
                    System.out.println("\nDealer busted!");
                    players[i].givePlayerBet(2);
                }
                else if (players[i].handTotal() == 21 && players[i].handTotal() > dealer.handTotal()) // Otherwise, if the player has blackjack and wins, give according bet. 
                {
                    System.out.println("Player " + players[i].getID() + ": Blackjack win!\n");
                    players[i].givePlayerBet(2);
                    players[i].givePlayerBet(0.5);
                }
                else if (players[i].handTotal() < dealer.handTotal()) // If the player loses, do nothing. 
                {
                    System.out.println("Player " + players[i].getID() + ": Lost.\n");
                }
                else if (players[i].handTotal() == dealer.handTotal()) // Prints correct message for a tie.
                {
                    System.out.println("Player " + players[i].getID() + ": Push!\n");
                }
                else if (players[i].handTotal() > dealer.handTotal()) // If the player wins normally, gives bet. 
                {
                    System.out.println("Player " + players[i].getID() + ": Win!\n");
                    players[i].givePlayerBet(2);
                }
                
            }
        }
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    public void runGame() // The equivalent of the main function. 
    {
        newGame(); // Initiates a new game. 
        
        while (peopleArePlaying() == true) // Continues to run as long as people are playing. 
        {
            getBets(); // Gets bets from users.
            everyoneGetsCards(); // Gives everyone cards. 
            
            if (peopleArePlaying() == false) // Checks again if people are playing. 
            {
                break;
            }
            
            System.out.println("\nDealer:\n[??] " + dealer.getHand()); // Shows dealer's first card. 
            
            for (int i = 0; i < players.length; i++) // For each player, asks what to do. 
            {
                if (players[i].isInactive() == false) // Ensures player is active before asking. 
                {
                    askWhatToDo(players[i]);
                }
            }
            
            dealerRevealWinCheck(); // Reveals dealer's cards and checks for a winner. 
            
        }
        
        System.out.println("\nThank you for playing!"); // Ending message after everyone is done playing. 
        
    }
    
    
    
}