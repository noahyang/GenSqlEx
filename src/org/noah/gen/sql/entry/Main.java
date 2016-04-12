package org.noah.gen.sql.entry;

import org.noah.gen.sql.core.ProcSql;
import org.noah.gen.sql.gui.MainView;

/**
 * @author noah.yang
 * 程序入口
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainView(new ProcSql(), ".desc");
	}
}
