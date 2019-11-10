import com.opencsv.*;
import com.opencsv.bean.CsvToBeanBuilder;


import java.io.*;
import java.util.List;

public class craftingRecipe {
    public String recipeName;
    public int classJobLevel, stars, suggestedCraftsmanship, suggestedControl, difficulty, quality, durability;
    public int recipeLevel, difficultyFactor, qualityFactor, durabilityFactor;

    /**
     * Given the item you want to craft, retrieve related recipe information from Recipe.csv and RecipeLevelTable.csv
     * @param itemResultID
     */
    public craftingRecipe(int itemResultID) throws FileNotFoundException {
        try {
            CsvToBeanBuilder<recipeBean> recipeBeanBuilder = new CsvToBeanBuilder(new InputStreamReader(new FileInputStream("data/Recipe.csv")));
            recipeBeanBuilder.withType(recipeBean.class);
            List<recipeBean> recipeBeans = recipeBeanBuilder.withSkipLines(3).build().parse();
            for (recipeBean rb : recipeBeans){
                if (rb.itemResultID == itemResultID){
                    System.out.print("Found target!");
                    rb.printBean();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

}
