package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private Long id;
    private String text;
    private Long creatorId;
    private LocalDateTime creationDateTime;
    private Long conversationId;
    private String imageUrl;

    private User creator;

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(text, message.text) &&
                Objects.equals(creatorId, message.creatorId) &&
                Objects.equals(creationDateTime, message.creationDateTime) &&
                Objects.equals(conversationId, message.conversationId) &&
                Objects.equals(creator, message.creator) &&
                Objects.equals(imageUrl, message.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creatorId,
                creationDateTime, conversationId, creator, imageUrl);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getFormattedCreationDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationDateTime.format(formatter);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
