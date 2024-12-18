import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * This is Management class, all the works that needs this game start is done in here
 * @author Mohammad Jafar Saberi
 * @version 1.0
 */
public class Management {
    /**
     * Allows a player to choose their cards from the available cards in their deck.
     * The player selects by card number and amount. The method validates inputs and ensures the player
     * only selects up to their allowed number of cards.
     *
     * @param player The player who is selecting cards.
     */
    public static void chooseCards(Player player) {
        Scanner scanner = new Scanner(System.in);
        int numberOfCard = 0; // Card number the player selects
        int amountOfCards = 0; // Number of that card type to select


        while (player.getAmountOfCard() > 0) {
            player.showAnimalsCard(player, player.getChosenCards()); // Display available cards
            System.out.println("You Have \'" + player.getAmountOfCard() + "\' Chance to Choose Cards");
            System.out.println("==========================================");
            System.out.println("Please Enter the \'NUMBER\' of Card");
            numberOfCard = scanner.nextInt();

            if (!(player.getCards().containsKey(numberOfCard))) {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
                continue;
            }

            System.out.println("Please Enter the \'AMOUNT\' of Card");
            amountOfCards = scanner.nextInt();


            if (!(amountOfCards >= 1 && amountOfCards <= player.getCards().get(numberOfCard).size())) {
                // If the card number is invalid
                System.out.println("INVALID NUMBER >>> Please Enter Again");
                continue;
            }

            int tmp = player.getAmountOfCard() - amountOfCards;
            if (tmp < 0) {
                // If the player tries to pick more cards than allowed
                System.out.println("INVALID NUMBER >>> Please Enter Again");
                System.out.println("You Have \'" + player.getAmountOfCard() + "\' Chance to Choose Cards");
                System.out.println("Please Choose equal/less than \'" + player.getAmountOfCard() + "\'");
                continue;
            }
            AnimalsCard obj = (player.getAnimals()).get(numberOfCard);
            ArrayList<AnimalsCard> list = new ArrayList<>();

            for (int i = 0; i < amountOfCards; i++) {
                list.add(obj); // Add the selected number of cards to the list
            }

            player.setChosenCards(numberOfCard, list); // Store chosen cards
            player.setAmountOfCard(player.getAmountOfCard() - amountOfCards);
            // Display the chosen cards
            for (int i = 1; i <= 12; i++) {
                ArrayList<AnimalsCard> lists;
                AnimalsCard animals;

                if (player.getChosenCards().containsKey(i)) {
                    lists = player.getChosenCards().get(i);
                    animals = lists.get(0);
                    System.out.print(animals.getAnimalsNumber() + "\t\t" + animals.getCardName() + "\t\t\t" + "\"" + lists.size() + "\"");
                    System.out.println();
                }
            }
        }
    }

