package by.epam.dmitriytomashevich.javatr.courses.domain;

public enum RequestStatus {
    SHIPPED("shipped"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    String status;

    RequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
