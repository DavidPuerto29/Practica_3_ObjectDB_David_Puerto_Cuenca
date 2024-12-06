package mainTest;

import clases.PuestoTrabajo;
import clases.Trabajador;

import javax.persistence.PersistenceException;
import java.util.*;

import static dao.TrabajadorDAO.*;
import static dao.TrabajadorDAO.eliminarPorID;

/**
 * Clase que contiene todas las opciones a tomar con la clase trabajador
 *  consta de un menu en el que se pueden seleccionar opciones y a su vez
 *  contiene métodos para la gestion de errores y de funcionalidad.
 *  Esta clase contiene dos HashMap uno de trabajadores y otro de puestos de
 *  trabajo en el que se guardan las instancias para evitar errores de duplicación
 *  de id etc...
 *
 *  A tener en cuenta que las instancias de Puesto de trabajo son predefinidas y el usuario
 *  no puede crear nuevas instancias, solo se puede por código.
 * @author David Puerto Cuenca
 */
public class main {
    //Para gestionar las transacciones a nivel de código.
    static HashMap<Integer, Trabajador> trabajadores = new HashMap<>();
    static List<PuestoTrabajo> puestos = new ArrayList<>();
    public static void main(String[] args) {
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("Parte de David Puerto Cuenca");
        //Cuando se inicia el programa se cargan todos los datos de la sql en el HashMap.
        cargarDatos();
        //------------------------------------------------Prefab----------------------------------------------------------------------------------------------------------------------
        PuestoTrabajo frutero = new PuestoTrabajo("1","Frutería","2B","Eso");
        PuestoTrabajo marketing = new PuestoTrabajo("2","Marketing","5C","Universitarios, superiores");
        PuestoTrabajo cajero = new PuestoTrabajo("3","Cajero","1D","Eso");
        PuestoTrabajo contable = new PuestoTrabajo("4","Contable","5A","Universitarios, superiores");
        PuestoTrabajo reponedor = new PuestoTrabajo("5","Reponedor","2D","Eso");
        puestos.add(frutero);
        puestos.add(marketing);
        puestos.add(cajero);
        puestos.add(contable);
        puestos.add(reponedor);
        //------------------------------------------------Prefab----------------------------------------------------------------------------------------------------------------------
        boolean bucle = true;

        while (bucle) {
            System.out.println("""
                         ╔════════════════════|Menu Trabajadores|════════════════════╗  \r
                         ║	                                        			  	 ║\r
                    	 ║	               1 Leer trabajador	                     ║\r
                    	 ║	               2 Leer todos los trabajadores	         ║\r
                    	 ║	               3 Añadir trabajador	                     ║\r
                    	 ║	               4 Actualizar trabajador				  	 ║\r
                    	 ║	               5 Eliminar trabajador				  	 ║\r	
                    	 ║	               6 Leer trabajador por edad			  	 ║\r
                    	 ║	               7 Leer suma total de nominas			  	 ║
                    	 ║	                                        			  	 ║\r
                    	 ║	               0 Finalizar Programa 				  	 ║\r
                         ╚═══════════════════════════════════════════════════════════╝\r
                    """);
            Integer op = escribirInteger();
            switch (op) {
                case 0:
                    bucle = false;
                    break;
                case 1:
                    System.out.println("Introduzca el id del trabajador: ");
                    try{
                        System.out.println("Datos del trabajador: "+leerTrabajador(escribirInteger()));
                    }catch (PersistenceException e){
                        System.out.println("No se ha encontrado el trabajador, compruebe que el id sea correcto.");
                    }
                    break;
                case 2:
                    leerTrabajadores();
                    break;
                case 3:
                    AñadirTrabajador();
                    break;
                case 4:
                    actualizarTrabajadorMain();
                    break;
                case 5 :
                    System.out.println("Introduzca el id del trabajador: ");
                    try{
                        Integer id = escribirInteger();
                        if(eliminarPorID(id)){
                            trabajadores.remove(id);
                            System.out.println("Trabajador eliminado correctamente.");
                        }else{
                            System.out.println("El trabajador no ha sido encontrado.");
                        }
                    }catch (PersistenceException e){
                        System.out.println("No se ha encontrado el trabajador, compruebe que la id sea correcta.");
                    }
                    break;
                case 6:
                    System.out.println("Introduzca la edad de los trabajadores a buscar: ");
                        Integer edad = escribirInteger();
                    leerTrabajadoresPorEdad(edad);
                    break;
                case 7:
                    System.out.println("El saldo de gasto mensual en nominas es de: "+leerTotalNominas()+"€");
                    break;
                case 999999:
                    System.out.println("Introduzca una opción correcta.");
                    break;
                default:
                    System.out.println("Seleccione una opción valida.");
            }
        }

        System.out.println("Final de parte de David Puerto Cuenca");
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }

