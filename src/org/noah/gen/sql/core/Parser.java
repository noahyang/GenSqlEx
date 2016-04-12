package org.noah.gen.sql.core;

import java.util.List;

import org.noah.gen.sql.bean.Field;
import org.noah.gen.sql.bean.FieldType;
import org.noah.gen.sql.utils.GsFunc;

import jxl.Sheet;

/**
 * @author noah.yang
 * 解析字符串（+:a,b$,c,e@,f@）
 * 关于 $@
 * $ 表示数值
 * @ 表示字符串
 */
public class Parser {
	// 左右分隔
	private static String SPLITTER = ":";
	// 正取反取
	private static String SOLU_POS = "+";
	private static String SOLU_NEG = "-";
	// 类型常量
	private static String TYPE_STR = "@";
	private static String TYPE_NUM = "$";
	// 构造函数
	public Parser() {}
	/**
	 * 将
	 * +:a,b$,c,e@,f@
	 * 分别翻译成（x 表示自动识别，既可能为 $，也可能为 @）
	 * 0x,1$,2x,4@,5@
	 * ----------*----------
	 * 将
	 * f-:b,c
	 * 翻译成
	 * 0x,3x,4x,5x
	 * @param list
	 * @param args
	 * @param sheet
	 * @param rowBegin
	 * @return
	 */
	public static String parse(List<Field> list, String args, Sheet sheet, int rowBegin) {
		// 检查字符串格式是否合法
		if (!args.contains(SPLITTER)) {
			return "Error: Have no \"" + SPLITTER + "\"";
		}
		// Lr: Left & Right
		String[] ssLr = args.split(SPLITTER);
		if (2 != ssLr.length) {
			return "Error: 2 != ssLr.length";
		}
		String strLeft = ssLr[0];
		String strRight = ssLr[1];
		if (!strLeft.contains(SOLU_POS) && !strLeft.contains(SOLU_NEG)) {
			return "Error: strLeft must be \"" + SOLU_POS + "\" or \"" + SOLU_NEG + "\"!";
		}
		// 判定是 “正取” 还是 “反取”
		if (strLeft.contains(SOLU_NEG)) {
			// “反取” 感觉意义不是很大，最近几年都不打算实现了
			return "Error: Unimplemented!";
		}
		String[] ssMt = strRight.split(",");
		// 安全检查
		for (String item : ssMt) {
			// 不能包含空串
			if ("" == item) {
				return "Error: \"\" == item";
			}
			// a~z，26 个字段还不够用？
			int length = item.length();
			if (2 < length) {
				return "Error: 2 < length";
			}
		}
		// 正式处理
		for (String item : ssMt) {
			if (1 == item.length()) {
				// 自动识别
				int num = getNumFromChar(item);
				String strDat = sheet.getCell(num, rowBegin).getContents();
				Float fValue = null;
				try {
					fValue = Float.valueOf(strDat);
				} catch (NumberFormatException e) {
//					e.printStackTrace();
				}
				FieldType type = FieldType.kFieldTypeStr;
				if (null != fValue) {
					type = FieldType.kFieldTypeNum;
				}
				list.add(new Field(num, type));
			} else {
				// 人为识别
				if (item.endsWith(TYPE_NUM)) {
					int num = getNumFromChar(item.replace(TYPE_NUM, ""));
					list.add(new Field(num, FieldType.kFieldTypeNum));
				} else if (item.endsWith(TYPE_STR)) {
					int num = getNumFromChar(item.replace(TYPE_STR, ""));
					list.add(new Field(num, FieldType.kFieldTypeStr));
				} else {
					return "Error: Have no \"" + TYPE_NUM + "\" and \"" + TYPE_STR + "\"!";
				}
			}
		}
		return "";
	}
	/**
	 * "a" => 0
	 * "b" => 1
	 * "c" => 2
	 * @param strChar
	 * @return
	 */
	private static int getNumFromChar(String strChar) {
		boolean flag = true;
		if (1 != strChar.length()) flag = false;
		char ch = strChar.charAt(0);
		if (ch < 'a' || ch > 'z') flag = false;
		System.out.print(flag ? "" : "Error: -1 == num" + GsFunc.getReturn());
		return flag ? (int)(ch - 'a') : -1;
	}
}
