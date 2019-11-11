package core;

import java.util.HashMap;

public class craftingAction {
    //protected static craftingProcess process;
    protected String actionName;
    protected int duration, cpCost, durabilityCost, qualityEfficiency, progressEfficiency;
    protected double successRate;
    protected int innerQuietIncrease;
    protected HashMap<String, Integer> buffMap;




    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getCpCost(){
        return cpCost;
    }
    public void setCpCost(int cpCost){
        this.cpCost = cpCost;
    }


    public int getDurabilityCost() {
        return durabilityCost;
    }

    public void setDurabilityCost(int durabilityCost) {
        this.durabilityCost = durabilityCost;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public int getQualityEfficiency() {
        return qualityEfficiency;
    }

    public void setQualityEfficiency(int qualityEfficiency) {
        this.qualityEfficiency = qualityEfficiency;
    }

    public int getProgressEfficiency() {
        return progressEfficiency;
    }

    public void setProgressEfficiency(int progressEfficiency) {
        this.progressEfficiency = progressEfficiency;
    }

    public int getInnerQuietIncrease() {
        return innerQuietIncrease;
    }

    public void setInnerQuietIncrease(int innerQuietIncrease) {
        this.innerQuietIncrease = innerQuietIncrease;
    }

    public void setBuffMap(HashMap<String, Integer> buffMap){
        this.buffMap = buffMap;
    }


    public craftingAction(){
        actionName = "Blank action";
        duration = 0;
        successRate = 1;
        cpCost = 0;
        durabilityCost = 0;
        qualityEfficiency = 0;
        progressEfficiency = 0;
        innerQuietIncrease = 0;
        buffMap = null;
    }

    public boolean canDoAction(){
        return craftingProcess.getCpCurrent() >= this.getCpCost();
    }

    public void doAction(){
    }

    public int calculateControlBonus(){
        int controlBonusFromIQ = 0;
        int controlBonusFromIN = 0;

        if (buffMap.containsKey("Inner Quiet")){
            int innerQuietStack = buffMap.get("Inner Quiet");
            controlBonusFromIQ = (int) Math.floor((float)craftingProcess.getControlDefault() * (0.2 * (innerQuietStack - 1)));
        }

        if (buffMap.containsKey("Innovation")){
            controlBonusFromIN = (int) Math.floor((float)craftingProcess.getControlDefault() * 0.5);
        }

        int controlBonusTotal = controlBonusFromIN + controlBonusFromIQ;

        if (controlBonusTotal > 3000){
            controlBonusTotal = 3000;
        }

        return controlBonusTotal;
    }


    public void handleWasteNot(){
        if (buffMap.containsKey("Waste Not") || buffMap.containsKey("Waste Not II")){
            this.durabilityCost = durabilityCost / 2;
        }
    }

    public void handleGreatStrides(){
        if (buffMap.containsKey("Great Strides")){
            this.qualityEfficiency += 100;
            buffMap.remove("Great Strides");
        }
    }

    public void handleInnerQuiet(){
        if (buffMap.containsKey("Inner Quiet")){
            int innerQuietStack = buffMap.get("Inner Quiet") + this.innerQuietIncrease;

            if (innerQuietStack >= 11){
                buffMap.put("Inner Quiet", 11);
            }
            else{
                buffMap.put("Inner Quiet", innerQuietStack);
            }
        }
    }

}