    /**
     * Método que se ejecuta cada vez que se inicia el programa,
     * se encarga de cargar los datos de la sql y guardarlo en el
     * HashMap.
     */
    private static void cargarDatos(){
        try{
            List<Trabajador> l = leerTodosTrabajadores();
            if(l != null) {
                for (Trabajador t : l) {
                    trabajadores.put(t.getId(), t);
                }
                System.out.println("Datos cargados correctamente.");
            }else{
                System.out.println("No hay datos registrados.");
            }
        }catch (PersistenceException e){
            System.out.println("Error en la carga de datos.");
        }
    }

    /**
     * Parecido al método de cargarDatos solo que este no guarda los datos
     * en el HashMap, si no que muestra los Trabajadores registrados al usuario.
     */
    private static void leerTrabajadores(){
        System.out.println("Los trabajadores disponibles son: ");
        try {
            List<Trabajador> l = leerTodosTrabajadores();
            if(l == null){
                System.out.println("No hay trabajadores registrados, añada algunos antes de leerlos.");
            }else {
                for (Trabajador t : l) {
                    System.out.println(t);
                }
            }
        }catch (PersistenceException e){
            System.out.println("Error en la consulta, compruebe que todos los valores sean correctos y estén creados correctamente.");
        }
    }

    /**
     * Parecido al método de cargarDatos solo que este no guarda los datos
     * en el HashMap, si no que muestra los Trabajadores registrados al usuario.
     */
    private static void leerTrabajadoresPorEdad(Integer edad){
        System.out.println("Los trabajadores disponibles son: ");
        try {
            List<Trabajador> l = leerTrabajadoresEdad(edad);
            if(l == null){
                System.out.println("No hay trabajadores registrados con esa edad.");
            }else {
                for (Trabajador t : l) {
                    System.out.println(t);
                }
            }
        }catch (PersistenceException e){
            System.out.println("Error en la consulta, compruebe que todos los valores sean correctos y estén creados correctamente.");
        }
    }


    /**
     * Método encargado de mostrar al usuario los departamentos registrados
     * en el programa.
     */
    private static void mostrarDepartamentos() {
        int indice = 1;
        System.out.println("Puestos de trabajo disponibles:");
        for(int i = 0; i < puestos.size();++i){
            System.out.println(indice+": "+puestos.get(i));
                ++indice;
        }
    }

    /**
     * Debido a que en varios métodos del programa se piden rellenar
     * los datos de un trabajador se crea un método estandar en el que
     * se pide al usuario introducir los datos de un trabajador
     * devolviendo el objeto trabajador al método que le llama.
     *
     * @return Devuelve la instancia de Trabajador con los datos rellenados por el usuario.
     */
    private static Trabajador introducirDatosTrabajador(){
        System.out.println("Para añadir el trabajador primero debes seleccionar su departamento, seleccione el indice de la lista:");
            mostrarDepartamentos();
                Integer depart = escribirInteger();
                    //En caso de que no se seleccione un valor correcto de la lista devuelve null.
                    if (depart > puestos.size() || depart <= 0){
                        return null;
                    }else {
                        //Para sincronizar la variable con la posición correcta de la lista.
                        depart--;
                        PuestoTrabajo pt = puestos.get(depart);
                            System.out.println("Introduzca el id del trabajador: ");
                                Integer id = escribirInteger();
                                    System.out.println("Introduzca el nombre del trabajador: ");
                                        String nombre = escribirString();
                                            System.out.println("Introduzca el correo del trabajador: ");
                                                String correo = escribirString();
                                                    System.out.println("Introduzca la edad del trabajador: ");
                                                        Integer edad = escribirInteger();
                                                            System.out.println("Introduzca los años de experiencia del trabajador: ");
                                                                Integer añosExperiencia = escribirInteger();
                                                                    System.out.println("Introduzca la nomina del trabajador: ");
                                                                        double nomina = escribirDouble();
                        return new Trabajador(id,nombre,correo,edad,añosExperiencia,nomina,pt);
                    }
    }

