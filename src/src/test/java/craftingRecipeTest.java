import org.junit.Test;

import java.io.FileNotFoundException;

public class craftingRecipeTest {
    @Test
    public void testValidConstructor() throws FileNotFoundException {
        craftingRecipe testRecipe = new craftingRecipe(5056);
        return;
    }

    @Test
    public void testCalculateActualProgressAndQuality() throws FileNotFoundException{
        craftingRecipe testRecipe = new craftingRecipe(27694); // Sandalwood Lumber
        System.out.println(testRecipe.getProgress());
        System.out.println(testRecipe.getQuality());
        System.out.println(testRecipe.getDurability());
    }

}
