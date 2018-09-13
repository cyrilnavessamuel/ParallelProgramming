package masterInt.CandP.exo2;

import javax.swing.JButton;
import javax.swing.JTextField;

public class RunnableButton extends JButton implements Runnable {

	// Please ignore that
	private static final long serialVersionUID = 7453535863156182464L;

	JTextField textfield;

	public RunnableButton(String text, JTextField _tf) {
		super(text);
		this.textfield = _tf;
	}

	@Override
	public void run() {
		// Print the content of the text field on the output stream
		while (true) {
			System.out.println(textfield.getText());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
