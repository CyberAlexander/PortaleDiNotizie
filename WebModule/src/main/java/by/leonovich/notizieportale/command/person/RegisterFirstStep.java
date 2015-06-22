package by.leonovich.notizieportale.command.person;

import by.leonovich.notizieportale.command.IActionCommand;
import by.leonovich.notizieportale.services.PersonService;
import by.leonovich.notizieportale.util.*;

/**
 * Created by alexanderleonovich on 02.05.15.
 */
@Deprecated
public class RegisterFirstStep implements IActionCommand {

    private AttributesManager attributesManager;
    private PersonService personService;

    public RegisterFirstStep() {
        attributesManager = AttributesManager.getInstance();
        personService = new PersonService();
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        Long id = null;

        /*Person person = (Person) sessionRequestContent.getSessionAttribute(P_PERSON);
        if (Objects.nonNull(person.getPersonId())) {
            person.setName(sessionRequestContent.getParameter(P_NAME));
            person.setSurname(sessionRequestContent.getParameter(P_SURNAME));
            person.setStatus(UNCONFIRMED);
            try {
                id = personService.update(person).getPersonId();
            } catch (ServiceExcpetion serviceExcpetion) {
                serviceExcpetion.printStackTrace();
            }
        } else {
            person.setName(sessionRequestContent.getParameter(P_NAME));
            person.setSurname(sessionRequestContent.getParameter(P_SURNAME));
            person.setStatus(UNCONFIRMED);
            try {
                id = personService.registerPersonFirstStep(person);
            } catch (ServiceExcpetion serviceExcpetion) {
                serviceExcpetion.printStackTrace();
            }
        }
        sessionRequestContent.setSessionAttribute(P_ID, id);
        sessionRequestContent.setSessionAttribute(P_PERSON, person);
        //personService.putSessionInHttp(sessionRequestContent.getHttpSession());*/
        return page = URLManager.getInstance().getProperty(UrlEnum.PATH_PAGE_REGISTRATION_2.getUrlCode());
    }
}

        /*person = attributesManager.parseParametersOfPerson(sessionRequestContent, person);
        if (!(StringUtils.isNullOrEmpty(person.getPersonDetail().getEmail()))
                && !(StringUtils.isNullOrEmpty(person.getPersonDetail().getPassword()))) {
            boolean operationResult = personService.registerPersonFirstStep(person);
            if (operationResult == true) {
                sessionRequestContent.setSessionAttribute(P_PERSON,
                        person = personService.getPersonByEmail(person.getPersonDetail().getEmail()));
                sessionRequestContent.setSessionAttribute(Const.PERSONTYPE, person.getPersonDetail().getRole());
                return page = URLManager.getInstance().getProperty(UrlEnum.URL_PERSONCABINET.getUrlCode());
            } else {
                sessionRequestContent.setRequestAttribute("duplicateEmail", MessageManager.getInstance().getProperty("message.duplicateEmail"));
                return page = URLManager.getInstance().getProperty(UrlEnum.PATH_PAGE_REGISTRATION_1.getUrlCode());
            }
        }else{
            sessionRequestContent.setRequestAttribute("nullemailorpassword", MessageManager.getInstance().getProperty("message.nullemailorpassword"));
            return page = URLManager.getInstance().getProperty(UrlEnum.PATH_PAGE_REGISTRATION_1.getUrlCode());
        }*/
