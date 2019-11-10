import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

public class craftingProcess {
    private static int steps;
    private static int progressCurrent, qualityCurrent, durabilityCurrent;
    private static int craftsmanshipCurrent, controlCurrent, cpCurrent;
    private static int progressIncreaseDefault, qualityIncreaseDefault;
    private static int progressEfficiency, qualityEfficiency;
    private int suggestedCraftsmanship, suggestedControl;
    private int progressLimit, qualityLimit, durabilityLimit;
    private int craftsmanshipFactorFromLevelDifference, controlFactorFromLevelDifference;


    public craftingProcess(craftingCrafter crafter, craftingRecipe recipe) {
        int crafterLevel = crafter.getCrafterLevel();

        int recipeLevel = recipe.getRecipeLevel();

        int levelDifference = crafterLevel - recipeLevel;
        setAttributeFactorsFromLevelDifference(levelDifference);


        initProcessAttributes(recipe);
        initCrafterAttributes(crafter);


    }

    /**
     * Initialize various attributes of the crafting process based on given recipe
     * This should change after implementing High Quality material, which provides bonus quality to qualityCurrent at start
     * @param recipe
     */
    private void initProcessAttributes(craftingRecipe recipe){
        this.progressLimit = recipe.getProgress();
        this.qualityLimit = recipe.getQuality();
        this.durabilityLimit = recipe.getDurability();
        this.steps = 0;
        this.progressCurrent = 0;
        this.qualityCurrent = 0;
        this.durabilityCurrent = durabilityLimit;
        this.suggestedCraftsmanship = recipe.getSuggestedCraftsmanship();
        this.suggestedControl = recipe.getSuggestedControl();
    }

    /**
     * Initialize various attributes of the crafting process based on given crafter
     * This should change after implementing Consumable (food / potion / fc buff)
     * @param crafter
     */
    private void initCrafterAttributes(craftingCrafter crafter){
        this.craftsmanshipCurrent = crafter.getCraftsmanship();
        this.controlCurrent = crafter.getControl();
        this.cpCurrent = crafter.getCraftingPoint();
    }


    /**
     * Given levelDifference, find progressFactor and qualityFactor from data/CraftLevelDifference.csv
     * @param levelDifference
     */
    private void setAttributeFactorsFromLevelDifference(int levelDifference){
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
                    this.craftsmanshipFactorFromLevelDifference = lb.progressFactor;
                    this.controlFactorFromLevelDifference = lb.qualityFactor;
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
    public int calculateQualityIncreaseDefault(){
        return (int)(Math.floor((float)controlFactorFromLevelDifference / 100 * (0.35 * controlCurrent + 35)
        * (10000 + controlCurrent) / (10000 + suggestedControl)));
    }


    public int calculateProgressIncreaseActual(){
        return (int)(Math.floor((float)progressEfficiency / 100 * progressIncreaseDefault));
    }

    public int calculateQualityIncreaseActual(){
        return (int)(Math.floor((float)qualityEfficiency / 100 * qualityIncreaseDefault));
    }



}
