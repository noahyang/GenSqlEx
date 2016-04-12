package org.noah.gen.sql.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.noah.gen.sql.bean.MetaDesc;
import org.noah.gen.sql.utils.GsFunc;
import org.noah.gen.sql.utils.IOBridge;
import org.noah.gen.sql.utils.SqliteUtil;

/**
 * @author noah.yang
 * 核心逻辑
 */
public class ProcSql implements IProc {
	private static final String TOKEN_SPLIT = GsFunc.getReturnEx();
	private static final String SUFFIX_SPEC = ".desc";
	private static final String FILE_NAME_SPEC = "absPathDb.txt";
	private static String absPathDb = "";
	@Override
	public void process(String strArgs) {
		// 获取待处理的 desc 文件列表（可拽入单个 desc 文件也可拽入包含 desc 文件的目录）
		List<File> fileList = getFileList(strArgs);
		// 处理文件列表中的各 desc 文件条目
		walkFileList(fileList);
	}
	/**
	 * @param strArgs
	 * @return
	 */
	private List<File> getFileList(String strArgs) {
		File file = new File(strArgs);
		// 使能同时支持处理文件或文件夹
		List<File> fileList = new ArrayList<File>();
		if (file.isFile()) {
			// 拽入单个文件
			if (file.getName().endsWith(SUFFIX_SPEC)) {
				fileList.add(file);				
			}
		} else if (file.isDirectory()) {
			// 拽入单个文件夹
			for (File item : file.listFiles()) {
				if (item.isDirectory()) continue;
				if (item.getName().equals(FILE_NAME_SPEC)) {
					String strTemp = IOBridge.toString(item, "utf-8");
					File fDb = new File(strTemp);
					if (fDb.exists() && fDb.isFile()) absPathDb = strTemp;
					continue;
				}
				if (!item.getName().endsWith(SUFFIX_SPEC)) continue;
				fileList.add(item);
			}
		}
		return fileList;
	}
	/**
	 * @param fileList
	 */
	private void walkFileList(List<File> fileList) {
		// 初始化 SqliteUtil 对象
		SqliteUtil su = null;
		if (!absPathDb.equals("")) {
			su = new SqliteUtil(absPathDb);
			// 还原，否则扰乱下次逻辑的正常执行
			absPathDb = "";
		} else {
			System.out.println("Warn: absPathDb == \"\"");
		}
		// 处理
		StringBuilder sb = new StringBuilder();
		for (File item : fileList) {
			String strAbsPath = item.getAbsolutePath();
			System.out.println(strAbsPath);
			MetaDesc md = MetaDesc.create(strAbsPath);
			if (md == null) {
				System.out.println("Error: Failed to MetaDesc.create()!");
				continue;
			}
			String strDst = md.toSql();
			// 执行 sql 片段（分块执行执行是为了方便排错）
			if (su != null) su.execSql(strDst);
			// 追加
			sb.append(strDst).append(TOKEN_SPLIT);
		}
		// 关闭数据库连接
		if (su != null) su.closeConn();
		// 将成品 sql 置入系统剪贴板
		GsFunc.setContents(sb.toString());
	}
}
