public class craftingCrafter {
    public int craftsmanship, control, craftingPoint, playerLevel;
    public String job;


    public craftingCrafter(){
        this.craftsmanship = 0;
        this.control = 0;
        this.craftingPoint = 0;
        this.playerLevel = 0;
        this.job = "";
    }
    public craftingCrafter(int craftsmanship, int control, int craftingPoint, int playerLevel, String job){
        this.craftsmanship = craftsmanship;
        this.control = control;
        this.craftingPoint = craftingPoint;
        this.playerLevel = playerLevel;
        this.job = job;
    }

    /**
     * Search the clv table and return corresponding clv given plv
     * @return crafterLevel
     */
    public int calculateCrafterLevel(){
        int crafterLevel = 0;
        return crafterLevel;
    }
}
