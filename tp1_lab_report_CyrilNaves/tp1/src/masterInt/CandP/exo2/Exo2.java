package masterInt.CandP.exo2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Exo2 extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = -2818867002346363736L;

	private RunnableButton button1, button2;

	private Thread thread1, thread2;

	private String textButton1, textButton2;

	public Exo2(String title) {
		// Set up the frame
		super(title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		textButton1 = "button1";
		textButton2 = "button2";
		this.setSize(300, 120);
		this.setLayout(new FlowLayout());
		JTextField tf1 = new JTextField("Text1", 10);
		JTextField tf2 = new JTextField("Text2", 10);

		// Set up button1
		button1 = new RunnableButton("Text1", tf1);
		button1.addActionListener(this);

		// Set up button 2
		button2 = new RunnableButton("Text2", tf2);
		button2.addActionListener(this);
		// Add button 1 and 2 to the frame
		this.add(tf1);
		this.add(button1);
		this.add(tf2);
		this.add(button2);

	}

	// This method is called when any of the button is clicked
	@SuppressWarnings("deprecation")
	// At the first click, the corresponding thread is started
	// At the subsequent, the state of the thread is changed
	// from active to inactive or vice versa
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof RunnableButton) {
			RunnableButton button = (RunnableButton) e.getSource();

			// Identify the button
			if (thread1 == null && button.getText() == "Text1") {
				thread1 = new Thread(button1);
				thread1.start();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (thread2 == null && button.getText() == "Text2") {
				thread2 = new Thread(button2);
				thread2.start();
				try {
					thread2.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else if (thread1 != null && thread1.getState().toString().equalsIgnoreCase("TIMED_WAITING")
					&& thread1.getName().equalsIgnoreCase("suspend") && button.getText().equalsIgnoreCase("Text1")) {
				thread1.resume();
				thread1.setName("");
			} else if (thread2 != null && thread2.getState().toString().equalsIgnoreCase("TIMED_WAITING")
					&& thread2.getName().equalsIgnoreCase("suspend") && button.getText().equalsIgnoreCase("Text2")) {
				thread2.resume();
				thread2.setName("");
			} else if (thread1 != null && thread1.getState().toString().equalsIgnoreCase("TIMED_WAITING")
					&& button.getText().equalsIgnoreCase("Text1")) {
				thread1.suspend();
				thread1.setName("suspend");
			} else if (thread2 != null && thread2.getState().toString().equalsIgnoreCase("TIMED_WAITING")
					&& button.getText().equalsIgnoreCase("Text2")) {
				thread2.suspend();
				thread2.setName("suspend");
			}

		}

	}

	public static void main(String[] arg) {
		Exo2 main = new Exo2("Exo1b");
		main.setVisible(true);
	}

	// Kill the thread before closing the windows
	@Override
	public void windowClosing(WindowEvent e) {
		if (thread1 != null) {
			thread1.stop();
		}
		if (thread2 != null) {
			thread2.stop();
		}
		this.setVisible(false);
		System.exit(0);

	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

}
