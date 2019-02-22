
/*
	Class: UserRating
	Description: Takes database user name and password as input to open database in Constructor
				 Comments are added in this class, and after that by calling produceRating() method
				 rating of a user is produced. One can get that rating by calling getRating() method
				 It's important to use User.close() method at last to close database connection used
				 by User class

	Author: Md. Reyadus Salahin (CSE, CUET)

*/


package myjava;

import java.util.StringTokenizer;
import java.util.TreeSet;
import java.sql.SQLException;

import myjava.Database;
import myjava.PorterStemmer;
import myjava.BanglaRootFinder;
import myjava.Aspect;

public class User {
	public static class Rating {
		private int[] rat;
		private Aspect asp;
		public Rating(int[] r, Aspect a) {
			rat = r;
			asp = a;
		}
		public int environment() {
			return rat[asp.environment()];
		}
		public int sanitary() {
			return rat[asp.sanitary()];
		}
		public int jobSecurity() {
			return rat[asp.jobSecurity()];
		}
		public int promotion() {
			return rat[asp.promotion()];
		}
		public int behaviour() {
			return rat[asp.behaviour()];
		}
		public int total() {
			return rat[asp.getCount()];
		}
		public int[] getRatingArray() {
			return rat;
		}
	}

	private Database db;
	private String com;
	private int lang;
	private Aspect asp;
	private PorterStemmer ps;
	private String[] pf;
	private BanglaRootFinder brf;
	private TreeSet<String> ts;
	private Rating rating;

	public User(String user, String pass) throws SQLException {
		db = new Database(user, pass);
		com = null;
		rating = null;
		pf = db.getWordList("postword");
		brf = new BanglaRootFinder(pf);
		ps = new PorterStemmer();
		asp = new Aspect();
		ts = new TreeSet<String>();
	}

	public void addComment(String c) throws SQLException {
		com = c.toLowerCase();
		lang = 1;
		for(char ch: com.toCharArray()) {
			if((int)ch > 255) {
				lang = 2;
				break;
			}
		}
		rating = null;
	}

	public void produceRating() throws SQLException {
		String[] ss = getSentences();
		ts.clear();
		int[] trat = new int[asp.getCount()+1];
		for(int i=0; i<ss.length; i++) {
			int[] srat = getSentenceRating(ss[i]);
			for(int j=0; j<srat.length; j++) trat[j] += srat[j];
		}
		rating = new Rating(trat, asp);
	}

	private String[] getSentences() {
		StringTokenizer tk = new StringTokenizer(com, ".|।");
		int n = tk.countTokens();
		String[] ss = new String[n];
		for(int i=0; i<n; i++) ss[i] = tk.nextToken();
		return ss;
	}

	private int[] getSentenceRating(String s) throws SQLException {
		StringTokenizer tk = new StringTokenizer(s);
		boolean[] field = new boolean[asp.getCount()];
		int sum = 0, count = 0;
		while(tk.hasMoreTokens()) {
			String w = tk.nextToken();
			if(db.containsWord(w, "stopword")) continue;

			String rw = getRoot(w);
			int t = db.getKeyValue(rw);
			for(int i=0; i<field.length; i++) {
				if(db.containsWord(rw, asp.getName(i) + "word")) field[i] = true;
			}
			if(t == 0 || ts.contains(rw)) continue;

			ts.add(rw);
			sum += t;
			count++;
		}
		
		int rs = (count == 0) ? 0 : (int)Math.round((double)sum/count);
		
		int[] rat = new int[field.length+1];
		for(int i=0; i<field.length; i++) {
			if(field[i]) rat[i] = rs;
		}
		rat[field.length] = rs;

		return rat;
	}

	private String getRoot(String word) {
		if(lang == 1) {
			ps.add(word);
			ps.findRoot();
			return ps.getRoot();
		}
		brf.add(word);
		brf.findRoot();
		return brf.getRoot();
	}

	public Rating getRating() {
		return rating;
	}

	public String[] getUsedKeyword() {
		String[] a = new String[ts.size()];
		a = ts.toArray(a);
		return a;
	}

	public void close() throws SQLException {
		db.close();
	}
}
