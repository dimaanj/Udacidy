package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestFormService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestFormServiceImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SendRequestCommand implements Command {
    private static final RequestFormService REQUEST_FORM_SERVICE = new RequestFormServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);

        List<Long> requestFormIds = new ArrayList<>();
        for(String stringId : jsonSectionsIds){
            RequestForm requestForm = new RequestForm();
            requestForm.setSectionId(Long.valueOf(stringId));

            requestFormIds.add(REQUEST_FORM_SERVICE.create(requestForm));
        }

        for(Long requestFormId : requestFormIds){

        }





        return Optional.empty();
    }
}
