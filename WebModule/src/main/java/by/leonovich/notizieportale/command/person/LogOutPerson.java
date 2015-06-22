package by.leonovich.notizieportale.command.person;

import by.leonovich.notizieportale.command.IActionCommand;
import by.leonovich.notizieportale.services.IPersonService;
import by.leonovich.notizieportale.services.PersonService;
import by.leonovich.notizieportale.services.exception.ServiceLayerException;
import by.leonovich.notizieportale.util.URLManager;
import by.leonovich.notizieportale.util.SessionRequestContent;
import by.leonovich.notizieportale.util.UrlEnum;

/**
 * Created by alexanderleonovich on 18.04.15.
 * Command class for log out autorized user and invalidate session
 */
@Deprecated
public class LogOutPerson implements IActionCommand {

    private IPersonService personService;

    public LogOutPerson() {
        personService = new PersonService();
    }

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = URLManager.getInstance().getProperty(UrlEnum.URL_INDEX.getUrlCode());
        try {
            personService.logOutPerson();
        } catch (ServiceLayerException serviceLayerException) {
            serviceLayerException.printStackTrace();
        }
        sessionRequestContent.invalidateSession();
        return page;
    }
}
