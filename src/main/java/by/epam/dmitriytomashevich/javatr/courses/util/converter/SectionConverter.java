package by.epam.dmitriytomashevich.javatr.courses.util.converter;

import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonSection;

public class SectionConverter implements EntityConverter<JsonSection, Section> {
    @Override
    public JsonSection convert(Section s) {
        JsonSection jsonSection = new JsonSection();
        jsonSection.setId(s.getId());
        jsonSection.setContentId(s.getContentId());
        jsonSection.setConferenceId(s.getConferenceId());
        jsonSection.setContent(s.getContent().getContent());
        return jsonSection;
    }
}
