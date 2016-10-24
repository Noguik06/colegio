/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdusuarios", query = "SELECT u FROM Usuarios u WHERE u.idusuarios = :idusuarios"),
    @NamedQuery(name = "Usuarios.findByNombres", query = "SELECT u FROM Usuarios u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuarios.findByApellidos", query = "SELECT u FROM Usuarios u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuarios.findByTipoidentificacion", query = "SELECT u FROM Usuarios u WHERE u.tipoidentificacion = :tipoidentificacion"),
    @NamedQuery(name = "Usuarios.findByNumeroidentificacion", query = "SELECT u FROM Usuarios u WHERE u.numeroidentificacion = :numeroidentificacion"),
    @NamedQuery(name = "Usuarios.findByCiudadidentificacion", query = "SELECT u FROM Usuarios u WHERE u.ciudadidentificacion = :ciudadidentificacion"),
    @NamedQuery(name = "Usuarios.findByFechanacimiento", query = "SELECT u FROM Usuarios u WHERE u.fechanacimiento = :fechanacimiento"),
    @NamedQuery(name = "Usuarios.findBySexo", query = "SELECT u FROM Usuarios u WHERE u.sexo = :sexo"),
    @NamedQuery(name = "Usuarios.findByTelefono", query = "SELECT u FROM Usuarios u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuarios.findByCorreoelectronico", query = "SELECT u FROM Usuarios u WHERE u.correoelectronico = :correoelectronico"),
    @NamedQuery(name = "Usuarios.findByDireccion", query = "SELECT u FROM Usuarios u WHERE u.direccion = :direccion"),
    @NamedQuery(name = "Usuarios.findByGruposanguineo", query = "SELECT u FROM Usuarios u WHERE u.gruposanguineo = :gruposanguineo"),
    @NamedQuery(name = "Usuarios.findByLugarnacimiento", query = "SELECT u FROM Usuarios u WHERE u.lugarnacimiento = :lugarnacimiento"),
    @NamedQuery(name = "Usuarios.findByEps", query = "SELECT u FROM Usuarios u WHERE u.eps = :eps"),
    @NamedQuery(name = "Usuarios.findByAlergias", query = "SELECT u FROM Usuarios u WHERE u.alergias = :alergias"),
    @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password"),
    @NamedQuery(name = "Usuarios.findByNombredeusuario", query = "SELECT u FROM Usuarios u WHERE u.nombredeusuario = :nombredeusuario")})
public class Usuarios implements Serializable {
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @OneToMany(mappedBy = "usuarios")
    private List<Relacionpagousuarios> relacionpagousuariosList;
    @OneToMany(mappedBy = "usuariospara")
    private List<Relacioncorreousuarios> relacioncorreousuariosList;
    @OneToMany(mappedBy = "idusuariosde")
    private List<Correos> correosList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuarios")
    private Long idusuarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoidentificacion")
    private String tipoidentificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroidentificacion")
    private String numeroidentificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ciudadidentificacion")
    private String ciudadidentificacion;
    @Basic(optional = false)
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 2147483647)
    @Column(name = "correoelectronico")
    private String correoelectronico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "gruposanguineo")
    private String gruposanguineo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lugarnacimiento")
    private String lugarnacimiento;
    @Size(max = 2147483647)
    @Column(name = "eps")
    private String eps;
    @Size(max = 2147483647)
    @Column(name = "alergias")
    private String alergias;
    @Size(max = 200)
    @Column(name = "password")
    private String password;
    @Column(name = "estado_activo")
    private boolean estado_activo;
    @Size(max = 2147483647)
    @Column(name = "nombredeusuario")
    private String nombredeusuario;
    @Size(max = 2147483647)
    @Column(name = "sessionid")
    private String sessionid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private List<Relacionusuariocronogramas> relacionusuariocronogramasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private List<Estudiantes> estudiantesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private List<Profesores> profesoresList;

    public Usuarios() {
    }

    public Usuarios(Long idusuarios) {
        this.idusuarios = idusuarios;
    }

    public Usuarios(Long idusuarios, String nombres, String apellidos, String tipoidentificacion, String numeroidentificacion, String ciudadidentificacion, Date fechanacimiento, String sexo, String telefono, String direccion, String lugarnacimiento) {
        this.idusuarios = idusuarios;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoidentificacion = tipoidentificacion;
        this.numeroidentificacion = numeroidentificacion;
        this.ciudadidentificacion = ciudadidentificacion;
        this.fechanacimiento = fechanacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.lugarnacimiento = lugarnacimiento;
    }

    public Long getIdusuarios() {
        return idusuarios;
    }

    public void setIdusuarios(Long idusuarios) {
        this.idusuarios = idusuarios;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoidentificacion() {
        return tipoidentificacion;
    }

    public void setTipoidentificacion(String tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getNumeroidentificacion() {
        return numeroidentificacion;
    }

    public void setNumeroidentificacion(String numeroidentificacion) {
        this.numeroidentificacion = numeroidentificacion;
    }

    public String getCiudadidentificacion() {
        return ciudadidentificacion;
    }

    public void setCiudadidentificacion(String ciudadidentificacion) {
        this.ciudadidentificacion = ciudadidentificacion;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGruposanguineo() {
        return gruposanguineo;
    }

    public void setGruposanguineo(String gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    public String getLugarnacimiento() {
        return lugarnacimiento;
    }

    public void setLugarnacimiento(String lugarnacimiento) {
        this.lugarnacimiento = lugarnacimiento;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombredeusuario() {
        return nombredeusuario;
    }

    public void setNombredeusuario(String nombredeusuario) {
        this.nombredeusuario = nombredeusuario;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public boolean isEstado_activo() {
		return estado_activo;
	}

	public void setEstado_activo(boolean estado_activo) {
		this.estado_activo = estado_activo;
	}

	@XmlTransient
    public List<Relacionusuariocronogramas> getRelacionusuariocronogramasList() {
        return relacionusuariocronogramasList;
    }

    public void setRelacionusuariocronogramasList(List<Relacionusuariocronogramas> relacionusuariocronogramasList) {
        this.relacionusuariocronogramasList = relacionusuariocronogramasList;
    }

    @XmlTransient
    public List<Estudiantes> getEstudiantesList() {
        return estudiantesList;
    }

    public void setEstudiantesList(List<Estudiantes> estudiantesList) {
        this.estudiantesList = estudiantesList;
    }

    @XmlTransient
    public List<Profesores> getProfesoresList() {
        return profesoresList;
    }

    public void setProfesoresList(List<Profesores> profesoresList) {
        this.profesoresList = profesoresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuarios != null ? idusuarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idusuarios == null && other.idusuarios != null) || (this.idusuarios != null && !this.idusuarios.equals(other.idusuarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Usuarios[ idusuarios=" + idusuarios + " ]";
    }

    @XmlTransient
    public List<Relacioncorreousuarios> getRelacioncorreousuariosList() {
        return relacioncorreousuariosList;
    }

    public void setRelacioncorreousuariosList(List<Relacioncorreousuarios> relacioncorreousuariosList) {
        this.relacioncorreousuariosList = relacioncorreousuariosList;
    }

    @XmlTransient
    public List<Correos> getCorreosList() {
        return correosList;
    }

    public void setCorreosList(List<Correos> correosList) {
        this.correosList = correosList;
    }

    @XmlTransient
    public List<Relacionpagousuarios> getRelacionpagousuariosList() {
        return relacionpagousuariosList;
    }

    public void setRelacionpagousuariosList(List<Relacionpagousuarios> relacionpagousuariosList) {
        this.relacionpagousuariosList = relacionpagousuariosList;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
