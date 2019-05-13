package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.util.Objects;

public class Section {
    private Long id;
    private Long conferenceId;
    private Long contentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        return Objects.equals(id, section.id) &&
                Objects.equals(conferenceId, section.conferenceId) &&
                Objects.equals(contentId, section.contentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conferenceId, contentId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
