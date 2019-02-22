

// Class: BanglaRootFinder
// Description: Extract root word from a given word(according to provided postifxes)
// Author: Md. Reyadus Salahin


package myjava;

import java.util.Arrays;

public class BanglaRootFinder {
	private String[] pf;
	private String word;
	private String root;

	public BanglaRootFinder(String[] p) {
		pf = new String[p.length];
		Arrays.sort(p);
		int i = p.length - 1, j = 0;
		while(i >= 0) pf[j++] = p[i--];
	}
	public void add(String w) {
		word = w;
		root = null;
	}
	public void findRoot() {
		for(String s: pf) {
			if(word.length() < s.length()) continue;
			if(word.regionMatches(word.length() - s.length(), s, 0, s.length())) {
				root = word.substring(0, word.length() - s.length());
				break;
			}
		}
		if(root == null) root = word;
	}
	public String getRoot() {
		return root;
	}
}
