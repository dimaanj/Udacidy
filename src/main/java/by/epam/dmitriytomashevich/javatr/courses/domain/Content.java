package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.util.Objects;

public class Content{
    private Long id;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content1 = (Content) o;
        return Objects.equals(id, content1.id) &&
                Objects.equals(content, content1.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
