package upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.util.CellReference;
import org.ericsson.parser.ImportData;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.StartValidation;


/**
 * Servlet implementation class Upload
 */

public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String FilePath;
	private String FileName;
	private int totalErrors;
	ReadFile fileLoader = new ReadFile();
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	/**
	 * handles file upload via HTTP POST method
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		String uploadPath = getServletContext().getRealPath("")
				+ File.separator + UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// parses the request's content to extract file data
			List formItems = upload.parseRequest(request);
			Iterator iterator = formItems.iterator();

			// iterates over form's fields
			while (iterator.hasNext()) {
				FileItem item = (FileItem) iterator.next();
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					String filePath = uploadPath + File.separator + fileName;
					File storeFile = new File(filePath);
					FileName = fileName;
					FilePath = filePath;
					// saves the file on disk

					System.out.println(FileName);
					System.out.println(FilePath);


					item.write(storeFile);
					fileLoader.LoadXLSXFile(FilePath);
					fileLoader.StartProcess();
					//ImportData importData = new ImportData(fileLoader.getWorkbook(), fileLoader.getFkerrors(),
					//fileLoader.getPkerror());
					//importData.populateDatabase();
					System.out.println("IMPORTING FROM SERVLET");
					System.out.println(fileLoader.getTotalNumberErrors());
					totalErrors = fileLoader.getTotalNumberErrors();
				}
			}
			request.setAttribute("messageSuccess", "Upload has been successfully Completed!");
			request.setAttribute("messageFileName", "\nFile Name = " + FileName);
			request.setAttribute("messageErrors", "\nTotal Numbers of Errors found in Dataset = " + totalErrors);
			request.setAttribute("messageFail", "No File/Wrong Fileformat Selected. Please select .xlsx files only");
			request.setAttribute("messageAllErrors", fileLoader.getAllInvalidCellRef());
			

		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		if(fileLoader.getValidFormat()){
			PrintWriter output = response.getWriter();
			//getServletContext().getRequestDispatcher("/successMessage.jsp").forward(request, response);
			output.println("<HTML><BODY>");
			output.println("<title>UploadT</title>");
			output.println("<center>");
			output.println("<p>Upload has been successfully Completed!</p>");
			output.println("<p>File Name = " + FileName + "</p>");
			output.println("<p>Total Numbers of Errors found in Dataset = " + totalErrors + "</p>");
			output.println("<p>");
			output.println("See Invalid CellReferences Found Below:");
			output.println("</center>");
			output.println("<ul>");
			for (CellReference invalidCells: fileLoader.getAllInvalidCellRef()){
				System.out.println(invalidCells.formatAsString());
				output.println("<li>"+invalidCells.formatAsString()+"</li>");
				//request.setAttribute("messageAllErrors", invalidCells.formatAsString());
			}
			output.println("</ul>");
			output.println("</p>");	
			
			output.println("<div>");
			output.println("<input type=\"button\" value=\"Close this window\" onclick=\"self.close()\">");
			output.println("</div>");
			output.println("</BODY></HTML>");
		}
		else if(!fileLoader.getValidFormat()){
			getServletContext().getRequestDispatcher("/failedMessage.jsp").forward(request, response);
		}
	}

}
