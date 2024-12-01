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

    public AnimalsCard() {
    }
    /**
     * Getter of the animalsNumber
     * @return animalsNumber
     */
    public int getAnimalsNumber() {
        return animalsNumber;
    }

    /**
     * Getter of the normal impact
     * @return amount of the normal impact
     */
    public int getNormalImpact() {
        return normalImpact;
    }
    /**
     * Getter of the strong impact
     * @return amount of the strong impact
     */
    public int getStrongImpact() {
        return strongImpact;
    }
    /**
     * Getter of the energy
     * @return amount of the energy
     */
    public int getEnergy() {
        return energy;
    }
    /**
     * Getter of the blood
     * @return amount of the blood an animals has
     */
    public int getBlood() {
        return blood;
    }

    /**
     * setter of the energy
     * @param energy
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    /**
     * setter of blood
     * @param blood
     */
    public void setBlood(int blood) {
        this.blood = blood;
    }

    public String getCardName() {
        return cardName;
    }
}
