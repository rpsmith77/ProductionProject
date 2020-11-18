import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {

  StringBuilder name;
  String username;
  String password;
  String email;

  Employee(String name, String password) {
    this.name = new StringBuilder();
    this.name.append(name);
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    } else {
      setUsername("default");
      setEmail("user@oracleacademy.Test");
    }
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }

  }

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

  private boolean checkName(String name) {
    return name.contains(" ");
  }

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

  private boolean isValidPassword(String password) {
    String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*., ?])";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(password);
    return m.find();
  }

  public String toString(){
    return "Employee Details\n"
        + "Name : " + name.toString() + "\n"
        + "Username : " + username + "\n"
        + "Email : " + email + "\n"
        + "Initial Password : " + password;
  }
}
