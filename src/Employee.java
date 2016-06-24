import java.io.Serializable;

public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;
	private String employeeID;
	private String lastName;
	private String firstName;
	private double annualSalary;
	
	public Employee(String empID, String last, String first, Double annSalary){
		//Set members
		setID(empID);
		setFirstName(first);
		setLastName(last);
		setAnnualSalary(annSalary);
	}
	
	// ********** SET / GET Methods **********
	// set/get String employeeID
	private void setID(String empID){
		employeeID = empID;
	}
	public String getID(){
		return employeeID;
	}
	
	// set/get String lastName
	private void setLastName(String last){
		lastName = last;
	}
	public String getLastName(){
		return lastName;
	}
	
	// set/get String firstName
	private void setFirstName(String first){
		firstName = first;
	}
	public String getFirstName(){
		return firstName;
	}
	
	//set/get double annualSalary
	public void setAnnualSalary(double annSal){
		annualSalary = annSal;
	}
	public double getAnnualSalary(){
		return annualSalary;
	}
	
	//********** toString to return all data members *********
	public String toString(){
		StringBuilder empInfo = new StringBuilder();
		empInfo.append("EID:  " + getID() + ",  Name (last, first):  " + getLastName() + ", " + getFirstName() + ",  Salary:  " + getAnnualSalary() + "\n");
		return empInfo.toString();
	}
}
