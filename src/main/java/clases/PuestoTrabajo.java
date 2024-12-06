package clases;

import javax.persistence.Embeddable;

/**
 * Esta clase contiene los datos de un puesto de trabajo de un trabajador,
 * es una clase embebida y siempre tiene que ir en un objeto de la clase trabajador.
 *
 * @author David Puerto Cuenca
 */
@Embeddable
public class PuestoTrabajo {

    private String codigo;  //Codigo de puesto de trabajo.
    private String departamento; //Por ejemplo informatica
    private String seccion; //Por ejemplo 2B
    private String nivelAcademico; //El nivel requerido para acceder al puesto. (Universitarios etc..)

    public PuestoTrabajo(String codigo, String departamento, String seccion, String nivelAcademico) {
        this.codigo = codigo;
        this.departamento = departamento;
        this.seccion = seccion;
        this.nivelAcademico = nivelAcademico;
    }

    @Override
    public String toString() {
        return "Codigo: '" + codigo +
                " Departamento: '" + departamento +
                " Seccion: '" + seccion +
                " Nivel academico: '" + nivelAcademico ;
    }
}
