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

public class Delivery extends Parser {
	public Delivery(String path) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(getOutputPath(path, "_delivery.xls"));

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
						c.setCellValue("Send");
						c = r.createCell(3);
						c.setCellValue("Recv");
						c = r.createCell(4);
						c.setCellValue("Rate(%)");

						int send_t = 0, recv_t = 0, conte_t = 0;
						double rate_t = 0.0;
						for (String subname : subfiles) {

							if (subname.startsWith("delivery")) {
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
									if (conte_t != 0) {
										c = r.createCell(2);
										c.setCellValue((double) send_t
												/ conte_t);
										send_t = 0;
										c = r.createCell(3);
										c.setCellValue((double) recv_t
												/ conte_t);
										recv_t = 0;
										c = r.createCell(4);
										c.setCellValue((double) rate_t
												/ conte_t);
										rate_t = 0;
										conte_t = 0;
										r = s.createRow(rownum++);
									}
									nodenum = num;
									r = s.createRow(rownum++);
									c = r.createCell(0);
									c.setCellValue(Integer.parseInt(nodenum));

								}
								conte_t++;

								c = r.createCell(1);
								c.setCellValue(Integer.parseInt(ser));
								String in = null;
								while ((in = bufReader.readLine()) != null) {
									if (in.startsWith("Send")) {
										String send = in.substring(in
												.indexOf(":") + 1);
										c = r.createCell(2);
										int send_i = Integer.parseInt(send);
										send_t += send_i;
										c.setCellValue(send_i);
									} else if (in.startsWith("Recv")) {
										String recv = in.substring(in
												.indexOf(":") + 1);
										c = r.createCell(3);
										int recv_i = Integer.parseInt(recv);
										recv_t += recv_i;
										c.setCellValue(recv_i);
									} else if (in.startsWith("Rate")) {
										String rate = in.substring(in
												.indexOf(":") + 1);
										c = r.createCell(4);
										rate = rate.substring(0,
												rate.lastIndexOf("%"));
										double rate_i = Double
												.parseDouble(rate);
										rate_t += rate_i;
										c.setCellValue(rate_i);
									}
								}
								bufReader.close();
							}
						}

						if (conte_t != 0) {
							r = s.createRow(rownum++);
							c = r.createCell(2);
							c.setCellValue((double) send_t / conte_t);
							send_t = 0;
							c = r.createCell(3);
							c.setCellValue((double) recv_t / conte_t);
							recv_t = 0;
							c = r.createCell(4);
							c.setCellValue((double) rate_t / conte_t);
							rate_t = 0;
							conte_t = 0;
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
