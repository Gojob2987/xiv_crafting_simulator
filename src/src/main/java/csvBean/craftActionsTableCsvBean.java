package csvBean;

import com.opencsv.bean.CsvBindByPosition;

public class craftActionsTableCsvBean {

    // Name
    @CsvBindByPosition(position = 1)
    public String name;

    // CostCP
    @CsvBindByPosition(position = 2)
    public int costCP;

    // CostDurability
    @CsvBindByPosition(position = 3)
    public int costDurability;


    // ProgressEfficiency
    @CsvBindByPosition(position = 4)
    public int progressEfficiency;

    // QualityEfficiency
    @CsvBindByPosition(position = 5)
    public int qualityEfficiency;

    // IncreaseIQ
    @CsvBindByPosition(position = 6)
    public int increaseIQ;

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


    public void printBean(){
        System.out.println("name " + name);
        System.out.println("costCP " + costCP);
        System.out.println("costDurability " + costDurability);
        System.out.println("progressEfficiency " + progressEfficiency);
        System.out.println("qualityEfficiency " + qualityEfficiency);
        System.out.println("increaseIQ " + increaseIQ);
        System.out.println("successRate " + successRate);
        System.out.println("isWait3 " + isWait3);
        System.out.println("isFirstStep " + isFirstStep);
        System.out.println("duration " + duration);
    }

}
