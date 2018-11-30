package com.picgure;

import com.picgure.logging.PicgureLogger;
import com.picgure.ui.root.RootFrame;

import javax.swing.*;
import java.util.logging.Logger;

/*
 * @author Jeet Prakash
 * */
public class PicgureLauncher {

	private static Logger logger = PicgureLogger.getLogger(PicgureLauncher.class);

	private RootFrame rootFrame = new RootFrame();

	public static void main(String[] args) {
		logger.info("Starting the Application.");
		new PicgureAppEventListener().syncSettingsFileStore();
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
