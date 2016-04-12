package org.noah.gen.sql.gui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.noah.gen.sql.core.ProcSql;
import org.noah.gen.sql.gui.sub.AWTEventListenerEx;
import org.noah.gen.sql.gui.sub.ImgJp;
import org.noah.gen.sql.gui.sub.ShakeThread;
import org.noah.gen.sql.res.IRes;
import org.noah.gen.sql.utils.GsFunc;

/**
 * @author noah.yang
 * sqlite 返回的结果集中，还暗藏的列名，所以取数据的时候会比较怪异（第一行即列名行！）
 * for (int i = 1; i <= rowCount; i ++)
 */
public class MainView extends JFrame {
	private static final long serialVersionUID = -4201376665536911902L;
	
	/**
	 * @param adapter
	 */
	public MainView(IProc proc, String suffix) {
		// 标题、尺寸、居中
		this.setTitle("drag " + suffix + " to start!");
		this.setSize(280, 300);
		GsFunc.moveCompToScreenCenter(this);
		// 背景图
		JPanel panel = new ImgJp(IRes.RES_PKG + IRes.RES_PNG_CAT);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		// 各种监听
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DtAdapter adapter = new DtAdapter(proc, suffix);
		adapter.setMv(this);
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, adapter);
		// 新增对 “最小化” 的支持
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		AWTEventListenerEx awtListener = new AWTEventListenerEx(this);
		toolkit.addAWTEventListener(awtListener, AWTEvent.KEY_EVENT_MASK);
		// 振动提醒功能
		this.initShakeThread();
		// 使可见
		this.setVisible(true);
	}
	
	// 振动提醒模块
	private ShakeThread shakeThread;
	private void initShakeThread() {
		shakeThread = new ShakeThread(this);
		shakeThread.start();
	}
	public void shake() {
		shakeThread.interrupt();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainView(new ProcSql(), ".desc");
	}
}
