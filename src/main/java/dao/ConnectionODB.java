package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static javax.persistence.Persistence.createEntityManagerFactory;

/**
 * Clase que contiene la conexion a la sql en este caso local.
 *
 * @author David Puerto Cuenca
 */
public class ConnectionODB {
    public static EntityManager connect() {
        EntityManagerFactory emf = createEntityManagerFactory("./db/trabajadores.odb");
        EntityManager em = emf.createEntityManager();
        return em;
    }
}
