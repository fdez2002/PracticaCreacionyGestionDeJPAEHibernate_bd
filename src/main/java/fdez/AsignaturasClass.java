package fdez;

import jakarta.persistence.*;

@Entity
@Table(name = "asignaturas", schema = "ESCUELA")
public class AsignaturasClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;

    public AsignaturasClass(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public AsignaturasClass(String nombre) {
        this.nombre = nombre;
    }

    public AsignaturasClass() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AsignaturasClass that = (AsignaturasClass) o;

        if (id != that.id) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AsignaturasClass{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
