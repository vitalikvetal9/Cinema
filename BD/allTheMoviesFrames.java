package BD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class allTheMoviesFrames {
	public String userType;
	private static String log;

	public allTheMoviesFrames() {
	}

	public allTheMoviesFrames(JFrame window, String log, String userType) {
		this.userType = userType;
		Connect d = new Connect();
		d.connect();
		String names = d.getMoviesNames();
		String arr_names[] = names.split("\n");
		this.log = log;
		loadFilmQuery(window, arr_names, arr_names[1], userType);
	}

	public static void insert(JFrame window, String name, String description, String production_year, String duration,
			String premiere_date, String end_showing, String budget, String age_restrictions, String genres,
			String countries, String awards, String adress, String[] arr_names, JComboBox moviesNames, JButton next,
			JButton nextA, JButton nextD) {

		try {
			window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/5.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		// film name
		JLabel film_name = new JLabel(converter(name, " "));
		film_name.setFont(new Font("Georgia", Font.BOLD, 45));
		film_name.setSize(1000, 200);
		film_name.setForeground(Color.WHITE);
		film_name.setLocation(20, 0);
		window.add(film_name);

		// cover loading
		String URL = adress;
		final JLabel label = new JLabel();
		Image img = loadImage(URL);
		label.setIcon(new ImageIcon(img, "Film"));
		label.setSize(450, 650);
		label.setLocation(18, 100);
		window.add(label);

		// box with films to choose
		for (int i = 0; i < arr_names.length; i++) {
			if (name.equals(arr_names[i])) {
				String temp;
				temp = arr_names[i];
				for (int j = i; j > 0; j--) {
					arr_names[j] = arr_names[j - 1];
				}
				arr_names[0] = temp;
			}
		}
		moviesNames = new JComboBox(arr_names);
		moviesNames.setBounds(200, 170, 225, 20);
		moviesNames.setLocation(875, 50);

		// button to load another movie
		next.setBounds(770, 44, 100, 30);
		next.setBackground(Color.BLACK);
		next.setForeground(Color.WHITE);
		window.add(next);

		// description
		JTextArea f_description = new JTextArea();
		description = converter(description, " ");
		f_description.setFont(new Font("Georgia", Font.BOLD, 16));
		f_description.setLocation(450, 150);
		f_description.setEditable(false);
		f_description.setOpaque(false);
		f_description.setSize(715, 170);
		f_description.setText(description);
		f_description.setVisible(true);
		f_description.setForeground(Color.WHITE);
		window.add(f_description);

		int size_1 = 240;
		int size_2 = 35;
		// info
		JLabel f_year = new JLabel("Рік випуску : " + production_year);
		f_year.setFont(new Font("Georgia", Font.BOLD, 20));
		f_year.setSize(300, 200);
		f_year.setLocation(450, size_1);
		f_year.setForeground(Color.WHITE);
		window.add(f_year);

		// info
		JLabel count = new JLabel("Країна : " + converter(countries, ","));
		count.setFont(new Font("Georgia", Font.BOLD, 20));
		count.setSize(800, 200);
		count.setLocation(450, size_1 + size_2);
		count.setForeground(Color.WHITE);
		window.add(count);

		// info
		JLabel f_dur = new JLabel("Тривалість : " + duration + " хв");
		f_dur.setFont(new Font("Georgia", Font.BOLD, 20));
		f_dur.setSize(300, 200);
		f_dur.setLocation(450, size_1 + 2 * size_2);
		f_dur.setForeground(Color.WHITE);
		window.add(f_dur);

		// info
		JLabel pred_d = new JLabel("Дата початку показу : " + premiere_date);
		pred_d.setFont(new Font("Georgia", Font.BOLD, 20));
		pred_d.setSize(400, 200);
		pred_d.setLocation(450, size_1 + 3 * size_2);
		pred_d.setForeground(Color.WHITE);
		window.add(pred_d);

		// info
		JLabel end_d = new JLabel("Дата кінця показу : " + end_showing);
		end_d.setFont(new Font("Georgia", Font.BOLD, 20));
		end_d.setSize(400, 200);
		end_d.setLocation(450, size_1 + 4 * size_2);
		end_d.setForeground(Color.WHITE);
		window.add(end_d);

		JLabel genr = new JLabel("Жанр : " + converter(genres, ","));
		genr.setFont(new Font("Georgia", Font.BOLD, 20));
		genr.setSize(500, 200);
		genr.setForeground(Color.WHITE);
		genr.setLocation(450, size_1 + 5 * size_2);

		window.add(genr);

		// infoActors
		JLabel act = new JLabel("У головних ролях : ");
		act.setFont(new Font("Georgia", Font.BOLD, 20));
		act.setSize(1000, 200);
		act.setForeground(Color.WHITE);
		act.setLocation(450, size_1 + 6 * size_2);
		window.add(act);

		nextA.setBounds(827, 538, 200, 30);
		nextA.setBackground(Color.BLACK);
		nextA.setForeground(Color.WHITE);
		window.add(nextA);

		JLabel dir = new JLabel("Режисери : ");
		dir.setFont(new Font("Georgia", Font.BOLD, 20));
		dir.setSize(140, 200);
		dir.setForeground(Color.WHITE);
		dir.setLocation(450, size_1 + 7 * size_2 + 10);
		window.add(dir);

		nextD.setBounds(760, 580, 200, 30);
		nextD.setBackground(Color.BLACK);
		nextD.setForeground(Color.WHITE);
		window.add(nextD);

		if (!age_restrictions.isEmpty()) {
			JLabel age = new JLabel("Вікові обмеження : " + age_restrictions + "+");
			age.setFont(new Font("Georgia", Font.BOLD, 20));
			age.setSize(400, 200);
			age.setForeground(Color.WHITE);
			age.setLocation(450, size_1 + 8 * size_2 + 25);
			window.add(age);
		}

		if (!budget.equals("null")) {
			JLabel f_b = new JLabel("Бюджет : " + budget + " $");
			f_b.setFont(new Font("Georgia", Font.BOLD, 20));
			f_b.setSize(400, 200);
			f_b.setForeground(Color.WHITE);
			f_b.setLocation(450, size_1 + 9 * size_2 + 25);
			window.add(f_b);
		}

		if (!awards.isEmpty()) {
			JLabel aw = new JLabel("Нагороди : " + converter(awards, ","));
			aw.setFont(new Font("Georgia", Font.BOLD, 20));
			aw.setSize(800, 200);
			aw.setForeground(Color.WHITE);
			aw.setLocation(450, size_1 + 10 * size_2 + 25);
			window.add(aw);
		}

		JLabel empty = new JLabel("");
		empty.setFont(new Font("Georgia", Font.BOLD, 20));
		empty.setSize(400, 200);
		empty.setLocation(450, size_1 + 11 * size_2);
		window.add(empty);

	}

	private static String converter(String str, String spliter) {
		String[] words = str.split(spliter);
		String res = " ";
		int k = 0, n = 58;
		for (int i = 0; i < words.length; i++) {
			if (k + words[i].length() > n) {
				res += '\n' + " ";
				res += words[i] + spliter;
				n += 58;
				k += 1;
			} else {
				res += words[i] + spliter;
			}
			k += words[i].length();
		}
		if (Character.toString(res.charAt(res.length() - 1)).equals(spliter))
			res = res.substring(0, res.length() - 1);
		return res;
	}

	private static Image loadImage(String url) {
		try {
			String fileName = "google.png";
			BufferedImage img = ImageIO.read(new URL(url));
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			ImageIO.write(img, "png", file);
			return img;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void loadFilmQuery(JFrame window, String[] films, String s, String userType) {
		Connect d = new Connect();
		d.connect();
		String yourFilmInfo = d.getMovie(s);
		String[] b = yourFilmInfo.split("\n");
		try {
			JButton next = new JButton("Перейти");
			JButton nextA = new JButton("Інформація про актора");
			JButton nextD = new JButton("Інформація про режисера");
			JComboBox moviesNames = new JComboBox();
			insert(window, b[0], b[1], b[3], b[2], b[4], b[5], b[6], b[7], b[12], b[11], b[10], b[13], films,
					moviesNames, next, nextA, nextD);
			moviesNames = new JComboBox(films);
			final JComboBox ugu = moviesNames;
			ugu.setBounds(200, 170, 225, 30);
			ugu.setLocation(875, 44);
			((JLabel) ugu.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			ugu.setBackground(Color.BLACK);
			ugu.setVisible(true);
			ugu.setForeground(Color.WHITE);
			window.add(ugu);

			String[] actorss = b[8].split(",");
			JComboBox actorsNames = new JComboBox(actorss);
			actorsNames.setBounds(670, 538, 145, 30);
			((JLabel) actorsNames.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			actorsNames.setBackground(Color.BLACK);
			actorsNames.setVisible(true);
			actorsNames.setForeground(Color.WHITE);
			window.add(actorsNames);

			String[] directorss = b[9].split(",");
			JComboBox directorsNames = new JComboBox(directorss);
			directorsNames.setBounds(600, 580, 145, 30);
			((JLabel) directorsNames.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			directorsNames.setBackground(Color.BLACK);
			directorsNames.setVisible(true);
			directorsNames.setForeground(Color.WHITE);
			window.add(directorsNames);

			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window.getContentPane().removeAll();
					window.getContentPane().repaint();
					window.getContentPane().revalidate();
					loadFilmQuery(window, films, ugu.getSelectedItem().toString(), userType);

				}
			});

			nextA.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!actorsNames.getSelectedItem().toString().isEmpty()) {
						window.getContentPane().removeAll();
						window.getContentPane().repaint();
						window.getContentPane().revalidate();
						loadArtistQuery(window, actorsNames.getSelectedItem().toString(), true, userType);
					}
				}
			});

			nextD.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!directorsNames.getSelectedItem().toString().isEmpty()) {
						window.getContentPane().removeAll();
						window.getContentPane().repaint();
						window.getContentPane().revalidate();
						loadArtistQuery(window, directorsNames.getSelectedItem().toString(), false, userType);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		JButton goBack = new JButton("До головного меню");
		goBack.setBounds(145, 711, 150, 30);
		goBack.setBackground(Color.BLACK);
		goBack.setForeground(Color.WHITE);
		goBack.setVisible(true);
		window.add(goBack);
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userType.equals("User"))
					new userFrame(window, log);
				else if (userType.equals("Admin"))
					try {
						new AdminFrame(window);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				else if (userType.equals("Cashier"))
					new cashierFrame(window, log);
			}
		});

		Font BigFontTR = new Font("Georgia", Font.BOLD, 18);
		JButton buyTicket = new JButton("Сеанси");
		buyTicket.setFont(BigFontTR);
		buyTicket.setBounds(1060, 665, 120, 30);
		buyTicket.setBackground(Color.BLACK);
		buyTicket.setForeground(Color.WHITE);
		buyTicket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (userType.equals("User"))
					new BuyTickets(window, log, "Купити", userType);
				else
					new BuyTickets(window, log, "Продати", userType);
			}
		});
		window.add(buyTicket);

		JButton exitSystem = new JButton("Sign out");
		exitSystem.setFont(BigFontTR);
		exitSystem.setBounds(1060, 700, 120, 30);
		exitSystem.setBackground(Color.BLACK);
		exitSystem.setForeground(Color.WHITE);
		exitSystem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				Authorization a = new Authorization();
			}
		});
		window.add(exitSystem);

		window.setVisible(true);

	}

	public static void loadArtistQuery(JFrame window, String fullName, Boolean actor, String userType) {
		window.getContentPane().removeAll();
		window.getContentPane().repaint();
		window.getContentPane().revalidate();

		Connect d = new Connect();
		d.connect();
		String yourFilmInfo = "";
		if (actor) {
			yourFilmInfo = d.getActorInfo(fullName);
		} else {
			yourFilmInfo = d.getDirectorInfo(fullName);
		}
		String[] b = yourFilmInfo.split("\n");
		JButton ArtistFilm = new JButton();
		JButton nextM = new JButton();
		JButton prevM = new JButton();
		String[] covers = b[5].split(",");
		insertArtcInfo(window, b[0], b[1], b[2], b[3], b[4], covers[0], ArtistFilm, nextM, prevM);

		ArtistFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getContentPane().removeAll();
				window.getContentPane().repaint();
				window.getContentPane().revalidate();
				Connect d = new Connect();
				d.connect();
				String[] movies = d.getMoviesNames().split("\n");
				loadFilmQuery(window, movies, d.getFilmNameDueToCover(covers[0]), userType);
			}
		});

		nextM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] covers_temp = resort_covers_next(covers);
				reinit(covers_temp, covers);
				insertFilmCover(window, ArtistFilm, covers[0]);
			}
		});

		prevM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] covers_temp = resort_covers_prew(covers);
				reinit(covers_temp, covers);
				insertFilmCover(window, ArtistFilm, covers[0]);
			}
		});

		JButton goBack = new JButton("До головного меню");
		goBack.setBounds(145, 711, 150, 30);
		goBack.setBackground(Color.BLACK);
		goBack.setForeground(Color.WHITE);
		goBack.setVisible(true);
		window.add(goBack);
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userType.equals("User"))
					new userFrame(window, log);
				else if (userType.equals("Admin"))
					try {
						new AdminFrame(window);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				else if (userType.equals("Cashier"))
					new cashierFrame(window, log);
			}
		});

		Font BigFontTR = new Font("Georgia", Font.BOLD, 18);

		JButton exitSystem = new JButton("Sign out");
		exitSystem.setFont(BigFontTR);
		exitSystem.setBounds(1025, 50, 150, 35);
		exitSystem.setBackground(Color.BLACK);
		exitSystem.setForeground(Color.WHITE);
		exitSystem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				Authorization a = new Authorization();
			}
		});
		window.add(exitSystem);

	}

	public static void reinit(String[] from, String[] to) {
		to = from;
	}

	public static String[] resort_covers_next(String[] covers) {
		String temp = covers[0];
		for (int i = 0; i < covers.length - 1; i++) {
			covers[i] = covers[i + 1];
		}
		covers[covers.length - 1] = temp;
		return covers;
	}

	public static String[] resort_covers_prew(String[] covers) {
		String temp = covers[covers.length - 1];
		for (int i = covers.length - 1; i > 0; i--) {
			covers[i] = covers[i - 1];
		}
		covers[0] = temp;
		return covers;
	}

	public static void insertArtcInfo(JFrame window, String name, String last_name, String birth_year,
			String citizenship, String cover, String movieCover, JButton film, JButton nextM, JButton prevM) {

		try {
			window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/4.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		// film name
		JLabel film_name = new JLabel(name + " " + last_name);
		film_name.setFont(new Font("Georgia", Font.BOLD, 45));
		film_name.setSize(1000, 200);
		film_name.setForeground(Color.WHITE);
		film_name.setLocation(50, -25);
		window.add(film_name);

		String URL = cover;
		final JLabel label = new JLabel();
		Image img = loadImage(URL);
		label.setIcon(new ImageIcon(img, "Film"));
		label.setSize(450, 650);
		label.setLocation(18, 100);
		window.add(label);

		// birth
		JLabel birth = new JLabel("Дата народження : " + birth_year);
		birth.setFont(new Font("Georgia", Font.BOLD, 20));
		birth.setSize(1000, 200);
		birth.setForeground(Color.WHITE);
		birth.setLocation(465, 65);
		window.add(birth);

		// citizenship
		JLabel ccitizenship = new JLabel("Громадянство : " + citizenship);
		ccitizenship.setFont(new Font("Georgia", Font.BOLD, 20));
		ccitizenship.setSize(1000, 200);
		ccitizenship.setForeground(Color.WHITE);
		ccitizenship.setLocation(465, 95);
		window.add(ccitizenship);

		// buttons for movies of current actor
		insertFilmCover(window, film, movieCover);
		nextM.setBounds(1020, 440, 100, 100);
		nextM.setOpaque(false);
		nextM.setContentAreaFilled(false);
		nextM.setBorderPainted(false);

		try {
			ImageIcon tmp = new ImageIcon(ImageIO.read(new File("img/but1.png")));
			nextM.setIcon(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		window.add(nextM);

		prevM.setBounds(525, 440, 100, 100);
		prevM.setOpaque(false);
		prevM.setContentAreaFilled(false);
		prevM.setBorderPainted(false);

		try {
			ImageIcon tmp2 = new ImageIcon(ImageIO.read(new File("img/but2.png")));
			prevM.setIcon(tmp2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		window.add(prevM);

		window.setVisible(true);

	}

	public static void insertFilmCover(JFrame window, JButton film, String LinkToMovieCover) {
		URL url;
		try {
			url = new URL(LinkToMovieCover);
			BufferedImage ll;
			try {
				ll = ImageIO.read(url);
				ImageIcon ol = new ImageIcon(ll);
				film.setIcon(ol);
				film.setBounds(675, 288, 300, 400);
				window.add(film);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}
}
