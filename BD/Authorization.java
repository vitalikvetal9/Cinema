package BD;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import acm.io.IODialog;

public class Authorization extends JFrame {
	private JTextField log;
	private JPasswordField pass;
	private JButton auth;
	private JButton reg;
	private JButton forgot;
	private IODialog d;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public Authorization() {
		this.setTitle("Авторизація");
		this.setSize(555, 265);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/bb.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}
		d = new IODialog();
		Font EmailFont = new Font("Georgia", Font.BOLD, 16);
		JLabel email = new JLabel("Email: ");
		email.setSize(120, 27);
		email.setLocation(270, 40);
		email.setForeground(Color.BLACK);
		email.setFont(EmailFont);
		JLabel password = new JLabel("Пароль:");
		password.setSize(120, 27);
		password.setLocation(270, 77);
		password.setFont(EmailFont);
		password.setForeground(Color.BLACK);
		log = new JTextField(20);
		log.setSize(150, 27);
		log.setLocation(350, 40);
		pass = new JPasswordField(20);
		pass.setEchoChar('•');
		pass.setSize(150, 27);
		pass.setLocation(350, 77);
		auth = new JButton();
		auth.setOpaque(false);
		auth.setContentAreaFilled(false);
		auth.setBorderPainted(false);
		auth.setHorizontalAlignment(SwingConstants.CENTER);

		try {
			ImageIcon tmp = new ImageIcon(ImageIO.read(new File("img/1.png")));
			auth.setIcon(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		auth.setSize(86, 27);
		Font BigFontTR = new Font("Georgia", Font.BOLD, 18);
		auth.setFont(BigFontTR);
		auth.setLocation(325, 130);
		auth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!(log.getText().isEmpty() && pass.getText().isEmpty()))
						onAuth(log.getText(), pass.getPassword());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		reg = new JButton();
		reg.setContentAreaFilled(false);
		reg.setBorderPainted(false);
		reg.setHorizontalAlignment(SwingConstants.CENTER);

		try {
			ImageIcon tmp = new ImageIcon(ImageIO.read(new File("img/2.png")));
			reg.setIcon(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		reg.setSize(86, 27);
		Font RegFont = new Font("Georgia", Font.BOLD, 14);
		reg.setFont(RegFont);
		reg.setLocation(435, 130);
		reg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(log.getText().isEmpty() && pass.getText().isEmpty()))
					onReg(log.getText().toString(), pass.getPassword());
			}
		});
		forgot = new JButton();
		forgot.setContentAreaFilled(false);
		forgot.setBorderPainted(false);
		forgot.setHorizontalAlignment(SwingConstants.CENTER);
		forgot.setFont(RegFont);
		forgot.setSize(150, 37);
		forgot.setLocation(347, 170);
		try {
			ImageIcon tmp = new ImageIcon(ImageIO.read(new File("img/3.png")));
			forgot.setIcon(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		forgot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!log.getText().isEmpty())
					forgotten(log.getText().toString());
			}
		});
		add(email);
		add(log);
		add(password);
		add(pass);
		add(auth);
		add(reg);
		add(forgot);
		this.setVisible(true);
	}

	public static boolean validate(final String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	private void onReg(final String log, char[] pass) {
		if (!validate(log)) {
			d.println("Enter right email!");
			return;
		}
		Connect c = new Connect();
		c.connect();
		if (!c.reg(log, pass))
			d.println("Registration faulted");
		else
			d.println("Registrated!");
	}

	private void onAuth(final String log, char[] pass) throws Exception {
		Connect c = new Connect();
		c.connect();
		JFrame w = new JFrame();
		w.setSize(1200, 800);
		w.setResizable(false);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (log.equals("kinoman.bd@gmail.com") && Arrays.equals(pass, "admin_kinoman12345".toCharArray())) {
			JFrame adm = new JFrame(log);
			adm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			adm.setSize(1200, 800);
			adm.setResizable(false);
			adm.setVisible(true);
			adm.setLocationRelativeTo(null);
			new AdminFrame(adm, log);
			this.dispose();
			return;
		}
		if (!c.authUser(log, pass)) {
			if (c.authCash(log, pass)) {
				this.dispose();
				new cashierFrame(w, log);
				return;
			} else {
				d.println("Authorization faulted!");
				return;
			}
		}
		this.dispose();
		new userFrame(w, log);
	}

	private void forgotten(final String log) {
		if (!validate(log)) {
			d.println("Enter right email!");
			return;
		}
		Connect c = new Connect();
		c.connect();
		if (!c.checkEmail(log)) {
			d.println("This email is not registrated!");
			return;
		}
		int code = c.forgottenPass(log);
		JFrame f = new JFrame("Відновлення");
		f.setSize(370, 210);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		try {
			f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/pf.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		JTextField codeInput = new JTextField(10);
		codeInput.setSize(150, 20);
		codeInput.setLocation(110, 75);
		Pass_recovery pas = new Pass_recovery(log);
		JButton exec = new JButton();
		exec.setSize(120, 30);
		exec.setLocation(127, 110);
		exec.setContentAreaFilled(false);
		exec.setBorderPainted(false);
		exec.setHorizontalAlignment(SwingConstants.CENTER);

		try {
			ImageIcon tmp = new ImageIcon(ImageIO.read(new File("img/send.png")));
			exec.setIcon(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		exec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Integer.toString(code).equals(codeInput.getText())) {
					pas.on();
					f.dispose();
				} else
					d.println("Code is wrong");
			}
		});
		f.add(exec);
		f.add(codeInput);
		f.setVisible(true);

	}
}