    /**
     * This method starts the game, allowing the user to play either against the computer or another player.
     * It facilitates a turn-based game where each player can either attack or exit the game.
     * The game will continue until a player wins or decides to exit.
     * The method uses the Scanner class for user input and various game logic methods for performing the actions during the game.
     */
    public static void play() {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();

        while (true) {
            welcome(); // Display welcome message and options
            int tmp = scanner.nextInt(); // Get player choice

            int turn = 1, choice = 0;
            int turn1 = 1;
            if (tmp == 1) {
                while (true) {
                    System.out.println("#####  >>>  Please Enter the Name of Player   <<<  #####");
                    player1.setName(scanner.next());
                    player1.fillTheAnimals(player1); // This method choose 30 cards for player
                    choose(player1); // 10 cards will choose from 30 by this method

                    menu1(); // show the menu to play with computer or person etc
                    choice = scanner.nextInt();

                    if (choice == 1) { // if choice be one player play with computer
                        Player computer = new Player();
                        computer.setName("Computer");

                        computer.fillTheAnimals(computer); // This method choose 30 cards for computer or bot
                        computer.showAnimalsCard(computer, computer.getChosenCards());

                        chooseComputerCards(computer); // 10 cards will choose from 30 by this method for bot randomly
                        Player.printCards(computer, 2); // print the 10 card that chosen

                        loop:
                        while (true) {
                            int choice2 = 0; // variable for taking the choice of the player
                            if (turn1 == 1) { // if it is one it is turn for player one or human
                                while (true) {
                                    System.out.println("#####  >>>  " + player1.getName() + "`s turn   <<<  #####");
                                    System.out.println("#####  >>>          1: Attack               <<<  #####");
                                    System.out.println("#####  >>>          0: Exit and New Game    <<<  #####");
                                    choice2 = scanner.nextInt();

                                    if (choice2 == 1) { // if it is one player will choose cards and other things
                                        attack(player1, computer); // this method able the player to choose cards for attacking
                                        Player.printCards(player1, 3); // print the chosen cards for attack
                                        attackToCard(player1, computer); // this method able the player to attack
                                        break;
                                    } else if (choice2 == 0) { // Player exits the loop and this game
                                        System.out.println("#####  >>>          Exited!!!          <<<  #####");
                                        break loop;
                                    } else {
                                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                                    }
                                }
                                turn1 = 2;
                            } else if (turn1 == 2) { // If it is 2 it's the computer's turn
                                System.out.println("#####  >>>  " + computer.getName() + "`s turn   <<<  #####");

                                computerAttackMethod(computer);// Computer makes its attack
                                Player.printCards(computer, 3);

                                computerAttackToCard(computer, player1); // Perform the computer's attack
                                turn1 = 1;
                            }
                            // clear the hashmap of the attack cards to add new next round
                            if (turn1 == 2) {
                                player1.getAttackCards().clear();
                            } else if (turn1 == 1) {
                                computer.getAttackCards().clear();
                            }

                            // Check for the winner after each round
                            if (clearingTheWinner(player1)) {
                                printWinner(computer);
                                break;
                            }
                            if (clearingTheWinner(computer)) {
                                printWinner(player1);
                                break;
                            }
                        }
                    } else if (choice == 2) { // Two-player mode
                        System.out.println("#####  >>>  Please Enter the Name of SECOND Player   <<<  #####");
                        player2.setName(scanner.next());
                        player2.fillTheAnimals(player2);
                        choose(player2);

                        second:
                        while (true) {
                            int choice2 = 0;
                            if (turn == 1) { // Player 1's turn
                                while (true) {
                                    System.out.println("#####  >>>  " + player1.getName() + "`s turn   <<<  #####");
                                    System.out.println("#####  >>>          1: Attack               <<<  #####");
                                    System.out.println("#####  >>>          0: Exit and New Game    <<<  #####");
                                    choice2 = scanner.nextInt();

                                    if (choice2 == 1) {
                                        attack(player1, player2);
                                        Player.printCards(player1, 3);
                                        attackToCard(player1, player2);
                                        break;
                                    } else if (choice2 == 0) { // Player 1 exits if wants to
                                        System.out.println("#####  >>>          Exited!!!          <<<  #####");
                                        break second;
                                    } else {
                                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                                    }
                                }
                                turn = 2;
                            } else if (turn == 2) { // Player 2's turn
                                while (true) {
                                    System.out.println("#####  >>>  " + player2.getName() + "`s turn   <<<  #####");
                                    System.out.println("#####  >>>          1: Attack               <<<  #####");
                                    System.out.println("#####  >>>          0: Exit and New Game    <<<  #####");
                                    choice2 = scanner.nextInt();

                                    if (choice2 == 1) {
                                        attack(player2, player1);
                                        Player.printCards(player2, 3);
                                        attackToCard(player2, player1);
                                        break;
                                    } else if (choice2 == 0) {
                                        System.out.println("#####  >>>          Exited!!!          <<<  #####");
                                        break second;
                                    } else {
                                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                                    }
                                }
                                turn = 1;
                            }
                            // Clear hashmap of attack cards after each turn for next round
                            if (turn == 2) {
                                player1.getAttackCards().clear();
                            } else if (turn == 1) {
                                player2.getAttackCards().clear();
                            }
                            // Check for the winner after each round
                            if (clearingTheWinner(player1)) {
                                printWinner(player2); // player 2 wins
                                break;
                            }
                            if (clearingTheWinner(player2)) {
                                printWinner(player1); // player 1 wins
                                break;
                            }
                        }
                    } else {
                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                    }
                }
            } else if (tmp == 2) { // User chooses to exit the game
                goodBye();
                break;
            } else {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
            }
        }
    }

