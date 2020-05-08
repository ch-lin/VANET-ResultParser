package ResultParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Delay extends Parser {
	public Delay(String path) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(getOutputPath(path, "_delay.xls"));

			Workbook wb = new HSSFWorkbook(); // create a new workbook

			String separate = System.getProperty("file.separator");
			File dir = new File(path);
			if (dir.isDirectory()) {
				int count = 0;
				String files[] = dir.list();
				for (String name : files) {
					Sheet s = wb.createSheet(); // create new sheet
					wb.setSheetName(count++, name);

					File subdir = new File(path + separate + name);
					if (subdir.isDirectory()) {

						String subfiles[] = subdir.list();
						Arrays.sort(subfiles, new FileListSort());

						int rownum = 0;
						String nodenum = "";

						Row r = null;// declare a row object reference
						Cell c = null;// declare a cell object reference

						r = s.createRow(rownum);
						rownum++;
						c = r.createCell(0);
						c.setCellValue("Node num");
						c = r.createCell(1);
						c.setCellValue("Serial");
						c = r.createCell(2);
						c.setCellValue("delay");

						int conte_all = 0;
						double delay_all = 0;
						for (String subname : subfiles) {

							if (subname.startsWith("delay")) {
								r = s.createRow(rownum++);

								BufferedReader bufReader = new BufferedReader(
										new FileReader(path + separate + name
												+ separate + subname));

								String num = subname.substring(subname
										.indexOf("_") + 1);
								String ser = num;

								ser = ser.substring(ser.lastIndexOf("_") + 1,
										ser.lastIndexOf("."));
								num = num.substring(num.indexOf("_") + 1,
										num.lastIndexOf("_"));

								if (!nodenum.equalsIgnoreCase(num)) {
									if (conte_all != 0) {
										c = r.createCell(2);
										c.setCellValue(delay_all / conte_all);
										delay_all = 0;
										conte_all = 0;
										r = s.createRow(rownum++);
									}
									nodenum = num;
									r = s.createRow(rownum++);
									c = r.createCell(0);
									c.setCellValue(Integer.parseInt(nodenum));

								}
								conte_all++;

								c = r.createCell(1);
								c.setCellValue(Integer.parseInt(ser));
								String in = null;
								int counter = 0, now = 0, counter2 = 0, column = 4;
								double total_delay = 0, now_delay = 0;
								while ((in = bufReader.readLine()) != null) {

									if (!in.startsWith("DELAY")) {
										int index = (int) Double.parseDouble(in
												.substring(0, in.indexOf(" ")));

										String data = in.substring(in
												.indexOf(" ") + 1);
										double delay = Double.parseDouble(data);
										total_delay += delay;
										counter++;

										if ((index / 5) != now) {
											if (now != 0) {
												c = r.createCell(column++);
												c.setCellValue(now_delay
														/ counter2);
											}
											now = ((int) (index / 5));
											now_delay = 0;
											counter2 = 0;
										} else {
											counter2++;
											now_delay += delay;
										}
									}
								}
								c = r.createCell(2);
								c.setCellValue(total_delay / counter);
								delay_all += total_delay / counter;
								bufReader.close();
							}
						}
						if (conte_all != 0) {
							r = s.createRow(rownum++);
							c = r.createCell(2);
							c.setCellValue(delay_all / conte_all);

							delay_all = 0;
							conte_all = 0;
							r = s.createRow(rownum++);
						}
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
}
