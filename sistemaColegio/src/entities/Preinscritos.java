/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "preinscritos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preinscritos.findAll", query = "SELECT p FROM Preinscritos p"),
    @NamedQuery(name = "Preinscritos.findByIdpreinscritos", query = "SELECT p FROM Preinscritos p WHERE p.idpreinscritos = :idpreinscritos"),
    @NamedQuery(name = "Preinscritos.findByApellidos", query = "SELECT p FROM Preinscritos p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Preinscritos.findByNombres", query = "SELECT p FROM Preinscritos p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Preinscritos.findByEdad", query = "SELECT p FROM Preinscritos p WHERE p.edad = :edad"),
    @NamedQuery(name = "Preinscritos.findByColegioanterior", query = "SELECT p FROM Preinscritos p WHERE p.colegioanterior = :colegioanterior"),
    @NamedQuery(name = "Preinscritos.findByTelefono", query = "SELECT p FROM Preinscritos p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Preinscritos.findByAcudiente", query = "SELECT p FROM Preinscritos p WHERE p.acudiente = :acudiente"),
    @NamedQuery(name = "Preinscritos.findBySexo", query = "SELECT p FROM Preinscritos p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Preinscritos.findByEmail", query = "SELECT p FROM Preinscritos p WHERE p.email = :email"),
    @NamedQuery(name = "Preinscritos.findByGrado", query = "SELECT p FROM Preinscritos p WHERE p.grado = :grado")})
public class Preinscritos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpreinscritos")
    private Long idpreinscritos;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 2147483647)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 2147483647)
    @Column(name = "edad")
    private String edad;
    @Size(max = 2147483647)
    @Column(name = "colegioanterior")
    private String colegioanterior;
    @Size(max = 2147483647)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 2147483647)
    @Column(name = "acudiente")
    private String acudiente;
    @Size(max = 2147483647)
    @Column(name = "sexo")
    private String sexo;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Column(name = "grado")
    private Short grado;

    public Preinscritos() {
    }

    public Preinscritos(Long idpreinscritos) {
        this.idpreinscritos = idpreinscritos;
    }

    public Long getIdpreinscritos() {
        return idpreinscritos;
    }

    public void setIdpreinscritos(Long idpreinscritos) {
        this.idpreinscritos = idpreinscritos;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getColegioanterior() {
        return colegioanterior;
    }

    public void setColegioanterior(String colegioanterior) {
        this.colegioanterior = colegioanterior;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(String acudiente) {
        this.acudiente = acudiente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getGrado() {
        return grado;
    }

    public void setGrado(Short grado) {
        this.grado = grado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpreinscritos != null ? idpreinscritos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preinscritos)) {
            return false;
        }
        Preinscritos other = (Preinscritos) object;
        if ((this.idpreinscritos == null && other.idpreinscritos != null) || (this.idpreinscritos != null && !this.idpreinscritos.equals(other.idpreinscritos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Preinscritos[ idpreinscritos=" + idpreinscritos + " ]";
    }
    
}
