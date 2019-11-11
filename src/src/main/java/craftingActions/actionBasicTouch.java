package craftingActions;
import core.craftingAction;
import core.craftingProcess;

public class actionBasicTouch extends craftingAction {

    public actionBasicTouch(craftingProcess process){
        super();
        this.setActionName("Basic Touch");
        this.setCpCost(18);
        this.setDurabilityCost(10);
        this.setInnerQuietIncrease(1);
        this.setQualityEfficiency(100);
        this.setBuffMap(process.getBuffMap());

        if (canDoAction()) {
            process.handleManipulation();
            this.doAction();
            process.stepNext();
            process.printProcessCurrent();
        }
        else {
            System.out.println("Cannot perform action: " + this.getActionName());
        }
    }


    @Override
    public void doAction(){
        handleWasteNot();
        handleGreatStrides();
        handleInnerQuiet();

        int controlBonusTotal = super.calculateControlBonus();
        craftingProcess.setControlCurrent(craftingProcess.getControlDefault() + controlBonusTotal);

        int qualityIncreaseActual = (int)(Math.floor((float)this.qualityEfficiency / 100 * craftingProcess.calculateQualityIncreaseDefault()));
        int qualityCurrent = qualityIncreaseActual + craftingProcess.getQualityCurrent();

        if (qualityCurrent > craftingProcess.getQualityLimit()){
            craftingProcess.setQualityCurrent(craftingProcess.getQualityLimit());
        }
        else {
            craftingProcess.setQualityCurrent(qualityCurrent);
        }

        craftingProcess.setCpCurrent(craftingProcess.getCpCurrent() - this.getCpCost());
        craftingProcess.setDurabilityCurrent(craftingProcess.getDurabilityCurrent() - this.durabilityCost);
    }
}
