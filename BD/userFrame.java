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
import javax.swing.SwingConstants;

public class userFrame {
	private static JFrame fr;
	private static String log;

	public userFrame(JFrame w, String log) {
		fr = w;
		this.log = log;
		fillFrame(w);
	}

	private static void fillFrame(JFrame w) {

		w.setLayout(null);

		try {
			w.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/u.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		Font BigFontTR = new Font("Georgia", Font.BOLD, 18);
		Font SmallFontTR = new Font("Georgia", Font.BOLD, 14);

		JButton allMovies = new JButton("Գ����, �� ����� ������������");
		allMovies.setFont(BigFontTR);
		allMovies.setBounds(420, 200, 380, 50);
		allMovies.setBackground(Color.BLACK);
		allMovies.setForeground(Color.WHITE);
		w.add(allMovies);

		allMovies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new allTheMoviesFrames(w, log, "User");
			}
		});

		JButton search = new JButton("����� ������");
		search.setFont(BigFontTR);
		search.setBounds(60, 375, 240, 50);
		search.setBackground(Color.BLACK);
		search.setForeground(Color.WHITE);
		w.add(search);

		Connect d = new Connect();
		d.connect();
		String[] allGenres = d.getAllGenres().split(",");

		JComboBox genres = new JComboBox(allGenres);
		genres.setFont(SmallFontTR);
		genres.setBounds(350, 375, 150, 50);
		genres.setBackground(Color.BLACK);
		genres.setForeground(Color.WHITE);
		((JLabel) genres.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		genres.setVisible(false);
		w.add(genres);

		String[] allActors = d.getAllActors().split(",");
		JComboBox actors = new JComboBox(allActors);
		actors.setFont(SmallFontTR);
		actors.setBounds(520, 375, 210, 50);
		actors.setBackground(Color.BLACK);
		actors.setForeground(Color.WHITE);
		((JLabel) actors.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		actors.setVisible(false);
		w.add(actors);

		String[] allDirectors = d.getAllDirectors().split(",");
		JComboBox directors = new JComboBox(allDirectors);
		directors.setFont(SmallFontTR);
		directors.setBounds(750, 375, 180, 50);
		directors.setBackground(Color.BLACK);
		directors.setForeground(Color.WHITE);
		((JLabel) directors.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		directors.setVisible(false);
		w.add(directors);

		JButton find = new JButton("������");
		find.setFont(BigFontTR);
		find.setBounds(950, 375, 180, 50);
		find.setBackground(Color.BLACK);
		find.setForeground(Color.WHITE);
		find.setVisible(false);
		w.add(find);

		JTextArea filters = new JTextArea("������ �������!");
		filters.setFont(new Font("Georgia", Font.BOLD, 35));
		filters.setBounds(450, 455, 330, 50);
		filters.setBackground(Color.black);
		filters.setForeground(Color.yellow);
		filters.setEditable(false);
		filters.setVisible(false);
		w.add(filters);

		JTextArea none = new JTextArea("������� ������ �� ���� ��������� �� ��������!");
		none.setFont(new Font("Georgia", Font.BOLD, 35));
		none.setBounds(130, 455, 950, 50);
		none.setForeground(Color.yellow);
		none.setBackground(Color.black);
		none.setVisible(false);
		w.add(none);

		search.addActionListener(new ActionListener() {
			boolean trigger = true;

			public void actionPerformed(ActionEvent e) {
				if (trigger) {
					genres.setVisible(false);
					actors.setVisible(false);
					directors.setVisible(false);
					find.setVisible(false);
					filters.setVisible(false);
					none.setVisible(false);
					trigger = false;

				} else {
					genres.setVisible(true);
					actors.setVisible(true);
					directors.setVisible(true);
					find.setVisible(true);
					trigger = true;
				}
			}
		});

		find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((genres.getSelectedItem().equals("�� �������")) && (actors.getSelectedItem().equals("�� �������"))
						&& (directors.getSelectedItem().equals("�� �������"))) {
					filters.setVisible(true);
					none.setVisible(false);
				} else {
					if (!(genres.getSelectedItem().equals("�� �������"))
							&& (actors.getSelectedItem().equals("�� �������"))
							&& (directors.getSelectedItem().equals("�� �������"))) {
						Connect d = new Connect();
						d.connect();
						String[] movieNames = d.getMoviesDueToGenre(genres.getSelectedItem().toString()).split(",");
						if (movieNames[0].equals("")) {
							filters.setVisible(false);
							none.setVisible(true);
						} else {
							new allTheMoviesFrames();
							allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0], "User");
						}
					} else {
						if ((genres.getSelectedItem().equals("�� �������"))
								&& (!actors.getSelectedItem().equals("�� �������"))
								&& (directors.getSelectedItem().equals("�� �������"))) {
							Connect d = new Connect();
							d.connect();
							String[] movieNames = d.getMoviesDueToActor(actors.getSelectedItem().toString()).split(",");
							if (movieNames[0].equals("")) {
								filters.setVisible(false);
								none.setVisible(true);
							} else {
								new allTheMoviesFrames();
								allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0], "User");
							}
						} else {
							if ((genres.getSelectedItem().equals("�� �������"))
									&& (actors.getSelectedItem().equals("�� �������"))
									&& (!directors.getSelectedItem().equals("�� �������"))) {
								Connect d = new Connect();
								d.connect();
								String[] movieNames = d.getMoviesDueToDirector(directors.getSelectedItem().toString())
										.split(",");
								if (movieNames[0].equals("")) {
									filters.setVisible(false);
									none.setVisible(true);
								} else {
									new allTheMoviesFrames();
									allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0], "User");
								}
							} else {
								if (!(genres.getSelectedItem().equals("�� �������"))
										&& !(actors.getSelectedItem().equals("�� �������"))
										&& (directors.getSelectedItem().equals("�� �������"))) {
									Connect d = new Connect();
									d.connect();
									String[] movieNames = d
											.getMoviesDueToGenreAndActor(actors.getSelectedItem().toString(),
													genres.getSelectedItem().toString())
											.split(",");
									if (movieNames[0].equals("")) {
										filters.setVisible(false);
										none.setVisible(true);
									} else {
										new allTheMoviesFrames();
										allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0], "User");
									}
								} else {
									if (!(genres.getSelectedItem().equals("�� �������"))
											&& (actors.getSelectedItem().equals("�� �������"))
											&& (!directors.getSelectedItem().equals("�� �������"))) {
										Connect d = new Connect();
										d.connect();
										String[] movieNames = d
												.getMoviesDueToGenreAndDirector(directors.getSelectedItem().toString(),
														genres.getSelectedItem().toString())
												.split(",");
										if (movieNames[0].equals("")) {
											filters.setVisible(false);
											none.setVisible(true);
										} else {
											new allTheMoviesFrames();
											allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0], "User");
										}
									} else {
										if ((genres.getSelectedItem().equals("�� �������"))
												&& (!actors.getSelectedItem().equals("�� �������"))
												&& (!directors.getSelectedItem().equals("�� �������"))) {
											Connect d = new Connect();
											d.connect();
											String[] movieNames = d
													.getMoviesDueToActorAndDirector(actors.getSelectedItem().toString(),
															directors.getSelectedItem().toString())
													.split(",");
											if (movieNames[0].equals("")) {
												filters.setVisible(false);
												none.setVisible(true);
											} else {
												new allTheMoviesFrames();
												allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0], "User");
											}
										} else {
											if (!(genres.getSelectedItem().equals("�� �������"))
													&& !(actors.getSelectedItem().equals("�� �������"))
													&& !(directors.getSelectedItem().equals("�� �������"))) {
												Connect d = new Connect();
												d.connect();
												String[] movieNames = d.getMoviesDueToGenreAndActorAndDirector(
														genres.getSelectedItem().toString(),
														actors.getSelectedItem().toString(),
														directors.getSelectedItem().toString()).split(",");
												if (movieNames[0].equals("")) {
													filters.setVisible(false);
													none.setVisible(true);
												} else {
													new allTheMoviesFrames();
													allTheMoviesFrames.loadFilmQuery(w, movieNames, movieNames[0],
															"User");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		});

		/*
		 * JButton sessions = new JButton("������"); sessions.setFont(BigFontTR);
		 * sessions.setBounds(420, 450, 380, 50); sessions.setBackground(Color.BLACK);
		 * sessions.setForeground(Color.WHITE); w.add(sessions);
		 */

		JButton buyTicket = new JButton("������/����������� ������");
		buyTicket.setFont(BigFontTR);
		buyTicket.setBounds(420, 550, 380, 50);
		buyTicket.setBackground(Color.BLACK);
		buyTicket.setForeground(Color.WHITE);
		buyTicket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BuyTickets(fr, log, "������", "User");
			}
		});
		w.add(buyTicket);

		JButton exitSystem = new JButton("Sign out");
		exitSystem.setFont(BigFontTR);
		exitSystem.setBounds(1000, 50, 150, 40);
		exitSystem.setBackground(Color.BLACK);
		exitSystem.setForeground(Color.WHITE);
		exitSystem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				w.dispose();
				Authorization a = new Authorization();
			}
		});
		w.add(exitSystem);

		w.setVisible(true);
	}

}
