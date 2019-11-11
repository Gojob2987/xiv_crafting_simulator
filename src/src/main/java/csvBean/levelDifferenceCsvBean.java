package csvBean;

import com.opencsv.bean.CsvBindByPosition;

public class levelDifferenceCsvBean {
    @CsvBindByPosition(position = 1)
    public int levelDifference;

    @CsvBindByPosition(position = 2)
    public int progressFactor;

    @CsvBindByPosition(position = 3)
    public int qualityFactor;

    public void printBean(){
        System.out.println("levelDifference " + levelDifference);
        System.out.println("progressFactor" + progressFactor);
        System.out.println("qualityFactor" + qualityFactor);
    }
}
