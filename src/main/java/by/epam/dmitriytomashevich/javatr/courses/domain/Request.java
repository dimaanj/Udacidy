package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Request {
    private Long id;
    private Long userId;
    private LocalDateTime creationDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(creationDateTime, request.creationDateTime) &&
                Objects.equals(userId, request.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, creationDateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
