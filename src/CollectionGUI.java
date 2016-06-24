import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CollectionGUI extends JFrame implements ActionListener {
	private final String initialExp = "Fill in all fields and click Save to create new employee";
	// JPanels
	private final JPanel northPane = new JPanel();
	private final JPanel westPane = new JPanel();
	private final JPanel centerPane = new JPanel();
	private final JPanel southPane = new JPanel();
	// JLabels
	private final JLabel lblEmpID = new JLabel("Employee ID:");
	private final JLabel lblFirst = new JLabel("First Name:");
	private final JLabel lblLast = new JLabel("Last Name:");
	private final JLabel lblSal = new JLabel("Annual Salary:");
	private final JLabel lblExplain = new JLabel(initialExp);
	// JTextFields
	private final JTextField txtEmpID = new JTextField();
	private final JTextField txtFirst = new JTextField();
	private final JTextField txtLast = new JTextField();
	private final JTextField txtSal = new JTextField();
	private final JTextArea txtListEmployees = new JTextArea();
	// Buttons and ButtonGroups
	private final ButtonGroup btnGroup = new ButtonGroup();
	private final JRadioButton rdbtnAddEmployee = new JRadioButton("Add Employee");
	private final JRadioButton rdbtnChangeSalary = new JRadioButton("Change Salary");
	private final JRadioButton rdbtnDeleteEmployee = new JRadioButton("Delete Employee");
	private final JRadioButton rdbtnListThisEmployee = new JRadioButton("List Single Employee");
	private final JRadioButton rdbtnListAllEmployees = new JRadioButton("List All Employees");
	private final JButton btnAction = new JButton("Save");
	private final JButton btnExit = new JButton("Exit");
	private final JScrollPane scrollPane;

	// ********** Constructor **********
	public CollectionGUI(String title) {
		super(title);

		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(false);
		getRootPane().setDefaultButton(btnAction);

		//********** NORTH PANEL ***********
		getContentPane().add(northPane, BorderLayout.NORTH);
		northPane.add(lblExplain);

		//********** WEST PANEL **********
		//holds radio button
		getContentPane().add(westPane, BorderLayout.WEST);
		westPane.setLayout(new GridLayout(5, 0, 0, 0));
		westPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		// add radio buttons to btnGroup
		btnGroup.add(rdbtnAddEmployee);
		btnGroup.add(rdbtnChangeSalary);
		btnGroup.add(rdbtnDeleteEmployee);
		btnGroup.add(rdbtnListThisEmployee);
		btnGroup.add(rdbtnListAllEmployees);
		rdbtnAddEmployee.setSelected(true);
		// add components to west
		westPane.add(rdbtnAddEmployee);
		westPane.add(rdbtnChangeSalary);
		westPane.add(rdbtnDeleteEmployee);
		westPane.add(rdbtnListThisEmployee);
		westPane.add(rdbtnListAllEmployees);

		//********** CENTER PANEL **********
		getContentPane().add(centerPane, BorderLayout.CENTER);
		centerPane.setLayout(new GridLayout(0, 2, 10, 10));
		centerPane.setPreferredSize(new Dimension(300, 200));
		lblEmpID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirst.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLast.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSal.setHorizontalAlignment(SwingConstants.RIGHT);
		// add components to center
		centerPane.add(lblEmpID);
		centerPane.add(txtEmpID);
		centerPane.add(lblFirst);
		centerPane.add(txtFirst);
		centerPane.add(lblLast);
		centerPane.add(txtLast);
		centerPane.add(lblSal);
		centerPane.add(txtSal);
		centerPane.add(btnAction);
		centerPane.add(btnExit);

		//********** SOUTH PANEL **********
		getContentPane().add(southPane, BorderLayout.SOUTH);
		southPane.setSize(new Dimension(300, 100));
		scrollPane = new JScrollPane(txtListEmployees);
		scrollPane.setPreferredSize(new Dimension(500, 100));
		txtListEmployees.setEnabled(false);
		southPane.add(scrollPane);
		scrollPane.setVisible(true);

		// Add Action Listeners
		rdbtnAddEmployee.addActionListener(this);
		rdbtnChangeSalary.addActionListener(this);
		rdbtnDeleteEmployee.addActionListener(this);
		rdbtnListThisEmployee.addActionListener(this);
		rdbtnListAllEmployees.addActionListener(this);
		btnExit.addActionListener(this);
		btnAction.addActionListener(this);
	}
	
	//********** EVENT HANDLERS *********
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// Change form textfields based on user selection of left menu
		if (source == rdbtnAddEmployee){
			clearText();
			lblExplain.setText(initialExp);
			txtEmpID.grabFocus();
			//prompt for ID last name, first name, annual salary).
			setVisibility(true);
			btnAction.setText("Save");
		}else if (source == rdbtnChangeSalary){
			clearText();
			lblExplain.setText("Enter employee # and click Search to change the salary");
			setVisibility(false);
			lblEmpID.setVisible(true);
			txtEmpID.setVisible(true); //prompt for the employee's ID
			btnAction.setText("Search");
		}else if (source == rdbtnDeleteEmployee){
			clearText();
			lblExplain.setText("Enter employee # then click Delete");
			setVisibility(false);
			lblEmpID.setVisible(true);
			txtEmpID.setVisible(true); //prompt for the employee's ID
			btnAction.setText("Delete");
		}else if (source == rdbtnListThisEmployee){
			clearText();
			lblExplain.setText("Enter employee # and click List to see all info");
			setVisibility(false);
			lblEmpID.setVisible(true);
			txtEmpID.setVisible(true); //prompt for the employee's ID
			btnAction.setText("List");
		}else if (source == rdbtnListAllEmployees){
			clearText();
			lblExplain.setText("Click List to see all current employee info");
			setVisibility(false);
			btnAction.setText("List");
		}
		// Event listenters when user selects "Action" button ie: "Save", "List" 
		else if (source == btnAction){			
			if (rdbtnAddEmployee.isSelected()){
				File file = new File("employee.data");
				String id = getEmpID();
				if (file.length() == 0){ // if employee file is empty just create Employee object and store first map to file
					//prompt for each piece of data (ID, last name, first name, annual salary)
					//Use this information to create an employee object.
					Employee newEmployee = new Employee(id,
							getLast(), getFirst(), getSalary());
					//Store this employee object in the Map.
					//Use the ID as the key.
					CollectionsApplication.storeMap(newEmployee, "add");
					txtEmpID.setText("");
					txtFirst.setText("");
					txtLast.setText("");
					txtSal.setText("");
				}else{ // if employee file is not empty retrieve last map and update
					Map<String, Employee> empMap = CollectionsApplication.retrieveMap();
					// check if key already exists in map
					// if it doesn't store updated map
					if (!empMap.containsKey(id)){
						Employee newEmployee = new Employee(id,
								getLast(), getFirst(), getSalary());
						//Store this employee object in the Map.
						//Use the ID as the key.
						CollectionsApplication.storeMap(newEmployee, "add");
						txtEmpID.setText("");
						txtFirst.setText("");
						txtLast.setText("");
						txtSal.setText("");
					}else{
						//Display an error message if adding this employee will cause a duplicate key (ID) in the Map.
						//Don't add the employee if a duplicate ID is used.
						JOptionPane.showMessageDialog(this, "This EID is already being used. Select another.", "EID Already in Use", JOptionPane.ERROR_MESSAGE);
					}
				}				
			}else if (rdbtnChangeSalary.isSelected()){
				Map<String, Employee> empMap = CollectionsApplication.retrieveMap();

				//When the user selects the option to change the employee's annual salary:
				//prompt for the employee's ID.
				empMap = CollectionsApplication.retrieveMap();
				//Retrieve this element from the Map.
				String id = getEmpID();
				//Display an error message if the employee whose ID is entered doesn't exist in the Map
				if(empMap.containsKey(id)){
					final String CHANGE_BUTTON = "Submit Change";
					txtListEmployees.setText("Current Salary: " + empMap.get(id).getAnnualSalary() );

					//Prompt for the new annual salary.
					if (btnAction.getText() != CHANGE_BUTTON){
						txtSal.setVisible(true);
						lblSal.setText("Enter New Salary:");
						lblSal.setVisible(true);
						btnAction.setText(CHANGE_BUTTON);
					} else {
						Employee emp = empMap.get(id);
						emp.setAnnualSalary(getNewSalary());
						//Use this new salary to update the employee object in the Map.
						CollectionsApplication.storeMap(emp, "add");
						//btnAction.setText("Search");
						txtSal.setText("");
						txtListEmployees.setText("Current Salary: " + empMap.get(id).getAnnualSalary());
					}
				} else {
					JOptionPane.showInputDialog(this, "That EID doesn't seem to exist, try again.", "Could Not Change Salary", JOptionPane.ERROR_MESSAGE);
				}

			}else if (rdbtnDeleteEmployee.isSelected()){
				Map<String, Employee> empMap = CollectionsApplication.retrieveMap();

				//prompt for the employee's ID.
				String id = getEmpID();

				//Delete this element from the Map.
				while (!empMap.containsKey(id)){
					id = JOptionPane.showInputDialog(this, "That EID doesn't seem to exist, try again.", "EID Does Not Exist", JOptionPane.ERROR_MESSAGE);
				}
				CollectionsApplication.storeMap(empMap.get(id), "remove");
				txtEmpID.setText("");
			}else if (rdbtnListThisEmployee.isSelected()){ 
				Map<String, Employee> empMap = CollectionsApplication.retrieveMap();
				String id = getEmpID();
				
				//Display an error message if the employee whose ID is entered doesn't exist in the Map
				while (!empMap.containsKey(id)){
					id = JOptionPane.showInputDialog(this, "That EID doesn't seem to exist, try again.", "EID Does Not Exist", JOptionPane.ERROR_MESSAGE);
					txtEmpID.setText(id);
				}
				Employee empInfo = empMap.get(getEmpID());
				//Retrieve the employee from the map and display each piece of data (ID, last name, first name, annual salary).
				txtListEmployees.setText(empInfo.toString());

			}else if (rdbtnListAllEmployees.isSelected()){
				Map<String, Employee> empMap = CollectionsApplication.retrieveMap();

				//When the user selects the option to display all employee date:
				//retrieve each object from the map and display its data.
				Collection<Employee> c = empMap.values();
				Iterator<Employee> itr =  c.iterator();
				
				txtListEmployees.setText("");
				while (itr.hasNext()){
					txtListEmployees.append(itr.next().toString());
				}
			}
		}
		//Continue to display this menu and process the input until the user indicates he/she wants to stop.
		else if (source == btnExit){
			//I Display a message when the user indicates he/she wants to stop processing employee data.
			//For example, if the user enters 99 the application will display a message like "Thanks for using the employee maintenance application" and the application will end.
			JOptionPane.showMessageDialog(this, "Thanks for using the employee maintenance application", "Employee Mantenance", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	// Reset visible fields 
	private void setVisibility(boolean bool){
		lblEmpID.setVisible(bool);
		txtEmpID.setVisible(bool);
		lblFirst.setVisible(bool);
		txtFirst.setVisible(bool);
		lblLast.setVisible(bool);
		txtLast.setVisible(bool);
		lblSal.setVisible(bool);
		txtSal.setVisible(bool);
	}

	// Clear text fields
	private void clearText(){
		txtListEmployees.setText("");
		txtEmpID.setText("");
		txtFirst.setText("");
		txtLast.setText("");
		txtSal.setText("");
	}

	//Edit the user input to make sure the user enters a valid value
	public String getEmpID(){
		// Get text from field
		String empID = txtEmpID.getText();
		// Verify only letters and numbers
		while (!empID.matches("[a-zA-Z_0-9]*")){
			empID = JOptionPane.showInputDialog(this, "You must enter letters and numbers only. Re-enter the employee ID here.", "Employee ID Error", JOptionPane.ERROR_MESSAGE);
		}
		txtEmpID.setText(empID); // set text field
		return empID;
	}
	public String getLast(){
		String last = txtLast.getText();
		// Verify only letters
		while (!last.matches("[a-zA-Z]*")){
			last = JOptionPane.showInputDialog(this, "You must enter letters only. Re-enter the last name here.", "Last Name Error", JOptionPane.ERROR_MESSAGE);
		}
		txtLast.setText(last); // set text field
		return last;
	}
	public String getFirst(){
		String first = txtFirst.getText();
		// Verify only letters
		while (!first.matches("[a-zA-Z]*")){
			first = JOptionPane.showInputDialog(this, "You must enter letters only. Re-enter the first name here.", "First Name Error", JOptionPane.ERROR_MESSAGE);
		}
		txtFirst.setText(first); // set text field
		return first;
	}
	public double getSalary(){
		String sal = txtSal.getText();
		// Verify only numbers and then a decimal followed by 2 numbers
		while (!sal.matches("([0-9]*)\\.([0-9]{0,2})")){
			sal = JOptionPane.showInputDialog(this, "You must enter numbers only. Re-enter the salary amount here.", "Salary Error", JOptionPane.ERROR_MESSAGE);
		}
		txtSal.setText(sal); // set text field
		return Double.parseDouble(sal);
	}
	public double getNewSalary(){
		String sal = txtSal.getText();
		// Verify only numbers and then a decimal followed by 2 numbers
		while (!sal.matches("([0-9]*)\\.([0-9]{0,2})")){
			sal = JOptionPane.showInputDialog(this, "You must enter numbers only. Re-enter the salary number here.", "Salary Error", JOptionPane.ERROR_MESSAGE);
		}
		txtSal.setText(sal); // set text field
		return Double.parseDouble(sal);
	}
}
