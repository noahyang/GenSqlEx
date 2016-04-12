package org.noah.gen.sql.gui.sub;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.commons.io.IOUtils;

/**
 * @author noah.yang
 * 可自定义背景图片，而且会平铺
 */
public class ImgJp extends JPanel {
	private static final long serialVersionUID = -2365495546002205629L;
	private ImageIcon imageIcon;
	
	/** 传入的是 classpath 下面的资源文件的路径~ */
	public ImgJp(String imgPath) {
		InputStream is = ImgJp.class.getResourceAsStream(imgPath);
		byte[] bytesImg = null;
		try {
			bytesImg = IOUtils.toByteArray(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imageIcon = new ImageIcon(bytesImg);
	}
	
	/** 平铺绘制~ */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 绘制平铺图片背景
		// 每一副图像的位置坐标
		int x = 0;
		int y = 0;
		// 平铺背景图片
		while(true) {
			// 绘制图片
			g.drawImage(imageIcon.getImage(), x, y, this);
			// 如果绘制完毕，退出循环
			if (x > getSize().width && y > getSize().height) {
				break;
			}
			if (x > getSize().width) {	// 如果绘完一行，换行绘制
				x = 0;
				y += imageIcon.getIconHeight();
			} else {	// 如果在当前行，得到下一个图片的坐标位置
				x += imageIcon.getIconWidth();
			}
		}
	}
}