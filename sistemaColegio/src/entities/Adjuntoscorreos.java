/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "adjuntos_correos")
@XmlRootElement
public class Adjuntoscorreos implements Serializable {
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adjuntos_correos")
    private Long id_adjuntos_correos;
    @JoinColumn(name = "idcorreos", referencedColumnName = "idcorreos")
    @ManyToOne
    private Correos correos;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "nombre")
    private String nombre;

    public Adjuntoscorreos() {
    }
    
    public Long getId_adjuntos_correos() {
		return id_adjuntos_correos;
	}

    public void setId_adjuntos_correos(Long id_adjuntos_correos) {
		this.id_adjuntos_correos = id_adjuntos_correos;
	}

    public Correos getCorreos() {
		return correos;
	}

	public void setCorreos(Correos correos) {
		this.correos = correos;
	}
	
    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id_adjuntos_correos != null ? id_adjuntos_correos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adjuntoscorreos)) {
            return false;
        }
        Adjuntoscorreos other = (Adjuntoscorreos) object;
        if ((this.id_adjuntos_correos == null && other.id_adjuntos_correos != null) || (this.id_adjuntos_correos != null && !this.id_adjuntos_correos.equals(other.id_adjuntos_correos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tareas[ idtareas=" + id_adjuntos_correos + " ]";
    }

}
