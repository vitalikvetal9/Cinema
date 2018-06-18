package BD;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import acm.io.IODialog;

public class Pass_recovery extends JFrame {
	private JPasswordField pass1;
	private JPasswordField pass2;
	private JButton reg;
	private IODialog d;
	private String log;

	public Pass_recovery(final String log) {
		this.log = log;
	}

	public void on() {
		this.setTitle("¬≥дновленн€");
		this.setSize(600, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		setLocationRelativeTo(null);

		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/cc.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		d = new IODialog();
		pass1 = new JPasswordField(20);
		pass1.setEchoChar('Х');
		pass1.setSize(150, 27);
		Font BigFontTR = new Font("Georgia", Font.BOLD, 18);
		JTextArea newP = new JTextArea("¬вед≥ть новий пароль:");
		newP.setBounds(20, 30, 250, 30);
		newP.setOpaque(false);
		newP.setEditable(false);
		newP.setBackground(Color.BLACK);
		newP.setForeground(Color.WHITE);
		newP.setFont(BigFontTR);
		this.add(newP);
		pass1.setLocation(20, 60);

		JTextArea newPP = new JTextArea("ѕ≥дтверд≥ть пароль:");
		newPP.setBounds(20, 95, 250, 30);
		newPP.setOpaque(false);
		newPP.setEditable(false);
		newPP.setBackground(Color.BLACK);
		newPP.setForeground(Color.WHITE);
		newPP.setFont(BigFontTR);
		this.add(newPP);

		pass2 = new JPasswordField(20);
		pass2.setEchoChar('Х');
		pass2.setSize(150, 27);
		pass2.setLocation(20, 125);
		reg = new JButton();
		reg.setOpaque(false);
		reg.setContentAreaFilled(false);
		reg.setBorderPainted(false);
		reg.setHorizontalAlignment(SwingConstants.CENTER);
		reg.setSize(130, 40);
		reg.setLocation(30, 175);
		try {
			ImageIcon tmp = new ImageIcon(ImageIO.read(new File("img/ch.png")));
			reg.setIcon(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPass(log, pass1.getPassword(), pass2.getPassword());
			}
		});
		add(pass1);
		add(pass2);
		add(reg);
		this.setVisible(true);
	}

	private void checkPass(final String log, char[] pass1, char[] pass2) {
		if (pass1.length != pass2.length) {
			d.println("Enter similiar passwords");
			return;
		} else {
			for (int i = 0; i < pass1.length; i++)
				if (pass1[i] != pass2[i]) {
					d.println("Enter similiar passwords");
					return;
				}
		}
		if (pass1.length < 8) {
			d.println("The passwords is too weak!");
			return;
		}
		Connect c = new Connect();
		c.connect();
		c.doPassword(pass1, log);
		this.dispose();
	}

}
