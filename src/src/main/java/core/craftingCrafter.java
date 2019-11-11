package core;

import csvBean.crafterLevelTableCsvBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

public class craftingCrafter {
    private String job;
    private int craftsmanship, control, craftingPoint, playerLevel;
    private int crafterLevel;



    public int getCraftsmanship() {
        return craftsmanship;
    }


    public int getControl() {
        return control;
    }


    public int getCraftingPoint() {
        return craftingPoint;
    }


    public int getCrafterLevel() {
        return crafterLevel;
    }

    public void setCrafterLevel(int crafterLevel) {
        this.crafterLevel = crafterLevel;
    }

    public craftingCrafter(){
        this.craftsmanship = 0;
        this.control = 0;
        this.craftingPoint = 0;
        this.playerLevel = 0;
        this.crafterLevel = 0;
        this.job = "";
    }
    public craftingCrafter(int craftsmanship, int control, int craftingPoint, int playerLevel, String job){
        this.craftsmanship = craftsmanship;
        this.control = control;
        this.craftingPoint = craftingPoint;
        this.playerLevel = playerLevel;
        this.crafterLevel = calculateCrafterLevel();
        this.job = job;
    }

    /**
     * Search data/CrafterLevelTable.csv and return corresponding crafterLevel for given playerLevel
     * @return crafterLevel
     */
    private int calculateCrafterLevel(){
        try{
            CsvToBeanBuilder<crafterLevelTableCsvBean> crafterLevelTableCsvBeanCsvToBeanBuilder =
                    new CsvToBeanBuilder<crafterLevelTableCsvBean>(new InputStreamReader(new FileInputStream("data/CrafterLevelTable.csv")));
            crafterLevelTableCsvBeanCsvToBeanBuilder.withType(crafterLevelTableCsvBean.class);
            List<crafterLevelTableCsvBean> crafterLevelTableCsvBeans = crafterLevelTableCsvBeanCsvToBeanBuilder.withSkipLines(3).build().parse();
            for (crafterLevelTableCsvBean cltb : crafterLevelTableCsvBeans){
                if (cltb.playerLevel == this.playerLevel){
                    System.out.println("Found target playerLevel in CrafterLevelTable.csv!");
                    return cltb.crafterLevel;
                }
            }

        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
