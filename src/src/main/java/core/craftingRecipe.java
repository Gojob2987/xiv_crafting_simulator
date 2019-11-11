package core;

import csvBean.recipeCsvBean;
import csvBean.recipeLevelTableCsvBean;
import com.opencsv.bean.CsvToBeanBuilder;


import java.io.*;
import java.util.List;

public class craftingRecipe {
    private int itemResultID;
    private int classJobLevel, stars, suggestedCraftsmanship, suggestedControl, defaultProgress, defaultQuality, defaultDurability;
    private int recipeLevel, progressFactor, qualityFactor, durabilityFactor;
    private int progress, quality, durability;

    public int getProgress() {
        return progress;
    }


    public int getQuality() {
        return quality;
    }


    public int getDurability() {
        return durability;
    }


    public int getItemResultID() {
        return itemResultID;
    }

    public int getClassJobLevel() {
        return classJobLevel;
    }

    public int getStars() {
        return stars;
    }

    public int getSuggestedCraftsmanship() {
        return suggestedCraftsmanship;
    }

    public int getSuggestedControl() {
        return suggestedControl;
    }


    public int getRecipeLevel() {
        return recipeLevel;
    }


    /**
     * Given id of the item you want to craft, retrieve related recipe information from data/Recipe.csv and data/RecipeLevelTable.csv
     * Then calculate the actual difficulty / quality / durability for this recipe
     * @param itemResultID
     */
    public craftingRecipe(int itemResultID) {
        this.itemResultID = itemResultID;
        try {
            CsvToBeanBuilder<recipeCsvBean> recipeCsvBeanBuilder =
                    new CsvToBeanBuilder(new InputStreamReader(new FileInputStream("data/Recipe.csv")));
            recipeCsvBeanBuilder.withType(recipeCsvBean.class);
            List<recipeCsvBean> recipeCSVBeans = recipeCsvBeanBuilder.withSkipLines(3).build().parse();
            for (recipeCsvBean rb : recipeCSVBeans){
                if (rb.itemResultID == itemResultID){
                    //System.out.println("Found recipe for target item in Recipe.csv!");
                    this.recipeLevel = rb.recipeLevel;
                    this.progressFactor = rb.progressFactor;
                    this.qualityFactor = rb.qualityFactor;
                    this.durabilityFactor = rb.durabilityFactor;
                    //rb.printBean();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try{
            CsvToBeanBuilder<recipeLevelTableCsvBean> recipeLevelTableBeanCsvToBeanBuilder =
                    new CsvToBeanBuilder<recipeLevelTableCsvBean>(new InputStreamReader(new FileInputStream("data/RecipeLevelTable.csv")));
            recipeLevelTableBeanCsvToBeanBuilder.withType(recipeLevelTableCsvBean.class);
            List<recipeLevelTableCsvBean> recipeLevelTableCsvBeans = recipeLevelTableBeanCsvToBeanBuilder.withSkipLines(3).build().parse();
            for (recipeLevelTableCsvBean rltb : recipeLevelTableCsvBeans){
                if (rltb.recipeLevel == this.recipeLevel){
                    //System.out.println("Found target recipeLevel in RecipeLevelTable.csv!");
                    this.classJobLevel = rltb.classJobLevel;
                    this.stars = rltb.stars;
                    this.suggestedCraftsmanship = rltb.suggestedCraftsmanship;
                    this.suggestedControl = rltb.suggestedControl;
                    this.defaultProgress = rltb.progress;
                    this.defaultQuality = rltb.quality;
                    this.defaultDurability = rltb.durability;
                    //rltb.printBean();
                    break;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        /*
        System.out.println("recipeLevel: " + this.recipeLevel);
        System.out.println("progressFactor: " + this.progressFactor);
        System.out.println("progress: " + calculateDifficulty());
        System.out.println("qualityFactor: " + this.qualityFactor);
        System.out.println("defaultQuality: " + this.defaultQuality);
        System.out.println("qualityFactor as %: " + (float)this.qualityFactor / 100);
        System.out.println("quality without flooring " + (float)this.qualityFactor / 100 * this.defaultQuality);

         */
        this.progress = calculateDifficulty();
        this.quality = calculateQuality();
        this.durability = calculateDurability();

    }



    private int calculateDifficulty(){
        return (int)(Math.floor((float)progressFactor / 100 * defaultProgress));
    }

    private int calculateQuality(){
        return (int)(Math.floor((float)qualityFactor / 100 * defaultQuality));
    }

    private int calculateDurability(){
        return (int)(Math.floor((float)durabilityFactor / 100 * defaultDurability));
    }
}
