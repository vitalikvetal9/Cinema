package BD;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import acm.io.IODialog;

public class BuyTickets {
	public static String movieN;
	public static String mDate;
	public static String mTime;
	private String login;

	public BuyTickets(JFrame w, String log, String bot, String userType) {
		new BuyTickets(w, "Пассажир", "2018-04-26", "2", log, bot, userType);
	}

	public BuyTickets(JFrame w, String movieName, String date, String ID, String log, String bot, String userType) {
		login = log;
		movieN = movieName;
		showHall(w, movieName, date, ID, bot, userType, log);
	}

	private String movie;
	IODialog d;

	public void showHall(JFrame w, String movieName, String date, String ID, String bot, String userType, String log) {
		w.getContentPane().removeAll();
		w.getContentPane().repaint();
		w.getContentPane().revalidate();
		try {
			w.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/h.jpg")))));
		} catch (IOException e) {
			System.out.println("Image does not exist");
		}
		
		Font FF = new Font("Georgia", Font.BOLD, 16);
		
		JButton goBackM = new JButton("Перейти до фільмів");
		goBackM.setBounds(40, 678, 220, 30);
		goBackM.setBackground(Color.BLACK);
		goBackM.setForeground(Color.WHITE);
		goBackM.setFont(FF);
		goBackM.setVisible(true);
		w.add(goBackM);
		goBackM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new allTheMoviesFrames(w, log, userType);
			}
		});
		
		JButton goBack = new JButton("До головного меню");
		goBack.setBounds(40, 711, 220, 30);
		goBack.setBackground(Color.BLACK);
		goBack.setForeground(Color.WHITE);
		goBack.setFont(FF);
		goBack.setVisible(true);
		w.add(goBack);
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userType.equals("User"))
					new userFrame(w, bot);
				else if (userType.equals("Admin"))
					try {
						new AdminFrame(w);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (userType.equals("Cashier"))
					new cashierFrame(w, bot);
			}
		});
		
		
		Font F = new Font("Georgia", Font.BOLD, 18);
		
		JButton exitSystem = new JButton("Sign out");
		exitSystem.setFont(F);
		exitSystem.setBounds(980, 80, 150, 35);
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

		d = new IODialog();
		movie = movieName;
		w.setTitle("Сеанси");
		w.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int id = Integer.parseInt(ID);
		// drawSeats(w, id);
		Connect c = new Connect();
		c.connect();
		Font BigFontTR = new Font("Georgia", Font.BOLD, 20);
		JLabel chooseMovie = new JLabel("Оберіть фільм: ");
		chooseMovie.setFont(BigFontTR);
		chooseMovie.setBounds(55, 70, 200, 30);
		chooseMovie.setBackground(Color.BLACK);
		chooseMovie.setForeground(Color.WHITE);
		w.add(chooseMovie);

		JComboBox allMovies = new JComboBox(c.getMoviesForBuying().split("\n"));
		allMovies.setBounds(50, 100, 190, 40);
		allMovies.setBackground(Color.BLACK);
		allMovies.setForeground(Color.white);
		allMovies.setFont(new Font("Arial", Font.BOLD, 15));
		allMovies.setVisible(true);
		w.add(allMovies);

		JLabel chooseDate = new JLabel("Оберіть дату: ");
		chooseDate.setFont(BigFontTR);
		chooseDate.setBounds(55, 150, 200, 30);
		chooseDate.setBackground(Color.BLACK);
		chooseDate.setForeground(Color.WHITE);
		w.add(chooseDate);

		JComboBox allDates = new JComboBox();
		allDates.setBounds(50, 180, 190, 40);
		allDates.setBackground(Color.BLACK);
		allDates.setForeground(Color.white);
		allDates.setFont(new Font("Arial", Font.BOLD, 15));
		allDates.setVisible(true);
		w.add(allDates);

		JComboBox sessionsOfCurrentMovie = new JComboBox(c.getMoviesSessions(movieName, date).split(","));
		sessionsOfCurrentMovie.setBounds(920, 130, 100, 30);
		sessionsOfCurrentMovie.setBackground(Color.BLACK);
		sessionsOfCurrentMovie.setForeground(Color.WHITE);
		sessionsOfCurrentMovie.setVisible(true);
		w.add(sessionsOfCurrentMovie);

		JLabel chooseBeginTime = new JLabel("Оберіть час : ");
		chooseBeginTime.setFont(BigFontTR);
		chooseBeginTime.setBounds(55, 230, 200, 30);
		chooseBeginTime.setBackground(Color.BLACK);
		chooseBeginTime.setForeground(Color.WHITE);
		w.add(chooseBeginTime);

		JComboBox chooseBeginTimes = new JComboBox();
		chooseBeginTimes.setBounds(50, 260, 190, 40);
		chooseBeginTimes.setBackground(Color.BLACK);
		chooseBeginTimes.setForeground(Color.white);
		chooseBeginTimes.setFont(new Font("Arial", Font.BOLD, 15));
		chooseBeginTimes.setVisible(true);
		w.add(chooseBeginTimes);
		if (movieN != null && mDate != null && mTime != null) {
			allMovies.setSelectedItem(movieN);
			allDates.addItem(mDate);
			chooseBeginTimes.addItem(mTime);
		}

		JButton next = new JButton("Перейти!");
		next.setBounds(1030, 130, 100, 30);
		next.setBackground(Color.BLACK);
		next.setForeground(Color.WHITE);
		w.add(next);

		allMovies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allDates.removeAllItems();
				String dates[] = c.getAllDates(allMovies.getSelectedItem().toString()).split(",");
				for (int i = 0; i < dates.length; i++) {
					allDates.addItem(dates[i]);
				}
			}
		});

		allDates.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chooseBeginTimes.removeAllItems();
				if (!(allDates.getItemCount() == 0)) {
					String time[] = c.getAllBeginTime(allMovies.getSelectedItem().toString(),
							allDates.getSelectedItem().toString()).split(",");
					for (int i = 0; i < time.length; i++) {
						chooseBeginTimes.addItem(time[i]);
					}

				}

			}
		});

		chooseBeginTimes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sessionsOfCurrentMovie.removeAllItems();
				if (!(chooseBeginTimes.getItemCount() == 0)) {
					String sessions[] = c.getSessions(allMovies.getSelectedItem().toString(),
							allDates.getSelectedItem().toString(), chooseBeginTimes.getSelectedItem().toString())
							.split(",");
					for (int i = 0; i < sessions.length; i++) {
						sessionsOfCurrentMovie.addItem(sessions[i]);
					}
				}

			}

		});

		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movieN = allMovies.getSelectedItem().toString();
				mDate = allDates.getSelectedItem().toString();
				mTime = chooseBeginTimes.getSelectedItem().toString();
				new BuyTickets(w, movieN, date, sessionsOfCurrentMovie.getSelectedItem().toString(), login, bot, userType);
				drawSeats(w, id, bot);
			}
		});

		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
	}

	public void drawSeats(JFrame w, int ses_id, String bot) {
		ArrayList<String> items = new ArrayList<String>();
		JComboBox<String> basket = new JComboBox<String>();
		basket.setBounds(50, 400, 190, 40);
		basket.setBackground(Color.BLACK);
		basket.setVisible(true);
		basket.setForeground(Color.WHITE);
		w.add(basket);
		int x = 0;
		int y = 0;
		MyButton b;
		Connect c = new Connect();
		c.connect();
		String info[] = c.getTicketSeats(ses_id).split("\n");
		String time = info[info.length - 1];
		String date = info[info.length - 2];
		String hall = info[info.length - 3];
		String tickets[];
		boolean vip = false;
		boolean bouk = false;
		for (int i = 0; i < info.length - 3; i++) {
			tickets = info[i].split(" ");
			for (int j = 0; j < 14; j++) {
				if (tickets[j].charAt(2) == '#') {
					vip = true;
				} else if (tickets[j].charAt(0) == '+') {
					bouk = true;
				} else if (tickets[j].charAt(1) == '?') {
					bouk = true;
				}
				b = new MyButton(i + 1, j + 1, vip, bouk, x, y);
				b.listener(basket, items);
				x += 60;
				b.addButt(w);
				bouk = false;
				vip = false;
			}
			y += 50;
			x = 0;
		}
		
		JTextArea hallID = new JTextArea("Зала №"+hall.substring(hall.length()-1, hall.length()));
		hallID.setFont(new Font("Georgia", Font.BOLD, 35));
		hallID.setBounds(300, 120, 170, 50);
		hallID.setForeground(Color.WHITE);
		hallID.setBackground(Color.BLACK);
		hallID.setEditable(false);
		hallID.setVisible(true);
		w.add(hallID);
		
		JButton buy = new JButton(bot);
		buy.setBounds(50, 450, 190, 40);
		buy.setBackground(Color.BLACK);
		buy.setForeground(Color.white);
		buy.setFont(new Font("Arial", Font.BOLD, 15));
		buy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!items.isEmpty()) {
					String seating_row = "";
					double price = 0;
					double whole_price = 0;
					String res = "Ви придбали квитки на фільм \"" + movieN + "\"\n";
					SendEmail s = new SendEmail(login, "Ваш квиток на фільм " + movieN, "d", true);
					Connect c = new Connect();
					c.connect();
					boolean promo = false;
					for (String item : items) {
						price = 0;
						if (!isNum(login))
							promo = c.addPromo(login);
						if (promo)
							price += 0.8 * Integer.parseInt(Character.toString(item.charAt(item.length() - 2)));
						else
							price += Integer.parseInt(Character.toString(item.charAt(item.length() - 2)));
						seating_row += getTicketInfo(item) + ',';
						res += item.split("Ціна")[0] + "Ціна - " + price + '\n';
						whole_price += price;
						if (!isNum(login)) {
							try {
								s.sendTicket(login, movieN,
										c.getTickId(ses_id, item.split("Ряд - ")[1].split(",")[0],
												item.split("Місце - ")[1].split(",")[0]),
										date.split("date: ")[1], time.split("time: ")[1],
										Integer.parseInt(hall.split(": ")[1]), item);
							} catch (NumberFormatException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
					res += "Загальна вартість " + whole_price + '$';
					c.updateTickets(ses_id, seating_row);
					basket.removeAllItems();
					items.removeAll(items);
					d.println(res);
				}
			}

			private String getTicketInfo(String item) {
				String row = item.split("Ряд - ")[1].split(",")[0];
				String seat = item.split("Ряд - ")[1].split(",")[1].split("Місце - ")[1];
				return row + ' ' + seat;
			}

		});
		w.add(buy);
	}

	private boolean isNum(String s) {
		for (int i = 0; i < s.length(); i++)
			if (!Character.isDigit(s.charAt(i)))
				return false;
		return true;
	}
}
