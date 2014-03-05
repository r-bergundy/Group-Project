package org.ericsson.parser;

import java.util.Scanner;

public class Main {

	public static void main(String args[]){


		new Main();
	}

	public Main(){
		Scanner option = new Scanner(System.in);
		boolean quit = false;
		int menuItem;		
		System.out.print("Choose menu item: \n---------------\n");		
		System.out.println("1)Validate Primary Keys\n" +
				"2)Validate Data Types\n" + 
				"3)Validate Foreign Key Fields\n" +
				"4)Quit");

		while (!quit) {
			menuItem = option.nextInt();
			switch (menuItem) {
			case 1:
				new ValidatePKFields();
				break;
			case 2:
				new ValidateDataTypes();
				break;
			case 3:
				new ValidateForeignKeys();
				break;
			case 4:
				quit = true;
				break;
			}
		} 
		System.out.println("Bye-bye!");

	}

}


