package csvBean;

import com.opencsv.bean.CsvBindByPosition;

public class recipeCsvBean {

    // Item{Result}
    @CsvBindByPosition(position = 4)
    public int itemResultID;

    // RecipeLevelTable
    @CsvBindByPosition(position = 3)
    public int recipeLevel;

    // DifficultyFactor
    @CsvBindByPosition(position = 29)
    public int progressFactor;

    // QualityFactor
    @CsvBindByPosition(position = 30)
    public int qualityFactor;

    // DurabilityFactor
    @CsvBindByPosition(position = 31)
    public int durabilityFactor;

    public void printBean(){
        System.out.println("itemResultID " + itemResultID);
        System.out.println("recipeLevel " + recipeLevel);
        System.out.println("progressFactor " + progressFactor);
        System.out.println("qualityFactor " + qualityFactor);
        System.out.println("durabilityFactor " + durabilityFactor);
    }
}
