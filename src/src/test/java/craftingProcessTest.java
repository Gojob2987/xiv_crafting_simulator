import core.craftingAction;
import core.craftingCrafter;
import core.craftingProcess;
import core.craftingRecipe;
import craftingActions.actionBasicTouch;
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
        actionBasicTouch touch1 = new actionBasicTouch(process);
        actionBasicTouch touch2 = new actionBasicTouch(process);
        actionBasicTouch touch3 = new actionBasicTouch(process);


    }

}
