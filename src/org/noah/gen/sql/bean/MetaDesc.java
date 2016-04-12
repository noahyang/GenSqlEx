package org.noah.gen.sql.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.noah.gen.sql.core.Parser;
import org.noah.gen.sql.utils.GsFunc;
import org.noah.gen.sql.utils.IOBridge;

/**
 * @author noah.yang
 * 单张表的描述
 */
public class MetaDesc {
	private static String BANNER = "----------*----------*----------*----------";
	// 成员变量
	private String strXlsFilePath; // 配置表格的绝对路径
	private String strSheetName; // 目标表单的名字
	private String strTableName; // 对应数据库表名
	private List<Field> ltField = new ArrayList<Field>(); // 索引及类型数据
	private int iRowBegin; // 起始行
	private int iRowEnd; // 结束行
	/**
	 * 
	 */
	private MetaDesc() {}
	/**
	 * @param strDescFileAbsPath
	 * @return
	 */
	public static MetaDesc create(String strDescPath) {
		// 格式检查
		List<String> strList = new ArrayList<String>();
		if (!MetaDesc.check(strList, strDescPath)) {
			return null;
		}
		// 创建对象
		String[] strArr = strList.get(3).split(",");
		MetaDesc md = new MetaDesc();
		md.strXlsFilePath = strList.get(0); // xls 文件的绝对路径
		md.strSheetName = strList.get(1); // 表单的名称
		md.strTableName = strList.get(2); // 数据库表名
		md.iRowBegin = Integer.valueOf(strArr[0]); // 起始行
		md.iRowEnd = Integer.valueOf(strArr[1]); // 终止行
		md.fillList(strList.get(4)); // 类型映射
		return md;
	}
	private static boolean check(List<String> strList, String strDescPath) {
		File fDest = new File(strDescPath);
		String strFileData = IOBridge.toString(fDest, "utf-8");
		// 调试打印
		System.out.println(BANNER);
		System.out.println(strFileData);
		System.out.println(BANNER);
		// 跨平台的行分割
		String strToken = strFileData.contains("\r\n") ? "\r\n" : "\n";
		String[] strArray = strFileData.split(strToken);
		// 过滤空行、注释行
		for (String strItem : strArray) {
			if (strItem.equals("")) continue;
			if (strItem.startsWith("#")) continue;
			strList.add(strItem);
		}
		// 格式检查
		if (5 != strList.size()) {
			System.out.println("5 != strList.size()");
			return false;
		}
		String[] strArr = strList.get(3).split(",");
		if (2 != strArr.length) {
			System.out.println("2 != strArr.length");
			return false;
		}
		return true;
	}
	/**
	 * @param strArgs
	 * @return
	 */
	private void fillList(String strArgs) {
		// 读取 xls 文件，获取目标表单
		Workbook book = this.getWorkbook(strXlsFilePath);
		Sheet sheet = this.getSheet(book, strSheetName);
		// 先翻译成便于处理的串
		String strLog = Parser.parse(ltField, strArgs, sheet, iRowBegin);
		book.close();
		// 输出日志
		if ("" != strLog) {
			System.out.println(strLog);
			System.exit(0);
		}
	}
	public String toSql() {
		// 初始化或清空
		StringBuilder sbSql = new StringBuilder();
		// 读取 xls 文件，获取目标表单
		Workbook book = this.getWorkbook(strXlsFilePath);
		Sheet sheet = this.getSheet(book, strSheetName);
		// 处理目标表单
		sbSql.append("DELETE FROM ").append(strTableName).append(";");
		sbSql.append(GsFunc.getReturn());
		for (int row = iRowBegin - 1; row < iRowEnd; row ++) {
			sbSql.append("INSERT INTO ").append(strTableName).append(" VALUES(");
			for (Field item : ltField) {
				int index = item.getIndex();
				FieldType type = item.getType();
				Cell cell = sheet.getCell(index, row);
//				System.out.println(row + " " + key.intValue());
				if (FieldType.kFieldTypeNum == type) {
					sbSql.append(cell.getContents()).append(",");
				} else if (FieldType.kFieldTypeStr == type) {
					sbSql.append("'").append(cell.getContents()).append("',");
				}
			}
			sbSql.deleteCharAt(sbSql.length() - 1);
			sbSql.append(");").append(GsFunc.getReturn());
		}
		sbSql.deleteCharAt(sbSql.length() - 1);
//		System.out.println(sbSql.toString());
		book.close();
		return sbSql.toString();
	}
	private Workbook getWorkbook(String strAbsPath) {
		Workbook book = null;
		try {
			File file = new File(strXlsFilePath);
			book = Workbook.getWorkbook(file);
		} catch (Exception e) {
			System.err.println(strXlsFilePath + " 读取出错");
		}
		return book;
	}
	private Sheet getSheet(Workbook book, String sheetName) {
		Sheet target = null;
		for (Sheet item : book.getSheets()) {
//			System.out.println(item.getName());
			if (item.getName().equals(strSheetName)) {
				target = item;
				break;
			}
		}
		return target;
	}
}
