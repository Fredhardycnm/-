package pro;

public class Users { private int id;private String username, password, email, role;

	public Users() {}

	public Users(String username, String password, String email) {this.username = username;this.password = password;this.email = email;}

	// Getters and Setters
	public int getId() { return id; }public void setId(int id) { this.id = id; }public String getUsername() { return username; }public void setUsername(String username) { this.username = username; }public String getPassword() { return password; }public void setPassword(String password) { this.password = password; }public String getEmail() { return email; }public void setEmail(String email) { this.email = email; }public String getRole() { return role; }public void setRole(String role) { this.role = role; }

	@Override
	public String toString() {return "Users [id = " + id + ", username = " + username + ", email = " + email + ", role = " + role + "]";}}