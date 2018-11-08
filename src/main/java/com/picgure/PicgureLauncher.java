package com.picgure;

import com.picgure.ui.root.RootFrame;

import javax.swing.*;
import java.util.logging.Logger;

public class PicgureLauncher {

	private static Logger logger = Logger.getLogger(PicgureLauncher.class.getName());

	private RootFrame rootFrame = new RootFrame();

	public static void main(String[] args) {
		logger.info("Starting the Application.");
		new PicgureLauncher().run();
	}

	private void run() {
		java.awt.EventQueue.invokeLater(() -> {
			try {
				rootFrame.setVisible(true);
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
				logger.severe("Unable to initialize UI " + ex.getMessage());
			}
		});
	}

}
