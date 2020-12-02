import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents Employee with name, username, email, and password
 *
 * @author Ryan Smith
 */
public class Employee {

  // class variables
  final StringBuilder name;
  String username;
  final String password;
  String email;

  /**
   * Default constructor
   *
   * @param name:     employee name
   * @param password: employee password
   */
  Employee(String name, String password) {
    this.name = new StringBuilder();
    this.name.append(name);
    // check if name is valid
    // if valid use user entered info
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    }
    // else use default
    else {
      setUsername("default");
      setEmail("user@oracleacademy.Test");
    }
    // check if password valid
    if (isValidPassword(password)) {
      this.password = password;
    }
    // else use default password
    else {
      this.password = "pw";
    }

  }

  /**
   * create user name from first letter of first name and last name
   * @param name: Employee's name
   */
  private void setUsername(String name) {
    String pattern = "(\\w)\\w*\\s(\\w*)";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(name);
    if (m.find()) {
      username = m.group(1) + m.group(2);
    } else {
      username = "default";
    }

  }

  /**
   * checks if name has space indicating first and last name
   * @param name: employee name
   * @return true if name contains a space
   */
  private boolean checkName(String name) {
    return name.contains(" ");
  }

  /**
   * set email address to first name and last name as one word
   * @param name: employee name
   */
  private void setEmail(String name) {
    String pattern = "(\\w*)\\s(\\w*)";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(name);
    if (m.find()) {
      email = m.group(1) + m.group(2) + "@oracleacademy.Test";
    } else {
      email = "user@oracleacademy.Test";
    }
  }

  /**
   * checks if password contains 'a-z', 'A-Z', or '-+_!@#$%^&*., ?'
   * @param password: Employee email
   * @return returns true if password contains required characters
   */
  private boolean isValidPassword(String password) {
    String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*., ?])";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(password);
    return m.find();
  }

  /**
   * string version of employee information
   * @return employee info
   */
  public String toString() {
    return "Employee Details\n"
        + "Name : " + name.toString() + "\n"
        + "Username : " + username + "\n"
        + "Email : " + email + "\n"
        + "Initial Password : " + password;
  }
}
