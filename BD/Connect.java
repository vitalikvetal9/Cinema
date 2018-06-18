package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ComboBoxModel;

import acm.io.IODialog;

public class Connect {
	private String connection;
	private String user;
	private String password;
	private Connection c;
	private int ticketid;
	private static IODialog d;

	public Connect(final String connection, final String user, final String password) {
		this.connection = connection;
		this.user = user;
		this.password = password;
		c = null;
		ticketid = 0;
		d = new IODialog();
	}

	public Connect() {
		connection = "jdbc:postgresql://localhost:5432/Cinema";
		user = "postgres";
		password = "12345";
		c = null;
	}

	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(connection, user, password);
			c.setAutoCommit(true);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} /*
			 * finally { try { c.close(); } catch (SQLException e) { e.printStackTrace(); }
			 * System.exit(0); }
			 */
	}

	public String getMovie(final String movie) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"movies\" WHERE \"name\" = '" + movie + "' ;";
			ResultSet rs = st.executeQuery(sql);
			String id = "";
			if (rs.next()) {
				res = rs.getString("name") + '\n' + rs.getString("description") + '\n' + rs.getInt("duration") + '\n'
						+ rs.getInt("production_year") + '\n' + rs.getDate("premiere_date") + '\n'
						+ rs.getDate("end_showing") + '\n' + rs.getString("budget") + '\n'
						+ rs.getString("age_restrictions") + '\n';
				id = Integer.toString(rs.getInt("movieid"));
			}
			sql = "SELECT \"first_name\",\"last_name\" " + "FROM \"actors\" " + "WHERE \"actorid\" IN("
					+ "SELECT \"actorid\"" + "FROM \"acting_movies\"" + "WHERE \"movieid\" = '" + id + "');";
			rs.close();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("first_name") + ' ' + rs.getString("last_name") + ',';
			}
			sql = "SELECT \"first_name\",\"last_name\" FROM \"directors\" WHERE \"directorid\" IN("
					+ "SELECT \"directorid\" FROM \"directing_movies\" WHERE \"movieid\" = '" + id + "');";
			rs.close();
			rs = st.executeQuery(sql);
			res += '\n';
			while (rs.next()) {
				res += rs.getString("first_name") + ' ' + rs.getString("last_name") + ',';
			}
			sql = "SELECT \"award_name\" " + "FROM \"awards\" " + "WHERE \"movie_id\" IN(" + "SELECT \"movieid\""
					+ "FROM \"movies\"" + "WHERE \"movieid\" = '" + id + "');";
			rs.close();
			rs = st.executeQuery(sql);
			res += '\n';
			while (rs.next()) {
				res += rs.getString("award_name") + ',';
			}
			sql = "SELECT \"country_name\" " + "FROM \"countries\" " + "WHERE \"movieid\" IN(" + "SELECT \"movieid\""
					+ "FROM \"movies\"" + "WHERE \"movieid\" = '" + id + "');";
			rs.close();
			rs = st.executeQuery(sql);
			res += '\n';
			while (rs.next()) {
				res += rs.getString("country_name") + ',';
			}
			rs.close();
			sql = "SELECT \"first_name\",\"last_name\" " + "FROM \"actors\" " + "WHERE \"actorid\" IN("
					+ "SELECT \"actorid\"" + "FROM \"acting_movies\"" + "WHERE \"movieid\" = '" + id + "');";
			rs.close();

			sql = "SELECT \"name\" " + "FROM \"genres\" " + " WHERE \"genreid\" IN("
					+ "SELECT \"genreid\" FROM \"genres_movies\" WHERE \"movieid\" IN (" + "SELECT \"movieid\""
					+ "FROM \"movies\"" + "WHERE \"movieid\" = '" + id + "'));";
			rs.close();
			rs = st.executeQuery(sql);
			res += '\n';
			while (rs.next()) {
				res += rs.getString("name") + ',';
			}

			sql = "SELECT \"cover\" FROM \"movies\" WHERE \"movieid\" = '" + id + "';";
			rs.close();
			rs = st.executeQuery(sql);
			res += '\n';
			while (rs.next()) {
				res += rs.getString("cover");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return res;
	}

	public String getMoviesNames() {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + '\n';
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesForBuying() {
		String res = "";
		Statement st = null;
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String today = df.format(d);
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"premiere_date\" + integer'30' > '" + today + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				res += rs.getString("name") + '\n';
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getActorInfo(String fullName) {
		String name = fullName.split(" ")[0];
		String last_name = fullName.split(" ")[1];
		String res = "";
		Statement st = null;
		String id = "";
		try {
			st = c.createStatement();
			String sql = "SELECT * " + "FROM \"actors\" " + " WHERE \"last_name\" = '" + last_name
					+ "' AND \"first_name\" = '" + name + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("first_name") + '\n' + rs.getString("last_name") + '\n' + rs.getString("birth_date")
						+ '\n' + rs.getString("citizenship") + '\n' + rs.getString("picture") + "\n";
				id = Integer.toString(rs.getInt("actorid"));
			}

			st = c.createStatement();
			sql = "SELECT \"cover\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"acting_movies\" WHERE \"actorid\" = '" + id + "');";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("cover") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getDirectorInfo(String fullName) {
		String name = fullName.split(" ")[0];
		String last_name = fullName.split(" ")[1];
		String res = "";
		Statement st = null;
		String id = "";
		try {
			st = c.createStatement();
			String sql = "SELECT * " + "FROM \"directors\" " + " WHERE \"last_name\" = '" + last_name
					+ "' AND \"first_name\" = '" + name + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("first_name") + '\n' + rs.getString("last_name") + '\n' + rs.getString("birth_date")
						+ '\n' + rs.getString("citizenship") + '\n' + rs.getString("picture") + "\n";
				id = Integer.toString(rs.getInt("directorid"));
			}

			st = c.createStatement();
			sql = "SELECT \"cover\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"directing_movies\" WHERE \"directorid\" = '" + id + "');";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("cover") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getFilmNameDueToCover(String cover) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"cover\" = '" + cover + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getAllGenres() {
		String res = "не вибрано,";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"genres\";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ',';
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getAllActors() {
		String res = "не вибрано,";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"first_name\",\"last_name\" FROM \"actors\";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("first_name") + " " + rs.getString("last_name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getAllDirectors() {
		String res = "не вибрано,";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"first_name\",\"last_name\" FROM \"directors\";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("first_name") + " " + rs.getString("last_name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToGenre(String genre) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" " + "FROM \"movies\" " + " WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"genres_movies\" WHERE \"genreid\" IN (" + "SELECT \"genreid\""
					+ "FROM \"genres\"" + "WHERE \"name\" = '" + genre + "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToActor(String fullName) {
		String name = fullName.split(" ")[0];
		String last_name = fullName.split(" ")[1];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"acting_movies\" WHERE \"actorid\" IN (" + "SELECT \"actorid\""
					+ "FROM \"actors\" WHERE \"first_name\" = '" + name + "' AND \"last_name\" = '" + last_name
					+ "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToDirector(String fullName) {
		String name = fullName.split(" ")[0];
		String last_name = fullName.split(" ")[1];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"directing_movies\" WHERE \"directorid\" IN (" + "SELECT \"directorid\""
					+ "FROM \"directors\" WHERE \"first_name\" = '" + name + "' AND \"last_name\" = '" + last_name
					+ "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToGenreAndActor(String fullName, String genre) {
		String name = fullName.split(" ")[0];
		String last_name = fullName.split(" ")[1];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"acting_movies\" WHERE \"actorid\" IN (" + "SELECT \"actorid\""
					+ "FROM \"actors\" WHERE \"first_name\" = '" + name + "' AND \"last_name\" = '" + last_name
					+ "')) AND \"movieid\" IN(" + "SELECT \"movieid\" FROM \"genres_movies\" WHERE \"genreid\" IN ("
					+ "SELECT \"genreid\" FROM \"genres\" WHERE \"name\" = '" + genre + "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToGenreAndDirector(String fullName, String genre) {
		String name = fullName.split(" ")[0];
		String last_name = fullName.split(" ")[1];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"directing_movies\" WHERE \"directorid\" IN (" + "SELECT \"directorid\""
					+ "FROM \"directors\" WHERE \"first_name\" = '" + name + "' AND \"last_name\" = '" + last_name
					+ "')) AND \"movieid\" IN(" + "SELECT \"movieid\" FROM \"genres_movies\" WHERE \"genreid\" IN ("
					+ "SELECT \"genreid\" FROM \"genres\" WHERE \"name\" = '" + genre + "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToActorAndDirector(String fullNameA, String fullNameD) {
		String name_A = fullNameA.split(" ")[0];
		String last_name_A = fullNameA.split(" ")[1];
		String name_D = fullNameD.split(" ")[0];
		String last_name_D = fullNameD.split(" ")[1];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"directing_movies\" WHERE \"directorid\" IN ("
					+ "SELECT \"directorid\" FROM \"directors\" WHERE \"first_name\" = '" + name_D
					+ "' AND \"last_name\" = '" + last_name_D + "')) AND \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"acting_movies\" WHERE \"actorid\" IN (" + "SELECT \"actorid\""
					+ "FROM \"actors\" WHERE \"first_name\" = '" + name_A + "' AND \"last_name\" = '" + last_name_A
					+ "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getMoviesDueToGenreAndActorAndDirector(String genre, String fullNameA, String fullNameD) {
		String name_A = fullNameA.split(" ")[0];
		String last_name_A = fullNameA.split(" ")[1];
		String name_D = fullNameD.split(" ")[0];
		String last_name_D = fullNameD.split(" ")[1];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"directing_movies\" WHERE \"directorid\" IN ("
					+ "SELECT \"directorid\" FROM \"directors\" WHERE \"first_name\" = '" + name_D
					+ "' AND \"last_name\" = '" + last_name_D + "')) AND \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"acting_movies\" WHERE \"actorid\" IN (" + "SELECT \"actorid\""
					+ "FROM \"actors\" WHERE \"first_name\" = '" + name_A + "' AND \"last_name\" = '" + last_name_A
					+ "')) AND \"movieid\" IN(" + "SELECT \"movieid\" FROM \"genres_movies\" WHERE \"genreid\" IN ("
					+ "SELECT \"genreid\"" + "FROM \"genres\"" + "WHERE \"name\" = '" + genre + "'));";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("name") + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public boolean authUser(final String log, char[] pass) {
		boolean res = false;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"account\";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("email").equals(log) && Arrays.equals(pass, rs.getString("password").toCharArray()))
					res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public boolean authCash(String log, char[] pass) {
		boolean res = false;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"cashier\";";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("telephone_num").equals(log)
						&& Arrays.equals(pass, rs.getString("password").toCharArray()))
					res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public boolean reg(final String log, char[] pass) {
		boolean res = false;
		if (authUser(log, pass))
			return res;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"account\" WHERE email = '" + log + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				return res;
			sql = "SELECT * FROM \"account\";";
			rs = st.executeQuery(sql);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (rs.next()) {
				ids.add(rs.getInt("accountid"));
			}
			st = c.createStatement();
			int id = Collections.max(ids) + 1;
			sql = "INSERT INTO \"account\"(accountid,email,password,bought_tickets_num,promo) VALUES('" + id + "','"
					+ log + "','" + charToStr(pass) + "','" + 0 + "','" + false + "');";
			st.executeUpdate(sql);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public boolean checkEmail(final String log) {
		boolean res = false;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"account\" WHERE \"email\" = '" + log + "' ;";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				if (log.equals(rs.getString("email")))
					return true;
			}
		} catch (Exception e) {

		}
		return res;
	}

	public int forgottenPass(final String log) {
		Random r = new Random();
		int code = r.nextInt(10000) + 3333;
		String text = "Якщо ви не знаєте чому це отримали, не відповідайте!\nВаш пароль для відновлення: " + code
				+ ";\n Введіть цей код у відповідне поле додатку.\n\t Команда розробників \"Наше кіно\"";
		String subject = "Відновлення паролю для входу в Кінотеатр \"Наше кіно\"";
		new SendEmail(log, subject, text, false);
		return code;
	}

	private String charToStr(char[] c) {
		String res = "";
		for (int i = 0; i < c.length; i++)
			res += c[i];
		return res;
	}

	public boolean doPassword(char[] pass1, final String log) {
		boolean done = true;
		String pass = charToStr(pass1);
		Statement st = null;
		try {
			st = c.createStatement();
			ArrayList<String> list = new ArrayList<String>();
			String sql = "SELECT \"password\" FROM \"account\" WHERE \"email\" = '" + log + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("password"));
			}
			if (list.contains(pass))
				return false;
			sql = "SELECT \"accountid\",\"password\" FROM \"account\" WHERE \"email\" = '" + log + "';";
			int id = 0;
			String oldpass = "";
			rs = st.executeQuery(sql);
			if (rs.next()) {
				id = rs.getInt("accountid");
				oldpass = rs.getString("password");
			}
			sql = "INSERT INTO \"old_passwords\"(oldpassword, accountid) VALUES('" + oldpass + "','" + id + "')";
			st.executeUpdate(sql);
			sql = "UPDATE \"account\" SET \"password\" = '" + pass + "' WHERE \"accountid\" = '" + id + "';";
			st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return done;
	}

	public int insertFilm(String name, String description, int prod_year, int duration, String premiere_date,
			String budget, short age_restriction, String cover, String genres, String awards, String countries) {
		if (!checkDate(premiere_date))
			throw new NullPointerException("Enter right date");
		if (!isNum(budget))
			throw new NullPointerException("Enter right number");
		String end_showing = getEndDate(premiere_date);
		Statement st = null;
		System.out.println("name: " + name);
		System.out.println("description: " + description);
		System.out.println("prod_year: " + prod_year);
		System.out.println("duration: " + duration);
		System.out.println("premiere_date: " + premiere_date);
		System.out.println("end_showing: " + end_showing);
		System.out.println("budget: " + budget);
		System.out.println("age_restriction: " + age_restriction);
		System.out.println("cover: " + cover);
		System.out.println("genres: " + genres);
		System.out.println("awards: " + awards);
		int id = 0;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"movies\";";
			ResultSet rs = st.executeQuery(sql);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (rs.next()) {
				ids.add(rs.getInt("movieid"));
			}
			st = c.createStatement();
			id = Collections.max(ids) + 1;
			sql = "INSERT INTO \"movies\"(movieid,name,description,production_year,duration,premiere_date,end_showing,budget,age_restrictions,cover) VALUES('"
					+ id + "','" + name + "','" + description + "','" + prod_year + "','" + duration + "','"
					+ premiere_date + "','" + end_showing + "','" + budget + "','" + age_restriction + "','" + cover
					+ "');";
			st.executeUpdate(sql);
			ArrayList<Integer> g_ids = new ArrayList<Integer>();
			// ArrayList<Integer> a_ids = new ArrayList<Integer>();
			// ArrayList<Integer> c_ids = new ArrayList<Integer>();
			if (!genres.contains(","))
				genres += ',';
			for (int i = 0; i < genres.split(",").length; i++) {
				st = c.createStatement();
				sql = "SELECT \"genreid\" FROM \"genres\" WHERE \"name\" = '" + genres.split(",")[i] + "';";
				rs = st.executeQuery(sql);
				if (rs.next())
					g_ids.add(rs.getInt("genreid"));
			}
			if (!awards.contains(","))
				awards += ',';
			for (int i = 0; i < awards.split(",").length; i++) {
				st = c.createStatement();
				sql = "SELECT MAX(\"id\") FROM \"awards\";";
				rs = st.executeQuery(sql);
				int a_id = 1;
				if (rs.next()) {
					System.out.println(rs.getInt("max"));
					a_id = rs.getInt("max") + 1;
				}
				st = c.createStatement();
				sql = "INSERT INTO \"awards\" (id,award_name,movie_id) VALUES('" + a_id + "','" + awards.split(",")[i]
						+ "','" + id + "') ";
				st.executeUpdate(sql);
			}
			if (!countries.contains(","))
				countries += ',';
			for (int i = 0; i < countries.split(",").length; i++) {
				st = c.createStatement();
				sql = "INSERT INTO \"countries\" (movieid,country_name) VALUES('" + id + "','" + countries.split(",")[i]
						+ "') ";
				st.executeUpdate(sql);
			}
			for (int i = 0; i < g_ids.size(); i++) {
				st = c.createStatement();
				sql = "INSERT INTO \"genres_movies\" (movieid,genreid) VALUES('" + id + "','" + g_ids.get(i) + "')";
				st.executeUpdate(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return id;
	}

	private String getEndDate(String premiere_date) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = f.parse(premiere_date);
		} catch (ParseException e) {
		}
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.DATE, 30);
		d = c.getTime();
		return Integer.toString(d.getYear()) + '-' + d.getMonth() + '-' + d.getDay();
	}

	public void addSession(String date, String session_begin, String movieName, int hall_id) throws Exception {
		if (hall_id < 1 || hall_id > 3)
			throw new NullPointerException("No such hall in our cinema");
		if (!checkDate(date))
			throw new NullPointerException("Enter right date");
		if (!checkPremDate(date, movieName))
			throw new NullPointerException("This movie is not longer broadcasted");
		int ses_id = -1;
		int movieid = -1;
		String session_end = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"movieid\",\"duration\" FROM \"movies\" WHERE \"name\" = '" + movieName + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				movieid = rs.getInt("movieid");
				session_end = makeTime(session_begin, rs.getInt("duration"));
			}
			// duration
			else
				throw new NullPointerException("No such film in our cinema");
			sql = "SELECT \"session_begin\", \"session_end\" FROM \"session\" WHERE \"date\" = '" + date
					+ "' AND \"hallid\" = '" + hall_id + "';";
			st = c.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next())
				if (!isFree(rs.getString("session_begin"), rs.getString("session_end"), session_begin, session_end))
					throw new NullPointerException("This hall is unavaliable at this time");
			st = c.createStatement();
			sql = "SELECT \"sessionid\" FROM \"session\";";
			rs = st.executeQuery(sql);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (rs.next())
				ids.add(rs.getInt("sessionid"));
			ses_id = Collections.max(ids) + 1;
			st = c.createStatement();
			sql = "INSERT INTO \"session\"(sessionid,date,session_begin,session_end,movieid,hallid) VALUES('" + ses_id
					+ "','" + date + "','" + session_begin + "','" + session_end + "','" + movieid + "','" + hall_id
					+ "');";
			st.executeUpdate(sql);
			if (ticketid == 0) {
				st = c.createStatement();
				sql = "SELECT MAX(\"ticketid\") FROM \"tickets\"";
				rs = st.executeQuery(sql);
				if (rs.next())
					ticketid = rs.getInt("max") + 1;
				System.out.println(ticketid);
				boolean vip_hall = false;
				boolean vip_seat = false;
				int rows = 0;
				st = c.createStatement();
				sql = "SELECT * FROM \"halls\" WHERE \"hallid\" = '" + hall_id + "';";
				rs = st.executeQuery(sql);
				int seats = 0;
				if (rs.next()) {
					seats = rs.getInt("whole_seats_num");
					vip_hall = rs.getBoolean("vip");
					rows = rs.getInt("row_count");
				}
				int j = 0;
				int price = 2;
				for (int i = 0; i < seats; i++) {
					if (vip_hall && j == rows) {
						vip_seat = true;
						price = 3;
					}
					st = c.createStatement();
					sql = "INSERT INTO \"tickets\" (ticketid,bought,booked,vip,seat,row,sessionid,price) VALUES ('"
							+ (ticketid + i) + "','" + "false" + "','" + "false" + "','" + vip_seat + "','" + (i + 1)
							+ "','" + ++j + "','" + ses_id + "','" + price + "');";
					st.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			throw new NullPointerException(e.getMessage());
		}
	}

	public void addDirector(int movie_id, String l_name, String f_name, String b_date, String citizenship,
			String link) {
		if (!DateChecker(b_date))
			throw new NullPointerException("Enter right date");
		if (link == "")
			link = "The picture'll be here later";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT MAX(\"directorid\") FROM \"directors\";";
			ResultSet rs = st.executeQuery(sql);
			int id = 1;
			if (rs.next())
				id = rs.getInt("max") + 1;
			st = c.createStatement();
			sql = "INSERT INTO \"directors\" (directorid,last_name,first_name,birth_date,citizenship,picture) VALUES ('"
					+ id + "','" + l_name + "','" + f_name + "','" + b_date + "','" + citizenship + "','" + link
					+ "');";
			st.executeUpdate(sql);
			st = c.createStatement();
			sql = "INSERT INTO \"directing_movies\" (movieid,directorid) VALUES('" + movie_id + "','" + id + "');";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addActor(int movie_id, String l_name, String f_name, String b_date, String citizenship, String link) {
		if (!DateChecker(b_date))
			throw new NullPointerException("Enter right date");
		if (link == "")
			link = "The picture'll be here later";
		int may_id = -1;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"actorid\" FROM \"actors\" WHERE \"last_name\" = '" + l_name
					+ "' AND \"first_name\" = '" + f_name + "' AND \"birth_date\" = '" + b_date + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				may_id = rs.getInt("actorid");
			if (may_id != -1) {
				st = c.createStatement();
				sql = "INSERT INTO \"acting_movies\" (movieid,actorid) VALUES('" + movie_id + "','" + may_id + "');";
				st.executeUpdate(sql);
				throw new NullPointerException("Цей актор вже є в базі, його додано до фільму");
			}
			st = c.createStatement();
			sql = "SELECT MAX(\"actorid\") FROM \"actors\";";
			rs = st.executeQuery(sql);
			int id = 1;
			if (rs.next())
				id = rs.getInt("max") + 1;
			st = c.createStatement();
			sql = "INSERT INTO \"actors\" (actorid,last_name,first_name,birth_date,citizenship,picture) VALUES ('" + id
					+ "','" + l_name + "','" + f_name + "','" + b_date + "','" + citizenship + "','" + link + "');";
			st.executeUpdate(sql);
			st = c.createStatement();
			sql = "INSERT INTO \"acting_movies\" (movieid,actorid) VALUES('" + movie_id + "','" + id + "');";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private boolean checkPremDate(String date, String movieName) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Statement st = null;
		try {
			Date dt1 = f.parse(date);
			Date dt2 = f.parse(date);
			dt1.setTime(dt1.getTime() + TimeUnit.DAYS.toMillis(30));
			String end_date = "";
			String prem_date = "";
			st = c.createStatement();
			String sql = "SELECT \"premiere_date\", \"end_showing\" FROM \"movies\" WHERE \"name\" = '" + movieName
					+ "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				prem_date = rs.getString("premiere_date");
				end_date = rs.getString("end_showing");
			} else
				return false;
			Date enD = f.parse(end_date);
			Date prD = f.parse(prem_date);
			if (dt2.before(prD) || dt2.after(enD))
				return false;
		} catch (ParseException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean isNum(String s) {
		for (int i = 0; i < s.length(); i++)
			if (!Character.isDigit(s.charAt(i)))
				return false;
		return true;
	}

	private boolean DateChecker(String date) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = f.parse(date);
		} catch (ParseException e) {
		}
		return true;
	}

	private boolean checkDate(String date) {
		boolean res = false;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = f.parse(date);
			Date currD = new Date();
			if (d.after(currD))
				res = true;
		} catch (ParseException e) {
		}
		return res;
	}

	private boolean isFree(String beg_time, String end_time, String beg_time2, String end_time2) {
		int b_time = Integer.parseInt(beg_time.split(":")[0]) * 60 + Integer.parseInt(beg_time.split(":")[1]);
		int e_time = Integer.parseInt(end_time.split(":")[0]) * 60 + Integer.parseInt(end_time.split(":")[1]);
		int b_time2 = Integer.parseInt(beg_time2.split(":")[0]) * 60 + Integer.parseInt(beg_time2.split(":")[1]);
		int e_time2 = Integer.parseInt(end_time2.split(":")[0]) * 60 + Integer.parseInt(end_time2.split(":")[1]);
		return (b_time2 >= b_time && b_time2 <= e_time) ? false
				: (e_time2 >= b_time && e_time2 <= e_time) ? false : true;
	}

	public void deleteSession(final int id) {
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"sessionid\" FROM \"session\";";
			ResultSet rs = st.executeQuery(sql);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (rs.next()) {
				ids.add(rs.getInt("sessionid"));
			}
			if (!ids.contains(id))
				throw new NullPointerException("No such session in our cinema");
			st = c.createStatement();
			sql = "DELETE FROM \"tickets\" WHERE \"sessionid\" = '" + id + "';";
			st.executeUpdate(sql);
			st = c.createStatement();
			sql = "DELETE FROM \"session\" WHERE \"sessionid\" = '" + id + "';";
			st.executeUpdate(sql);
		} catch (Exception e) {
			d.println(e.getMessage());
		}
	}

	private String makeTime(String time, int min) {
		int hours = 0;
		int minutes = 0;
		try {
			String timeValues[] = time.split(":");
			hours = Integer.parseInt(timeValues[0]);
			minutes = Integer.parseInt(timeValues[1]);
			hours += min / 60;
			minutes += min % 60;
		} catch (Exception e) {
			throw new NullPointerException("Enter right time!");
		}
		return Integer.toString(hours) + ':' + Integer.toString(minutes);
	}

	public String getPopularMovies(String order) {
		if (!(order.equals("asc") || order.equals("desc")))
			throw new NullPointerException("This is a shit");
		String res = "";
		int id = 0;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"name\" FROM \"movies\" WHERE \"movieid\" IN (SELECT \"movieid\" FROM \"session\" INNER JOIN \"tickets\" ON "
					+ "\"session\".\"sessionid\" = \"tickets\".\"sessionid\" WHERE \"bought\" IS false AND \"booked\" IS false "
					+ "GROUP BY \"movieid\" ORDER BY SUM(\"price\") " + order + " limit 3);";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				res += rs.getString("name") + ',';
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res.substring(0, res.length() - 1);
	}

	public String getPopularDirectors() {
		String films[] = getPopularMovies("desc").split(",");
		String res = "";
		Statement st = null;
		try {
			for (int i = 0; i < films.length; i++) {
				st = c.createStatement();
				String sql = "SELECT DISTINCT \"first_name\",\"last_name\" FROM \"directors\" WHERE \"directorid\" IN "
						+ "(SELECT \"directorid\" FROM \"directing_movies\" WHERE \"movieid\" IN"
						+ "( SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + films[i] + "'))";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next())
					res += rs.getString("first_name") + ' ' + rs.getString("last_name") + ',';
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res.substring(0, res.length() - 1);
	}

	public String getPopularGenres() {
		String films[] = getPopularMovies("desc").split(",");
		String res = "";
		Statement st = null;
		try {
			for (int i = 0; i < films.length; i++) {
				st = c.createStatement();
				String sql = "SELECT DISTINCT \"name\" FROM \"genres\" WHERE \"genreid\" IN "
						+ "(SELECT \"genreid\" FROM \"genres_movies\" WHERE \"movieid\" IN"
						+ "( SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + films[i] + "'))";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next())
					res += rs.getString("name") + ',';
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res.substring(0, res.length() - 1);
	}

	public String getPopularActors() {
		String films[] = getPopularMovies("desc").split(",");
		String res = "";
		Statement st = null;
		try {
			for (int i = 0; i < films.length; i++) {
				st = c.createStatement();
				String sql = "SELECT DISTINCT \"first_name\",\"last_name\" FROM \"actors\" WHERE \"actorid\" IN "
						+ "(SELECT \"actorid\" FROM \"acting_movies\" WHERE \"movieid\" IN"
						+ "( SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + films[i] + "'))";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next())
					res += rs.getString("first_name") + ' ' + rs.getString("last_name") + ',';
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res.substring(0, res.length() - 1);
	}

	public int getProfit(String begin, String end) {
		if (!DateChecker(begin) || !DateChecker(end)) {
			d.println("Enter right date");
			throw new NullPointerException("Enter right date");
		}
		int res = 0;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT SUM(\"price\") FROM \"tickets\" INNER JOIN \"session\" "
					+ "ON \"tickets\".\"sessionid\" = \"session\".\"sessionid\" "
					+ "WHERE \"bought\" IS true AND \"date\" >= '" + begin + "' AND \"date\" <= '" + end + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				res = rs.getInt("sum");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getNonPopularDays() {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT DISTINCT  \"date\", SUM(\"price\") FROM \"session\" INNER JOIN \"tickets\""
					+ "ON \"session\".\"sessionid\" = \"tickets\".\"sessionid\" GROUP BY \"date\""
					+ "ORDER BY SUM(\"price\") asc limit 3;";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				res += rs.getString("date") + ',';
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getTicketSeats(final int ses_id) {
		String res = "";
		String date = "";
		String session_time = "";
		int movie_id = 0;
		int hall_id = 0;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"session\" WHERE \"sessionid\" = '" + ses_id + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				date = rs.getString("date");
				session_time = rs.getString("session_begin") + " - " + rs.getString("session_end");
				movie_id = rs.getInt("movieid");
				hall_id = rs.getInt("hallid");
			} else
				throw new NullPointerException("Такого сеансу не існує");
			sql = "SELECT * FROM \"tickets\" WHERE \"sessionid\" = '" + ses_id + "' ORDER BY \"ticketid\" ASC;";
			int i = 0;
			st = c.createStatement();
			rs = st.executeQuery(sql);
			char bought;
			char booked;
			char vip;
			String t = "";
			char spliter;
			while (rs.next()) {
				bought = rs.getBoolean("bought") ? '+' : '-';
				booked = rs.getBoolean("booked") ? '?' : '!';
				vip = rs.getBoolean("vip") ? '#' : '$';
				spliter = ++i % 14 == 0 ? '\n' : ' ';
				t = Character.toString(bought) + Character.toString(booked) + Character.toString(vip)
						+ Character.toString(spliter);
				res += t;
			}
			res += "hall: " + hall_id + '\n' + "date: " + date + '\n' + "time: " + session_time;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public void updateTickets(int ses_id, String seating_row) {
		String pairs[] = seating_row.split(",");
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"session\" WHERE \"sessionid\" = '" + ses_id + "';";
			ResultSet rs = st.executeQuery(sql);
			if (!rs.next()) {
				throw new NullPointerException("Такого сеансу не існує");
			}
			for (int i = 0; i < pairs.length; i++) {
				// st = c.createStatement();
				sql = "UPDATE \"tickets\" SET \"bought\" = true WHERE \"sessionid\" = '" + ses_id + "' AND \"row\" = '"
						+ pairs[i].split(" ")[0] + "' AND \"seat\" = '" + pairs[i].split(" ")[1] + "';";
				st.executeUpdate(sql);
				// c.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public String getMoviesSessions(final String movieName, final String date) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"sessionid\" FROM \"session\" WHERE movieid IN("
					+ "SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + movieName + "') AND \"date\" ='" + date
					+ "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				res += rs.getString("sessionid") + ",";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getAllDates(final String movieName) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT DISTINCT \"date\" FROM \"session\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + movieName + "');";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				res += rs.getString("date") + ",";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getAllBeginTime(final String movieName, String date) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT DISTINCT \"session_begin\" FROM \"session\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + movieName + "') AND \"date\" = '" + date
					+ "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				res += rs.getString("session_begin") + ",";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String getSessions(final String movieName, String date, String time) {
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT DISTINCT \"sessionid\" FROM \"session\" WHERE \"movieid\" IN("
					+ "SELECT \"movieid\" FROM \"movies\" WHERE \"name\" = '" + movieName + "') AND \"date\" = '" + date
					+ "' AND \"session_begin\" = '" + time + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				res += rs.getInt("sessionid") + ",";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public int getTickId(int ses_id, String row, String seat) {
		int id = 0;
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT \"ticketid\" FROM \"tickets\" WHERE \"sessionid\" = '" + ses_id + "' AND \"row\" = '"
					+ row + "' AND \"seat\" = '" + seat + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				id = rs.getInt("ticketid");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return id;
	}

	public boolean addPromo(String whom) {
		Statement st = null;
		boolean res = false;
		int tick_num = -1;
		String promo_date = "";
		try {
			st = c.createStatement();
			String sql = "SELECT * FROM \"account\" WHERE \"email\" = '" + whom + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				tick_num = rs.getInt("bought_tickets_num");
				if (tick_num % 5 == 0) {
					promo_date = rs.getString("bought_tickets_num");
					res = true;
				}
			}
			if (!promo_date.isEmpty()) {
				res = true;
			}
			tick_num += 1;
			st = c.createStatement();
			sql = "UPDATE \"account\" set \"bought_tickets_num\" = '" + tick_num + "'WHERE \"email\" = '" + whom + "';";
			st.executeUpdate(sql);
			if (tick_num % 5 == 0) {
				if (promo_date.isEmpty()) {
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					Date now = new Date();
					now.setYear(now.getYear() + 1);
					promo_date = f.format(now);
				} else
					promo_date = Integer.toString((Integer.parseInt(promo_date.split("-")[0]) + 1)) + '-'
							+ promo_date.split("-")[1] + '-' + promo_date.split("-")[2];
				System.out.println(promo_date);
				sql = "UPDATE \"account\" SET \"promo_code_end_date\" = '" + promo_date
						+ "', \"promo\" = 'true' WHERE \"email\" = '" + whom + "';";

			} else
				sql = "UPDATE \"account\" SET \"promo_code_end_date\" = '" + "2100-12-12"
						+ "', \"promo\" = 'false' WHERE \"email\" = '" + whom + "';";
			st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String[] getMoviesToShow() {
		Statement st = null;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		String currD = f.format(d);
		String res[];
		try {
			st = c.createStatement();
			String sql = "SELECT name FROM \"movies\" WHERE \"end_showing\" > '" + currD + "';";
			ResultSet rs = st.executeQuery(sql);
			ArrayList<String> movies = new ArrayList<String>();
			int i = 0;
			String m = "";
			while (rs.next()) {
				m = rs.getString("name");
				if (m.contains(":"))
					m = m.split(":")[0];
				movies.add(m);
			}
			res = new String[movies.size()];
			movies.toArray(res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	public String ActorIDDueToFullName(String fullName) {
		String last_name = fullName.split(" ")[1];
		String first_name = fullName.split(" ")[0];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT actorid " + "FROM \"actors\" " + " WHERE \"last_name\" = '" + last_name
					+ "' AND \"first_name\" = '" + first_name + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("actorid");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public String DirectorIDDueToFullName(String fullName) {
		String last_name = fullName.split(" ")[1];
		String first_name = fullName.split(" ")[0];
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "SELECT directorid " + "FROM \"directors\" " + " WHERE \"last_name\" = '" + last_name
					+ "' AND \"first_name\" = '" + first_name + "';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				res += rs.getString("directorid");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return res;
	}

	public void addActorToTheMovie(String movie_id, String actor_id) {
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "INSERT INTO \"acting_movies\" (movieid, actorid) VALUES('" + movie_id + "','" + actor_id
					+ "');";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addDirectorToTheMovie(String movie_id, String director_id) {
		Statement st = null;
		try {
			st = c.createStatement();
			String sql = "INSERT INTO \"directing_movies\" (movieid, directorid) VALUES('" + movie_id + "','"
					+ director_id + "');";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
