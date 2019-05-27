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

    private List<Conference> conferences;

    public ClientConferencesInitializer(Builder builder) {
        this.client = builder.client;
        this.conferences = builder.conferences;
        this.userService = builder.userService;
        this.sectionService = builder.sectionService;
        this.contentService = builder.contentService;
        this.requestService = builder.requestService;
    }

    public void init() throws LogicException {
        for (Conference c : conferences) {
            User author = userService.findById(c.getAuthorId());
            List<Section> sections = sectionService.findSectionsByConferenceId(c.getId());

            for(Section s : sections){
                s.setContent(contentService.findById(s.getContentId()));
            }
            c.setContent(contentService.findById(c.getContentId()));
            c.setAuthor(author);
            c.setSections(sections);
            c.setRequestSent(isRequestAlreadySentRequest(client.getId(), c.getId()));
            if(c.isRequestSent()) {
                Request request = requestService.findByUserIdAndConferenceId(client.getId(), c.getId());
                c.setRequestStatus(request.getRequestStatus());
            }
        }
    }

    private boolean isRequestAlreadySentRequest(Long userId, Long conferenceId) throws LogicException {
        return requestService.findByUserIdAndConferenceId(userId, conferenceId) != null;
    }

    public static class Builder {
        private final User client;
        private final List<Conference> conferences;

        private UserService userService;
        private SectionService sectionService;
        private ContentService contentService;
        private RequestService requestService;

        /**
         * Constructor
         */
        public Builder(User client, List<Conference> conferences) {
           this.client = client;
           this.conferences = conferences;
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
