package input;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadExcel {

    public  void readExcel(String location, ArrayList<String> urls) {

        try {
            FileInputStream xlsxFile = new FileInputStream(new File(location));
            Workbook workbook = new XSSFWorkbook(xlsxFile);
            Sheet workSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = workSheet.iterator();

            while(iterator.hasNext()){
                Row currentRow = iterator.next();
                Cell cell1 = currentRow.getCell(0);
                String url = cell1.toString().trim();
                urls.add(url);
                System.out.println(url);

            }

            xlsxFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
