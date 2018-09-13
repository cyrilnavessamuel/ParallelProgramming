package masterInt.CandP.exo3;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Printer extends JFrame implements WindowListener, Runnable {

	private static final long serialVersionUID = 4835711038057686272L;

	PipedInputStream pipedInputStream = null;

	private JTextArea textarea;

	public Printer(PipedInputStream _pipe) {

		this.pipedInputStream = _pipe;

		// Set up the window
		this.setSize(250, 200);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.addWindowListener(this);

		// Set up the text area
		textarea = new JTextArea(9, 20);
		textarea.setEditable(false);
		this.add(textarea);

		this.setVisible(true);
	}

	// Read when available
	// And print in the Text area
	@Override
	public void run() {
		while (true) {
			StringBuilder fina = new StringBuilder();
			char text;
			int c;
			try {
				int count = pipedInputStream.available();
				for (int i = 0; i < count; i++) {
					text = (char) pipedInputStream.read();
					fina.append(text);
				}
				if (!fina.toString().isEmpty()) {
					textarea.append(fina.toString() + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String convertto(InputStream is) {
		StringBuilder sb = null;
		String a;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			while ((a = br.readLine()) != null) {
				sb.append(a);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	// Kill the thread before killing the program
	@Override
	public void windowClosing(WindowEvent arg0) {
		Thread.currentThread().interrupt();
		this.setVisible(false);
		System.exit(0);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
