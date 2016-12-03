/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 * @author juannoguera
 */
@Entity
@Table(name = "segmentopreguntas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segmentopreguntas.findAll", query = "SELECT s FROM Segmentopreguntas s")
    , @NamedQuery(name = "Segmentopreguntas.findByIdsegmentopreguntas", query = "SELECT s FROM Segmentopreguntas s WHERE s.idsegmentopreguntas = :idsegmentopreguntas")
    , @NamedQuery(name = "Segmentopreguntas.findByNombre", query = "SELECT s FROM Segmentopreguntas s WHERE s.nombre = :nombre")})
public class Segmentopreguntas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsegmentopreguntas")
    private Long idsegmentopreguntas;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private int tipo;
    
    @OneToMany(mappedBy = "segmentopreguntas", fetch=FetchType.LAZY)
    private List<Preguntas> preguntasList;

    public Segmentopreguntas() {
    }

    public Segmentopreguntas(Long idsegmentopreguntas) {
        this.idsegmentopreguntas = idsegmentopreguntas;
    }

    public Long getIdsegmentopreguntas() {
        return idsegmentopreguntas;
    }

    public void setIdsegmentopreguntas(Long idsegmentopreguntas) {
        this.idsegmentopreguntas = idsegmentopreguntas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@XmlTransient
    public List<Preguntas> getPreguntasList() {
        return preguntasList;
    }

    public void setPreguntasList(List<Preguntas> preguntasList) {
        this.preguntasList = preguntasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsegmentopreguntas != null ? idsegmentopreguntas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmentopreguntas)) {
            return false;
        }
        Segmentopreguntas other = (Segmentopreguntas) object;
        if ((this.idsegmentopreguntas == null && other.idsegmentopreguntas != null) || (this.idsegmentopreguntas != null && !this.idsegmentopreguntas.equals(other.idsegmentopreguntas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.losalpes.entities.Segmentopreguntas[ idsegmentopreguntas=" + idsegmentopreguntas + " ]";
    }
    
}
