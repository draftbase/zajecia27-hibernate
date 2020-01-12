package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User user = new User();
        user.setName("Marcin");
        user.setType(Type.PREMIUM);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        user.setName("Tomasz");
        entityManager.getTransaction().commit();

        User retrievedUser = entityManager.find(User.class, 1L);
        System.out.println(retrievedUser);

//        entityManager.getTransaction().begin();
//        entityManager.remove(user);
//        entityManager.getTransaction().commit();

        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name LIKE '%asz'", User.class);
        List<User> resultList = query.getResultList();
        resultList.forEach(u -> System.out.println(u));

        entityManager.close();
        entityManagerFactory.close();
    }
}
