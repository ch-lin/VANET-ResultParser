package ResultParser;

import java.util.Comparator;

public class FileListSort implements Comparator<String> {

	public int compare(String arg0, String arg1) {
		if (arg0.endsWith("tr"))
			return 0;
		if (arg1.endsWith("tr"))
			return 1;
		String a0 = arg0.substring(arg0.indexOf("_") + 1);
		a0 = a0.substring(a0.indexOf("_") + 1);
		a0 = a0.substring(0, a0.indexOf("_"));

		String a1 = arg1.substring(arg1.indexOf("_") + 1);
		a1 = a1.substring(a1.indexOf("_") + 1);
		a1 = a1.substring(0, a1.indexOf("_"));

		if (Integer.parseInt(a0) < Integer.parseInt(a1))
			return 0;
		else if (Integer.parseInt(a0) > Integer.parseInt(a1)) {
			return 1;
		} else {

			int ar0 = Integer.parseInt(arg0.substring(
					arg0.lastIndexOf("_") + 1, arg0.lastIndexOf(".")));
			int ar1 = Integer.parseInt(arg1.substring(
					arg1.lastIndexOf("_") + 1, arg1.lastIndexOf(".")));
			if (ar0 < ar1)
				return 0;
			else
				return 1;
		}
	}

}
