package app.DAOS;

import app.HibernateConfig;
import app.entities.Package;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PackageDAOTest {
    private static EntityManagerFactory emf;
    private static PackageDAO packageDAO;

    Package p1, p2, p3;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTestMode(true);
        emf = HibernateConfig.getEntityManagerFactory();
        packageDAO = new PackageDAO(emf);
    }

    @AfterAll
    static void tearDownAll() {HibernateConfig.setTestMode(false);}

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Package("ab12", "sender1", "reciever1", Package.DeliveryStatus.PENDING);
        p2 = new Package("cd34", "sender2", "reciever2", Package.DeliveryStatus.IN_TRANSIT);
        p3 = new Package("ef56", "sender3", "reciever3", Package.DeliveryStatus.DELIVERED);
        em.getTransaction().begin();
        em.createQuery("delete from Package").executeUpdate();
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test that we can create a boat")
    void create() {
        Package toBeCreated = new Package("gh78","sender4", "reciever4", Package.DeliveryStatus.PENDING);
        Package p = packageDAO.create(toBeCreated);
        assert p.getId() != null;
    }

    @Test
    void delete() {
    }

    @Test
    void findByTrackingNumber() {
    }

    @Test
    void updateDeliveryStatus() {
    }
}