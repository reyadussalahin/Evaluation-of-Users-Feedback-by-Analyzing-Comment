
/*
	Class: Main
	Description: Takes database user name, password comment from a file and produce user rating
				 by making object of UserRating output writtens in another file

	Author: Md. Reyadus Salahin (CSE, CUET)

*/


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import java.sql.SQLException;

import myjava.Database;
import myjava.User;

public class Main {

	static String getComment(BufferedReader br) throws IOException {
		String str = null;
		String temp = br.readLine();
		if(temp == null) return null;
		while(!temp.equals("#")) {
			if(str == null) str = temp;
			else str = str + " " + temp;
			temp = br.readLine();
		}
		return str;
	}

/*
	static void showRating(User.Rating rat, PrintWriter out) {
		out.print("env(" + rat.environment() + ") ");
		out.print("san(" + rat.sanitary() + ") ");
		out.print("job(" + rat.jobSecurity() + ") ");
		out.print("prom(" + rat.promotion() + ") ");
		out.print("behave(" + rat.behaviour() + ") ");
		out.println("total(" + rat.total() + ")");
	}
*/
	public static void main(String[] args) {
		try {
			System.out.print("Enter database username: ");
			String user = System.console().readLine();
			System.out.print("Enter database user password: ");
			char[] passArray = System.console().readPassword();
			String pass = new String(passArray);
			
			System.out.println("Creating database connection .....");
			Database db = new Database(user, pass);
			System.out.println("Database connection created successfully.");
			
			db.close();

			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;

			FileOutputStream fos = null;
			OutputStreamWriter osr = null;
			PrintWriter out = null;

			String input = null, output = null;

			System.out.print("Enter comment(input) file path: ");
			input = System.console().readLine();

			System.out.println("Opening comment(input) file .....");
			fis = new FileInputStream(input);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			System.out.println("Comment(input) file opened successfully.");

			System.out.print("Enter output file path: ");
			output = System.console().readLine();
			
			System.out.println("Opening output file .....");
			fos = new FileOutputStream(output);
			osr = new OutputStreamWriter(fos, "UTF-8");
			out = new PrintWriter(osr, true);
			System.out.println("Output file opened successfully.");

			User u = new User(user, pass);

			br.readLine(); /* Skipping First Line of Input File */
			String comment;
			while((comment = getComment(br)) != null) {
				u.addComment(comment);
				u.produceRating();
				User.Rating rat = u.getRating();
				out.print("env(" + rat.environment() + ") ");
				out.print("san(" + rat.sanitary() + ") ");
				out.print("job(" + rat.jobSecurity() + ") ");
				out.print("prom(" + rat.promotion() + ") ");
				out.print("behave(" + rat.behaviour() + ") ");
				out.println("total(" + rat.total() + ")");
			}

			u.close();

			out.close();
			osr.close();
			fos.close();

			br.close();
			isr.close();
			fis.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
