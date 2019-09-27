package org.openj9.test;

import com.ibm.oti.shared.SharedClassFilter;

public class StoreFilter implements SharedClassFilter{
	String filter = "org.openj9.test.ivj.Disk";

	public boolean acceptStore(String className) {
		if (className.indexOf(filter) == -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean acceptFind(String className) {
		if (className.indexOf(filter) == -1) {
			return true;
		} else {
			return false;
		}
	}

}