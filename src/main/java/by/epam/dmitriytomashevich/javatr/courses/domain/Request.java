package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Request {
    private Long id;
    private Long userId;
    private LocalDateTime creationDateTime;
    private RequestStatus requestStatus;
    private Long conferenceId;
    private Long sectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(userId, request.userId) &&
                Objects.equals(creationDateTime, request.creationDateTime) &&
                requestStatus == request.requestStatus &&
                Objects.equals(conferenceId, request.conferenceId) &&
                Objects.equals(sectionId, request.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, userId, creationDateTime,
                requestStatus, conferenceId, sectionId);
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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
