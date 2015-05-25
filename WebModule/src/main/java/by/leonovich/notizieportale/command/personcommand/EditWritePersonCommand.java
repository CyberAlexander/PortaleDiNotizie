package by.leonovich.notizieportale.command.personcommand;

import static by.leonovich.notizieportale.util.WebConstants.Const;

import by.leonovich.notizieportale.command.IActionCommand;
import by.leonovich.notizieportale.domain.Person;
import by.leonovich.notizieportale.domain.PersonDetail;
import by.leonovich.notizieportale.services.PersonService;
import by.leonovich.notizieportale.util.*;
import com.mysql.jdbc.StringUtils;


/**
 * Created by alexanderleonovich on 10.05.15.
 */
public class EditWritePersonCommand implements IActionCommand {

    private PersonService personService;

    public EditWritePersonCommand() {
        personService = PersonService.getInstance();
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        Person person = (Person) sessionRequestContent.getSessionAttribute(Const.PERSON);
        PersonDetail personDetail = person.getPersonDetail();
        if (!StringUtils.isNullOrEmpty(sessionRequestContent.getParameter(Const.P_NEW_EMAIL))) {
            personDetail.setEmail(sessionRequestContent.getParameter(Const.P_NEW_EMAIL));
        } else {
            personDetail.setEmail(sessionRequestContent.getParameter(Const.EMAIL));
        }
        if (!StringUtils.isNullOrEmpty(sessionRequestContent.getParameter(Const.P_NEW_PASSWORD))) {
            personDetail.setPassword(sessionRequestContent.getParameter(Const.P_NEW_PASSWORD));
        } else {
            personDetail.setPassword(sessionRequestContent.getParameter(Const.PASSWORD));
        }
        person.setName(sessionRequestContent.getParameter(Const.P_NAME));
        person.setSurname(sessionRequestContent.getParameter(Const.P_SURNAME));
        personDetail.setBirthday(AttributesManager.getInstance()
                .parseDateFromRequest(sessionRequestContent.getParameter(Const.P_BIRTHDAY)));
        person.setPersonDetail(personDetail);
        personService.updateUserInformation(person);

        String page = URLManager.getInstance().getProperty(UrlEnum.PATH_PAGE_USERCABINET.getUrlCode());
        sessionRequestContent.setSessionAttribute(Const.PERSON, person);
        sessionRequestContent.setRequestAttribute("infoUpdated", MessageManager.getInstance().getProperty("message.user.info.updated"));
        return page;
    }
}
