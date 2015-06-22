package by.leonovich.notizieportale.services;

import by.leonovich.notizieportale.dao.INewsDao;
import by.leonovich.notizieportale.dao.NewsDao;
import by.leonovich.notizieportale.domain.Category;
import by.leonovich.notizieportale.domain.News;
import by.leonovich.notizieportale.domain.enums.StatusEnum;
import by.leonovich.notizieportale.services.exception.ServiceLayerException;
import by.leonovich.notizieportale.services.util.IntComparator;
import by.leonovich.notizieportale.exception.PersistException;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static by.leonovich.notizieportale.domain.enums.StatusEnum.PERSISTED;
import static by.leonovich.notizieportale.services.util.ServiceConstants.Const.*;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

/**
 * Created by alexanderleonovich on 29.04.15.
 * Service layer for domain entity News
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class NewsService implements INewsService {
    private static final Logger logger = Logger.getLogger(NewsService.class);
    @Autowired
    private INewsDao newsDao;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IPersonService personService;

    public NewsService() {

    }

    /**
     * @param personId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<News> getNewsesByPersonId(Long personId) {
        List<News> newses = emptyList();
        try {
            newses = newsDao.getByPersonId(personId);
        } catch (HibernateException e) {
            logger.error(ERROR_GET_NEWSES_BY_PERSON + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return newses;
    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<News> getNewsesByCategoryId(Long categoryId) {
        List<News> newses = emptyList();
        try {

            newses = newsDao.getByCategoryId(categoryId);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return newses;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<News> getListOfNewsByCategoryIdNoOrder(Long categoryId) {
        List<News> newses = emptyList();
        try {
            newses = newsDao.getByCategoryIdNoOrder(categoryId);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return newses;
    }

    /**
     * @param date
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<News> getNewsesByDate(Date date) {
        List<News> newses = null;
        try {
            newses = newsDao.getByDate(date);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return newses;
    }

    /**
     * @param pageId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public News getNewsByPageId(String pageId) {
        News news = null;
        if (!(isNullOrEmpty(pageId))) {
            try {
                news = newsDao.getByPageId(pageId);
                Hibernate.initialize(news.getCommentaries());
            } catch (HibernateException e) {
                logger.error("Error get list of Categories from database" + e);
            } catch (PersistException e) {
                logger.error(e);
            }
        }
        return news;
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<News> getNewsByCriteria(int pageNumber, int pageSize, Long categoryId) {
        List<News> newses = new ArrayList<>();
        try {

            newses = newsDao.getNewsByCriteria(pageNumber, pageSize, categoryId);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return newses;
    }

    /**
     * @param category
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List getCountNews(Category category) {
        List result = null;
        try {
            result = newsDao.countNews(category);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return result;
    }

    /**
     * REFACTOR THIS METHOD
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<News> getMostPopularNewsList() {
        List<News> newsList = new ArrayList<>();
        for (int i = ZERO; i < 3; ) {
            News news;
            Long randomId = (long) (Math.random() * 115 + 15); // вещественное число из [3;8)
            try {
                news = newsDao.get(randomId);
                if (news != null && news.getNewsId() != null) {
                    newsList.add(news);
                    i++;
                }
            } catch (PersistException e) {
                logger.error(e);
            }
        }
        return newsList;
    }

    /**
     * use this method for receiving list of pagination numbers.
     * This numbers are used in view
     * @param numberOfPages     total count (from cretery) of pages in database
     * @param pageNumber        number of page what user click
     * @param newsesPackageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Integer> getList(long numberOfPages, int pageNumber, int newsesPackageSize) {
        //**bigSize - number of all pageLists
        int bigSize = size(numberOfPages, newsesPackageSize);
        List<Integer> list = new ArrayList<Integer>(FIVE);
        if (pageNumber < ONE) {
            pageNumber = ONE;
        }
        if (pageNumber > bigSize) {
            pageNumber = bigSize;
        }
        list.add(pageNumber);

        int size = bigSize;
        if (size > FIVE) {
            size = FIVE;
        }
        //** every turn of cycle step will be increased by 1
        int step = ONE;

//         pagination number will be written
//         if it is more then 0 and less then bigSize
        for (int i = ZERO; ; i++, step++) {
            int elem1 = pageNumber - step;
            if (elem1 >= ONE) {
                list.add(elem1);
            }
            if (list.size() >= size) {
                break;
            }
            int elem2 = pageNumber + step;
            if (elem2 <= bigSize) {
                list.add(elem2);
            }
            if (list.size() >= size) break;
        }
        Collections.sort(list, new IntComparator());

        return list;
    }

    @Override
    public Long update(News news, Long categoryId, Long personId) throws ServiceLayerException {
        if (nonNull(news.getNewsId())) {
            Long pK = news.getNewsId();
            try {
                news.setStatus(PERSISTED);
                news.setCategory(categoryService.get(categoryId));
                news.setPerson(personService.get(personId));
                newsDao.update(news);
                return pK;
            }catch (PersistException e) {
                logger.error(e);
            }
        }
        return (long) MINUS_ONE;
    }

    @Override
    public Long save(News news, Long categoryId, Long personId) throws ServiceLayerException {
        if (nonNull(news.getNewsId())) {
            Long pK = news.getNewsId();
            try {
                news.setStatus(PERSISTED);
                news.setCategory(categoryService.get(categoryId));
                news.setPerson(personService.get(personId));
                newsDao.save(news);
                return pK;
            }catch (PersistException e) {
                logger.error(e);
            }
        }
        return (long) MINUS_ONE;
    }


    @Override
    public Long save(News news) {
        Long savedNewsId = null;
        try {
            news.setStatus(PERSISTED);
            savedNewsId = newsDao.save(news);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return savedNewsId;
    }

    @Override
    public Long saveOrUpdate(News news) throws ServiceLayerException {
        return null;
    }

    @Override
    public News update(News news) {
        if (nonNull(news.getNewsId())) {
            Long updatedNewsId = news.getNewsId();
            try {
                news.setStatus(PERSISTED);
                newsDao.update(news);
                news = newsDao.get(updatedNewsId);
                Hibernate.initialize(news.getCommentaries());
            }catch (PersistException e) {
                logger.error(e);
            }
        }
        return news;
    }

    @Override
    public Long delete(News news) {
        if (nonNull(news.getNewsId())) {
            Long deletedNewsId = news.getNewsId();
            try {
                news.setStatus(StatusEnum.DELETED);
                newsDao.update(news);
                return deletedNewsId;
            }catch (PersistException e) {
                logger.error("Error delete category from database:   " + e);
                logger.error(e);
            }
        }
        return (long) MINUS_ONE;
    }

    @Override
    public void remove(News news) {
        try {
            newsDao.delete(news);
        }catch (PersistException e) {
            logger.error("Error remove category from database:   " + e);
            logger.error(e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public News get(Long pK) {
        News news = null;
        try {
            news = newsDao.get(pK);
            Hibernate.initialize(news.getCommentaries());
        } catch (PersistException e) {
            logger.error(e);
        }
        return news;
    }

    @Override
    public News load(Long pK) {
        News news = null;
        try {
            news = newsDao.load(pK);
        } catch (HibernateException e) {
            logger.error("Error get list of Categories from database" + e);
        } catch (PersistException e) {
            logger.error(e);
        }
        return news;
    }

    private int size(long numberOfPages, int pageSize) {
        return (int) Math.ceil((numberOfPages - 1) / pageSize) + 1;
    }

}
