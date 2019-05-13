package by.epam.dmitriytomashevich.javatr.courses.domain;

public enum  UserRole {
    UNDEFINED("undefined"),
    USER("user"),
    ADMIN("admin");

    private String value;

    UserRole(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
