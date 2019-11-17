package csvBean;

import com.opencsv.bean.CsvBindByPosition;

public class craftActionsTableCsvBean {

    // Name
    @CsvBindByPosition(position = 1)
    public String name;

    // CPCost
    @CsvBindByPosition(position = 2)
    public int cpCost;

    // DurabilityCost
    @CsvBindByPosition(position = 3)
    public int durabilityCost;


    // ProgressEfficiency
    @CsvBindByPosition(position = 4)
    public int progressEfficiency;

    // QualityEfficiency
    @CsvBindByPosition(position = 5)
    public int qualityEfficiency;

    // InnerQuietIncrease
    @CsvBindByPosition(position = 6)
    public int innerQuietIncrease;

    // SuccessRate
    @CsvBindByPosition(position = 7)
    public int successRate;

    // IsWait3
    @CsvBindByPosition(position = 8)
    public boolean isWait3;

    // IsFirstStep
    @CsvBindByPosition(position = 9)
    public boolean isFirstStep;

    // Duration
    @CsvBindByPosition(position = 10)
    public int duration;

    // GiveBuff
    @CsvBindByPosition(position = 11)
    public boolean giveBuff;

    // BuffName
    @CsvBindByPosition(position = 12)
    public String buffName;


    public void printBean(){
        System.out.println("name " + name);
        System.out.println("cpCost " + cpCost);
        System.out.println("durabilityCost " + durabilityCost);
        System.out.println("progressEfficiency " + progressEfficiency);
        System.out.println("qualityEfficiency " + qualityEfficiency);
        System.out.println("innerQuietIncrease " + innerQuietIncrease);
        System.out.println("successRate " + successRate);
        System.out.println("isWait3 " + isWait3);
        System.out.println("isFirstStep " + isFirstStep);
        System.out.println("duration " + duration);
        System.out.println("giveBuff " + giveBuff);
        System.out.println("buffName " + buffName);
    }

}
