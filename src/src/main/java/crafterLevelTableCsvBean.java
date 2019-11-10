import com.opencsv.bean.CsvBindByPosition;

public class crafterLevelTableCsvBean {
    // PlayerLevel
    @CsvBindByPosition(position = 1)
    public int playerLevel;

    // CrafterLevel
    @CsvBindByPosition(position = 2)
    public int crafterLevel;

    public void printBean(){
        System.out.println("playerLevel " + playerLevel);
        System.out.println("crafterLevel " + crafterLevel);
    }
}
