package by.epam.dmitriytomashevich.javatr.courses.util.converter;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonSection;

import java.util.ArrayList;
import java.util.List;

public class ConferenceConverter implements EntityConverter<JsonConference, Conference>{
    @Override
    public JsonConference convert(Conference c) {
        JsonConference jConference = new JsonConference();
        jConference.setId(c.getId());
        jConference.setAuthorId(c.getAuthor().getId());
        jConference.setContent(c.getContent().getContent());
        jConference.setContentId(c.getContentId());
        jConference.setAuthorFirstName(c.getAuthor().getFirstName());
        jConference.setAuthorLastName(c.getAuthor().getLastName());

        List<Section> sections = c.getSections();
        List<JsonSection> jsonSections = new ArrayList<>();
        for(Section s : sections){
            JsonSection jsonSection = new SectionConverter().convert(s);
            jsonSections.add(jsonSection);
        }
        jConference.setJsonSection(jsonSections);
        return jConference;
    }
}
