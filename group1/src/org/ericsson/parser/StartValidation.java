package org.ericsson.parser;

import java.util.Scanner;

public class StartValidation {

	//public static void main(String args[]){
	//	new Main();
	//}
	private int totalErrors;
	ValidatePKFields pkfields = new ValidatePKFields();
	ValidateForeignKeys fkfields = new ValidateForeignKeys();

	public StartValidation(){
		pkfields.CheckISFailureClassTableValid();
		pkfields.CheckIsIMSIValid();
		pkfields.CheckIsTACValid(); 
		pkfields.printArrayList();
		new ValidateForeignKeys();
		CalculateTotalNumberOfErrors();
		new ImportData();
	}
	
	public void Menu(){
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
	
	public void CalculateTotalNumberOfErrors(){
		totalErrors = pkfields.getInvalidCellRef().size() + fkfields.getInvlaidCellRef().size();
		System.out.println("Total Numbmer of Errors: " + totalErrors);
	}

	
}

