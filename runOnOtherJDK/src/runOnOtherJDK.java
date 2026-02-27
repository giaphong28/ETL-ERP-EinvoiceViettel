import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class runOnOtherJDK {
	public static void OM_create_invoice_draft_RT(String header_id)
			throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\om_create_invoice_draft.jar",
				header_id };
		try {

			Process process = Runtime.getRuntime().exec(command);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void AR_create_invoice_draft_RT(String header_id)
			throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\ar_create_invoice_draft.jar",
				header_id };
		try {

			Process process = Runtime.getRuntime().exec(command);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void OM_preview_pdf_RT(String header_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\om_preview_pdf.jar", header_id };

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void AR_preview_pdf_RT(String header_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\ar_preview_pdf.jar", header_id };

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void OM_INVOICE(String header_id, String method_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\OM_INVOICE.jar", header_id, method_id };

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}public static void AR_INVOICE(String header_id, String method_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\AR_INVOICE.jar", header_id, method_id };

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void INV_INVOICE(String header_id, String method_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\INV_INVOICE.jar", header_id, method_id };

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void PO_INVOICE(String header_id, String method_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\PO_INVOICE.jar", header_id, method_id };

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));

			writer.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static String create_invoice_draft_RT_debug(String header_id
			 ) throws Exception {

		String[] command = { "cmd", "/k",
					"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
					"-jar", "E:\\FORMS\\EINVOICE\\debug_create_invoice_draft.jar",
					header_id };
		String result_str = "";

		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if(!line.contains("C:\\"))
				result_str += line;
			}
			reader.close();
		} catch (IOException e) {
			result_str = getStackTrace(e);
		}
		return result_str;
	}

	public static String preview_pdf_RT_debug(String header_id) throws Exception {
		String[] command = { "cmd", "/k",
					"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
					"-jar", "E:\\FORMS\\EINVOICE\\debug_preview_pdf.jar",
					header_id};
		String result_str = "";
		try {
			Process process = Runtime.getRuntime().exec(command);    

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			reader.close();
		} catch (IOException e) {
			result_str = getStackTrace(e);
		}
		return result_str;
	}
	public static String function_debug(String header_id, String method_id) throws Exception {
		String[] command = { "cmd", "/k",
				"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0_45\\bin\\java.exe",
				"-jar", "E:\\FORMS\\EINVOICE\\debug_OM_function.jar",
				header_id , method_id};
	String result_str = "";
	try {
		Process process = Runtime.getRuntime().exec(command);    

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				process.getOutputStream()));
		writer.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line;
		reader.close();
	} catch (IOException e) {
		result_str = getStackTrace(e);
	}
	return result_str;
}
	
	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
}