    /**
     * Para comenzar este método llama al método de introducirDatosTrabajador
     * y lo guarda en una variable de Trabajador después envía esa variable al
     * método guardado en clase TrabajadorDAO llamado insertarTrabajador donde lo
     * introduce en la base de datos.
     */
    private static void AñadirTrabajador(){
        Trabajador t = introducirDatosTrabajador();
        boolean duplicado = false;
        //En caso de seleccionar un valor de la lista puesto inexistente se gestiona el error.
        if(t != null) {
            if(t.getId() == -1111111111) {
                System.out.println("Introduzca in id correcto.");
            }else {
                for(Trabajador tHm : trabajadores.values()){
                    if(t.getId() == tHm.getId()){
                        duplicado = true;
                    }
                }
                if (insertarTrabajador(t) && !duplicado) {
                    //Guardamos la variable en el HashMap para que no pueda haber dos id iguales.
                    trabajadores.put(t.getId(), t);
                    System.out.println("Trabajador añadido correctamente.");
                }else{
                    System.out.println("No puede haber dos id iguales, por favor introduzca otra id.");
                }
            }
        }else{
            System.out.println("Error introduzca un puesto de trabajo correcto.");
        }
    }

    /**
     * Método encargado de permitir al usuario actualizar un Trabajador,
     * este permite al usuario introducir de nuevo los valores y crea un objeto
     * de Trabajador una vez echo esto se llama al método DAO de actualizar y se
     * actualizan los valores del Trabajador con el id seleccionado, también se
     * realiza una sobre escritura en el HashMap.
     */
    private static void actualizarTrabajadorMain(){
        boolean existente = false;
        System.out.println("Introduzca el id del trabajador a modificar: ");

            Integer id = escribirInteger();
                //Comprobamos que la id se encuentra registrada.
                for(Trabajador tHm : trabajadores.values()){
                    if (id.equals(tHm.getId())) {
                        existente = true;
                            break;
                    }
                }
                if(existente) {
                    //No se puede llamar al método IntroducirDatosTrabajador debido a que si no nos permitiría modificar el id.
                    System.out.println("Seleccione el departamento del trabajador: ");
                        mostrarDepartamentos();
                            Integer depart = escribirInteger();
                    if (depart > puestos.size() || depart <= 0) {
                        System.out.println("Introduzca un puesto de trabajo valido.");
                    } else {
                        //Para sincronizar la variable con la posición correcta de la lista.
                        depart--;
                        PuestoTrabajo pt = puestos.get(depart);

                        System.out.println("Introduzca el nombre del trabajador: ");
                            String nombre = escribirString();
                                System.out.println("Introduzca el correo del trabajador: ");
                                    String correo = escribirString();
                                        System.out.println("Introduzca la edad del trabajador: ");
                                            Integer edad = escribirInteger();
                                                System.out.println("Introduzca los años de experiencia del trabajador: ");
                                                    Integer añosExperiencia = escribirInteger();
                                                        System.out.println("Introduzca la nomina del trabajador: ");
                                                            double nomina = escribirDouble();
                                                                Trabajador t = new Trabajador(id, nombre, correo, edad, añosExperiencia, nomina, pt);
                        if (actualizarTrabajador(t)) {
                            //Actualizamos en el HashMap
                            trabajadores.remove(id);
                            trabajadores.put(id, t);
                            System.out.println("Trabajador actualizado correctamente.");
                        }
                    }
                }else{
                    System.out.println("El id del trabajador no ha sido encontrado.");
                }
    }

    /**
     *Este método se utiliza cuando al usuario se le pide una entrada de un dato entero
     * debido a que se puede producir una excepción, si el usuario introduce otro dato
     * se gestiona la excepción, se informa al usuario de que introduzca un dato correcto.
     *
     * @return En caso de que el dato no sea numérico se devolverá un valor imposible para que el usuario llegue, ya que
     * si devolviera null habría que modificar todo el codigo y por cuestiones de tiempo no es posible.
     */
    private static Integer escribirInteger(){
            Scanner num = new Scanner(System.in);
            try{
                return num.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Introduzca un dato correcto.");
                return -1111111111;
            }
    }

    /**
     *Este método se utiliza cuando al usuario se le pide una entrada de un dato entero
     * debido a que se puede producir una excepción, si el usuario introduce otro dato
     * se gestiona la excepción, se informa al usuario de que introduzca un dato correcto.
     *
     *@return En caso de que el dato no sea numérico se devolverá un valor imposible para que el usuario llegue
     */
    private static double escribirDouble(){
        Scanner num2 = new Scanner(System.in);
        try{
            return num2.nextDouble();
        }catch (InputMismatchException e){
            System.out.println("Introduzca un dato correcto.");
            return -1111111111;
        }
    }

    /**
     * Para evitar la repetición de código cada vez que el usuario tenga
     * que introducir una cadena de caracteres se llama a este método.
     * @return devuelve la cadena de caracteres introducida por el usuario.
     */
    private static String escribirString(){
        Scanner sc = new Scanner(System.in);
            return sc.nextLine();
    }
}

