package output;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExcel {

    public void setCellValues(XSSFSheet sheet, int i, String league,
                              String dateS, String homeTeamS, String awayTeamS,
                              String allHomeMins, String allAwayMins){

        Row row = sheet.createRow(i);
        Cell cell0 = row.createCell(0);
        Cell cell1 = row.createCell(1);
        Cell cell2 = row.createCell(2);
        Cell cell3 = row.createCell(3);
        Cell cell4 = row.createCell(4);
        Cell cell5 = row.createCell(5);


        cell0.setCellValue(league);
        cell1.setCellValue(dateS);
        cell2.setCellValue(homeTeamS);
        cell3.setCellValue(awayTeamS);
        cell4.setCellValue(allHomeMins);
        cell5.setCellValue(allAwayMins);

    }

    public void writeFile(XSSFWorkbook workbookk, String outputPath){
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            workbookk.write(outputStream);
        } catch (IndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
        }
    }


}
