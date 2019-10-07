package proj2;

import java.util.Scanner;

public class TreeMain {
	
	/** Array containing the Pretraversal order from input */
	public static char pretrav[];
	
	/** Array containing the Posttraversal order from input */
	public static char posttrav[];

	public static void main(String[] args) {
		// Setup Scanner
		Scanner input = new Scanner(System.in);
		String pretravString = getInputString(input);
		String posttravString = getInputString(input);
		int numNodes = pretravString.length();
		
		// Initialize arrays with proper length
		pretrav = new char[numNodes];
		posttrav = new char[numNodes];
		
		// Fill Pretraversal array
		for (int i = 0; i < numNodes; i++) {
			pretrav[i] = pretravString.charAt(i);
		}
		
		// Fill Posttraversal array
		for (int i = 0; i < numNodes; i++) {
			posttrav[i] = posttravString.charAt(i);
		}
	}
	
	/**
	 * Scans the first line of input and returns the number of nodes contained
	 * @param input Scanner object parsing the tree input
	 * @return Number of nodes contained in the input
	 */
	public static String getInputString (Scanner input) {
		String inputString = "";
		String temp = input.next();
		if (temp.equals("\n")) {
			temp = input.next();
		}
		if (temp.equals(">") || temp.equals("<")) {
			temp = input.next();
			while (!temp.equals(".")) {
				if (!temp.equals(",")) {
					inputString += temp;
				}
				temp = input.next();
			}
		}
		return inputString;
	}	
}








