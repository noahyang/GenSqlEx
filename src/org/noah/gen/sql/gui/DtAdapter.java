package org.noah.gen.sql.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @author noah.yang 拖拽监听
 */
public class DtAdapter extends DropTargetAdapter {
	private MainView mv = null;
	private IProc proc = null;

	public DtAdapter(IProc proc, String suffix) {
		this.proc = proc;
	}

	@SuppressWarnings("unchecked")
	public void drop(DropTargetDropEvent event) {
		if (!event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			event.rejectDrop();
			JOptionPane.showMessageDialog(null, "仅限抓入文件列表");
			return;
		}
		event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		DataFlavor df = DataFlavor.javaFileListFlavor;
		List<File> list = null;
		try {
			list = (List<File>) (event.getTransferable().getTransferData(df));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 仅限抓入一个
		if (list.size() != 1) {
			JOptionPane.showMessageDialog(null, "仅限抓入一个条目");
			return;
		}
		// 仅限处理文件而非目录
		File file = list.get(0);
		proc.process(file.getAbsolutePath());
		event.dropComplete(true);
		// 抖动窗口
		if (mv != null) {
			mv.shake();
		} else {
			System.out.println("Warn: DtAdapter.mv == null");
		}
	}
	
	public MainView getMv() {
		return mv;
	}
	public void setMv(MainView mv) {
		this.mv = mv;
	}
}
