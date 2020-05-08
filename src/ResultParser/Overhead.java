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

public class Overhead extends Parser {
	public Overhead(String path) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(getOutputPath(path, "_overhead.xls"));

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
						c.setCellValue("Control send");
						c = r.createCell(3);
						c.setCellValue("Control size");
						c = r.createCell(4);
						c.setCellValue("broadcast send");
						c = r.createCell(5);
						c.setCellValue("broadcast size");
						c = r.createCell(6);
						c.setCellValue("hello send");
						c = r.createCell(7);
						c.setCellValue("hello size");
						c = r.createCell(8);
						c.setCellValue("Total send");
						c = r.createCell(9);
						c.setCellValue("Total size");

						int c_send_t = 0, c_size_t = 0, b_send_t = 0, b_size_t = 0, h_send_t = 0, h_size_t = 0, t_send_t = 0, t_size_t = 0, conte_t = 0;
						for (String subname : subfiles) {

							int c_send = 0, c_size = 0, b_send = 0, b_size = 0, h_send = 0, h_size = 0;
							if (subname.startsWith("overhead")) {
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
										c.setCellValue((double) c_send_t
												/ conte_t);
										c_send_t = 0;
										c = r.createCell(3);
										c.setCellValue((double) c_size_t
												/ conte_t);
										c_size_t = 0;

										c = r.createCell(4);
										c.setCellValue((double) b_send_t
												/ conte_t);
										b_send_t = 0;
										c = r.createCell(5);
										c.setCellValue((double) b_size_t
												/ conte_t);
										b_size_t = 0;

										c = r.createCell(6);
										c.setCellValue((double) h_send_t
												/ conte_t);
										h_send_t = 0;
										c = r.createCell(7);
										c.setCellValue((double) h_size_t
												/ conte_t);
										h_size_t = 0;

										c = r.createCell(8);
										c.setCellValue((double) t_send_t
												/ conte_t);
										t_send_t = 0;
										c = r.createCell(9);
										c.setCellValue((double) t_size_t
												/ conte_t);
										t_size_t = 0;

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
									if (in.startsWith("CONTROL OVERHEAD")) {
										String send = in = bufReader.readLine();
										if (send != null) {
											c = r.createCell(2);
											send = send.substring(send
													.indexOf(":") + 1);
											c_send = Integer.parseInt(send);
											c_send_t += c_send;
											c.setCellValue(c_send);
										}
										String size = in = bufReader.readLine();
										if (size != null) {
											c = r.createCell(3);
											size = size.substring(size
													.indexOf(":") + 1);
											c_size = Integer.parseInt(size);
											c_size_t += c_size;
											c.setCellValue(c_size);
										}
									}
									if (in.startsWith("BROADCAST OVERHEAD")) {
										String send = in = bufReader.readLine();
										if (send != null) {
											c = r.createCell(4);
											send = send.substring(send
													.indexOf(":") + 1);
											b_send = Integer.parseInt(send);
											b_send_t += b_send;
											c.setCellValue(b_send);
										}
										String size = in = bufReader.readLine();
										if (size != null) {
											c = r.createCell(5);
											size = size.substring(size
													.indexOf(":") + 1);
											b_size = Integer.parseInt(size);
											b_size_t += b_size;
											c.setCellValue(b_size);
										}
									}
									if (in.startsWith("HELLO OVERHEAD")) {
										String send = in = bufReader.readLine();
										if (send != null) {
											c = r.createCell(6);
											send = send.substring(send
													.indexOf(":") + 1);
											h_send = Integer.parseInt(send);
											h_send_t += h_send;
											c.setCellValue(h_send);
										}
										String size = in = bufReader.readLine();
										if (size != null) {
											c = r.createCell(7);
											size = size.substring(size
													.indexOf(":") + 1);
											h_size = Integer.parseInt(size);
											h_size_t += h_size;
											c.setCellValue(h_size);
										}
									}
								}
								c = r.createCell(8);
								t_send_t += c_send + b_send + h_send;
								c.setCellValue(c_send + b_send + h_send);
								c = r.createCell(9);
								t_size_t += c_size + b_size + h_size;
								c.setCellValue(c_size + b_size + h_size);
								bufReader.close();
							}
						}

						if (conte_t != 0) {
							r = s.createRow(rownum++);
							c = r.createCell(2);
							c.setCellValue((double) c_send_t / conte_t);
							c_send_t = 0;
							c = r.createCell(3);
							c.setCellValue((double) c_size_t / conte_t);
							c_size_t = 0;

							c = r.createCell(4);
							c.setCellValue((double) b_send_t / conte_t);
							b_send_t = 0;
							c = r.createCell(5);
							c.setCellValue((double) b_size_t / conte_t);
							b_size_t = 0;

							c = r.createCell(6);
							c.setCellValue((double) h_send_t / conte_t);
							h_send_t = 0;
							c = r.createCell(7);
							c.setCellValue((double) h_size_t / conte_t);
							h_size_t = 0;

							c = r.createCell(8);
							c.setCellValue((double) t_send_t / conte_t);
							t_send_t = 0;
							c = r.createCell(9);
							c.setCellValue((double) t_size_t / conte_t);
							t_size_t = 0;

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