    /**
     * This method allows the computer to choose its cards from its available collection.
     * The computer selects cards by iterating over its card collection and choosing as many cards as needed
     * until the total number of chosen cards equals the number of cards it is allowed to choose.
     * The chosen cards are then assigned to the computer's list of selected cards, and its remaining card count is updated.
     *
     * @param computer The Player object representing the computer, from which cards are chosen.
     */
    public static void chooseComputerCards(Player computer) {
        int number = 1;

        ArrayList<AnimalsCard> card;

        while (computer.getAmountOfCard() > 0) {
            if (computer.getCards().containsKey(number)) {
                card = computer.getCards().get(number);

                int t = computer.getAmountOfCard() - card.size();
                if (t <= 0) {  // If the number of cards to choose is less than or equal to the remaining cards
                    ArrayList<AnimalsCard> tmp = new ArrayList<>();

                    for (int i = 0; i < computer.getAmountOfCard(); i++) {
                        tmp.add(card.get(0));
                    }
                    computer.setChosenCards(number, tmp);
                    computer.setAmountOfCard(computer.getAmountOfCard() - card.size());
                    break;
                }

                computer.setChosenCards(number, card);
                computer.setAmountOfCard(computer.getAmountOfCard() - card.size());
                number++;
            }else{
                number++;
            }
        }
    }
    /**
     * This method prints the winner of the game to the console.
     * It takes a Player object and displays a message announcing the player's victory.
     *
     * @param player The Player object representing the winner of the game.
     */
    public static void printWinner(Player player) {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>   " + player.getName() + " is the Winner    <<<<<<   ######");
        System.out.println("################################################################");
    }
    /**
     * This method checks if a player has won the game by determining whether
     * the player's chosen cards list is empty.
     * If the chosen cards list is empty, it means the player has won, and the method returns true.
     * Otherwise, it returns false.
     *
     * @param player The Player object whose chosen cards are being checked.
     * @return true if the player's chosen cards list is empty, indicating the player has won; false otherwise.
     */
    public static boolean clearingTheWinner(Player player) {
        if (player.getChosenCards().isEmpty()) {
            return true;
        }
        return false;
    }
    /**
     * This method displays the main menu for starting a new game.
     * It provides options for the player to choose either to play against the computer or with another player.
     */
    public static void menu1() {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>               NEW GAME            <<<<<<   ######");
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>      1: Play With Computer        <<<<<<   ######");
        System.out.println("#####   >>>>>>      2: Play With Friend          <<<<<<   ######");
        System.out.println("#####   >>>>>>      Please Enter a Number        <<<<<<   ######");
        System.out.println("################################################################");
    }
    /**
     * This method prompts the player to choose 10 cards from their available cards.
     * It displays the player's name and a list of available cards for selection.
     *
     * @param player The Player object representing the player who is choosing their cards.
     */
    public static void choose(Player player) {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>   Choose Your 10 Cards \'" + player.getName() + "\'  <<<<<<   ######");
        System.out.println("#####   >>>>>>   You have These Following Cards  <<<<<<   ######");
        System.out.println("################################################################");

        chooseCards(player); // Calls chooseCards method to handle the actual selection of cards
    }
    /**
     * This method displays a welcome message to the player when they start the game.
     * It provides options to either start the game or exit.
     */
    public static void welcome() {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>   WELCOME TO FOREST ANIMALS GAME  <<<<<<   ######");
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>         1: START THE GAME         <<<<<<   ######");
        System.out.println("#####   >>>>>>             2: EXIT               <<<<<<   ######");
        System.out.println("################################################################");
    }
    /**
     * This method displays a goodbye message when the game ends or the player chooses to exit.
     */
    public static void goodBye() {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>        GOOD BYE MY FRIENDS        <<<<<<   ######");
        System.out.println("################################################################");
    }
    /**
     * This method handles the computer's attack on the player's cards.
     * It selects the type of attack (normal, strong, or energy reset) based on a random number,
     * calculates the attack power, checks if the computer has enough energy to perform the attack,
     * and then applies the attack on the player's health.
     * Additionally, it handles the resetting of energy if needed.
     *
     * @param computer The Player object representing the computer performing the attack.
     * @param player The Player object representing the opponent (the player being attacked).
     */
    public static void computerAttackToCard(Player computer, Player player) {
        Random random = new Random();
        int[] array = new int[2];

        out:
        while (true) {    // Loop until a valid attack is chosen
            int number = random.nextInt(3) + 1;
            int attackPower = 0;

            if (number == 1) { // Normal Attack
                System.out.println("Normal Attack");
                array = culculate(computer, 1);
                attackPower = array[0] / array[1];

                System.out.println("attack power " + array[0]);
                System.out.println("number of attacker " + array[1]);
                // Check if the card has enough energy to perform the attack
                for (Integer key : computer.getAttackCards().keySet()) {
                    boolean con = check(computer, key, attackPower);

                    if (con == false) {
                        System.out.println("#####   >>>>>>          Not Enough Energy!!!         <<<<<<   ######");
                        System.out.println("#####   >>>>>>     Choose Your Attack Kind Again     <<<<<<   ######");
                        continue out;
                    }
                }
                // Apply the attack on the opponent
                computerDecreaseTheOppenentBlood(player, array[0]);

                chechForDiedCards(player); // Check for any cards that have died due to the attack

                // Decrease the attacking cards' energy
                decreaseAttackerEnergy(computer, attackPower);
            } else if (number == 2) {// Strong Attack
                for (Integer key: computer.getAttackCards().keySet()) {
                    if (computer.getAttackCards().get(key).get(0).getStrongImpact() == 0) {
                        System.out.println("#####   >>>>>>          Cannot attack Strong!!!         <<<<<<   ######");
                        continue out;
                    }
                }
                System.out.println("strong attack");
                array = culculate(computer, 2);
                attackPower = array[0] / array[1];

                System.out.println("attack power " + array[0]);
                System.out.println("number of attacker " + array[1]);

                for (Integer key : computer.getAttackCards().keySet()) {
                    boolean con = check(computer, key, attackPower);

                    if (con == false) {
                        System.out.println("#####   >>>>>>          Not Enough Energy!!!         <<<<<<   ######");
                        System.out.println("#####   >>>>>>     Choose Your Attack Kind Again     <<<<<<   ######");
                        continue out;
                    }
                }
                computerDecreaseTheOppenentBlood(player, array[0]);

                chechForDiedCards(player);

                decreaseAttackerEnergy(computer, attackPower);
            } else if (computer.getReviveChance() == 0) {
                System.out.println("Your Chances to Reset Card Energy finished");
                continue;
            } else if (number == 3) { // Energy Reset
                computerResetEnergy(computer);
            }
            break;
        }
    }
    /**
     * This method allows the player to choose and perform an attack on the opponent.
     * The player can choose between a normal attack, a strong attack, or resetting the energy of their cards.
     * The method calculates the attack power based on the chosen attack type and checks if the player has enough energy to perform the attack.
     * If the energy is sufficient, the attack is applied to the opponent's health, and the attacker's energy is reduced.
     * If the energy is insufficient, the player is prompted to choose another attack.
     *
     * @param player1 The Player object representing the attacking player.
     * @param player2 The Player object representing the opponent to attack.
     */
    public static void attackToCard(Player player1, Player player2) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[2];

