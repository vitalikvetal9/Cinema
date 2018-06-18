package BD;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import acm.io.IODialog;

public class AdminFrame {
	private static IODialog d;
	private static String log;

	public AdminFrame(JFrame w) throws Exception {
		new AdminFrame(w, "kinoman.bd@gmail.com");
	}

	public AdminFrame(JFrame w, String log) throws Exception {
		fillFrame(w);
		this.log = log;
		d = new IODialog();
	}

	@SuppressWarnings("deprecation")
	private static void fillFrame(JFrame w) throws Exception {
		Connect c = new Connect();
		c.connect();
		w.setLayout(null);

		try {
			w.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/a.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}

		Font BigFontTR = new Font("Georgia", Font.BOLD, 18);
		Font SmallFontTR = new Font("Georgia", Font.BOLD, 14);
		JButton exitSystem = new JButton("Sign out");
		exitSystem.setFont(BigFontTR);
		exitSystem.setBounds(1020, 80, 150, 40);
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

		JButton allMovies = new JButton("Фільми, що зараз транслюються");
		allMovies.setFont(BigFontTR);
		allMovies.setBounds(50, 200, 380, 50);
		allMovies.setBackground(Color.BLACK);
		allMovies.setForeground(Color.WHITE);
		w.add(allMovies);

		allMovies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new allTheMoviesFrames(w, log, "Admin");
			}
		});

		int y = 158;

		JTextArea enterMovieName = new JTextArea("Назва фільму : ");
		enterMovieName.setFont(new Font("Georgia", Font.BOLD, 20));
		enterMovieName.setBounds(500, y, 170, 30);
		enterMovieName.setForeground(Color.white);
		enterMovieName.setBackground(Color.black);
		enterMovieName.setEditable(false);
		enterMovieName.setVisible(false);
		w.add(enterMovieName);

		JTextField enterMovieNameB = new JTextField();
		enterMovieNameB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterMovieNameB.setForeground(Color.white);
		enterMovieNameB.setBackground(Color.black);
		enterMovieNameB.setBounds(770, y, 280, 30);
		enterMovieNameB.setVisible(false);
		w.add(enterMovieNameB);
		y += 44;

		JTextArea enterDescription = new JTextArea("Опис фільму : ");
		enterDescription.setFont(new Font("Georgia", Font.BOLD, 20));
		enterDescription.setBounds(500, y, 160, 30);
		enterDescription.setForeground(Color.white);
		enterDescription.setBackground(Color.black);
		enterDescription.setEditable(false);
		enterDescription.setVisible(false);
		w.add(enterDescription);

		JTextField enterDescriptionB = new JTextField();
		enterDescriptionB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterDescriptionB.setForeground(Color.white);
		enterDescriptionB.setBackground(Color.black);
		enterDescriptionB.setBounds(770, y, 280, 30);
		enterDescriptionB.setVisible(false);
		w.add(enterDescriptionB);
		y += 44;

		JTextArea enterProdYear = new JTextArea("Рік випуску: ");
		enterProdYear.setFont(new Font("Georgia", Font.BOLD, 20));
		enterProdYear.setBounds(500, y, 145, 30);
		enterProdYear.setForeground(Color.white);
		enterProdYear.setBackground(Color.black);
		enterProdYear.setEditable(false);
		enterProdYear.setVisible(false);
		w.add(enterProdYear);

		JTextField enterProdYearB = new JTextField();
		enterProdYearB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterProdYearB.setForeground(Color.white);
		enterProdYearB.setBackground(Color.black);
		enterProdYearB.setBounds(770, y, 280, 30);
		enterProdYearB.setVisible(false);
		w.add(enterProdYearB);
		y += 44;

		JTextArea enterDuration = new JTextArea("Тривалість фільму: ");
		enterDuration.setFont(new Font("Georgia", Font.BOLD, 20));
		enterDuration.setBounds(500, y, 220, 30);
		enterDuration.setForeground(Color.white);
		enterDuration.setBackground(Color.black);
		enterDuration.setEditable(false);
		enterDuration.setVisible(false);
		w.add(enterDuration);

		JTextField enterDurationB = new JTextField();
		enterDurationB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterDurationB.setForeground(Color.white);
		enterDurationB.setBackground(Color.black);
		enterDurationB.setBounds(770, y, 280, 30);
		enterDurationB.setVisible(false);
		w.add(enterDurationB);
		y += 44;

		JTextArea enterPremiereDate = new JTextArea("Дата прем'єри: ");
		enterPremiereDate.setFont(new Font("Georgia", Font.BOLD, 20));
		enterPremiereDate.setBounds(500, y, 160, 30);
		enterPremiereDate.setForeground(Color.white);
		enterPremiereDate.setBackground(Color.black);
		enterPremiereDate.setEditable(false);
		enterPremiereDate.setVisible(false);
		w.add(enterPremiereDate);
		JTextField enterPremiereDateB = new JTextField();
		enterPremiereDateB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterPremiereDateB.setForeground(Color.white);
		enterPremiereDateB.setBackground(Color.black);
		enterPremiereDateB.setBounds(770, y, 280, 30);
		enterPremiereDateB.setVisible(false);
		w.add(enterPremiereDateB);
		y += 44;

		JTextArea enterBudget = new JTextArea("Бюджет фільму: ");
		enterBudget.setFont(new Font("Georgia", Font.BOLD, 20));
		enterBudget.setBounds(500, y, 175, 30);
		enterBudget.setForeground(Color.white);
		enterBudget.setBackground(Color.black);
		enterBudget.setEditable(false);
		enterBudget.setVisible(false);
		w.add(enterBudget);
		JTextField enterBudgetB = new JTextField();
		enterBudgetB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterBudgetB.setForeground(Color.white);
		enterBudgetB.setBackground(Color.black);
		enterBudgetB.setBounds(770, y, 280, 30);
		enterBudgetB.setVisible(false);
		w.add(enterBudgetB);
		y += 44;

		JTextArea enterAge = new JTextArea("Вікові обмеження: ");
		enterAge.setFont(new Font("Georgia", Font.BOLD, 20));
		enterAge.setBounds(500, y, 215, 30);
		enterAge.setForeground(Color.white);
		enterAge.setBackground(Color.black);
		enterAge.setEditable(false);
		enterAge.setVisible(false);
		w.add(enterAge);
		JTextField enterAgeB = new JTextField();
		enterAgeB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterAgeB.setForeground(Color.white);
		enterAgeB.setBackground(Color.black);
		enterAgeB.setBounds(770, y, 280, 30);
		enterAgeB.setVisible(false);
		w.add(enterAgeB);
		y += 44;

		JTextArea enterCover = new JTextArea("Посилання на постер: ");
		enterCover.setFont(new Font("Georgia", Font.BOLD, 20));
		enterCover.setBounds(500, y, 250, 30);
		enterCover.setForeground(Color.white);
		enterCover.setBackground(Color.black);
		enterCover.setEditable(false);
		enterCover.setVisible(false);
		w.add(enterCover);

		JTextField enterCoverB = new JTextField();
		enterCoverB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterCoverB.setForeground(Color.white);
		enterCoverB.setBackground(Color.black);
		enterCoverB.setBounds(770, y, 280, 30);
		enterCoverB.setVisible(false);
		w.add(enterCoverB);
		y += 44;

		JTextArea enterAwards = new JTextArea("Нагороди фільму: ");
		enterAwards.setFont(new Font("Georgia", Font.BOLD, 20));
		enterAwards.setBounds(500, y, 200, 30);
		enterAwards.setForeground(Color.white);
		enterAwards.setBackground(Color.black);
		enterAwards.setEditable(false);
		enterAwards.setVisible(false);
		w.add(enterAwards);

		JTextField enterAwardsB = new JTextField();
		enterAwardsB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterAwardsB.setForeground(Color.white);
		enterAwardsB.setBackground(Color.black);
		enterAwardsB.setBounds(770, y, 280, 30);
		enterAwardsB.setVisible(false);
		w.add(enterAwardsB);
		y += 44;

		JTextArea enterGenres = new JTextArea("Жанри фільму: ");
		enterGenres.setFont(new Font("Georgia", Font.BOLD, 20));
		enterGenres.setBounds(500, y, 165, 30);
		enterGenres.setForeground(Color.white);
		enterGenres.setBackground(Color.black);
		enterGenres.setEditable(false);
		enterGenres.setVisible(false);
		w.add(enterGenres);

		JComboBox enterGenresB = new JComboBox();
		enterGenresB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterGenresB.setForeground(Color.white);
		enterGenresB.setBackground(Color.black);
		enterGenresB.setBounds(770, y, 280, 30);
		String[] g = c.getAllGenres().split(",");
		for (int i = 0; i < g.length; i++) {
			enterGenresB.addItem(g[i]);
		}
		enterGenresB.setVisible(false);
		w.add(enterGenresB);

		JButton anotherG = new JButton("Додати");
		anotherG.setFont(new Font("Georgia", Font.BOLD, 13));
		anotherG.setForeground(Color.white);
		anotherG.setBackground(Color.black);
		anotherG.setBounds(1060, y, 100, 30);
		anotherG.setVisible(false);
		w.add(anotherG);
		y += 44;

		JButton temp_ganres = new JButton("");

		anotherG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String genres = temp_ganres.getText();
				if ((!temp_ganres.getText().contains((enterGenresB.getSelectedItem().toString() + ",")))
						&& (!(enterGenresB.getSelectedItem().toString().equals("не вибрано")))) {
					genres += (enterGenresB.getSelectedItem().toString() + ",");
					temp_ganres.setText(genres);
				}
				enterGenresB.setSelectedItem(enterGenresB.getItemAt(0));
			}

		});

		JTextArea enterCountries = new JTextArea("Країни випуску: ");
		enterCountries.setFont(new Font("Georgia", Font.BOLD, 20));
		enterCountries.setBounds(500, y, 175, 30);
		enterCountries.setForeground(Color.white);
		enterCountries.setBackground(Color.black);
		enterCountries.setEditable(false);
		enterCountries.setVisible(false);
		w.add(enterCountries);

		JTextField enterCountriesB = new JTextField();
		enterCountriesB.setFont(new Font("Georgia", Font.BOLD, 16));
		enterCountriesB.setForeground(Color.white);
		enterCountriesB.setBackground(Color.black);
		enterCountriesB.setBounds(770, y, 280, 30);
		enterCountriesB.setVisible(false);
		w.add(enterCountriesB);
		y += 44;

		JButton sessions = new JButton("Робота з сеансами");
		sessions.setFont(BigFontTR);
		sessions.setBounds(50, 300, 380, 50);
		sessions.setBackground(Color.BLACK);
		sessions.setForeground(Color.WHITE);
		w.add(sessions);

		JButton movie_work = new JButton("Робота з фільмами");
		movie_work.setFont(BigFontTR);
		movie_work.setBounds(50, 620, 380, 50);
		movie_work.setBackground(Color.BLACK);
		movie_work.setForeground(Color.WHITE);
		movie_work.setVisible(true);
		w.add(movie_work);

		JButton addM = new JButton("Додати фільм");
		addM.setFont(BigFontTR);
		addM.setBounds(795, 650, 230, 50);
		addM.setBackground(Color.BLACK);
		addM.setForeground(Color.WHITE);
		addM.setVisible(false);
		w.add(addM);

		addM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connect c = new Connect();
				c.connect();
				System.out.println(temp_ganres.getText());
				try {
					int id = c.insertFilm(enterMovieNameB.getText(), enterDescriptionB.getText(),
							Integer.parseInt(enterProdYearB.getText()), Integer.parseInt(enterDurationB.getText()),
							enterPremiereDateB.getText(), enterBudgetB.getText(),
							(short) Integer.parseInt(enterAgeB.getText()), enterCoverB.getText(), temp_ganres.getText(),
							enterAwardsB.getText(), enterCountriesB.getText());
					d.println("Фільм додано");
					new addNewArtist(id);
				} catch (Exception en) {
					d.println(en.getMessage());
				}
			}
		});

		JTextArea ent_date = new JTextArea("Дата сеансу: ");
		ent_date.setBounds(505, 310, 140, 30);
		ent_date.setFont(BigFontTR);
		ent_date.setForeground(Color.WHITE);
		ent_date.setBackground(Color.BLACK);
		ent_date.setEditable(false);
		JTextField ses_date = new JTextField();
		ses_date.setBounds(690, 300, 170, 50);
		ses_date.setBackground(Color.BLACK);
		ses_date.setForeground(Color.WHITE);
		ses_date.setFont(BigFontTR);
		JTextArea ses_id = new JTextArea("Номер сеансу: ");
		ses_id.setBounds(880, 310, 150, 30);
		ses_id.setFont(BigFontTR);
		ses_id.setForeground(Color.WHITE);
		ses_id.setBackground(Color.BLACK);
		ses_id.setEditable(false);
		JTextArea ent_begin = new JTextArea("Початок сеансу: ");
		ent_begin.setBounds(505, 380, 170, 30);
		ent_begin.setFont(BigFontTR);
		ent_begin.setForeground(Color.WHITE);
		ent_begin.setBackground(Color.BLACK);
		ent_begin.setEditable(false);
		JTextField ses_begin = new JTextField();
		ses_begin.setBounds(690, 370, 170, 50);
		ses_begin.setBackground(Color.BLACK);
		ses_begin.setForeground(Color.WHITE);
		ses_begin.setFont(BigFontTR);
		JTextField en_sesId = new JTextField();
		en_sesId.setBounds(1060, 300, 110, 50);
		en_sesId.setBackground(Color.BLACK);
		en_sesId.setForeground(Color.WHITE);
		en_sesId.setFont(BigFontTR);
		JTextArea ent_movieName = new JTextArea("Назва фільму: ");
		ent_movieName.setBounds(505, 450, 150, 30);
		ent_movieName.setFont(BigFontTR);
		ent_movieName.setForeground(Color.WHITE);
		ent_movieName.setBackground(Color.BLACK);
		ent_movieName.setEditable(false);
		JComboBox ses_movieName = new JComboBox(c.getMoviesToShow());
		ses_movieName.setBounds(690, 440, 170, 50);
		ses_movieName.setBackground(Color.BLACK);
		ses_movieName.setForeground(Color.WHITE);
		ses_movieName.setFont(SmallFontTR);
		JTextArea ent_hall = new JTextArea("Номер залу: ");
		ent_hall.setBounds(505, 520, 130, 30);
		ent_hall.setFont(BigFontTR);
		ent_hall.setForeground(Color.WHITE);
		ent_hall.setBackground(Color.BLACK);
		ent_hall.setEditable(false);
		JTextField ses_hall_id = new JTextField();
		ses_hall_id.setBounds(690, 510, 170, 50);
		ses_hall_id.setBackground(Color.BLACK);
		ses_hall_id.setForeground(Color.WHITE);
		ses_hall_id.setFont(BigFontTR);
		w.setVisible(true);

		JButton add = new JButton("Додати сеанс");
		add.setFont(BigFontTR);
		add.setBounds(582, 600, 200, 50);
		add.setBackground(Color.BLACK);
		add.setForeground(Color.WHITE);
		add.setVisible(false);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!(ses_date.getText().isEmpty() || ses_begin.getText().isEmpty() || ses_movieName.getSelectedItem().toString().isEmpty()
						|| ses_hall_id.getText().isEmpty())) {
					Connect c = new Connect();
					c.connect();
					try {
						c.addSession(ses_date.getText(), ses_begin.getText(), ses_movieName.getSelectedItem().toString(),
								Integer.parseInt(ses_hall_id.getText()));
						d.println("Сеанс додано");
					} catch (Exception en) {
						if (en.getMessage().contains("ОШИБКА:") && en.getMessage().contains("date"))
							d.println("Enter right date(YYYY-MM-DD)");
						else
							d.println(en.getMessage());
					}
				}
			}
		});
		w.add(add);

		JButton del = new JButton("Видалити сеанс");
		del.setFont(BigFontTR);
		del.setBounds(930, 385, 190, 50);
		del.setBackground(Color.BLACK);
		del.setForeground(Color.WHITE);
		del.setVisible(false);
		w.add(del);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame addSess = new JFrame();
				addSess.setSize(400, 300);
				addSess.setDefaultCloseOperation(addSess.DISPOSE_ON_CLOSE);
				addSess.setResizable(false);
				addSess.setLayout(null);

			}
		});

		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!en_sesId.getText().isEmpty()) {
					Connect c = new Connect();
					c.connect();
					try {
						c.deleteSession(Integer.parseInt(en_sesId.getText()));
						d.println("Сеанс видалено");
					} catch (Exception en) {
						d.println("No such session in our cinema");
					}
				}
			}
		});

		JButton statistics = new JButton("Статистика");
		statistics.setFont(BigFontTR);
		statistics.setBounds(140, 460, 200, 50);
		statistics.setBackground(Color.BLACK);
		statistics.setForeground(Color.WHITE);
		statistics.setVisible(true);
		w.add(statistics);

		JTextArea moviesPop = new JTextArea("Найпопулярніші фільми: ");
		moviesPop.setFont(new Font("Georgia", Font.BOLD, 20));
		moviesPop.setBounds(450, 338, 280, 30);
		moviesPop.setForeground(Color.white);
		moviesPop.setBackground(Color.black);
		moviesPop.setEditable(false);
		moviesPop.setVisible(false);
		w.add(moviesPop);

		JComboBox moviesPopB = new JComboBox(c.getPopularMovies("desc").split(","));
		moviesPopB.setFont(new Font("Georgia", Font.BOLD, 16));
		moviesPopB.setForeground(Color.white);
		moviesPopB.setBackground(Color.black);
		moviesPopB.setBounds(770, 338, 300, 30);
		moviesPopB.setVisible(false);
		w.add(moviesPopB);

		JTextArea genresPop = new JTextArea("Найпопулярніші жанри: ");
		genresPop.setFont(new Font("Georgia", Font.BOLD, 20));
		genresPop.setBounds(450, 383, 280, 30);
		genresPop.setForeground(Color.white);
		genresPop.setBackground(Color.black);
		genresPop.setEditable(false);
		genresPop.setVisible(false);
		w.add(genresPop);

		JComboBox genresPopB = new JComboBox(c.getPopularGenres().split(","));
		genresPopB.setFont(new Font("Georgia", Font.BOLD, 16));
		genresPopB.setForeground(Color.white);
		genresPopB.setBackground(Color.black);
		genresPopB.setBounds(770, 383, 300, 30);
		genresPopB.setVisible(false);
		w.add(genresPopB);

		JTextArea actorsPop = new JTextArea("Найпопулярніші актори: ");
		actorsPop.setFont(new Font("Georgia", Font.BOLD, 20));
		actorsPop.setBounds(450, 428, 280, 30);
		actorsPop.setForeground(Color.white);
		actorsPop.setBackground(Color.black);
		actorsPop.setEditable(false);
		actorsPop.setVisible(false);
		w.add(actorsPop);

		JComboBox actorsPopB = new JComboBox(c.getPopularActors().split(","));
		actorsPopB.setFont(new Font("Georgia", Font.BOLD, 16));
		actorsPopB.setForeground(Color.white);
		actorsPopB.setBackground(Color.black);
		actorsPopB.setBounds(770, 428, 300, 30);
		actorsPopB.setVisible(false);
		w.add(actorsPopB);

		JTextArea directorsPop = new JTextArea("Найпопулярніші режисери: ");
		directorsPop.setFont(new Font("Georgia", Font.BOLD, 20));
		directorsPop.setBounds(450, 472, 305, 30);
		directorsPop.setForeground(Color.white);
		directorsPop.setBackground(Color.black);
		directorsPop.setEditable(false);
		directorsPop.setVisible(false);
		w.add(directorsPop);

		JComboBox directorsPopB = new JComboBox(c.getPopularDirectors().split(","));
		directorsPopB.setFont(new Font("Georgia", Font.BOLD, 16));
		directorsPopB.setForeground(Color.white);
		directorsPopB.setBackground(Color.black);
		directorsPopB.setBounds(770, 472, 300, 30);
		directorsPopB.setVisible(false);
		w.add(directorsPopB);

		JTextArea profit = new JTextArea("Прибуток кінотеатру від ");
		profit.setFont(new Font("Georgia", Font.BOLD, 20));
		profit.setBounds(450, 608, 270, 30);
		profit.setForeground(Color.white);
		profit.setBackground(Color.black);
		profit.setEditable(false);
		profit.setVisible(false);
		w.add(profit);

		JTextField profitDate_beg = new JTextField();
		profitDate_beg.setFont(new Font("Georgia", Font.BOLD, 20));
		profitDate_beg.setBounds(732, 608, 140, 30);
		profitDate_beg.setForeground(Color.white);
		profitDate_beg.setBackground(Color.black);
		profitDate_beg.setVisible(false);
		w.add(profitDate_beg);

		JTextArea profit_ = new JTextArea(" до");
		profit_.setFont(new Font("Georgia", Font.BOLD, 20));
		profit_.setBounds(880, 608, 35, 30);
		profit_.setForeground(Color.white);
		profit_.setBackground(Color.black);
		profit_.setEditable(false);
		profit_.setVisible(false);
		w.add(profit_);

		JTextField profitDate_end = new JTextField();
		profitDate_end.setFont(new Font("Georgia", Font.BOLD, 20));
		profitDate_end.setBounds(928, 608, 140, 30);
		profitDate_end.setForeground(Color.white);
		profitDate_end.setBackground(Color.black);
		profitDate_end.setVisible(false);
		w.add(profitDate_end);

		JTextField profit_sum = new JTextField();
		profit_sum.setFont(new Font("Georgia", Font.BOLD, 20));
		profit_sum.setBounds(732, 650, 140, 30);
		profit_sum.setForeground(Color.white);
		profit_sum.setBackground(Color.black);
		profit_sum.setVisible(false);
		profit_sum.setEditable(false);
		w.add(profit_sum);

		JButton profit_calc = new JButton(" Вирахувати");
		profit_calc.setFont(new Font("Georgia", Font.BOLD, 20));
		profit_calc.setBounds(900, 650, 170, 30);
		profit_calc.setForeground(Color.white);
		profit_calc.setBackground(Color.black);
		profit_calc.setVisible(false);
		w.add(profit_calc);

		profit_calc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(profitDate_beg.getText().isEmpty() || profitDate_end.getText().isEmpty())) {
					Connect c = new Connect();
					c.connect();
					try {
						profit_sum.setText(
								Integer.toString(c.getProfit(profitDate_beg.getText(), profitDate_end.getText()))
										+ "$");
					} catch (Exception en) {
						d.println(en.getMessage());
					}
				}
			}
		});

		JTextArea fewVisitors = new JTextArea("Дні, коли мало відвідувачів: ");
		fewVisitors.setFont(new Font("Georgia", Font.BOLD, 20));
		fewVisitors.setBounds(450, 563, 310, 30);
		fewVisitors.setForeground(Color.white);
		fewVisitors.setBackground(Color.black);
		fewVisitors.setEditable(false);
		fewVisitors.setVisible(false);
		w.add(fewVisitors);

		JComboBox fewVisitorsD = new JComboBox(daysOfWeek(c.getNonPopularDays()));
		fewVisitorsD.setFont(new Font("Georgia", Font.BOLD, 16));
		fewVisitorsD.setForeground(Color.white);
		fewVisitorsD.setBackground(Color.black);
		fewVisitorsD.setBounds(770, 563, 300, 30);
		fewVisitorsD.setVisible(false);
		w.add(fewVisitorsD);

		JTextArea notPopularMovies = new JTextArea("Непопулярні фільми : ");
		notPopularMovies.setFont(new Font("Georgia", Font.BOLD, 20));
		notPopularMovies.setBounds(450, 518, 250, 30);
		notPopularMovies.setForeground(Color.white);
		notPopularMovies.setBackground(Color.black);
		notPopularMovies.setEditable(false);
		notPopularMovies.setVisible(false);
		w.add(notPopularMovies);

		JComboBox notPopularMoviesB = new JComboBox(c.getPopularMovies("asc").split(","));
		notPopularMoviesB.setFont(new Font("Georgia", Font.BOLD, 16));
		notPopularMoviesB.setForeground(Color.white);
		notPopularMoviesB.setBackground(Color.black);
		notPopularMoviesB.setBounds(770, 518, 300, 30);
		notPopularMoviesB.setVisible(false);
		w.add(notPopularMoviesB);

		sessions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ent_date.setVisible(true);
				ses_id.setVisible(true);
				ses_date.setVisible(true);
				en_sesId.setVisible(true);
				ent_begin.setVisible(true);
				ses_begin.setVisible(true);
				ent_movieName.setVisible(true);
				ses_movieName.setVisible(true);
				ent_hall.setVisible(true);
				ses_hall_id.setVisible(true);
				del.setVisible(true);
				add.setVisible(true);
				moviesPop.setVisible(false);
				moviesPopB.setVisible(false);
				genresPop.setVisible(false);
				genresPopB.setVisible(false);
				actorsPop.setVisible(false);
				actorsPopB.setVisible(false);
				directorsPop.setVisible(false);
				directorsPopB.setVisible(false);
				profit.setVisible(false);
				profit_.setVisible(false);
				profit_calc.setVisible(false);
				profit_sum.setVisible(false);
				profitDate_beg.setVisible(false);
				profitDate_end.setVisible(false);
				fewVisitors.setVisible(false);
				fewVisitorsD.setVisible(false);
				notPopularMovies.setVisible(false);
				// notPopularSessions.setVisible(false);
				notPopularMoviesB.setVisible(false);
				enterMovieNameB.setVisible(false);
				enterMovieName.setVisible(false);
				enterDescription.setVisible(false);
				enterDescriptionB.setVisible(false);
				enterProdYear.setVisible(false);
				enterProdYearB.setVisible(false);
				enterDuration.setVisible(false);
				enterDurationB.setVisible(false);
				enterPremiereDate.setVisible(false);
				enterPremiereDateB.setVisible(false);
				enterBudget.setVisible(false);
				enterBudgetB.setVisible(false);
				enterAge.setVisible(false);
				enterAgeB.setVisible(false);
				enterCover.setVisible(false);
				enterCoverB.setVisible(false);
				addM.setVisible(false);
				enterAwards.setVisible(false);
				enterAwardsB.setVisible(false);
				enterCountries.setVisible(false);
				enterCountriesB.setVisible(false);
				enterGenres.setVisible(false);
				enterGenresB.setVisible(false);
				anotherG.setVisible(false);
				w.add(ent_date);
				w.add(ses_id);
				w.add(ses_date);
				w.add(en_sesId);
				w.add(ent_begin);
				w.add(ses_begin);
				w.add(ent_movieName);
				w.add(ses_movieName);
				w.add(ent_hall);
				w.add(ses_hall_id);
				add.setVisible(true);
				del.setVisible(true);
			}
		});

		statistics.addActionListener(new ActionListener() {
			boolean triger = false;

			public void actionPerformed(ActionEvent e) {
				triger = !triger;
				notPopularMoviesB.setVisible(false);
				enterMovieNameB.setVisible(false);
				enterMovieName.setVisible(false);
				enterDescription.setVisible(false);
				enterDescriptionB.setVisible(false);
				enterProdYear.setVisible(false);
				enterProdYearB.setVisible(false);
				enterDuration.setVisible(false);
				enterDurationB.setVisible(false);
				enterPremiereDate.setVisible(false);
				enterPremiereDateB.setVisible(false);
				enterBudget.setVisible(false);
				enterBudgetB.setVisible(false);
				enterAge.setVisible(false);
				enterAgeB.setVisible(false);
				enterCover.setVisible(false);
				enterCoverB.setVisible(false);
				anotherG.setVisible(false);
				addM.setVisible(false);
				enterAwards.setVisible(false);
				enterAwardsB.setVisible(false);
				enterCountries.setVisible(false);
				enterCountriesB.setVisible(false);
				enterGenres.setVisible(false);
				enterGenresB.setVisible(false);
				ent_date.setVisible(false);
				ses_id.setVisible(false);
				ses_date.setVisible(false);
				en_sesId.setVisible(false);
				ent_begin.setVisible(false);
				ses_begin.setVisible(false);
				ent_movieName.setVisible(false);
				ses_movieName.setVisible(false);
				ent_hall.setVisible(false);
				ses_hall_id.setVisible(false);
				del.setVisible(false);
				add.setVisible(false);
				moviesPop.setVisible(true);
				moviesPopB.setVisible(true);
				genresPop.setVisible(true);
				genresPopB.setVisible(true);
				actorsPop.setVisible(true);
				actorsPopB.setVisible(true);
				directorsPop.setVisible(true);
				directorsPopB.setVisible(true);
				profit.setVisible(true);
				profit_.setVisible(true);
				profit_calc.setVisible(true);
				profit_sum.setVisible(true);
				profitDate_beg.setVisible(true);
				profitDate_end.setVisible(true);
				fewVisitors.setVisible(true);
				fewVisitorsD.setVisible(true);
				notPopularMovies.setVisible(true);
				// notPopularSessions.setVisible(true);
				notPopularMoviesB.setVisible(true);
			}
		});

		movie_work.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enterMovieNameB.setVisible(true);
				enterMovieName.setVisible(true);
				enterDescription.setVisible(true);
				enterDescriptionB.setVisible(true);
				enterProdYear.setVisible(true);
				enterProdYearB.setVisible(true);
				enterDuration.setVisible(true);
				enterDurationB.setVisible(true);
				enterPremiereDate.setVisible(true);
				enterPremiereDateB.setVisible(true);
				enterBudget.setVisible(true);
				enterBudgetB.setVisible(true);
				enterAge.setVisible(true);
				enterAgeB.setVisible(true);
				enterCover.setVisible(true);
				enterCoverB.setVisible(true);
				addM.setVisible(true);
				enterAwards.setVisible(true);
				enterAwardsB.setVisible(true);
				enterCountries.setVisible(true);
				enterCountriesB.setVisible(true);
				enterGenres.setVisible(true);
				enterGenresB.setVisible(true);
				anotherG.setVisible(true);
				moviesPop.setVisible(false);
				moviesPopB.setVisible(false);
				genresPop.setVisible(false);
				genresPopB.setVisible(false);
				actorsPop.setVisible(false);
				actorsPopB.setVisible(false);
				directorsPop.setVisible(false);
				directorsPopB.setVisible(false);
				profit.setVisible(false);
				profit_.setVisible(false);
				profit_calc.setVisible(false);
				profit_sum.setVisible(false);
				profitDate_beg.setVisible(false);
				profitDate_end.setVisible(false);
				fewVisitors.setVisible(false);
				fewVisitorsD.setVisible(false);
				notPopularMovies.setVisible(false);
				// notPopularSessions.setVisible(true);
				notPopularMoviesB.setVisible(false);
				ent_date.setVisible(false);
				ses_id.setVisible(false);
				ses_date.setVisible(false);
				en_sesId.setVisible(false);
				ent_begin.setVisible(false);
				ses_begin.setVisible(false);
				ent_movieName.setVisible(false);
				ses_movieName.setVisible(false);
				ent_hall.setVisible(false);
				ses_hall_id.setVisible(false);
				del.setVisible(false);
				add.setVisible(false);
			}
		});

		w.setVisible(true);

	}

	@SuppressWarnings("deprecation")
	static String[] daysOfWeek(String date) throws ParseException {
		SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date MyDate;
		String currDate = "";
		String D[] = date.split(",");
		for (int i = 0; i < D.length; i++) {
			newDateFormat.applyPattern("yyyy-MM-dd");
			currDate = D[i];
			MyDate = newDateFormat.parse(currDate);
			newDateFormat.applyPattern("EEEE");
			D[i] = newDateFormat.format(MyDate);
			System.out.println(D[i]);
		}
		return D;
	}

	public static void rewrite(String from, String to) {
		to = from;
	}

}
