import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import sun.reflect.CallerSensitive;

public class recipeBean {

    // Item{Result}
    @CsvBindByPosition(position = 4)
    public int itemResultID;

    // RecipeLevelTable
    @CsvBindByPosition(position = 3)
    public int recipeLevel;

    // DifficultyFactor
    @CsvBindByPosition(position = 28)
    public int difficultyFactor;

    // QualityFactor
    @CsvBindByPosition(position = 30)
    public int qualityFactor;

    // DurabilityFactor
    @CsvBindByPosition(position = 31)
    public int durabilityFactor;

    public void printBean(){
        System.out.println("itemResultID " + itemResultID);
        System.out.println("recipeLevel " + recipeLevel);
        System.out.println("difficultyFactor " + difficultyFactor);
        System.out.println("qualityFactor " + qualityFactor);
        System.out.println("durabilityFactor " + durabilityFactor);
    }
}