        int choice = 0;
        int attackPower = 0;

        out:
        while (true) {
            System.out.println("################################################################");
            System.out.println("#####   >>>>>>          1: Normal Attack         <<<<<<   ######");
            System.out.println("#####   >>>>>>          2: Strong Attack         <<<<<<   ######");
            System.out.println("#####   >>>>>>          3: Reset Energy          <<<<<<   ######");
            System.out.println("#####   >>>>>>          4: Movement impossiple   <<<<<<   ######");
            System.out.println("################################################################");
            choice = scanner.nextInt();

            if (choice == 1) { // Normal attack
                array = culculate(player1, 1);
                attackPower = array[0] / array[1];

                System.out.println("attack power " + array[0]);
                System.out.println("number of attacker " + array[1]);

                for (Integer key : player1.getAttackCards().keySet()) {
                    boolean con = check(player1, key, attackPower);

                    if (con == false) {
                        System.out.println("#####   >>>>>>          Not Enough Energy!!!         <<<<<<   ######");
                        System.out.println("#####   >>>>>>     Choose Your Attack Kind Again     <<<<<<   ######");
                        continue out;
                    }
                }
                decreaseTheOppenentBlood(player2, array[0]);

                chechForDiedCards(player2);

                decreaseAttackerEnergy(player1, attackPower);
            } else if (choice == 2) { // Strong attack
                for (Integer key: player1.getAttackCards().keySet()) {
                    if (player1.getAttackCards().get(key).get(0).getStrongImpact() == 0) {
                        System.out.println("#####   >>>>>>          Cannot attack Strong!!!         <<<<<<   ######");
                        continue out;
                    }
                }
                array = culculate(player1, 2);
                attackPower = array[0] / array[1];

                System.out.println("attack power " + array[0]);
                System.out.println("number of attacker " + array[1]);

                for (Integer key : player1.getAttackCards().keySet()) {
                    boolean con = check(player1, key, attackPower);

                    if (con == false) {
                        System.out.println("#####   >>>>>>          Not Enough Energy!!!         <<<<<<   ######");
                        System.out.println("#####   >>>>>>     Choose Your Attack Kind Again     <<<<<<   ######");
                        continue out;
                    }
                }
                decreaseTheOppenentBlood(player2, array[0]);

                chechForDiedCards(player2);

                decreaseAttackerEnergy(player1, attackPower);
            } else if (player1.getReviveChance() == 0) { // if the all the chances to reset energy finished
                System.out.println("Your Chances to Reset Card Energy finished");
                continue;
            } else if (choice == 3) { // reset energy
                resetEnergy(player1);

            } else if (choice == 4) {
                break;
            } else {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
                continue;
            }
            break;
        }
    }
    /**
     * This method simulates the computer's decision to select a card from its chosen cards
     * and set it as the attack cards for its turn. The computer randomly selects a card number
     * from its chosen cards and assigns the card(s) to the attack pool.
     *
     * @param computer The Player object representing the computer (attacking player).
     */
    public static void computerAttackMethod(Player computer) {
        Random random = new Random();

        while (true) {
            int number = random.nextInt(12) + 1;

            if (computer.getChosenCards().containsKey(number)) {
                computer.setAttackCards(number, computer.getChosenCards().get(number));
                break;
            }
        }
    }
    /**
     * This method allows the computer to reset the energy of one of its chosen cards
     * and decreases the number of revive chances available to the computer.
     * The computer randomly selects a card from its chosen cards and resets its energy
     * to its primary energy value, if a card is available.
     *
     * @param computer The Player object representing the computer (attacking player).
     */

    public static void computerResetEnergy(Player computer) {
        Random random = new Random();

        while (true) {
            int number = random.nextInt(12) + 1;

            if (computer.getChosenCards().containsKey(number)) {
                AnimalsCard card = computer.getChosenCards().get(number).get(0);
                card.setEnergy(card.getPrimaryEnergy());
                computer.setReviveChance(computer.getReviveChance() - 1); // Decrease the revive chance
                System.out.println("reset number " + computer.getReviveChance());
                break;
            }
        }
    }
    /**
     * This method allows the player to reset the energy of one of their chosen cards.
     * The player is prompted to choose a card, and if the card exists in their chosen cards,
     * the energy of the card is reset to its primary energy value. The player's revive chances
     * are then decreased by 1.
     *
     * @param player The Player object representing the player whose card energy is to be reset.
     */
    public static void resetEnergy(Player player) {
        Scanner scanner = new Scanner(System.in);
        // Print the player's current attack cards
        Player.printCards(player, 2);

        while (true) {
            System.out.println("#####   >>>>>>     Choose The Card to Reset Its Energy    <<<<<<   ######");
            int choice = scanner.nextInt();

            if (player.getChosenCards().containsKey(choice)) {
                AnimalsCard card = player.getChosenCards().get(choice).get(0);
                card.setEnergy(card.getPrimaryEnergy());
                player.setReviveChance(player.getReviveChance() - 1);
                System.out.println("reset number " + player.getReviveChance());
                break; // Exit the loop after resetting energy
            } else {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
            }
        }
    }
    /**
     * This method decreases the energy of each card in the player's attack cards by a given amount.
     * The energy of each card in the player's attack pool is reduced by the specified decrease amount.
     *
     * @param player The Player object representing the player whose attack cards' energy is to be decreased.
     * @param decreaseAmount The amount by which the energy of each card should be decreased.
     */
    public static void decreaseAttackerEnergy(Player player, int decreaseAmount) {
        for (Integer key : player.getAttackCards().keySet()) {
            ArrayList<AnimalsCard> list = player.getAttackCards().get(key);

            AnimalsCard card = list.get(0);
            // Decrease the energy of the attack card
            card.setEnergy(card.getEnergy() - decreaseAmount);
        }
    }
    /**
     * This method checks for any cards in the player's chosen cards that have died (i.e., their blood is 0 or less),
     * and removes such cards from the player's chosen cards.
     *
     * @param player The Player object whose cards are being checked for death.
     */
    public static void chechForDiedCards(Player player) {
        for (int i = 1; i < 13; i++) {
            if (player.getChosenCards().containsKey(i)) {
                AnimalsCard card = player.getChosenCards().get(i).get(0);

                // If the card's blood is 0 or less, remove it from the chosen cards
                if (card.getBlood() <= 0) {
                    player.getChosenCards().remove(i);
                }
            }
        }
    }
    /**
     * This method reduces the blood of one of the opponent's cards by a given attack power.
     * The method randomly selects one of the opponent's cards and decreases its blood by the specified attack power.
     *
     * @param player The Player object representing the opponent whose card will have its blood decreased.
     * @param attackPower The attack power to decrease the selected card's blood by.
     */
    public static void computerDecreaseTheOppenentBlood(Player player, int attackPower) {
        Random random = new Random();

        while (true) {
            // Randomly choose a card number between 1 and 12
            int number = random.nextInt(12) + 1;

            if (player.getChosenCards().containsKey(number)) {
                AnimalsCard card = player.getChosenCards().get(number).get(0);
                card.setBlood(card.getBlood() - attackPower);
                System.out.println("card blood " + card.getBlood());
                break; // Exit the loop after reducing the card's blood
            }
        }
    }
    /**
     * This method allows the player to manually choose one of the opponent's cards to attack,
     * reducing the chosen card's blood by a given attack power.
     *
     * @param player The Player object representing the opponent whose card will have its blood decreased.
     * @param attackPower The attack power to decrease the selected card's blood by.
     */
    public static void decreaseTheOppenentBlood(Player player, int attackPower) {
        Scanner scanner = new Scanner(System.in);
        // Print the opponent's available cards
        Player.printCards(player, 1);

        while (true) {
            System.out.println("################################################################");
            System.out.println("#####   >>>>>>     Choose The Card to Attack     <<<<<<   ######");
            System.out.println("################################################################");
            int choice = scanner.nextInt();
            // Check if the chosen card exists in the opponent's chosen cards
            if (player.getChosenCards().containsKey(choice)) {
                ArrayList<AnimalsCard> list = player.getChosenCards().get(choice);

                AnimalsCard card = list.get(0);
                card.setBlood(card.getBlood() - attackPower);
                System.out.println("card blood " + card.getBlood());
                break;
            } else {
                // If the chosen card doesn't exist, prompt the player to try again
                System.out.println("INVALID NUMBER >>> Please Enter Again");
            }
        }
    }
    /**
     * Checks if the player's card with the specified key has enough energy for an attack.
     *
     * @param player The Player object whose card's energy is being checked.
     * @param key The key representing the card to be checked.
     * @param amountOfAttackPower The required amount of energy for the attack.
     * @return true if the card has enough energy for the attack, false otherwise.
     */
    public static boolean check(Player player, Integer key, int amountOfAttackPower) {
        if (player.getAttackCards().containsKey(key)) {
            if (amountOfAttackPower <= player.getAttackCards().get(key).get(0).getEnergy()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * Calculates the total attack power and the number of cards used in the attack.
     *
     * @param player The Player object whose attack power is being calculated.
     * @param number Determines the type of attack:
     *
     * @return An array of two integers:
     */
    public static int[] culculate(Player player, int number) {
        int total = 0; // Total attack power
        int numberOfCards = 0;

        for (Integer key : player.getAttackCards().keySet()) {
            AnimalsCard cards = player.getAttackCards().get(key).get(0);
            int size = player.getAttackCards().get(key).size();

            if (number == 1) {
                total += cards.getNormalImpact() * size;
                numberOfCards += size;
            } else if (number == 2) {
                total += cards.getStrongImpact() * size;
                numberOfCards += size;
            }

        }
        int[] arr = new int[2]; // Store results in an array
        arr[0] = total;
        arr[1] = numberOfCards; // Number of cards used in attack

        return arr;
    }
    /**
     * Allows the player to choose cards to launch an attack on the opponent.
     *
     * This method handles the process of displaying available cards, selecting
     * the cards for the attack, validating input, and storing the selected cards
     * for the attack.
     *
     * @param player1 The attacking player selecting cards.
     * @param player2 The defending player displaying cards.
     */
    public static void attack(Player player1, Player player2) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("#####   >>>>>>              Your Cards           <<<<<<   ######");
        Player.printCards(player1, 2);
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>            Oppenect Cards         <<<<<<   ######");
        Player.printCards(player2, 1);
        System.out.println("################################################################");
        // Prompt for selecting cards for the attack
        System.out.println("#####   >>>>>>    Choose Your Card to attack     <<<<<<   ######");
        System.out.println("################################################################");
        int numberOfCard = 0, amountOfCards = 0;
        int firstTurn = 1; // Ensures player can't exit without selecting a card first

        while (true) {
            System.out.println("#####   >>>>>>           1: Choose Card          <<<<<<   ######");
            System.out.println("#####   >>>>>>           2: Exit Choosing        <<<<<<   ######");
            System.out.println("################################################################");
            int choice = scanner.nextInt();


            if (choice == 1) {
                System.out.println("Please Enter the \'NUMBER\' of Card");
                numberOfCard = scanner.nextInt();
                // check if the chosen card exists
                if (!(player1.getChosenCards().containsKey(numberOfCard))) {
                    System.out.println("INVALID NUMBER >>> Please Enter Again");
                    continue;
                }

                System.out.println("Please Enter the \'AMOUNT\' of Card");
                amountOfCards = scanner.nextInt();

                // check the amount of cards selected
                if (!(amountOfCards >= 1 && amountOfCards <= player1.getChosenCards().get(numberOfCard).size())) {
                    System.out.println("INVALID NUMBER >>> Please Enter Again");
                    continue;
                }

                AnimalsCard obj = (player1.getAnimals()).get(numberOfCard);
                ArrayList<AnimalsCard> list = new ArrayList<>();

                for (int i = 0; i < amountOfCards; i++) {
                    list.add(obj);
                }

                player1.setAttackCards(numberOfCard, list);
                firstTurn = 2; // Allow exiting after at least one card has been chosen

            } else if (choice == 2 && firstTurn != 1) {
                System.out.println("################################################################");
                System.out.println("#####   >>>>>>            Choosing Ended         <<<<<<   ######");
                System.out.println("################################################################");
                break;
            } else {
                System.out.println("#####   >>>>>>   INVALID NUMBER >>> Please Enter Again     <<<<<<   ######");
            }
        }
    }
}
