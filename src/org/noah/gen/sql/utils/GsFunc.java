package org.noah.gen.sql.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Properties;

/**
 * @author noah.yang
 * grep -i '\\n' `find . -name "*.java"`
 */
public class GsFunc {
	private enum SysType {
		kSysTypeUnix,
		kSysTypeWin,
	}
	private static SysType SYS_TYPE = SysType.kSysTypeUnix;
	static {
		Properties prop = System.getProperties();
		String strName = prop.getProperty("os.name");
		System.out.println(strName);
		if (strName.startsWith("win") || strName.startsWith("Win")) {
			SYS_TYPE = SysType.kSysTypeWin;
		}
	}
	
	/**
	 * 获取对应系统的换行符
	 * @return
	 */
	public static String getReturn() {
		return SYS_TYPE == SysType.kSysTypeUnix ? "\n" : "\r\n";
	}
	public static String getReturnEx() {
		return SYS_TYPE == SysType.kSysTypeUnix ? "\n\n" : "\r\n\r\n";
	}
	
	/**
	 * 将窗口对象移动到屏幕中心
	 * @param comp
	 */
	public static void moveCompToScreenCenter(Component comp) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)(dim.getWidth() - comp.getWidth()) / 2;
		int y = (int)(dim.getHeight() - comp.getHeight()) / 2;
		comp.setLocation(x, y);
	}
	
	/**
	 * 将字符串设置到系统剪贴板
	 * @param strArg
	 */
	public static void setContents(String strArg) {
		Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard clipboard = tk.getSystemClipboard();
        Transferable tStr = new StringSelection(strArg);
        clipboard.setContents(tStr, null);
	}
}
