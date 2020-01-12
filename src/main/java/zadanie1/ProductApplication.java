package zadanie1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class ProductApplication {

    private Scanner scanner;
    private EntityManager entityManager;

    public ProductApplication() {
        this.scanner = new Scanner(System.in);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void run() {

        while (true) {
            System.out.println();
            System.out.println("0. Wyjście");
            System.out.println("1. Dodaj");
            System.out.println("2. Wyświetl wszystkie");
            System.out.println("3. Wyświetl wszystkie posortowane");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    showAll("");
                    break;
                case "3":
                    showAllSorted();
                    break;
                case "0":
                    entityManager.close();
                    return;
                default:
                    System.out.println("Nie ma takiej opcji");
            }
        }
    }

    private void showAllSorted() {
        System.out.println("Po czym sortujemy?");
        String sortAttribute = scanner.nextLine();

        String orderBy = "";
        if ("name".equalsIgnoreCase(sortAttribute)) {
            orderBy = "ORDER BY prod.name";
        } else if ("price".equalsIgnoreCase(sortAttribute)) {
            orderBy = "ORDER BY prod.price";
        } else {
            System.err.println("Nie ma takiego pola");
            return;
        }

        showAll(orderBy);
    }

    private void showAll(String orderBy) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT prod FROM Product prod " + orderBy, Product.class);
        List<Product> resultList = query.getResultList();
        resultList.forEach(product -> System.out.println(product));
    }

    private void addProduct() {
        System.out.println("Podaj nazwę: ");
        String name = scanner.nextLine();

        System.out.println("Podaj cenę: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Product product = new Product(name, price);
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }
}
