package fdez;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "notas", schema = "ESCUELA")
public class NotasClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_alumno")
    private int idAlumno;
    @Basic
    @Column(name = "id_asignatura")
    private int idAsignatura;
    @Basic
    @Column(name = "nota")
    private BigDecimal nota;
    public NotasClass(int idAlumno, int idAsignatura, BigDecimal nota){
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
        this.nota = nota;
    }

    public NotasClass() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotasClass that = (NotasClass) o;

        if (id != that.id) return false;
        if (idAlumno != that.idAlumno) return false;
        if (idAsignatura != that.idAsignatura) return false;
        if (nota != null ? !nota.equals(that.nota) : that.nota != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idAlumno;
        result = 31 * result + idAsignatura;
        result = 31 * result + (nota != null ? nota.hashCode() : 0);
        return result;
    }
}
