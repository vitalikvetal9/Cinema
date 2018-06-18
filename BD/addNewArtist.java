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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class addNewArtist {
	private int movieid;

	public addNewArtist(int movieid) {
		this.movieid = movieid;
		JFrame w = new JFrame();
		w.setSize(675, 592);
		w.setResizable(false);
		w.setTitle("Додати актора/режисера");
		w.setDefaultCloseOperation(w.getDefaultCloseOperation());
		fillFrame(w);
	}

	private void fillFrame(JFrame w) {

		try {
			w.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/add.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		int y = 150;

		JTextArea artistFirstName = new JTextArea("Ім'я:");
		artistFirstName.setFont(new Font("Georgia", Font.BOLD, 20));
		artistFirstName.setBounds(70, y, 55, 30);
		artistFirstName.setForeground(Color.white);
		artistFirstName.setBackground(Color.black);
		artistFirstName.setEditable(false);
		artistFirstName.setVisible(true);
		w.add(artistFirstName);

		JTextField artistFirstNameB = new JTextField();
		artistFirstNameB.setFont(new Font("Georgia", Font.BOLD, 16));
		artistFirstNameB.setForeground(Color.white);
		artistFirstNameB.setBackground(Color.black);
		artistFirstNameB.setBounds(300, y, 210, 30);
		artistFirstNameB.setVisible(true);
		w.add(artistFirstNameB);
		y += 44;

		JTextArea artistLastName = new JTextArea("Прізвище:");
		artistLastName.setFont(new Font("Georgia", Font.BOLD, 20));
		artistLastName.setBounds(70, y, 120, 30);
		artistLastName.setForeground(Color.white);
		artistLastName.setBackground(Color.black);
		artistLastName.setEditable(false);
		artistLastName.setVisible(true);
		w.add(artistLastName);

		JTextField artistLastNameB = new JTextField();
		artistLastNameB.setFont(new Font("Georgia", Font.BOLD, 16));
		artistLastNameB.setForeground(Color.white);
		artistLastNameB.setBackground(Color.black);
		artistLastNameB.setBounds(300, y, 210, 30);
		artistLastNameB.setVisible(true);
		w.add(artistLastNameB);
		y += 44;

		JTextArea dateBirth = new JTextArea("Дата народження:");
		dateBirth.setFont(new Font("Georgia", Font.BOLD, 20));
		dateBirth.setBounds(70, y, 200, 30);
		dateBirth.setForeground(Color.white);
		dateBirth.setBackground(Color.black);
		dateBirth.setEditable(false);
		dateBirth.setVisible(true);
		w.add(dateBirth);

		JTextField dateBirthB = new JTextField();
		dateBirthB.setFont(new Font("Georgia", Font.BOLD, 16));
		dateBirthB.setForeground(Color.white);
		dateBirthB.setBackground(Color.black);
		dateBirthB.setBounds(300, y, 210, 30);
		dateBirthB.setVisible(true);
		w.add(dateBirthB);
		y += 44;

		JTextArea citizenship = new JTextArea("Громадянство:");
		citizenship.setFont(new Font("Georgia", Font.BOLD, 20));
		citizenship.setBounds(70, y, 165, 30);
		citizenship.setForeground(Color.white);
		citizenship.setBackground(Color.black);
		citizenship.setEditable(false);
		citizenship.setVisible(true);
		w.add(citizenship);

		JTextField citizenshipB = new JTextField();
		citizenshipB.setFont(new Font("Georgia", Font.BOLD, 16));
		citizenshipB.setForeground(Color.white);
		citizenshipB.setBackground(Color.black);
		citizenshipB.setBounds(300, y, 210, 30);
		citizenshipB.setVisible(true);
		w.add(citizenshipB);
		y += 44;
		JTextArea pict = new JTextArea("Фото (лінк):");
		pict.setFont(new Font("Georgia", Font.BOLD, 20));
		pict.setBounds(70, y, 140, 30);
		pict.setForeground(Color.white);
		pict.setBackground(Color.black);
		pict.setEditable(false);
		pict.setVisible(true);
		w.add(pict);

		JTextField pictB = new JTextField();
		pictB.setFont(new Font("Georgia", Font.BOLD, 16));
		pictB.setForeground(Color.white);
		pictB.setBackground(Color.black);
		pictB.setBounds(300, y, 210, 30);
		pictB.setVisible(true);
		w.add(pictB);
		y += 65;
		Font FF = new Font("Georgia", Font.BOLD, 16);

		JButton addA = new JButton("Додати актора");
		addA.setBounds(62, y+100, 220, 40);
		addA.setBackground(Color.BLACK);
		addA.setForeground(Color.WHITE);
		addA.setFont(FF);
		addA.setVisible(true);
		w.add(addA);
		
		JComboBox addEA = new JComboBox();
		addEA.setBounds(40, y, 265, 40);
		addEA.setBackground(Color.BLACK);
		addEA.setForeground(Color.WHITE);
		addEA.setFont(FF);
		addEA.setVisible(true);
		addEA.addItem("Додати існуючого актора");
		Connect c = new Connect();
		c.connect();
		String s[] = c.getAllActors().split(","); 
		for (int i = 1; i<s.length; i++) 
			addEA.addItem(s[i]);
		w.add(addEA);
		
		JButton push = new JButton ("Закріпити актора");
		push.setBounds(40, y+55, 265, 30);
		push.setBackground(Color.BLACK);
		push.setForeground(Color.WHITE);
		push.setFont(FF);
		push.setVisible(true);
		w.add(push);
		
		JComboBox addED = new JComboBox();
		addED.setBounds(350, y, 275, 40);
		addED.setBackground(Color.BLACK);
		addED.setForeground(Color.WHITE);
		addED.setFont(FF);
		addED.setVisible(true);
		addED.addItem("Додати існуючого режисера");
		String е[] = c.getAllDirectors().split(","); 
		for (int i = 1; i<е.length; i++) 
			addED.addItem(е[i]);
		w.add(addED);
		
		JButton push2 = new JButton ("Закріпити режисера");
		push2.setBounds(350, y+55, 275, 30);
		push2.setBackground(Color.BLACK);
		push2.setForeground(Color.WHITE);
		push2.setFont(FF);
		push2.setVisible(true);
		w.add(push2);

		addA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(artistFirstNameB.getText().isEmpty() && artistLastNameB.getText().isEmpty()
						&& dateBirthB.getText().isEmpty() && citizenshipB.getText().isEmpty()
						&& pictB.getText().isEmpty())) {
					Connect c = new Connect();
					c.connect();
					c.addActor(movieid, artistLastNameB.getText(), artistFirstNameB.getText(), dateBirthB.getText(),
							citizenshipB.getText(), pictB.getText());
					artistFirstNameB.setText("");
					artistLastNameB.setText("");
					dateBirthB.setText("");
					citizenshipB.setText("");
					pictB.setText("");
				}
			}
		});
		
		push.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!addEA.equals("не вибрано")) {
					Connect c = new Connect();
					c.connect();
					c.addActorToTheMovie(Integer.toString(movieid), c.ActorIDDueToFullName(addEA.getSelectedItem().toString()));
				}
			}
		});
		
		push2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!addED.equals("не вибрано")) {
					Connect c = new Connect();
					c.connect();
					c.addDirectorToTheMovie(Integer.toString(movieid), c.DirectorIDDueToFullName(addED.getSelectedItem().toString()));
				}
			}
		});

		JButton addD = new JButton("Додати режисерa");
		addD.setBounds(375, y+100, 220, 40);
		addD.setBackground(Color.BLACK);
		addD.setForeground(Color.WHITE);
		addD.setFont(FF);
		addD.setVisible(true);
		w.add(addD);

		addD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(artistFirstNameB.getText().isEmpty() && artistLastNameB.getText().isEmpty()
						&& dateBirthB.getText().isEmpty() && citizenshipB.getText().isEmpty()
						&& pictB.getText().isEmpty())) {
					Connect c = new Connect();
					c.connect();
					c.addDirector(movieid, artistFirstNameB.getText(), artistLastNameB.getText(), dateBirthB.getText(),
							citizenshipB.getText(), pictB.getText());
					artistFirstNameB.setText("");
					artistLastNameB.setText("");
					dateBirthB.setText("");
					citizenshipB.setText("");
				}
			}
		});

		w.setVisible(true);
	}

}
