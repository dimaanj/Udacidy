package by.epam.dmitriytomashevich.javatr.courses.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Conversation {
    private Long id;
    private LocalDate createDate;
    private ConversationType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conversation that = (Conversation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate, type);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }


    public enum ConversationType{
        /**
         * QUESTION_CONVERSATION should be single for 1 user account.
         * But for admins might be a lot.
         */
        QUESTION_CONVERSATION("questionConversation"),
        SIMPLE_CONVERSATION("simpleConversation");

        private String value;

        ConversationType(String type){
            this.value = type;
        }

        public static ConversationType fromValue(String v) throws IllegalArgumentException{
            for (ConversationType c: ConversationType.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

        public String getValue() {
            return value;
        }
    }
}
