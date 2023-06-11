package Components;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        Dotenv dotenv = Dotenv.load();

        try {
            Configuration configuration = new Configuration()
                    .configure()
                    .setProperty("hibernate.connection.url", String.format("jdbc:mysql://localhost/scd", dotenv.get("DB_HOST"), dotenv.get("DB_NAME")))
                    .setProperty("hibernate.connection.username", dotenv.get("DB_USER"))
                    .setProperty("hibernate.connection.password", dotenv.get("DB_PASS"));

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}