package csvBean;

import com.opencsv.bean.CsvBindByPosition;

public class recipeLevelTableCsvBean {
    // key
    @CsvBindByPosition(position = 0)
    public int recipeLevel;
    // ClassJobLevel
    @CsvBindByPosition(position = 1)
    public int classJobLevel;
    // Stars
    @CsvBindByPosition(position = 2)
    public int stars;
    // SuggestedCraftsmanship
    @CsvBindByPosition(position = 3)
    public int suggestedCraftsmanship;
    // SuggestedControl
    @CsvBindByPosition(position = 4)
    public int suggestedControl;
    // Difficulty
    @CsvBindByPosition(position = 5)
    public int progress;
    // Quality
    @CsvBindByPosition(position = 6)
    public int quality;
    // Durability
    @CsvBindByPosition(position = 7)
    public int durability;

    public void printBean(){
        System.out.println("recipeLevel " + recipeLevel);
        System.out.println("classJobLevel " + classJobLevel);
        System.out.println("stars " + stars);
        System.out.println("suggestedCraftsmanship " + suggestedCraftsmanship);
        System.out.println("suggestedControl " + suggestedControl);
        System.out.println("progress " + progress);
        System.out.println("quality " + quality);
        System.out.println("durability " + durability);
    }
}
