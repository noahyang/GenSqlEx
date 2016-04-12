package org.noah.gen.sql.gui.sub;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.noah.gen.sql.gui.MainView;
import org.noah.gen.sql.res.IRes;

/**
 * @author noah.yang
 */
public class AWTEventListenerEx implements AWTEventListener, IRes {
	
	private MainView mainView;
	private List<JDialog> compList = new ArrayList<JDialog>();
	
	public AWTEventListenerEx(MainView mainView) {
		this.mainView = mainView;
		compList.add(new TxtDialog(RES_TXT_READ_ME, 400, 300));
		compList.add(new TxtDialog(RES_DESC_DEMO, 400, 300));
		compList.add(new TxtDialog(RES_TXT_ROAD_MAP, 400, 300));
	}
	
	@Override
	public void eventDispatched(AWTEvent event) {
		// TODO Auto-generated method stub
		KeyEvent ke = (KeyEvent)event;
//		System.out.println(ke.getKeyChar() + "\t" + ke.getKeyCode());
		// 如果不是键按下事件，立马返回
		if (KeyEvent.KEY_PRESSED != ke.getID()) {
			return;
		}
		int keyCode = ke.getKeyCode();
		// 最小化
		if (KeyEvent.VK_M == keyCode && ke.isMetaDown()) {
			mainView.setExtendedState(JFrame.ICONIFIED);
			return;
		}
		// 关闭当前
		if (KeyEvent.VK_W == keyCode && ke.isMetaDown()) {
			this.closeHint();
			return;
		}
		if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 && ke.isMetaDown()) {
			this.showHint(keyCode - KeyEvent.VK_0);
			return;
		}
	}
	
	private void showHint(int number) {
		int index = number - 1;
		if (index < 0 || index > (compList.size() - 1)) {
			System.out.println("out of bounds!");
			return;
		}
		JDialog dialog = compList.get(index);
		for (JDialog item : compList) {
			if (item != dialog) {
				item.setVisible(false);
			} else {
				if (item.isVisible()) {
					item.setVisible(false);
				} else {
					item.setVisible(true);
				}
			}
		}
	}
	
	private void closeHint() {
		boolean bFlagQuit = true;
		for (JDialog item : compList) {
			if (item.isVisible()) {
				item.setVisible(false);
				bFlagQuit = false;
				break;
			}
		}
		if (bFlagQuit) {
			System.exit(0);
		}
	}
}
