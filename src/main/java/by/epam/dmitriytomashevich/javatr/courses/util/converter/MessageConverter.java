package by.epam.dmitriytomashevich.javatr.courses.util.converter;

import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;

public class MessageConverter implements EntityConverter<JsonMessage, Message> {
    @Override
    public JsonMessage convert(Message message) {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setId(message.getId());
        jsonMessage.setFormattedCreationDateTime(message.getFormattedCreationDateTime());
        jsonMessage.setCreatorFirstName(message.getCreator().getFirstName());
        jsonMessage.setCreatorLastName(message.getCreator().getLastName());
        jsonMessage.setText(message.getText());
        jsonMessage.setCreatorId(message.getCreator().getId());
        jsonMessage.setCreatorRole(message.getCreator().getRole());
        jsonMessage.setConversationId(message.getConversationId());
        jsonMessage.setImageUrl(message.getImageUrl());
        return jsonMessage;
    }
}
