package com.xiaoyu;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.xiaoyu.annotation.ApplicationBoot;
import com.xiaoyu.init.context.ContextInitializer;
import com.xiaoyu.ui.LaunchUi;
import com.xiaoyu.ui.MainFrame;

@ApplicationBoot
public class Application {

	public Application(Class<Application> source) {
		ContextInitializer context = new ContextInitializer(source);
		context.initializer();
	}

	public void run() {
		EventQueue.invokeLater(MainFrame::run);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread ui = new Thread(() -> {
			LaunchUi launchUi = new LaunchUi();
			Thread main = Thread.currentThread();
			Thread current = main;
			try {
				current.sleep(2000);
			} catch (InterruptedException e) {
				current.interrupt();
			}
			while (true) {
				if (current.isInterrupted()) {
					launchUi.dispose();
					break;
				}
				try {
					current.sleep(500);
				} catch (InterruptedException e) {
					current.interrupt();
				}
			}
		});
		ui.start();
		long from = System.currentTimeMillis();
		Application app = new Application(Application.class);
		long to = System.currentTimeMillis();
		long adjust = to - from;
		Thread mainApp = Thread.currentThread();
		if (adjust < 2000)
			mainApp.sleep(2000 - adjust);
		ui.interrupt();
		mainApp.sleep(500);
		app.run();
	}
}
