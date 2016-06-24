import java.io.*;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CollectionsApplication {
	// Do not create ANY class data members in this class.
	public static void main(String[] args) {
		// Display App
		CollectionGUI frame = new CollectionGUI("Edit Employee");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public static void storeMap(Employee emp, String addOrRemove){
		// Create a sorted Map that will store Employee objects.
		TreeMap<String, Employee> map = new TreeMap<String, Employee>();
		addOrRemove = addOrRemove.toLowerCase();
		File file = new File ("employee.data");
		// Retrieve current map from file unless file is empty
		if(!(file.length() == 0)){
			// Retrieve current map if one exists
			try{
				FileInputStream fis = new FileInputStream("employee.data");
				ObjectInputStream ois = new ObjectInputStream(fis);
				map = (TreeMap<String, Employee>) ois.readObject();
				fis.close();
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		// Update map by either adding or removing Employee
		if (addOrRemove == "add")
			map.put(emp.getID(), emp);
		else if (addOrRemove == "remove")
			map.remove(emp.getID());

		// serialize the map (store map to file)
		try {
			FileOutputStream fos = new FileOutputStream("employee.data");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Employee> retrieveMap(){
		TreeMap<String, Employee> map = new TreeMap<>();
		// Read Map Object from the file
			try {
				FileInputStream fis = new FileInputStream("employee.data");
				ObjectInputStream ois = new ObjectInputStream(fis);
				map = (TreeMap<String, Employee>) ois.readObject();
				fis.close();
				ois.close();
			} catch (FileNotFoundException e){ 
				//If user attempts to retrieve info from file before it exists display message
				JOptionPane.showMessageDialog(null, "You must store employee data before attempting to retreive it.", "File Does Not Exist", JOptionPane.ERROR_MESSAGE);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		return map;
	}

}
