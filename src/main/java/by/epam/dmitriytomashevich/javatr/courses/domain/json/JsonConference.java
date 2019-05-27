package by.epam.dmitriytomashevich.javatr.courses.domain.json;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;

import java.util.List;
import java.util.Objects;

public class JsonConference {
    private Long id;
    private Long contentId;
    private Long authorId;
    private String content;
    private String authorFirstName;
    private String authorLastName;
    private List<JsonSection> jsonSections;
    private boolean isRequestAlreadySent;
    private RequestStatus requestStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonConference that = (JsonConference) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(authorId, that.authorId) &&
                Objects.equals(content, that.content) &&
                Objects.equals(authorFirstName, that.authorFirstName) &&
                Objects.equals(authorLastName, that.authorLastName) &&
                Objects.equals(isRequestAlreadySent, that.isRequestAlreadySent) &&
                Objects.equals(requestStatus, that.requestStatus) &&
                Objects.equals(jsonSections, that.jsonSections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestStatus, id, contentId, authorId, content, authorFirstName, authorLastName, jsonSections, isRequestAlreadySent);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public List<JsonSection> getJsonSection() {
        return jsonSections;
    }

    public void setJsonSection(List<JsonSection> jsonSection) {
        this.jsonSections = jsonSection;
    }

    public boolean isRequestAlreadySent() {
        return isRequestAlreadySent;
    }

    public void setRequestAlreadySent(boolean requestAlreadySent) {
        isRequestAlreadySent = requestAlreadySent;
    }

    public List<JsonSection> getJsonSections() {
        return jsonSections;
    }

    public void setJsonSections(List<JsonSection> jsonSections) {
        this.jsonSections = jsonSections;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
