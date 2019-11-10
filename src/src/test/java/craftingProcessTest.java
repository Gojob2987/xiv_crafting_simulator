import org.junit.Test;

public class craftingProcessTest {
    /**
     * https://xivapi.com/search?string=Sandalwood%20Lumber
     * We know the item id for Sandalwood Lumber is 27694
     * Lets test it out
      */
    @Test
    public void testValidConstructor(){
        craftingCrafter crafter = new craftingCrafter(2142, 2029, 600, 80, "Carpenter");
        craftingRecipe recipe = new craftingRecipe(27694);
        craftingProcess process = new craftingProcess(crafter, recipe);
        System.out.println(process.calculateProgressIncreaseDefault());
        System.out.println(process.calculateQualityIncreaseDefault());

    }
}
