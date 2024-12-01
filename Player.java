import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Player {
    private String name;
    private int reviveChance; // the number of chances to reset the energy of card
    private HashMap<Integer, AnimalsCard> animals; // Hashmap of animals cards from every animal there is one
    private HashMap<Integer, ArrayList<AnimalsCard>> cards; // Every player has 30 cards, these hashmap keeps them
    private int amountOfCard;
    private HashMap<Integer, ArrayList<AnimalsCard>> chosenCards;
    private HashMap<Integer, ArrayList<AnimalsCard>> attackCards;

    public Player() {
        cards = new HashMap<>();
        animals = new HashMap<>();
        reviveChance = 3;
        amountOfCard = 10;
        chosenCards = new HashMap<>();
        attackCards = new HashMap<>();
        AnimalsCard lion = new AnimalsCard(1, 150, 500, 1000, 1000, 900, "Lion");
        animals.put(1, lion);
        AnimalsCard bear = new AnimalsCard(2, 130, 600, 900, 900, 850, "Bear");
        animals.put(2, bear);
        AnimalsCard tiger = new AnimalsCard(3, 120, 650, 850, 850, 850, "Tiger");
        animals.put(3, tiger);
        AnimalsCard vulture = new AnimalsCard(4, 100, 0, 600, 600, 350, "vulture");
        animals.put(4, vulture);
        AnimalsCard fox = new AnimalsCard(5, 90, 0, 600, 600, 400, "Fox  ");
        animals.put(5, fox);
        AnimalsCard elefent = new AnimalsCard(6, 50, 70, 500, 500, 1200, "Elefent");
        animals.put(6, elefent);
        AnimalsCard wolf = new AnimalsCard(7, 50, 700, 700, 700, 450, "Wolf");
        animals.put(7, wolf);
        AnimalsCard pig = new AnimalsCard(8, 80, 0, 500, 500, 1100, "Pig  ");
        animals.put(8, pig);
        AnimalsCard seaCow = new AnimalsCard(9, 110, 0, 360, 360, 1100, "Sea Cow");
        animals.put(9, seaCow);
        AnimalsCard cow = new AnimalsCard(10, 90, 100, 400, 400, 750, "Cow  ");
        animals.put(10, cow);
        AnimalsCard rabbit = new AnimalsCard(11, 80, 0, 350, 350, 200, "Rabbit");
        animals.put(11, rabbit);
        AnimalsCard turtle = new AnimalsCard(12, 200, 0, 230, 230, 350, "Turtle");
        animals.put(12, turtle);
    }

    public int getReviveChance() {
        return reviveChance;
    }

    public int getAmountOfCard() {
        return amountOfCard;
    }

    public HashMap<Integer, ArrayList<AnimalsCard>> getAttackCards() {
        return attackCards;
    }

    public void setAttackCards(Integer key, ArrayList<AnimalsCard> list) {
        attackCards.put(key, list);
    }

    public void setAmountOfCard(int amountOfCard) {
        this.amountOfCard = amountOfCard;
    }

    public void setReviveChance(int reviveChance) {
        this.reviveChance = reviveChance;
    }

    public HashMap<Integer, ArrayList<AnimalsCard>> getChosenCards() {
        return chosenCards;
    }

    public void setChosenCards(Integer key, ArrayList<AnimalsCard> list) {
        chosenCards.put(key, list);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, ArrayList<AnimalsCard>> getCards() {
        return cards;
    }

    public HashMap<Integer, AnimalsCard> getAnimals() {
        return animals;
    }

    public void fillTheAnimals(Player player) {
        Random rand = new Random();
        int number = 30;

        int[] array = new int[12];
        for (int i = 0; i < 12; i++) {
            array[i] = 0;
        }

        while (number > 0) {
            int animalNumber = rand.nextInt(12) + 1;
            // Eid Mohammad Code he is a good boy my rommate i bother him a lot
            // I eat his foods and biscut a lot but ho do not buy biscute 😩😩😩
            AnimalsCard tmp = player.getAnimals().get(animalNumber);
            if (player.getCards().containsKey(animalNumber)) {
                if (array[animalNumber - 1] < 5) {
                    player.getCards().get(animalNumber).add(tmp);
                    number--;
                    array[animalNumber - 1]++;
                }
            } else {
                player.getCards().put(animalNumber, new ArrayList<>());
                player.getCards().get(animalNumber).add(tmp);
                array[animalNumber - 1]++;
                number--;
            }
        }

    }

    public void showAnimalsCard(Player player, HashMap<Integer, ArrayList<AnimalsCard>> playerCards) {
        HashMap<Integer, ArrayList<AnimalsCard>> cards = player.getCards();
        System.out.println("Number" + "\t" + "Card Name" + "\t Number of Cards");
        for (int i = 1; i <= 12; i++) {
            ArrayList<AnimalsCard> list = new ArrayList<>();
            AnimalsCard animals = new AnimalsCard();

            if (cards.containsKey(i) && !(playerCards.containsKey(i))) {
                list = cards.get(i);
                animals = list.get(0);
                System.out.print(animals.getAnimalsNumber() + "\t\t" + animals.getCardName() + "\t\t\t" + "\"" + list.size() + "\"");
                System.out.println();
            }
        }
    }

    public static void printCards(Player player, int number) {
        if (number == 1) {
            System.out.println("Number" + "\tName" + "\tEnergy" + "\tBlood");
        } else {
            System.out.println("Number" + "\tName" + "\tNormal Impact" + "\tStrong Impact" + "\tEnergy" + "\tBlood" + "\t\tAmount");
        }
        AnimalsCard cards;

        if (number == 1) {
            for (Integer key : player.getChosenCards().keySet()) {
                cards = (player.getChosenCards().get(key)).get(0);
                System.out.println(cards.getAnimalsNumber() + "\t\t" + cards.getCardName() + "\t" + cards.getEnergy() + "\t\t" + cards.getBlood());
            }
        } else if (number == 2) {
            for (Integer key : player.getChosenCards().keySet()) {
                cards = (player.getChosenCards().get(key)).get(0);
                System.out.println(cards.getAnimalsNumber() + "\t\t" + cards.getCardName() + "\t" + cards.getNormalImpact() + "\t\t\t\t" +
                        cards.getStrongImpact() + "\t\t\t\t" + cards.getEnergy() + "\t\t" + cards.getBlood() + "\t\t\t" + (player.getChosenCards().get(key)).size());
            }
        } else if (number == 3) {
            for (Integer key : player.getAttackCards().keySet()) {
                cards = (player.getAttackCards().get(key)).get(0);
                System.out.println(cards.getAnimalsNumber() + "\t\t" + cards.getCardName() + "\t" + cards.getNormalImpact() + "\t\t\t\t" +
                        cards.getStrongImpact() + "\t\t\t\t" + cards.getEnergy() + "\t\t" + cards.getBlood() + "\t\t\t" + (player.getAttackCards().get(key)).size());
            }
        }
    }
}
