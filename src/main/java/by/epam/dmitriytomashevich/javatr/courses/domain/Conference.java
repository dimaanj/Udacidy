package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.util.Objects;

public class Conference {
    private Long id;
    private Long contentId;
    private Long authorId;

    private User author;
    private Content content;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conference conference = (Conference) o;
        return Objects.equals(id, conference.id) &&
                Objects.equals(contentId, conference.contentId) &&
                Objects.equals(authorId, conference.authorId) &&
                Objects.equals(author, conference.author) &&
                Objects.equals(content, conference.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentId, authorId, author, content);
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
