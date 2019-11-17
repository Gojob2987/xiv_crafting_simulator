package core;

import csvBean.craftActionsTableCsvBean;
import csvBean.levelDifferenceCsvBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;

public class craftingProcess {
    private static craftingCrafter crafter;
    private static craftingRecipe recipe;
    private static int steps; // updated in each step
    private static int progressCurrent, qualityCurrent, durabilityCurrent; // updated in each step
    private static int craftsmanshipCurrent, controlCurrent, cpCurrent; // updated in each step
    private static int progressIncreaseDefault, qualityIncreaseDefault; // 100% efficiency, updated in each step
    private static int controlBonus;

    private static int craftsmanshipFactorFromLevelDifference, controlFactorFromLevelDifference; // fixed values, calculate at initial
    private static HashMap<String, Integer> buffMap; // updated in each step
    private static final Set<String> specialBuffs = new HashSet<>(Arrays.asList("Inner Quiet", "Nameless"));


    public static craftingCrafter getCrafter() {
        return crafter;
    }

    public static void setCrafter(craftingCrafter crafter) {
        craftingProcess.crafter = crafter;
    }

    public static craftingRecipe getRecipe() {
        return recipe;
    }

    public static void setRecipe(craftingRecipe recipe) {
        craftingProcess.recipe = recipe;
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

    public static HashMap<String, Integer> getBuffMap() {
        return buffMap;
    }

    public void setBuffMap(HashMap<String, Integer> buffMap) {
        this.buffMap = buffMap;
    }




    public craftingProcess(craftingCrafter crafter, craftingRecipe recipe) {
        this.crafter = crafter;
        this.recipe = recipe;

        int levelDifference = crafter.getCrafterLevel() - recipe.getRecipeLevel();
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
        steps = 0;
        progressCurrent = 0;
        qualityCurrent = 0;
        durabilityCurrent = recipe.getDurability();
        controlBonus = 0;
    }

    /**
     * Initialize various attributes of the crafting process based on given crafter
     * This should change after implementing Consumable (food / potion / fc buff)
     * @param crafter
     */
    private void initCrafterAttributes(craftingCrafter crafter){
        craftsmanshipCurrent = crafter.getCraftsmanship();
        controlCurrent = crafter.getControl();
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
                * (10000 + craftsmanshipCurrent) / (10000 + recipe.getSuggestedCraftsmanship())));
    }

    /**
     * Calculate the quality increase at 100% efficiency
     * This should change after implementing random conditions (Normal / Good / Excellent / Poor)
     *
     * @return
     */
    public int calculateQualityIncreaseDefault(){
        return (int)(Math.floor((float)controlFactorFromLevelDifference / 100 * (0.35 * controlCurrent + 35)
        * (10000 + controlCurrent) / (10000 + recipe.getSuggestedControl())));
    }


    public int calculateProgressIncreaseActual(int progressEfficiency){
        return (int)(Math.floor((float)progressEfficiency / 100 * progressIncreaseDefault));
    }



    public int calculateQualityIncreaseActual(int qualityEfficiency){
        return (int)(Math.floor((float)qualityEfficiency / 100 * qualityIncreaseDefault));
    }

    public void updateControlCurrent(){
        if (buffMap.containsKey("Inner Quiet")){
            int innerQuietStack = buffMap.get("Inner Quiet");
            controlBonus = (int)Math.floor((float)crafter.getControl() * 0.2 * (innerQuietStack - 1));
            if (controlBonus > 3000){
                controlBonus = 3000;
            }
            controlCurrent = crafter.getControl() + controlBonus;
        }

    }


    public boolean isCraftOver(){
        boolean isOver = false;
        if (progressCurrent >= recipe.getProgress()){
            System.out.println("Craft finished! Progress limit is reached!");
            isOver = true;
        }
        else if (durabilityCurrent <= 0){
            System.out.println("Craft failed! Durability reaches 0");
            isOver = true;
        }
        return isOver;
    }

    public void updateDurabilityRecover(craftActionsTableCsvBean actionBean){
        if (buffMap.containsKey("Manipulation")) {
            durabilityCurrent += 5;
        }

        if (actionBean.name.equals("Master's Mend")){
            durabilityCurrent += 30;
        }

        if (durabilityCurrent > recipe.getDurability()){
            durabilityCurrent = recipe.getDurability();
        }

    }


    public void updateBuffs(craftActionsTableCsvBean actionBean){
        if (buffMap.containsKey("Inner Quiet")){
            if (actionBean.name.equals("Byregot's Blessing")){
                buffMap.remove("Inner Quiet");
            }
            else {
                int newInnerQuietStack = buffMap.get("Inner Quiet") + actionBean.innerQuietIncrease;
                if (newInnerQuietStack >= 11) {
                    buffMap.put("Inner Quiet", 11);
                } else {
                    buffMap.put("Inner Quiet", newInnerQuietStack);
                }
            }
        }

        if (buffMap.containsKey("Great Strides") && actionBean.qualityEfficiency > 0){
            buffMap.remove("Great Strides");
        }


        // Update existing buffs
        for (Map.Entry<String, Integer> buffEntry : buffMap.entrySet()) {
            String buffName = buffEntry.getKey();
            int buffValue = buffEntry.getValue();

            if (!specialBuffs.contains(buffName)) { // every buff except Inner Quiet and Nameless will lose one stack
                buffEntry.setValue(buffValue - 1);
            }

            if (buffName.equals("Name of the Elements") && buffValue == 0){ // if Name of the Elements run out, apply Nameless
                buffMap.put("Nameless", 1);
            }
        }
        buffMap.entrySet().removeIf(entry -> entry.getValue().equals(0));


        // Introduce new buffs
        if (actionBean.giveBuff){
            if (actionBean.name.equals("Inner Quiet") || actionBean.name.equals("Reflect")){
                buffMap.put(actionBean.buffName, actionBean.innerQuietIncrease);
            }
            else {
                buffMap.put(actionBean.buffName, actionBean.duration);
            }
        }

    }



    public craftActionsTableCsvBean applyBuffs(craftActionsTableCsvBean actionBean){
        if (actionBean.name.equals("Byregot's Blessing") && buffMap.containsKey("Inner Quiet")){
            int innerQuietStack = buffMap.get("Inner Quiet");
            actionBean.qualityEfficiency = 100 + (innerQuietStack - 1) * 20;
        }
        if ((actionBean.name.equals("Focused Touch") || actionBean.name.equals("Focused Synthesis")) && buffMap.containsKey("Observe")){
            actionBean.successRate = 100;
        }
        if (actionBean.name.equals("Brand of the Elements") && buffMap.containsKey("Name of the Elements")){
            actionBean.progressEfficiency =  300 - (int)(Math.floor((float) 100 * progressCurrent / recipe.getProgress())) * 2;
        }
        if (buffMap.containsKey("Innovation")){
            actionBean.qualityEfficiency += 20;
            actionBean.progressEfficiency += 20;
        }
        if (buffMap.containsKey("Great Strides")){
            buffMap.remove("Great Strides");
            actionBean.qualityEfficiency += 100;
        }
        if (buffMap.containsKey("Waste Not") || buffMap.containsKey("Waste Not II")){
            actionBean.durabilityCost /= 2;
        }
        return actionBean;
    }


    public craftActionsTableCsvBean getCraftActionBean(String actionName){
        try {
            CsvToBeanBuilder<craftActionsTableCsvBean> craftActionsTableCsvBeanCsvToBeanBuilder =
                    new CsvToBeanBuilder(new InputStreamReader(new FileInputStream("data/CraftActionsTable.csv")));
            craftActionsTableCsvBeanCsvToBeanBuilder.withType(craftActionsTableCsvBean.class);
            List<craftActionsTableCsvBean> craftActionsTableCsvBeans= craftActionsTableCsvBeanCsvToBeanBuilder.withSkipLines(3).build().parse();
            for (craftActionsTableCsvBean ab : craftActionsTableCsvBeans){
                if (ab.name.equals(actionName)){
                    System.out.println("Found target craft action: " + actionName + " in CraftActionsTable.csv!");
                    return ab;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean canDoAction(craftActionsTableCsvBean actionBean){
        Boolean canDo = true;
        String reason = "";
        if (actionBean.name.equals("Prudent Touch") && (buffMap.containsKey("Waste Not") || buffMap.containsKey("Waste Not II"))){
            canDo = false;
            reason = "Cannot use Prudent Touch with Waste Not";
        }
        if (actionBean.name.equals("Inner Quiet") && buffMap.containsKey("Inner Quiet")){
            canDo = false;
            reason = "Inner Quiet is already in effect";
        }
        else if (actionBean.name.equals("Byregot's Blessing") && !buffMap.containsKey("Inner Quiet")){
            canDo = false;
            reason = "Cannot use Byregot's Blessing without Inner Quiet";
        }
        else if (actionBean.isFirstStep == true && steps != 0){
            canDo = false;
            reason = "This action is limited to be used in first step";
        }
        else if (actionBean.cpCost > cpCurrent){
            canDo = false;
            reason = "Not enough CP";
        }
        System.out.println(reason);
        return canDo;
    }


    public void doAction(String actionName){
        craftActionsTableCsvBean actionBean = getCraftActionBean(actionName);
        if (actionBean == null){
            System.out.println("Action is null somehow");
            return;
        }
        if (!canDoAction(actionBean)){return;}


        //System.out.println("actionBean before buff: ");
        //actionBean.printBean();

        actionBean = applyBuffs(actionBean);

        //System.out.println("actionBean after buff: ");
        //actionBean.printBean();

        qualityIncreaseDefault = calculateQualityIncreaseDefault();
        progressIncreaseDefault = calculateProgressIncreaseDefault();
        qualityCurrent += calculateQualityIncreaseActual(actionBean.qualityEfficiency);
        progressCurrent += calculateProgressIncreaseActual(actionBean.progressEfficiency);
        durabilityCurrent -= actionBean.durabilityCost;
        cpCurrent -= actionBean.cpCost;


        updateBuffs(actionBean);
        updateControlCurrent();


        if (!isCraftOver()){
            updateDurabilityRecover(actionBean);
            steps += 1;
            printProcessCurrent();
        }
        else{
            return;
        }

    }




    public void printProcessInitial(){
        System.out.println("progressLimit: " + recipe.getProgress());
        System.out.println("qualityLimit: " + recipe.getQuality());
        System.out.println("cpCurrent: " + cpCurrent);
        System.out.println("durabilityLimit: " + recipe.getDurability());
        System.out.println("steps: " + steps);
        System.out.println("=====");


    }

    public void printProcessCurrent(){
        System.out.println("progressCurrent: " + progressCurrent);
        System.out.println("qualityCurrent: " + qualityCurrent);
        System.out.println("cpCurrent: " + cpCurrent);
        System.out.println("durabilityCurrent: " + durabilityCurrent);
        System.out.println("steps: " + steps);
        System.out.println("controlBonus " + controlBonus);
        System.out.println("controlCurrent: " + controlCurrent);
        System.out.print("buffMap: ");
        for (Map.Entry<String, Integer> buffEntry : buffMap.entrySet()) {
            System.out.println(buffEntry.getKey() + ": " + buffEntry.getValue());
        }
        System.out.println("=====");

    }

}
