package by.epam.dmitriytomashevich.javatr.courses.domain.json;

import java.util.Objects;

public class JsonAlertMessage {
    private String message;
    private String alertType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonAlertMessage that = (JsonAlertMessage) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(alertType, that.alertType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, alertType);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}
