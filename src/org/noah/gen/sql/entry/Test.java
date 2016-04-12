package org.noah.gen.sql.entry;

import org.noah.gen.sql.core.ProcSql;


/**
 * @author noah.yang
 * 测试功能
 */
public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String searchPath = "./usage";
		ProcSql ps = new ProcSql();
		ps.process(searchPath);
	}
}
