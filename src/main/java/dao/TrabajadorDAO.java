package dao;

import clases.PuestoTrabajo;
import clases.Trabajador;

import javax.persistence.*;
import java.util.List;

/**
 * Clase que contiene todos los métodos de consultas y acciones
 * con la sql de la clase trabajador.
 *
 * @author David Puerto Cuenca
 */

public class TrabajadorDAO {

    /**
     * Este método mediante el uso de los métodos del EntityManager
     * añade un objeto Trabajador a la base de datos.
     *
     * @param t El Trabajador a guardar.
     * @return true si la operación ha sido correcta, false si ha habido algún error.
     */
    public static boolean insertarTrabajador(Trabajador t){
        EntityManager em = null;
        try {
            em = ConnectionODB.connect();
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
                return true;
        }catch (RollbackException e){
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    /**
     * Este método mediante el uso de los metodos del EntityManager
     * elimina un objeto Trabajador a la base de datos.
     *
     * @param id El Id del trabajador a eliminar.
     * @return true si la operación ha sido correcta, false si ha habido algún error.
     */
    public static boolean eliminarPorID(int id) {
        EntityManager em = null;
        try {
            em = ConnectionODB.connect();
            em.getTransaction().begin();
            Trabajador t = em.find(Trabajador.class, id);
            if (t == null) {
                return false;
            }

            em.remove(t);
            em.getTransaction().commit();
            return true;
        }catch (RollbackException e){
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    /**
     * Este método mediante el uso de los metodos del EntityManager
     * y setters del objeto Trabajador actualiza los datos de un trabajador,
     * primero buscando su id en la base de datos y después actualizándolo.
     *
     * @param t El Trabajador a actualizar.
     * @return true si la operación ha sido correcta, false si ha habido algún error.
     */
    public static boolean actualizarTrabajador(Trabajador t){
       EntityManager em = null;

        try{
        em = ConnectionODB.connect();
        em.getTransaction().begin();
            Trabajador trabajador = em.find(Trabajador.class, t.getId());

        trabajador.setNombre(t.getNombre());
        trabajador.setCorreo(t.getCorreo());
        trabajador.setEdad(t.getEdad());
        trabajador.setAñosExperiencia(t.getAñosExperiencia());
        trabajador.setNomina(t.getNomina());
        trabajador.setPuesto(t.getPuesto());

        em.merge(trabajador);
            em.getTransaction().commit();
                em.close();
                return true;

        }catch (RollbackException e){
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    /**
     * Este método se encarga de realizar una consulta SELECT para
     * buscar el Trabajador por la id que se le introduce.
     *
     * @param id La id del trabajador a encontrar.
     * @return Devuelve la entidad Trabajador en caso de encontrarla.
     */
    public static Trabajador leerTrabajador(Integer id) throws PersistenceException{
        String sent = "SELECT p from Trabajador p WHERE p.id = ?1";
            EntityManager em = ConnectionODB.connect();
                Query query = em.createQuery(sent);
                    query.setParameter(1, id);
                        em.getTransaction().begin();
                            Trabajador t = (Trabajador) query.getSingleResult();
                                em.close();
        return t;
    }

    /**
     * Este método se encarga de realizar una consulta SELECT para
     * buscar todos los trabajadores almacenados en la sql, después
     * los almacena en una lista que posteriormente se devuelve.
     *
     * @return Devuelve la lista con los trabajadores encontrados, en caso de
     * estar vacía devuelve null.
     */
    public static List<Trabajador> leerTodosTrabajadores() throws PersistenceException {
        EntityManager em = ConnectionODB.connect();
            String sent = "SELECT p FROM Trabajador p";
                Query query = em.createQuery(sent);
                    List<Trabajador> trabajadores = query.getResultList();
                        em.close();
                        if (trabajadores.isEmpty()){
                            return null;
                        }
                        return trabajadores;
    }

    /**
     * Este método se encarga de realizar una consulta SELECT para
     * buscar todos los trabajadores con la edad introducida, después
     * los almacena en una lista que posteriormente se devuelve.
     *
     * @param edad La edad por la que se buscan los Trabajadores.
     * @return Devuelve la lista con los trabajadores encontrados, en caso de
     * estar vacía devuelve null.
     */
    public static List<Trabajador> leerTrabajadoresEdad(Integer edad)  throws PersistenceException{
        String sent = "SELECT p from Trabajador p WHERE p.edad = ?1";
            EntityManager em = ConnectionODB.connect();
                Query query = em.createQuery(sent);
                    query.setParameter(1, edad);
                        em.getTransaction().begin();
                            List<Trabajador> l = query.getResultList();
                                if (l.isEmpty()){
                                    return null;
                                }
                                em.close();
        return l;
    }

    /**
     * Mediante una consulta SELECT se guarda en una lista de Double
     * la nomina de cada trabajador para después sumarlo todo en la variable
     * suma y devolverlo.
     *
     * @return La suma total de nominas.
     */
    public static double leerTotalNominas(){
        double suma = 0;
        String sent = "SELECT p.nomina from Trabajador p ";
            EntityManager em = ConnectionODB.connect();
                    Query query = em.createQuery(sent);
                        List<Double> nominas = query.getResultList();
                        for (Double nomina : nominas) {
                            suma += nomina;
                        }
        return suma;
    }

    }


