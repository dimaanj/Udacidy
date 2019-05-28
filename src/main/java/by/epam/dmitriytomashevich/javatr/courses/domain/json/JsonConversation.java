package by.epam.dmitriytomashevich.javatr.courses.domain.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class JsonConversation {
    private Long id;
    private LocalDate dateCreation;
    private JsonMessage lastMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonConversation that = (JsonConversation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateCreation, that.dateCreation) &&
                Objects.equals(lastMessage, that.lastMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreation, lastMessage);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public JsonMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(JsonMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}
