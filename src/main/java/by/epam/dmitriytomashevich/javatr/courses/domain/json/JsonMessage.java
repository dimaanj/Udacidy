package by.epam.dmitriytomashevich.javatr.courses.domain.json;

import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;

import java.util.Objects;

public class JsonMessage {
    private Long id;
    private String text;
    private String formattedCreationDateTime;
    private String creatorFirstName;
    private String creatorLastName;
    private Long creatorId;
    private UserRole creatorRole;
    private Long conversationId;
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonMessage that = (JsonMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(formattedCreationDateTime, that.formattedCreationDateTime) &&
                Objects.equals(creatorFirstName, that.creatorFirstName) &&
                Objects.equals(creatorLastName, that.creatorLastName) &&
                Objects.equals(creatorRole, that.creatorRole) &&
                Objects.equals(creatorId, that.creatorId) &&
                Objects.equals(conversationId, that.conversationId) &&
                Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, formattedCreationDateTime,
                creatorFirstName, creatorLastName, creatorRole, creatorId, conversationId, imageUrl);
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

    public String getFormattedCreationDateTime() {
        return formattedCreationDateTime;
    }

    public void setFormattedCreationDateTime(String formattedCreationDateTime) {
        this.formattedCreationDateTime = formattedCreationDateTime;
    }

    public String getCreatorFirstName() {
        return creatorFirstName;
    }

    public void setCreatorFirstName(String creatorFirstName) {
        this.creatorFirstName = creatorFirstName;
    }

    public String getCreatorLastName() {
        return creatorLastName;
    }

    public void setCreatorLastName(String creatorLastName) {
        this.creatorLastName = creatorLastName;
    }

    public UserRole getCreatorRole() {
        return creatorRole;
    }

    public void setCreatorRole(UserRole role) {
        this.creatorRole = role;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
