package by.epam.dmitriytomashevich.javatr.courses.domain.json;

import java.util.Objects;

public class JsonSection {
    private Long id;
    private Long contentId;
    private Long conferenceId;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonSection that = (JsonSection) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(conferenceId, that.conferenceId) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentId, conferenceId, content);
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

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
