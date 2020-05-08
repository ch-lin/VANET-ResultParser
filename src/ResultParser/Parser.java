package ResultParser;

import java.io.File;

public class Parser {

	public String getOutputPath(String path, String filename) {
		String output = path.substring(0,
				path.lastIndexOf(System.getProperty("file.separator")))
				+ System.getProperty("file.separator")
				+ "Results"
				+ System.getProperty("file.separator");
		File f = new File(output);
		if (!f.exists())
			f.mkdirs();
		return output
				+ path.substring(path.lastIndexOf(System
						.getProperty("file.separator")) + 1) + filename;
	}

	public String getPrefix(String path) {
		return path.substring(path.lastIndexOf(System
				.getProperty("file.separator")) + 1);
	}

}
