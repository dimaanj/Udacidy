package by.epam.dmitriytomashevich.javatr.courses.domain.json;

import by.epam.dmitriytomashevich.javatr.courses.domain.Message;

import java.util.List;

public class JsonMessages {
    private List<JsonMessage> messageList;

    public JsonMessages(List<JsonMessage> messageList) {
        this.messageList = messageList;
    }

    public int size() {
        return messageList.size();
    }

    public JsonMessage get(int i) {
        return messageList.get(i);
    }
}
