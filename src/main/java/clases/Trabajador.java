package clases;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Clase que contiene los atributos de un trabajador de supermercado esta clase
 * es una entidad con la que se van a realizar varias operaciones en el main y sql
 * con los métodos de la clase TrabajadorDAO, contiene una clase embebida de PuestoTrabajo.
 *
 * @author David Puerto Cuenca
 */

@Entity
public class Trabajador {
    @Id private Integer id;
    private String nombre;
    private String correo;
    private Integer edad;
    private Integer añosExperiencia;
    private double nomina;
    @Embedded private PuestoTrabajo puesto; //La clase embebida.

    public Trabajador(int id, String nombre, String correo, Integer edad, Integer añosExperiencia, double nomina, PuestoTrabajo puesto) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.añosExperiencia = añosExperiencia;
        this.nomina = nomina;
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " Nombre: '" + nombre +
                " Correo: '" + correo +
                " Edad:" + edad +
                " Años de experiencia: " + añosExperiencia +
                " Nomina: " + nomina +
                " Puesto: " + puesto ;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setAñosExperiencia(Integer añosExperiencia) {
        this.añosExperiencia = añosExperiencia;
    }

    public void setNomina(double nomina) {
        this.nomina = nomina;
    }

    public void setPuesto(PuestoTrabajo puesto) {
        this.puesto = puesto;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Integer getEdad() {
        return edad;
    }

    public Integer getAñosExperiencia() {
        return añosExperiencia;
    }

    public double getNomina() {
        return nomina;
    }

    public PuestoTrabajo getPuesto() {
        return puesto;
    }
}
