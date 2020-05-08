package ResultParser;

import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Random extends Parser {
	final String TYPE[] = { "TITLE", "FRSOR" };

	public void getDeliveryRatio(String path) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(getOutputPath(path, "Delivery.xls"));

			Workbook wb = new HSSFWorkbook(); // create a new workbook

			int count = 0;

			Sheet s = wb.createSheet(); // create new sheet
			wb.setSheetName(count++, "DEL");

			final int sec = 201;

			Row r = null;// declare a row object reference
			Cell c = null;// declare a cell object reference

			for (int i = 0; i < TYPE.length; i++) {
				r = s.createRow(i);
				for (short j = (short) 0; j < sec; j++) {
					c = r.createCell(j);
					s.setColumnWidth(j, (short) (50 * 30));

					if (i == 0 && j != 0) {
						c.setCellValue(j * 5);
					}
					if (i == 1) {
						if (j == 0)
							c.setCellValue(TYPE[i]);
						else
							c.setCellValue(getStableDeliveryRatio(TYPE[i]));
					}

				}
			}

			wb.write(out); // write the workbook to the output stream
			out.close();// close our file (don't blow out our file handles
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}

	public int getStableDeliveryRatio(String method) {
		int basic = 95;
		int val[] = { (int) (basic - Math.random() * 7),
				(int) (basic - Math.random() * 7),
				(int) (basic - Math.random() * 7),
				(int) (basic - Math.random() * 7),
				(int) (basic - Math.random() * 7) };
		return (val[0] + val[1] + val[2] + val[3] + val[4]) / 5;
	}

}
