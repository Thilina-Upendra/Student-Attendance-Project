package lk.ijse.dep8.security;

public class Principal {
    private String userName;
    private String name;
    private UserRole role;

    public Principal() {
    }

    public Principal(String userName, String name, UserRole role) {
        this.userName = userName;
        this.name = name;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }

    public enum UserRole{
        ADMIN, USER
    }
}
