package org.noah.gen.sql.gui.sub;

import java.awt.Point;
import java.awt.Window;
import java.util.Random;

/**
 * @author noah.yang
 * 抖动线程
 */
public class ShakeThread extends Thread {
	// 调用时振动的次数
	public static final int SHAKE_COUNT = 7;
	// 振动作用的对象窗口
	private Window window;
	public ShakeThread(Window window) {
		this.window = window;
	}
	@Override
	public void run() {
		Random random = new Random();
		int shakeCount = 0;
		Point oldLoc, newLoc;
		while (true) {
			try {
				// 振完后一睡不醒，节省 cpu，需要振动的时候就打断一下
				if (shakeCount <= 0) {
					shakeCount = SHAKE_COUNT;
					Thread.sleep(1000 * 3600 * 24);	// 睡 24h
				}
				oldLoc = window.getLocation();
				switch (random.nextInt(4)) {
				case 0:
					newLoc = new Point(oldLoc.x + 1, oldLoc.y + 1);	// 向右下振
					break;
				case 1:
					newLoc = new Point(oldLoc.x - 1, oldLoc.y - 1);	// 向左上振
					break;
				case 2:
					newLoc = new Point(oldLoc.x + 1, oldLoc.y - 1);	// 向右上振
					break;
				default:
					newLoc = new Point(oldLoc.x - 1, oldLoc.y + 1);	// 向左下振
					break;
				}
				// 偏离
				window.setLocation(newLoc);
				Thread.sleep(30);
				// 复位
				window.setLocation(oldLoc);
				Thread.sleep(30);
				shakeCount --;
			} catch (InterruptedException e) {
				System.err.println("ShakeThread 被唤醒，执行抖动逻辑");
			}
		}
	}
}
