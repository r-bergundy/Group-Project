package org.ericsson.parser;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ValidateMNC_MCC_Combination {
	private XSSFSheet mcc_mncTable;
	private ReadFile readFile = new ReadFile();
	private XSSFWorkbook workBook;
	private XSSFSheet baseData;
	private ArrayList<String> validOperatorsList = new ArrayList<String>();
	private ArrayList<String> InValidOperatorsList = new ArrayList<String>();
	private String validOperator;

	public ValidateMNC_MCC_Combination(XSSFWorkbook workbook){
		//setworkbook
		setWorkBook(workbook);
		//setSheets
		setMcc_mncTable(workbook.getSheet(("MCC - MNC Table")));
		setBaseData(workbook.getSheetAt(0));
		//populated list with concatination of mccmnc		
		populatateValidOperatorList(validOperatorsList, getMcc_mncTable());
		ValidateCombination();
		System.out.println("Size of InvalidOperatorsList" + getInValidOperatorsList().size());
	}

	public ValidateMNC_MCC_Combination(){

	}	

	public ArrayList<String> getInValidOperatorsList() {
		return InValidOperatorsList;
	}

	public void setInValidOperatorsList(ArrayList<String> inValidOperatorsList) {
		InValidOperatorsList = inValidOperatorsList;
	}

	public XSSFSheet getMcc_mncTable() {
		return mcc_mncTable;
	}

	public void setMcc_mncTable(XSSFSheet mcc_mncTable) {
		this.mcc_mncTable = mcc_mncTable;
	}

	public ReadFile getReadFile() {
		return readFile;
	}

	public void setReadFile(ReadFile readFile) {
		this.readFile = readFile;
	}

	public XSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(XSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	public XSSFSheet getBaseData() {
		return baseData;
	}

	public void setBaseData(XSSFSheet baseData) {
		this.baseData = baseData;
	}

	public ArrayList<String> getValidOperatorsList() {
		return validOperatorsList;
	}

	public void setValidOperatorsList(ArrayList<String> validOperatorsList) {
		this.validOperatorsList = validOperatorsList;
	}

	public String getValidOperator() {
		return validOperator;
	}

	public void setValidOperator(String validOperator) {
		this.validOperator = validOperator;
	}

	public void populatateValidOperatorList(ArrayList validList, XSSFSheet sheetName){
		Cell cell = null;
		String mcc = null;
		String mnc = null;

		for (int rowNumber = 1; rowNumber <= sheetName.getLastRowNum(); rowNumber++)
		{
			Row row = sheetName.getRow(rowNumber);
			for (int colunmNumber = 0; colunmNumber <= row.getLastCellNum(); colunmNumber++) {
				if (colunmNumber == 0){
					cell = row.getCell(colunmNumber);
					if(!(cell == null)){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							mcc = String.valueOf(cell.getNumericCellValue());
							//validList.add(mcc);
						}
					}
				}
				if (colunmNumber == 1){
					cell = row.getCell(colunmNumber);
					if(!(cell == null)){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							mnc = String.valueOf(cell.getNumericCellValue());
							//validList.add(mnc);
						}
					}
				}

			}
			validOperator = (mcc + "-"+mnc);
			validList.add(validOperator);


		}
		System.out.println("ArrayList Size = " + getValidOperatorsList().size());

	}

	public void ValidateCombination(){
		Cell cell = null;
		XSSFSheet baseData = getBaseData();
		String mcc = null;
		String mnc = null;
		String invlaidConcatMNCMCC;


		for (int rowNumber = 1; rowNumber <= baseData.getLastRowNum(); rowNumber++)
		{
			Row row = baseData.getRow(rowNumber);
			for (int colunmNumber = 0; colunmNumber <= row.getLastCellNum(); colunmNumber++) {
				if (colunmNumber == 4){
					cell = row.getCell(colunmNumber);
					if(!(cell == null)){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							mcc = String.valueOf(cell.getNumericCellValue());
							//validList.add(mcc);
						}
					}
				}
				if (colunmNumber == 5){
					cell = row.getCell(colunmNumber);
					if(!(cell == null)){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							mnc = String.valueOf(cell.getNumericCellValue());
							//validList.add(mnc);
						}
					}
				}

			}
			invlaidConcatMNCMCC = (mcc + "-"+mnc);
			//System.out.println(invlaidConcatMNCMCC);
			CheckIfComboIsValid(invlaidConcatMNCMCC);
		}


	}

	public void CheckIfComboIsValid(String invalidCombination){
		if(!validOperatorsList.contains(invalidCombination)){
			System.out.println("IM IN HEREEEEEEEEEEEEEEEEEEEEEEEEEEE");
			getInValidOperatorsList().add(invalidCombination);
			System.out.println(invalidCombination);

		}
	}
}
