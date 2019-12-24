package Methods;

import java.io.Serializable;

public class Librarian extends User implements Serializable{/*the Librarian is user too*/
	public enum Role {//Roles of Librarian
		Manager, Librarian;
	}
	public enum Department {//Departments of Worker
		Management, Workforce; 
	}
	
	private Role Role;
	private Department Department;
	
	/*Constructor of Librarian*/
	public Librarian(String iD, String firstName, String lastName, String userName, String password, String phone, String email, boolean onLineStatus,
			Role role, Department department) {
		super(iD, firstName, lastName, userName, password, phone, email, onLineStatus);
		Role = role;
		Department = department;
	}
	
	public Librarian(User data, Methods.Librarian.Role role, Methods.Librarian.Department department) {
		super(data.getID(), data.getFirstName(), data.getLastName(), data.getUserName(), data.getPassword(), 
				data.getPhone(), data.getEmail(), data.isOnline());
		Role = role;
		Department = department;
	}

	/*setters & getters of librarian attributes*/
	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
	}

	public Department getDepartment() {
		return Department;
	}

	public void setDepartment(Department department) {
		Department = department;
	}

	@Override/*toString of librarian*/
	public String toString() {
		return "Role=" + Role + "\n Department=" + Department;
	}
}