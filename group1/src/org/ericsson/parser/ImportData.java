package org.ericsson.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.entities.AccessCapability;
import com.entities.CallFailure;
import com.entities.Device;
import com.entities.EventCause;
import com.entities.FailureClass;
import com.entities.Operator;
import com.entities.UEAccessCapability;
import com.entities.UserEquipment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.PersistenceUtil;

public class ImportData {

	private FileInputStream xlsxFile;
	private XSSFWorkbook workbook;
	private XSSFSheet currentSheet;
	private ArrayList<String> accessCapabilities = new ArrayList<String>();
	private ArrayList<String> operators = new ArrayList<String>();
	private ArrayList<String> eventCauses = new ArrayList<String>();
	private HashMap<Integer, EventCause> hmpEventCauses = new HashMap<Integer, EventCause>();
	private long startTime;
	IterateThroughFile iterateThroughFile = new IterateThroughFile();
	ReadFile readFile = new ReadFile();
	ValidatePKFields validPK = new ValidatePKFields();

	public ImportData() {
		//CreateWorkBook(filePath);
		populateDatabase();

	}

	public void populateDatabase(){

		startTime = System.currentTimeMillis();

		populateEventCause();
		populateAccessCapability();
		populateUserEquipment();
		populateFailureClass();
		populateUEAccessCapability();
		populateOperator();
		populateDevice();
		populateCallFailure();
		try {
			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void populateCallFailure(){
		ChooseSheet("Base Data");

		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {
			CallFailure callFailure = new CallFailure();

			Row row = currentSheet.getRow(i);

			callFailure.setDuration((int)row.getCell(7).getNumericCellValue());
			callFailure.setNEVersion(row.getCell(9).getStringCellValue());
			callFailure.setDateTime(row.getCell(0).getDateCellValue());
			callFailure.setCellID((int)row.getCell(6).getNumericCellValue());
			callFailure.setHier3id(String.valueOf((int)row.getCell(11).getNumericCellValue()));
			callFailure.setHier32id(String.valueOf((int)row.getCell(12).getNumericCellValue()));
			callFailure.setHier321id(String.valueOf((int)row.getCell(13).getNumericCellValue()));

			if(row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING){
				continue;
			}
			FailureClass thisFC = (FailureClass) PersistenceUtil.findEntityByIntPK
					(FailureClass.class, (int) row.getCell(2).getNumericCellValue());
			callFailure.setFailureclass(thisFC);


			Device thisDevice = (Device) PersistenceUtil.findEntityByIntPK
					(Device.class, String.valueOf(((long)(row.getCell(10).getNumericCellValue()))));
			callFailure.setDevice(thisDevice);

			if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC){
				String idInExcelSheet = String.valueOf((int)row.getCell(8).getNumericCellValue()) +
						"-" + String.valueOf((int)row.getCell(1).getNumericCellValue());

				EventCause thisEC = hmpEventCauses.get(idInExcelSheet.hashCode());			
				callFailure.setEventcause(thisEC);

			}else if(row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING){
				continue;
			}

			PersistenceUtil.persistTrust(callFailure);
		}

		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated CallFailure");

	}

	private void populateEventCause() {

		ChooseSheet("Event-Cause Table");
		ArrayList<CellReference> valid = new ArrayList<CellReference>();
		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {
			EventCause eventCause = new EventCause();
			Row row = currentSheet.getRow(i);


			eventCause.setCauseCode((int) row.getCell(0)
					.getNumericCellValue());
			CellReference cellref = new CellReference(row.getCell(0));
			valid.add(cellref);

			eventCause.setEventID((int) row.getCell(1)
					.getNumericCellValue());
			CellReference cellref2 = new CellReference(row.getCell(1));
			valid.add(cellref);
			eventCause.setDescription(row.getCell(2).getStringCellValue());
			CellReference cellref3 = new CellReference(row.getCell(2));
			valid.add(cellref3);

			PersistenceUtil.persist(eventCause);

			String concatID = String.valueOf(eventCause.getCauseCode()) 
					+ "-" + String.valueOf(eventCause.getEventID());
			hmpEventCauses.put(concatID.hashCode(), eventCause);
		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated EventCause");

	}
	private void populateFailureClass(){
		ChooseSheet("Failure Class Table");

		for (int i =1; i< currentSheet.getLastRowNum(); i++){
			FailureClass failureClass = new FailureClass();
			Row row = currentSheet.getRow(i);

			failureClass.setFailureClassID((int)row.getCell(0)
					.getNumericCellValue());
			CellReference cellRef = new CellReference(row.getCell(0));
			if(!validPK.getInvalidCellRef().contains(cellRef)){
				failureClass.setDescription(row.getCell(1)
						.getStringCellValue());
				PersistenceUtil.persist(failureClass);

			}
		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated FailureClass");
	}

	private void populateUserEquipment() {

		ChooseSheet("UE Table");


		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {
			UserEquipment userEquipment = new UserEquipment();
			Row row = currentSheet.getRow(i);

			userEquipment.setTac((int)(row.getCell(0).getNumericCellValue()));
			CellReference cellref = new CellReference(row.getCell(0));
			if (!validPK.getInvalidCellRef().contains(cellref)){
				if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC){
					userEquipment.setMarketingName(String.valueOf(row.getCell(1).getNumericCellValue()));

				}
				else if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING){
					userEquipment.setMarketingName(row.getCell(1).getStringCellValue());
				}
				userEquipment.setManufacturer(row.getCell(2).getStringCellValue());
				if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC){
					userEquipment.setModel(String.valueOf(row.getCell(4).getNumericCellValue()));

				}
				else if(row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING){
					userEquipment.setModel(row.getCell(4).getStringCellValue());
				}
				userEquipment.setVenderName(row.getCell(5).getStringCellValue());
				userEquipment.setUEType(row.getCell(6).getStringCellValue());
				userEquipment.setOperatingSystem(row.getCell(7).getStringCellValue());
				userEquipment.setInputMode(row.getCell(8).getStringCellValue());
				PersistenceUtil.persist(userEquipment);
			}
		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated UserEquipment");

	}

	private void populateAccessCapability() {
		ChooseSheet("UE Table");
		accessCapabilities = new ArrayList<String>();

		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {

			Row row = currentSheet.getRow(i);
			String[] accessCapabilitiesForRow = row.getCell(3)
					.getStringCellValue().split(", ");

			for (String s : accessCapabilitiesForRow) {
				if (!accessCapabilities.contains(s)) {
					accessCapabilities.add(s);
				}
			}

		}

		for (String s : accessCapabilities) {
			AccessCapability accessCapability = new AccessCapability();
			accessCapability.setAccessName(s);
			PersistenceUtil.persist(accessCapability);
		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated AccessCapability");
	}

	private void populateDevice(){
		ChooseSheet("Base Data");

		ArrayList<String> alreadyPersisted = new ArrayList<String>();
		for(int i =1; i < currentSheet.getLastRowNum(); i++){
			Device device = new Device();
			Row row = currentSheet.getRow(i);

			String imsi = String.valueOf(((long)(row.getCell(10).getNumericCellValue())));	
			CellReference cellref = new CellReference(row.getCell(10));
			if(!validPK.getInvalidCellRef().contains(cellref))
			{
				device.setImsi(imsi);


				UserEquipment thisUE = (UserEquipment) PersistenceUtil.findEntityByIntPK
						(UserEquipment.class, (int) row.getCell(3).getNumericCellValue());
				device.setUserequipment(thisUE);

				String idInExcelSheet = String.valueOf((int)row.getCell(4).getNumericCellValue()) +
						"-" + String.valueOf((int)row.getCell(5).getNumericCellValue());
				for (int j = 0; j< operators.size() ; j++){
					String s = operators.get(j);
					if (s.equals(idInExcelSheet)){
						Operator thisOp = (Operator) PersistenceUtil.findEntityByIntPK(Operator.class, 
								j+1);
						device.setOperator(thisOp);
					}
				}

				if (! alreadyPersisted.contains(device.getImsi())){
					alreadyPersisted.add(device.getImsi());
					PersistenceUtil.persist(device);
				}

			}

		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated Device");
	}
	private void populateOperator(){
		ChooseSheet("MCC - MNC Table");


		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {
			Operator operator = new Operator();
			Row row = currentSheet.getRow(i);       

			operator.setMcc((int)row.getCell(0).getNumericCellValue());
			operator.setMnc((int)row.getCell(1).getNumericCellValue());
			operator.setCountry(row.getCell(2).getStringCellValue());
			operator.setOperatorName(row.getCell(3).getStringCellValue());
			PersistenceUtil.persist(operator);

			//Populate operators
			String concatID = String.valueOf(operator.getMcc()) + "-" + String.valueOf(operator.getMnc());
			operators.add(concatID);

		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated Operator");
	}
	private void populateArrayOperators(){
		for(int i =1; i < currentSheet.getLastRowNum(); i++){
			Operator op = (Operator) PersistenceUtil.
					findEntityByIntPK(Operator.class,i);
			String concatID = String.valueOf(op.getMcc()) + "-" + String.valueOf(op.getMnc());
			operators.add(concatID);
		}
	}
	private void populateUEAccessCapability() {

		ChooseSheet("UE Table");

		if (accessCapabilities.size()==0){
			populateArrayAccessCapabilities();
		}

		for (int i = 1; i < currentSheet.getLastRowNum(); i++) {

			Row row = currentSheet.getRow(i);

			String[] accessCapabilitiesForRow = row.getCell(3)
					.getStringCellValue().split(", ");

			for (String s : accessCapabilitiesForRow) {

				UEAccessCapability ueac = new UEAccessCapability();

				UserEquipment thisUE = (UserEquipment) PersistenceUtil.findEntityByIntPK(UserEquipment.class, 
						(int) row.getCell(0).getNumericCellValue());
				ueac.setUserequipment(thisUE);

				if (accessCapabilities.contains(s)) {
					ueac.setAccesscapability(findAccessCapabilityByName(s));
					PersistenceUtil.persist(ueac);
				}
			}

		}
		System.out.println((System.currentTimeMillis()-startTime)/1000 + "s: Populated UEAccessCapability");
	}

	private void populateArrayAccessCapabilities(){

		for (int i = 1 ; i < currentSheet.getLastRowNum() ; i++){
			AccessCapability ac = (AccessCapability) PersistenceUtil
					.findEntityByIntPK(AccessCapability.class, i);
			accessCapabilities.add(ac.getAccessName());//NullPointerExc
		}

	}

	private AccessCapability findAccessCapabilityByName(String name){
		AccessCapability ac = new AccessCapability();

		for (int i = 0; i < accessCapabilities.size() ; i++){
			String s = accessCapabilities.get(i);
			if (s.equals(name)){
				return (AccessCapability) PersistenceUtil
						.findEntityByIntPK(AccessCapability.class, i+1);
			}
		}

		return ac;

	}

	private void ChooseSheet(String sheetName) {
		currentSheet = iterateThroughFile.getWorkbook().getSheet(sheetName);
	}

	private void CreateWorkBook(String filePath) {

		try {
			xlsxFile = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(xlsxFile);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("In correct file passed to create workbook");
		}

	}

}