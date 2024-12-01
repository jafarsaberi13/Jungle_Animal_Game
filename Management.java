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
     *
     * @param player
     */
    public static void chooseCards(Player player) {
        Scanner scanner = new Scanner(System.in);
        int numberOfCard = 0;
        int amountOfCards = 0;

        while (player.getAmountOfCard() > 0) {
            player.showAnimalsCard(player, player.getChosenCards());
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
                System.out.println("INVALID NUMBER >>> Please Enter Again");
                continue;
            }

            int tmp = player.getAmountOfCard() - amountOfCards;
            if (tmp < 0) {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
                System.out.println("You Have \'" + player.getAmountOfCard() + "\' Chance to Choose Cards");
                System.out.println("Please Choose equal/less than \'" + player.getAmountOfCard() + "\'");
                continue;
            }
            AnimalsCard obj = (player.getAnimals()).get(numberOfCard);
            ArrayList<AnimalsCard> list = new ArrayList<>();

            for (int i = 0; i < amountOfCards; i++) {
                list.add(obj);
            }

            player.setChosenCards(numberOfCard, list);
            player.setAmountOfCard(player.getAmountOfCard() - amountOfCards);

            for (int i = 1; i <= 12; i++) {
                ArrayList<AnimalsCard> lists = new ArrayList<>();
                AnimalsCard animals = new AnimalsCard();

                if (player.getChosenCards().containsKey(i)) {
                    lists = player.getChosenCards().get(i);
                    animals = lists.get(0);
                    System.out.print(animals.getAnimalsNumber() + "\t\t" + animals.getCardName() + "\t\t\t" + "\"" + lists.size() + "\"");
                    System.out.println();
                }
            }
        }
    }

    public static void play() {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();
        while (true) {
            welcome();
            int tmp = scanner.nextInt();

            int turn = 1, choice = 0;
            int turn1 = 1;
            if (tmp == 1) {
                while (true) {
                    System.out.println("#####  >>>  Please Enter the Name of Player   <<<  #####");
                    player1.setName(scanner.next());
                    player1.fillTheAnimals(player1);
                    choose(player1);

                    menu1();
                    choice = scanner.nextInt();

                    if (choice == 1) {
                        Player computer = new Player();
                        computer.setName("Computer");

                        computer.fillTheAnimals(computer);
                        computer.showAnimalsCard(computer, computer.getChosenCards());

                        chooseComputerCards(computer);
                        Player.printCards(computer, 2);
                        loop:
                        while (true) {
                            int choice2 = 0;
                            if (turn1 == 1) {
                                while (true) {
                                    System.out.println("#####  >>>  " + player1.getName() + "`s turn   <<<  #####");
                                    System.out.println("#####  >>>          1: Attack               <<<  #####");
                                    System.out.println("#####  >>>          0: Exit and New Game    <<<  #####");
                                    choice2 = scanner.nextInt();

                                    if (choice2 == 1) {
                                        attack(player1, computer);
                                        Player.printCards(player1, 3); // then clean
                                        attackToCard(player1, computer);
                                        break;
                                    } else if (choice2 == 0) {
                                        System.out.println("#####  >>>          Exited!!!          <<<  #####");
                                        break loop;
                                    } else {
                                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                                    }
                                }
                                turn1 = 2;
                            } else if (turn1 == 2) {
                                System.out.println("#####  >>>  " + computer.getName() + "`s turn   <<<  #####");

                                computerAttackMethod(computer);
                                Player.printCards(computer, 3);

                                computerAttackToCard(computer, player1);
                                turn1 = 1;
                            }

                            if (turn == 2) {
                                player1.getAttackCards().clear();
                            } else if (turn == 1) {
                                computer.getAttackCards().clear();
                            }

                            if (clearingTheWinner(player1)) {
                                printWinner(computer);
                                break;
                            }
                            if (clearingTheWinner(computer)) {
                                printWinner(player1);
                                break;
                            }
                        }
                    } else if (choice == 2) {
                        System.out.println("#####  >>>  Please Enter the Name of SECOND Player   <<<  #####");
                        player2.setName(scanner.next());
                        player2.fillTheAnimals(player2);
                        choose(player2);

                        second:
                        while (true) {
                            int choice2 = 0;
                            if (turn == 1) {
                                while (true) {
                                    System.out.println("#####  >>>  " + player1.getName() + "`s turn   <<<  #####");
                                    System.out.println("#####  >>>          1: Attack               <<<  #####");
                                    System.out.println("#####  >>>          0: Exit and New Game    <<<  #####");
                                    choice2 = scanner.nextInt();

                                    if (choice2 == 1) {
                                        attack(player1, player2);
                                        Player.printCards(player1, 3); // then clean
                                        attackToCard(player1, player2);
                                        break;
                                    } else if (choice2 == 0) {
                                        System.out.println("#####  >>>          Exited!!!          <<<  #####");
                                        break second;
                                    } else {
                                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                                    }
                                }
                                turn = 2;
                            } else if (turn == 2) {
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

                            if (turn == 2) {
                                player1.getAttackCards().clear();
                            } else if (turn == 1) {
                                player2.getAttackCards().clear();
                            }

                            if (clearingTheWinner(player1)) {
                                printWinner(player2);
                                break;
                            }
                            if (clearingTheWinner(player2)) {
                                printWinner(player1);
                                break;
                            }
                        }
                    } else {
                        System.out.println("INVALID NUMBER >>> Please Enter Again");
                    }
                }
            } else if (tmp == 2) {
                goodBye();
                break;
            } else {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
            }
        }
    }

    public static void chooseComputerCards(Player computer) {
        int number = 1;

        ArrayList<AnimalsCard> card;

        while (computer.getAmountOfCard() > 0) {
            if (computer.getCards().containsKey(number)) {
                card = computer.getCards().get(number);

                int t = computer.getAmountOfCard() - card.size();
                if (t <= 0) {
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

    public static void printWinner(Player player) {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>   " + player.getName() + " is the Winner    <<<<<<   ######");
        System.out.println("################################################################");
    }

    public static boolean clearingTheWinner(Player player) {
        if (player.getChosenCards().isEmpty()) {
            return true;
        }
        return false;
    }

    public static void menu1() {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>               NEW GAME            <<<<<<   ######");
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>      1: Play With Computer        <<<<<<   ######");
        System.out.println("#####   >>>>>>      2: Play With Friend          <<<<<<   ######");
        System.out.println("#####   >>>>>>      Please Enter a Number        <<<<<<   ######");
        System.out.println("################################################################");
    }

    public static void choose(Player player) {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>   Choose Your 10 Cards \'" + player.getName() + "\'  <<<<<<   ######");
        System.out.println("#####   >>>>>>   You have These Following Cards  <<<<<<   ######");
        System.out.println("################################################################");
        chooseCards(player);
    }

    public static void welcome() {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>   WELCOME TO FOREST ANIMALS GAME  <<<<<<   ######");
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>         1: START THE GAME         <<<<<<   ######");
        System.out.println("#####   >>>>>>             2: EXIT               <<<<<<   ######");
        System.out.println("################################################################");
    }

    public static void goodBye() {
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>        GOOD BYE MY FRIENDS        <<<<<<   ######");
        System.out.println("################################################################");
    }

    public static void computerAttackToCard(Player computer, Player player) {
        Random random = new Random();
        int[] array = new int[2];

        out:
        while (true) {
            int number = random.nextInt(3) + 1;
            int attackPower = 0;

            if (number == 1) {
                System.out.println("Normal Attack");
                array = culculate(computer, 1);
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
            } else if (number == 2) {
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
            } else if (number == 3) {
                computerResetEnergy(computer);
            }
            break;
        }
    }

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

            if (choice == 1) {
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
            } else if (choice == 2) {
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
            } else if (player1.getReviveChance() == 0) {
                System.out.println("Your Chances to Reset Card Energy finished");
                continue;
            } else if (choice == 3) {
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

    public static void computerResetEnergy(Player computer) {
        Random random = new Random();

        while (true) {
            int number = random.nextInt(12) + 1;

            if (computer.getChosenCards().containsKey(number)) {
                AnimalsCard card = computer.getChosenCards().get(number).get(0);
                card.setEnergy(card.getPrimaryEnergy());
                computer.setReviveChance(computer.getReviveChance() - 1);
                System.out.println("reset number " + computer.getReviveChance());
                break;
            }
        }
    }

    public static void resetEnergy(Player player) {
        Scanner scanner = new Scanner(System.in);

        Player.printCards(player, 2);
        while (true) {
            System.out.println("#####   >>>>>>     Choose The Card to Reset Its Energy    <<<<<<   ######");
            int choice = scanner.nextInt();

            if (player.getChosenCards().containsKey(choice)) {
                AnimalsCard card = player.getChosenCards().get(choice).get(0);
                card.setEnergy(card.getPrimaryEnergy());
                player.setReviveChance(player.getReviveChance() - 1);
                System.out.println("reset number " + player.getReviveChance());
                break;
            } else {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
            }
        }
    }

    public static void decreaseAttackerEnergy(Player player, int decreaseAmount) {
        for (Integer key : player.getAttackCards().keySet()) {
            ArrayList<AnimalsCard> list = player.getAttackCards().get(key);

            AnimalsCard card = list.get(0);
            card.setEnergy(card.getEnergy() - decreaseAmount);
        }
    }

    public static void chechForDiedCards(Player player) {
        for (int i = 1; i < 13; i++) {
            if (player.getChosenCards().containsKey(i)) {
                AnimalsCard card = player.getChosenCards().get(i).get(0);

                if (card.getBlood() <= 0) {
                    player.getChosenCards().remove(i);
                }
            }
        }
    }

    public static void computerDecreaseTheOppenentBlood(Player player, int attackPower) {
        Random random = new Random();

        while (true) {
            int number = random.nextInt(12) + 1;

            if (player.getChosenCards().containsKey(number)) {
                AnimalsCard card = player.getChosenCards().get(number).get(0);
                card.setBlood(card.getBlood() - attackPower);
                System.out.println("card blood " + card.getBlood());
                break;
            }
        }
    }

    public static void decreaseTheOppenentBlood(Player player, int attackPower) {
        Scanner scanner = new Scanner(System.in);

        Player.printCards(player, 1);

        while (true) {
            System.out.println("################################################################");
            System.out.println("#####   >>>>>>     Choose The Card to Attack     <<<<<<   ######");
            System.out.println("################################################################");
            int choice = scanner.nextInt();

            if (player.getChosenCards().containsKey(choice)) {
                ArrayList<AnimalsCard> list = player.getChosenCards().get(choice);


                AnimalsCard card = list.get(0);
                card.setBlood(card.getBlood() - attackPower);
                System.out.println("card blood " + card.getBlood());
                break;
            } else {
                System.out.println("INVALID NUMBER >>> Please Enter Again");
            }
        }
    }

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

    public static int[] culculate(Player player, int number) {
        int total = 0;
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
        int[] arr = new int[2];
        arr[0] = total;
        arr[1] = numberOfCards;

        return arr;
    }

    public static void attack(Player player1, Player player2) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("#####   >>>>>>              Your Cards           <<<<<<   ######");
        Player.printCards(player1, 2);
        System.out.println("################################################################");
        System.out.println("#####   >>>>>>            Oppenect Cards         <<<<<<   ######");
        Player.printCards(player2, 1);
        System.out.println("################################################################");

        System.out.println("#####   >>>>>>    Choose Your Card to attack     <<<<<<   ######");
        System.out.println("################################################################");
        int numberOfCard = 0, amountOfCards = 0;
        int firstTurn = 1;

        while (true) {
            System.out.println("#####   >>>>>>           1: Choose Card          <<<<<<   ######");
            System.out.println("#####   >>>>>>           2: Exit Choosing        <<<<<<   ######");
            System.out.println("################################################################");
            int choice = scanner.nextInt();


            if (choice == 1) {
                System.out.println("Please Enter the \'NUMBER\' of Card");
                numberOfCard = scanner.nextInt();

                if (!(player1.getChosenCards().containsKey(numberOfCard))) {
                    System.out.println("INVALID NUMBER >>> Please Enter Again");
                    continue;
                }

                System.out.println("Please Enter the \'AMOUNT\' of Card");
                amountOfCards = scanner.nextInt();


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
                firstTurn = 2;

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
