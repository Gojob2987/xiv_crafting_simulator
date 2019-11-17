import core.craftingCrafter;
import core.craftingProcess;
import core.craftingRecipe;
import org.junit.Test;

public class craftingProcessTest {
    /**
     * https://xivapi.com/search?string=Sandalwood%20Lumber
     * We know the item id for Sandalwood Lumber is 27694
     * Lets test it out
      */
    @Test
    public void testValidConstructor1(){
        craftingCrafter crafter = new craftingCrafter(2142, 2029, 600, 80, "Carpenter");
        craftingRecipe recipe = new craftingRecipe(27694);
        craftingProcess process = new craftingProcess(crafter, recipe);
        System.out.println(process.calculateProgressIncreaseDefault());
        System.out.println(process.calculateQualityIncreaseDefault());

    }

    /**
     * https://xivapi.com/search?string=Lemon%20Curd
     * Stat comes from my character in game
     */
    @Test
    public void testValidConstructor2(){
        craftingCrafter crafter = new craftingCrafter(2403, 2392, 600, 80, "Culinarian");
        craftingRecipe recipe = new craftingRecipe(27890);
        craftingProcess process = new craftingProcess(crafter, recipe);
        System.out.println(process.calculateProgressIncreaseDefault());
        System.out.println(process.calculateQualityIncreaseDefault());

    }


    /**
     * Try to run Basic Touch till durability runs out
     * https://xivapi.com/search?string=Lignum%20Vitae%20Fishing%20Rod
     * Item id: 27150
     */
    @Test
    public void testBasicActionsI(){
        craftingCrafter crafter = new craftingCrafter(2142, 2029, 600, 80, "Carpenter");
        craftingRecipe recipe = new craftingRecipe(27150);
        craftingProcess process = new craftingProcess(crafter, recipe);
        process.doAction("Inner Quiet");
        process.doAction("Preparatory Touch");
        process.doAction("Preparatory Touch");

    }

}
