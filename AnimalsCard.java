/**
 * This class is for the animals cards
 * Every onimals has its own atributes but there are the same with different numbres
 * @author Mohammad Jafar Saberi
 * @version 1.0
 */
public class AnimalsCard {
    private String cardName;
    private int animalsNumber;
    private int normalImpact;
    private int strongImpact;
    private int energy;
    private int primaryEnergy;
    private int blood;

    /**
     * THis the constructor of the class that takes four parameters
     * @param animalsNumber
     * @param normalImpact
     * @param strongImpact
     * @param energy
     * @param blood
     * @param cardName
     */
    public AnimalsCard(int animalsNumber, int normalImpact, int strongImpact, int energy, int primaryEnergy ,int blood, String cardName) {
        this.animalsNumber = animalsNumber;
        this.normalImpact = normalImpact;
        this.strongImpact = strongImpact;
        this.energy = energy;
        this.blood = blood;
        this.cardName = cardName;
        this.primaryEnergy = primaryEnergy;
    }

    public int getPrimaryEnergy() {
        return primaryEnergy;
    }

    public int getAnimalsNumber() {
        return animalsNumber;
    }

    public int getNormalImpact() {
        return normalImpact;
    }

    public int getStrongImpact() {
        return strongImpact;
    }

    public int getEnergy() {
        return energy;
    }

    public int getBlood() {
        return blood;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public String getCardName() {
        return cardName;
    }
}
