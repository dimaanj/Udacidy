package by.epam.dmitriytomashevich.javatr.courses.util.logic_helper;

import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;

import java.util.List;

public class ClientConferencesInitializer {
    private final User client;

    private final UserService userService;
    private final SectionService sectionService;
    private final ContentService contentService;
    private final RequestService requestService;

    private final Conference conference;

    public ClientConferencesInitializer(Builder builder) {
        this.client = builder.client;
        this.conference = builder.conference;
        this.userService = builder.userService;
        this.sectionService = builder.sectionService;
        this.contentService = builder.contentService;
        this.requestService = builder.requestService;
    }

    public void init() throws LogicException {
        conference.setAuthor(userService.findById(conference.getAuthorId()));
        conference.setContent(contentService.findById(conference.getContentId()));

        List<Section> sections = sectionService.findSectionsByConferenceId(conference.getId());
        for (Section s : sections) {
            s.setContent(contentService.findById(s.getContentId()));
        }
        conference.setSections(sections);

        List<Request> requests = requestService.findAllByUserIdAndConferenceId(client.getId(), conference.getId());
        if(requests != null && !requests.isEmpty()){
            conference.setRequestSent(true);
            conference.setRequestStatus(requestService.findById(requests.get(0).getId()).getRequestStatus());
        }
    }

    public static class Builder {
        private final User client;
        private final Conference conference;

        private UserService userService;
        private SectionService sectionService;
        private ContentService contentService;
        private RequestService requestService;

        /**
         * Constructor
         */
        public Builder(User client, Conference conference) {
           this.client = client;
           this.conference = conference;
        }

        public Builder withUserService(UserService userService) {
            this.userService = userService;
            return this;
        }

        public Builder withSectionService(SectionService sectionService) {
            this.sectionService = sectionService;
            return this;
        }

        public Builder withContentService(ContentService contentService) {
            this.contentService = contentService;
            return this;
        }

        public Builder  withRequestService(RequestService requestService){
            this.requestService = requestService;
            return this;
        }

        public ClientConferencesInitializer build() {
            return new ClientConferencesInitializer(this);
        }
    }
}
