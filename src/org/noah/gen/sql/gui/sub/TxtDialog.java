package org.noah.gen.sql.gui.sub;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.noah.gen.sql.res.IRes;
import org.noah.gen.sql.utils.GsFunc;
import org.noah.gen.sql.utils.IOBridge;

/**
 * @author noah.yang
 */
public class TxtDialog extends JDialog {
	private static final long serialVersionUID = -7427530454905787051L;
	public static final int JFRAME_WIDTH = 400;
	public static final int JFRAME_HEIGHT = 300;
	private JScrollPane jsp;
	private JTextArea jta;
	
	public TxtDialog(String fName, int w, int h) {
		this(fName, fName, w, h);
	}
	
	public TxtDialog(String title, String fName, int w, int h) {
		// 标题、尺寸、居中
		this.setTitle(title);
		this.setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		GsFunc.moveCompToScreenCenter(this);
		// 获取帮助文字
		String strRelPath = IRes.RES_PKG + fName;
		System.out.println("strRelPath = " + strRelPath);
		InputStream is = TxtDialog.class.getResourceAsStream(strRelPath);
		String strHelp = IOBridge.toString(is, "utf-8");
		// 将文字填充到文本框控件中
		jta = new JTextArea();
		jta.setLineWrap(true);
		jta.setText(strHelp);
		jta.setEditable(false);
		// 将文本框置入滚动层中
		jsp = new JScrollPane();
		jsp.setViewportView(jta);
		JScrollBar jsb = jsp.getVerticalScrollBar();
		jsb.setValue(jsb.getMaximum());
		// 将滚动层放进弹出窗中
		this.add(jsp);
		// 关闭的时候将修改过的属性覆盖到 jar 里面的属性配置文件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				TxtDialog.this.setVisible(false);
			}
		});
		this.setVisible(false);
	}
}
