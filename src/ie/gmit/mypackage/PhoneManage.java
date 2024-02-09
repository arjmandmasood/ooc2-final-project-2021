package ie.gmit.mypackage;

import ie.gmit.mypackage.PhoneManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhoneManage implements Serializable {

	private static final long serialVersionUID = 1L;

	// Create Phone ArrayList
	private List<Phone> phoneList = null;

	// Constructor
	public PhoneManage() {
		phoneList = new ArrayList<Phone>();
	}

	// phone Add Method
	public boolean addPhone(Phone phoneObject) {

		// Loop over all phone and check if new phone is already on List
		for (Phone phone : phoneList) { // Foreach phone in the phoneList
			if (phone.getId().equals(phoneObject.getId())) {
				System.out.println("Phone NOT Added to Phone List. phone already on Phone List!");
				return false;
			}
		}

		return phoneList.add(phoneObject);

	}

	// Phone Remove Method
	public boolean removePhone(Phone phoneObject) {
		return phoneList.remove(phoneObject);
	}

	public Phone deletePhoneByNumber(int number) {
		try {
			return phoneList.remove(number - 1);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("There are " + phoneList.size()
					+ "phones on the list. Please pick a number from 1 to " + phoneList.size());
		}
		return null;
	}

	public int findTotalPhone() {
		// returns the current number of Phone in the ArrayList
		return phoneList.size();
	}

	public String listAllPhone() {
		// Create a StringBuilder object
		StringBuilder sb = new StringBuilder();
		int counter = 1;

		sb.append(String.format("%-15s%-23s%-29s%-23s%-20s\n", "No.", "ID", "Title","Release Year", "Price"));
		sb.append(String.format("===============================================================\n"));
		for (Phone phone : this.phoneList) {
			sb.append(counter + ": \t\t" + phone.findAllFieldValuesInCSVFormat().replace(",", "\t\t\t") + "\n");
			counter++;
		}

		return sb.toString();
	}

	public void loadPhoneFromCSVFile(File phoneCSVFile) {
		FileReader phoneCSVFileReader = null;
		BufferedReader bufferedPhoneCSVFileReader = null;
		String bufferData = null; // Used to store lines of data we read from the buffer

		// Create a file reader
		try {
			phoneCSVFileReader = new FileReader(phoneCSVFile);
			// Add a buffer to the file reader
			bufferedPhoneCSVFileReader = new BufferedReader(phoneCSVFileReader);
			// Read first line of file and discard it. It contains column headers.
			bufferedPhoneCSVFileReader.readLine();

			while ((bufferData = bufferedPhoneCSVFileReader.readLine()) != null) {
				// System.out.println(bufferData);
				String[] phoneFieldValues = bufferData.split(",");
				Phone newPhone = new Phone(phoneFieldValues[0], phoneFieldValues[1], Float.parseFloat(phoneFieldValues[2]),
						Integer.parseInt(phoneFieldValues[3]));
				this.addPhone(newPhone); // Add phone to the phoneList
			}
			System.out.println("Loaded phone List from CSV file successfully!");
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} finally {
			try {
				if (phoneCSVFileReader != null) {
					// Flushes buffer, which transfers buffer data to the file, then closes buffer.
					bufferedPhoneCSVFileReader.close();
					// Close the file reader stream
					phoneCSVFileReader.close();
				}
			} catch (IOException IOExc) {
				IOExc.printStackTrace();
			} // End catch
		} // End finally
	} // End load method

	public void savePhoneToCSVFile(File phoneDBFile) {
		FileWriter phoneFileWriterStream = null;
		BufferedWriter bufferedphoneFileWriterStream = null;
		try {
			phoneFileWriterStream = new FileWriter(phoneDBFile);
			bufferedphoneFileWriterStream = new BufferedWriter(phoneFileWriterStream);
			bufferedphoneFileWriterStream.write("ID,Title,Price,Release Year" + "\n");

			// Write out phone data from phoneList to buffer and flush it to CSV file
			for (Phone phoneObject : phoneList) {
				bufferedphoneFileWriterStream.write(phoneObject.getId() + "," + phoneObject.getTitle()
						+ "," + phoneObject.getPrice() + "," + phoneObject.getReleaseYear() + "\n");
				// bufferedphoneFileWriterStream.write(phoneObject.findAllFieldValuesInCSVFormat()
				// + "\n");
				bufferedphoneFileWriterStream.flush(); // Flushes buffer which transfers buffer data to the file
			}
			System.out.println("Saved phone List to CSV file successfully!");
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} finally {
			try {
				if (phoneFileWriterStream != null) {
					// Close buffer
					bufferedphoneFileWriterStream.close();
					// Close file writer
					phoneFileWriterStream.close();
				}
			} catch (IOException IOExc) {
				IOExc.printStackTrace();
			} // End catch
		} // End finally
	} // End Save method

	public PhoneManage loadPhoneManageObjectFromFile(File phoneObjectsFile) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		PhoneManage pm = null;
		try {
			fis = new FileInputStream(phoneObjectsFile);
			ois = new ObjectInputStream(fis);
			pm = (PhoneManage) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("hi");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					// Close ObjectOutputStream
					ois.close();
				}
				if (fis != null) {
					// Close FileOutputStream
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} // End catch
		} // End finally
		if (pm == null) {
		}
		return pm; // Returns null if no object is read in.
	}

	public void savePhoneManageObjectToFile(File phoneObjectsFile) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(phoneObjectsFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					// Close ObjectOutputStream
					oos.close();
				}
				if (fos != null) {
					// Close FileOutputStream
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} // End catch
		} // End finally

	}

} // End Class
