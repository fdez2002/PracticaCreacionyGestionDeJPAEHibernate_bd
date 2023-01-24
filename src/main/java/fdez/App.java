package fdez;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class App {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static EntityTransaction transaction = entityManager.getTransaction();
    private static Scanner scanner = new Scanner(System.in);//nos permite utilizar el teclado

    public static void main(String[] args){

        menu();


    }

    /**
     * Nos muestra las distintas opciones que podemos hacer.
     * Comprueba si la opcion existe
     * Nos redirige a la opcion que le hayamos dicho
     */
    private static void menu(){
        //menu
        System.out.println("Selecciona una opcion");
        System.out.println("1: Creacion de un alumno");
        System.out.println("2: Eliminación de un alumno");
        System.out.println("3: Creacion de una asignatura");
        System.out.println("4: Eliminación de una asignatura");
        System.out.println("5: Creacion de una nota");
        System.out.println("6: Eliminación de una nota");
        System.out.println("7: Mostrar tablas");
        System.out.println("8: Salir");


        int opcion = scanner.nextInt();
        //comprobamos si ese numero existe
        while (opcion <= 0 || opcion >= 8){
            System.out.println("Seleccione una opción correcta");
            opcion = scanner.nextInt();

        }
        switch (opcion){
            case 1:
                crearAlumno();
                break;
            case 2:
                pedirEliAlum();
                break;
            case 3:
                crearAsignatura();
                break;
            case 4:
                pedirEliAsig();
                break;
            case 5:
                crearNota();
                break;
            case 6:
                pedirEliNota();
                break;
            case 7:
                mostrarAlumnos();
                mostrarAsignatura();
                mostrarNotas();
                break;
            case 8:
                System.exit (0);
                break;
        }
    }

    /**
     * Mostramos la tabla alumnos
     */
    private static void mostrarAlumnos(){
        //iniciamos la conexion
        transaction.begin();
        //enviamos la consulta
        Query query = entityManager.createQuery("SELECT p FROM AlumnosClass p");
        //guardamos el resultado de una consulta en una lista
        List<AlumnosClass> alumnosClassesList = query.getResultList();
        //recorremos la lista y mostramos por pantalla
        for (AlumnosClass alumn : alumnosClassesList){
            System.out.println(alumn.toString());
        }
        //cerramos la conexion
        transaction.commit();
    }

    /**
     * Mostramos la tabla asignatura
     */
    private static void mostrarAsignatura(){
        transaction.begin();
        Query query = entityManager.createQuery("SELECT p FROM AsignaturasClass p");
        List<AsignaturasClass> asignaturaClassesList = query.getResultList();
        for (AsignaturasClass asig : asignaturaClassesList){
            System.out.println(asig.toString());
        }
        transaction.commit();
    }

    /**
     * Mostramos la tabla notas
     */
    private static void mostrarNotas(){
        transaction.begin();
        Query query = entityManager.createQuery("SELECT p FROM NotasClass p");
        List<NotasClass> notasClassesList = query.getResultList();
        for (NotasClass nota : notasClassesList){
            System.out.println(nota.toString());
        }
        transaction.commit();
    }

    /**
     * Pedimos al usuario los distintos datos que conforma a un alumno y se lo enviamos al metodo guardar alumno
     */
    private static void crearAlumno(){
        System.out.println("Nombre");
        String nombre = scanner.next();
        System.out.println("Apellido");
        String apellido = scanner.next();
        System.out.println("Edad");
        int edad = scanner.nextInt();

        guardarAlumno(nombre, apellido, edad);
        menu();

    }

    /**
     * Realiza un insert con los datos que ha introducido el usuario en la tabla Alumnos
     * @param nombre
     * @param apellidos
     * @param edad
     */
    private static void guardarAlumno(String nombre, String apellidos, int edad) {
        AlumnosClass alumnosClass = new AlumnosClass( nombre, apellidos, edad);

        transaction.begin();

        AlumnosClass a = entityManager.merge(alumnosClass);

        entityManager.persist(a);

        transaction.commit();


    }

    /**
     * Pedimos al usaurio los datos que se necesita para crear una asignatura y llama al metodo que hace ese insert
     */
    private static void crearAsignatura(){
        System.out.println("Nombre");
        String nombre = scanner.next();

        guardarAsignaturas(nombre);
        menu();

    }

    /**
     * Obtenemos el datos que el usuario a introducido y creamos la asignatura
     * @param nombre
     */
    private static void guardarAsignaturas(String nombre) {

        AsignaturasClass asignaturasClass = new AsignaturasClass( nombre);

        transaction.begin();

        AsignaturasClass asig = entityManager.merge(asignaturasClass);

        entityManager.persist(asig);

        transaction.commit();


    }

    /**
     * Le pedimos al usuario los datos que se necesitan para crear una nota
     */
    private static void crearNota(){
        System.out.println("Id alumno");
        int idAlum = scanner.nextInt();
        System.out.println("Id asignatura");
        int idAsig = scanner.nextInt();
        System.out.println("Nota");
        BigDecimal nota = scanner.nextBigDecimal();

        guardarNota(idAlum, idAsig, nota);
        menu();

    }

    /**
     * Obtenemos los datos para crear una asignatura
     * @param idAlumno
     * @param idAsugnatura
     * @param nota
     */
    private static void guardarNota(int idAlumno, int idAsugnatura, BigDecimal nota) {

        try {
            NotasClass notasClass = new NotasClass(idAlumno, idAsugnatura, nota);

            transaction.begin();

            NotasClass notas = entityManager.merge(notasClass);

            entityManager.persist(notas);

            transaction.commit();
        }catch (Exception e){
            //si la asignatura o el alumno no existen, le informaremos al usuario de ello
            System.out.println("La asignatura o el alumno no existen");
            //terminaremos la ejecucion
            transaction.commit();

        }


    }

    /**
     * Le pedimos al usuario el id del alumno a eliminar
     */
    private static void pedirEliAlum(){
        System.out.println("Introduce el id del alumno a eliminar:");
        int id = scanner.nextInt();
        eliminarAlumno(id);
        menu();
    }

    /**
     * Obtenemos el id que el usuario a introducido  y haremos un remove dcon ese id
     * @param id
     */
    private static void eliminarAlumno(int id) {
        try{

            transaction.begin();

            entityManager.remove(entityManager.find(AlumnosClass.class, id));

            transaction.commit();

        }catch(IllegalArgumentException e){
            //si el alumno no existe se lo informaremos al usuario y volveremos al menu principal
            System.out.println("Este alumno no existe");
            transaction.commit();
        }
    }

    /**
     * Le pedimos al usuario el id de la asifnatura para eliminar
     */
    private static void pedirEliAsig(){
        System.out.println("Introduce el id del la asignatura a eliminar:");
        int id = scanner.nextInt();
        eliminarAsignatura(id);
        menu();
    }

    /**
     * Obtenemos el id de la asignatura y haremos un remove en la base de datos
     * @param id
     */
    private static void eliminarAsignatura(int id) {

        try{
        transaction.begin();

        entityManager.remove(entityManager.find(AsignaturasClass.class, id));

        transaction.commit();
        }catch(IllegalArgumentException e){
            //si la asignatura no existe se lo indicaremos al usuario
            System.out.println("Esta asignatura no existe");
            transaction.commit();
        }
    }
    //Pedimos al usuario el id de la nota a eliminar
    private static void pedirEliNota(){
        System.out.println("Introduce el id del la nota a eliminar:");
        int id = scanner.nextInt();
        eliminarNota(id);
        menu();
    }

    /**
     * Obtenemos el id de la nota a eliminar  y haremos un remove con ese id
     * @param id
     */
    private static void eliminarNota(int id) {

        try{
            transaction.begin();

            entityManager.remove(entityManager.find(NotasClass.class, id));

            transaction.commit();
        }catch(IllegalArgumentException e){
            System.out.println("Esta nota no existe");
            transaction.commit();
        }
    }

}
