package core;

import csvBean.levelDifferenceCsvBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class craftingProcess {
    private static int steps;
    private static int progressCurrent, qualityCurrent, durabilityCurrent;
    private static int craftsmanshipCurrent, controlCurrent, cpCurrent;
    private static int progressIncreaseDefault, qualityIncreaseDefault;
    private static int progressEfficiency, qualityEfficiency;
    private static int craftsmanshipDefault, controlDefault;

    private static int suggestedCraftsmanship, suggestedControl;
    private static int progressLimit, qualityLimit, durabilityLimit;
    private static int craftsmanshipFactorFromLevelDifference, controlFactorFromLevelDifference;
    private static HashMap<String, Integer> buffMap;



    public int getCraftsmanshipDefault() {
        return craftsmanshipDefault;
    }


    public static int getControlDefault() {
        return controlDefault;
    }

    public static int getSteps() {
        return steps;
    }


    public static int getProgressCurrent() {
        return progressCurrent;
    }


    public static int getQualityCurrent() {
        return qualityCurrent;
    }

    public static void setQualityCurrent(int qualityCurrent) {
        craftingProcess.qualityCurrent = qualityCurrent;
    }

    public static int getDurabilityCurrent() {
        return durabilityCurrent;
    }

    public static void setDurabilityCurrent(int durabilityCurrent) {
        craftingProcess.durabilityCurrent = durabilityCurrent;
    }

    public static int getCraftsmanshipCurrent() {
        return craftsmanshipCurrent;
    }

    public static void setCraftsmanshipCurrent(int craftsmanshipCurrent) {
        craftingProcess.craftsmanshipCurrent = craftsmanshipCurrent;
    }

    public static int getControlCurrent() {
        return controlCurrent;
    }

    public static void setControlCurrent(int controlCurrent) {
        craftingProcess.controlCurrent = controlCurrent;
    }

    public static int getCpCurrent() {
        return cpCurrent;
    }

    public static void setCpCurrent(int cpCurrent) {
        craftingProcess.cpCurrent = cpCurrent;
    }

    public static int getProgressIncreaseDefault() {
        return progressIncreaseDefault;
    }


    public static int getQualityIncreaseDefault() {
        return qualityIncreaseDefault;
    }



    public int getSuggestedCraftsmanship() {
        return suggestedCraftsmanship;
    }


    public int getSuggestedControl() {
        return suggestedControl;
    }


    public static int getProgressLimit() {
        return progressLimit;
    }


    public static int getQualityLimit() {
        return qualityLimit;
    }


    public int getDurabilityLimit() {
        return durabilityLimit;
    }


    public static HashMap<String, Integer> getBuffMap() {
        return buffMap;
    }

    public void setBuffMap(HashMap<String, Integer> buffMap) {
        this.buffMap = buffMap;
    }




    public craftingProcess(craftingCrafter crafter, craftingRecipe recipe) {
        int crafterLevel = crafter.getCrafterLevel();
        int recipeLevel = recipe.getRecipeLevel();
        int levelDifference = crafterLevel - recipeLevel;
        initAttributeFactorsFromLevelDifference(levelDifference);

        initProcessAttributes(recipe);
        initCrafterAttributes(crafter);

        buffMap = new HashMap<String, Integer>();

        printProcessInitial();

    }

    /**
     * Initialize various attributes of the crafting process based on given recipe
     * This should change after implementing High Quality material, which provides bonus quality to qualityCurrent at start
     * @param recipe
     */
    private void initProcessAttributes(craftingRecipe recipe){
        progressLimit = recipe.getProgress();
        qualityLimit = recipe.getQuality();
        durabilityLimit = recipe.getDurability();
        steps = 0;
        progressCurrent = 0;
        qualityCurrent = 0;
        durabilityCurrent = durabilityLimit;
        suggestedCraftsmanship = recipe.getSuggestedCraftsmanship();
        suggestedControl = recipe.getSuggestedControl();
    }

    /**
     * Initialize various attributes of the crafting process based on given crafter
     * This should change after implementing Consumable (food / potion / fc buff)
     * @param crafter
     */
    private void initCrafterAttributes(craftingCrafter crafter){
        craftsmanshipCurrent = craftsmanshipDefault =crafter.getCraftsmanship();
        controlCurrent = controlDefault = crafter.getControl();
        cpCurrent = crafter.getCraftingPoint();

    }


    /**
     * Given levelDifference, find progressFactor and qualityFactor from data/CraftLevelDifference.csv
     * @param levelDifference
     */
    private void initAttributeFactorsFromLevelDifference(int levelDifference){
        if (levelDifference < -30) {
            levelDifference = -30;
        }
        else if (levelDifference > 49){
            levelDifference = 49;
        }

        try {
            CsvToBeanBuilder<levelDifferenceCsvBean> levelDifferenceCsvBeanBuilder =
                    new CsvToBeanBuilder(new InputStreamReader(new FileInputStream("data/CraftLevelDifference.csv")));
            levelDifferenceCsvBeanBuilder.withType(levelDifferenceCsvBean.class);
            List<levelDifferenceCsvBean> levelDifferenceCsvBeans = levelDifferenceCsvBeanBuilder.withSkipLines(3).build().parse();
            for (levelDifferenceCsvBean lb : levelDifferenceCsvBeans){
                if (lb.levelDifference == levelDifference){
                    System.out.println("Found target levelDifference in CraftLevelDifference.csv!");
                    craftsmanshipFactorFromLevelDifference = lb.progressFactor;
                    controlFactorFromLevelDifference = lb.qualityFactor;
                    break;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Calculate the progress increase at 100% efficiency
     *
     * @return
     */
    public int calculateProgressIncreaseDefault(){
        return (int)(Math.floor((float)craftsmanshipFactorFromLevelDifference / 100 * (0.21 * craftsmanshipCurrent + 2)
                * (10000 + craftsmanshipCurrent) / (10000 + suggestedCraftsmanship)));
    }

    /**
     * Calculate the quality increase at 100% efficiency
     * This should change after implementing random conditions (Normal / Good / Excellent / Poor)
     *
     * @return
     */
    public static int calculateQualityIncreaseDefault(){
        return (int)(Math.floor((float)controlFactorFromLevelDifference / 100 * (0.35 * controlCurrent + 35)
        * (10000 + controlCurrent) / (10000 + suggestedControl)));
    }


    public int calculateProgressIncreaseActual(){
        return (int)(Math.floor((float)progressEfficiency / 100 * progressIncreaseDefault));
    }

    public int calculateQualityIncreaseActual(){
        return (int)(Math.floor((float)qualityEfficiency / 100 * qualityIncreaseDefault));
    }

    public void stepNext(){
        if (progressCurrent >= progressLimit){
            System.out.println("Craft finished! Progress limit is reached!");
        }
        else if (durabilityCurrent <= 0){
            System.out.println("Craft failed! Durability reaches 0");
        }
        else {
            for (Map.Entry<String, Integer> buffEntry : buffMap.entrySet()) {
                if (!buffEntry.getKey().equals("Inner Quiet")) { // every buff except Inner Quiet will get one less stack
                    buffEntry.setValue(buffEntry.getValue() - 1);
                }
            }
            buffMap.entrySet().removeIf(entry -> entry.getValue().equals(0));
            steps += 1;
        }
    }

    public void handleManipulation(){
        if (buffMap.containsKey("Manipulation")){
            durabilityCurrent += 5;
            if (durabilityCurrent > durabilityLimit){
                durabilityCurrent = durabilityLimit;
            }
        }
    }

    public void printProcessInitial(){
        System.out.println("progressLimit: " + progressLimit);
        System.out.println("qualityLimit: " + qualityLimit);
        System.out.println("cpCurrent: " + cpCurrent);
        System.out.println("durabilityLimit: " + durabilityLimit);
        System.out.println("===== steps: " + steps);


    }

    public void printProcessCurrent(){
        System.out.println("progressCurrent: " + progressCurrent);
        System.out.println("qualityCurrent: " + qualityCurrent);
        System.out.println("cpCurrent: " + cpCurrent);
        System.out.println("durabilityCurrent: " + durabilityCurrent);
        System.out.println("steps: " + steps);
        System.out.println("controlCurrent: " + controlCurrent);
        System.out.println("buffMap: ");
        for (Map.Entry<String, Integer> buffEntry : buffMap.entrySet()) {
            System.out.println(buffEntry.getKey() + ": " + buffEntry.getValue());
        }
    }

}
