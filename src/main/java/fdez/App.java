package fdez;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.Scanner;


public class App {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static EntityTransaction transaction = entityManager.getTransaction();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){

        menu();


    }

    private static void menu(){
        //menu
        System.out.println("Selecciona una opcion");
        System.out.println("1: Creacion de un alumno");
        System.out.println("2: Eliminación de un alumno");
        System.out.println("3: Creacion de una asignatura");
        System.out.println("4: Eliminación de una asignatura");
        System.out.println("5: Creacion de una nota");
        System.out.println("6: Eliminación de una nota");
        System.out.println("7: Salir");

        int opcion = scanner.nextInt();
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
                System.exit (0);
                break;
        }
    }
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
    private static void guardarAlumno(String nombre, String apellidos, int edad) {

        AlumnosClass alumnosClass = new AlumnosClass( nombre, apellidos, edad);

        transaction.begin();

        AlumnosClass a = entityManager.merge(alumnosClass);

        entityManager.persist(a);

        transaction.commit();


    }
    private static void crearAsignatura(){
        System.out.println("Nombre");
        String nombre = scanner.next();

        guardarAsignaturas(nombre);
        menu();

    }
    private static void guardarAsignaturas(String nombre) {

        AsignaturasClass asignaturasClass = new AsignaturasClass( nombre);

        transaction.begin();

        AsignaturasClass asig = entityManager.merge(asignaturasClass);

        entityManager.persist(asig);

        transaction.commit();


    }
    private static void crearNota(){
        System.out.println("Id alumno");
        int idAlum = scanner.nextInt();
        System.out.println("Id asignatura");
        int idAsig = scanner.nextInt();
        BigDecimal nota = scanner.nextBigDecimal();

        guardarNota(idAlum, idAsig, nota);
        menu();

    }
    private static void guardarNota(int idAlumno, int idAsugnatura, BigDecimal nota) {

        try {
            NotasClass notasClass = new NotasClass(idAlumno, idAsugnatura, nota);

            transaction.begin();

            NotasClass notas = entityManager.merge(notasClass);

            entityManager.persist(notas);

            transaction.commit();
        }catch (Exception e){
            System.out.println("La asignatura o el alumno no existen");
            transaction.commit();

        }


    }
    private static void pedirEliAlum(){
        System.out.println("Introduce el id del alumno a eliminar:");
        int id = scanner.nextInt();
        eliminarAlumno(id);
        menu();
    }
    private static void eliminarAlumno(int id) {
        try{

            transaction.begin();

            entityManager.remove(entityManager.find(AlumnosClass.class, id));

            transaction.commit();

        }catch(IllegalArgumentException e){
            System.out.println("Este alumno no existe");
            transaction.commit();
            pedirEliAlum();
        }
    }

    private static void pedirEliAsig(){
        System.out.println("Introduce el id del la asignatura a eliminar:");
        int id = scanner.nextInt();
        eliminarAsignatura(id);
        menu();
    }
    private static void eliminarAsignatura(int id) {

        try{
        transaction.begin();

        entityManager.remove(entityManager.find(AsignaturasClass.class, id));

        transaction.commit();
        }catch(IllegalArgumentException e){
            System.out.println("Esta asignatura no existe");
            transaction.commit();
            pedirEliAsig();
        }
    }
    private static void pedirEliNota(){
        System.out.println("Introduce el id del la nota a eliminar:");
        int id = scanner.nextInt();
        eliminarNota(id);
        menu();
    }
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
