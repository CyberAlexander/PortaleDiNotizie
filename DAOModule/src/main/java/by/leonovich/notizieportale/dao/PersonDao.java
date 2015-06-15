package by.leonovich.notizieportale.dao;

import by.leonovich.notizieportale.domain.Person;
import by.leonovich.notizieportale.domain.enums.StatusEnum;
import by.leonovich.notizieportale.exception.PersistException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import static by.leonovich.notizieportale.domain.enums.StatusEnum.*;
import static by.leonovich.notizieportale.util.DaoConstants.Const.EMAIL;
import static by.leonovich.notizieportale.util.DaoConstants.Const.STATUS;

/**
 * Created by alexanderleonovich on 11.04.15.
 * Class for working with persistence entity of PERSON
 */
@Repository
public class PersonDao extends AbstractDao<Person> implements IPersonDao {
    private static final Logger logger = Logger.getLogger(PersonDao.class);


    public PersonDao() {
        super();
    }

    /**
     *
     * @param session
     * @return
     * @throws PersistException
     */
    @Override
    protected List<Person> parseResultSet(Session session) throws PersistException {
        Criteria criteria = session.createCriteria(Person.class);
        criteria.add(Restrictions.eq(STATUS, PERSISTED));
        List<Person> result = criteria.list();
        return result;
    }

    /**
     *
     * @param email
     * @param session
     * @return
     * @throws PersistException
     */
    @Override
    public Person getByEmail(String email, Session session) throws PersistException {
        StatusEnum status = PERSISTED;
        Person person;
        String hql = "SELECT p FROM Person p WHERE p.status=:status and p.personDetail.email=:email";
        Query query = session.createQuery(hql)
                .setParameter(STATUS, status)
                .setParameter(EMAIL, email);
        person = (Person) query.uniqueResult();
        return person;
    }
}
