package org.noah.gen.sql.utils;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

/**
 * @author noah.yang 2013.0206.1135，
 * commons-io 库的性能很强劲，自己写的和它真是没法比，果断换！
 */
public class IOBridge {

	/**
	 * @param f
	 * @return
	 */
	public static byte[] toByteArray(File f) {
		byte[] target = null;
		try {
			InputStream is = new FileInputStream(f);
			target = IOUtils.toByteArray(is);
			IOUtils.closeQuietly(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
	}

	/**
	 * @param filePath
	 * @return
	 */
	public static byte[] toByteArray(String filePath) {
		return toByteArray(new File(filePath));
	}
	
	
	/**
	 * InputStream 转 byte[]
	 * @param is
	 * @return
	 */
	public static byte[] toByteArray(InputStream is) {
		byte[] target = null;
		try {
			target = IOUtils.toByteArray(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
	}

	/**
	 * 把字节数组保存为一个文件
	 * @param bytes
	 * @param file
	 */
	public static void write(byte[] bytes, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			IOUtils.write(bytes, os);
			IOUtils.closeQuietly(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param bytes
	 * @param filePath
	 */
	public static void write(byte[] bytes, String filePath) {
        write(bytes, new File(filePath));
	}
	
	/**
	 * 将字符串存储为一个文件，当文件不存在时候，自动创建该文件，当文件已存在时候，
	 * 重写文件的内容，特定情况下，还与操作系统的权限有关
	 * @param text 字符串
	 * @param destFile 存储的目标文件
	 * @return 当存储正确无误时返回true，否则返回false
	 */
	public static void write(String strInput, String encoding, File destFile) {
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		try {
			OutputStream os = new FileOutputStream(destFile);
			IOUtils.write(strInput, os, encoding);
			IOUtils.closeQuietly(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String strInput, File destFile) {
		write(strInput, "utf-8", destFile);
	}
	
	/**
	 * byte[] 转 InputStream
	 * @param bytes
	 * @return
	 */
	public static InputStream toInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}
	
	/**
	 * 读取文件为一个内存字符串,保持文件原有的换行格式
	 * @param file 文件对象
	 * @param charset 文件字符集编码
	 * @return 文件内容的字符串
	 */
	public static String toString(File file, String encoding) {
		String target = null;
		try {
			InputStream is = new FileInputStream(file);
			target = IOUtils.toString(is, encoding);
			IOUtils.closeQuietly(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target;
	}
	
	/**
	 * 文件转换为字符串
	 * @param is 		字节流
	 * @param encoding 	文件的字符集
	 * @return 			文件内容
	 */
	public static String toString(InputStream is, String encoding) {
		String target = null;
		try {
			target = IOUtils.toString(is, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return target;
	}
	
    /**
     * 无条件的关闭一个流
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
	
	/**
	 * @param unicodeStr
	 * @return
	 */
	public static String changeEncoding(String unicodeStr, String charset) {
		String utf8Str = null;
		try {
			utf8Str = new String(unicodeStr.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return utf8Str;
	}
}
