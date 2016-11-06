/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "preguntas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preguntas.findAll", query = "SELECT p FROM Preguntas p")
    , @NamedQuery(name = "Preguntas.findByIdpreguntas", query = "SELECT p FROM Preguntas p WHERE p.idpreguntas = :idpreguntas")
    , @NamedQuery(name = "Preguntas.findByTexto", query = "SELECT p FROM Preguntas p WHERE p.texto = :texto")})
public class Preguntas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpreguntas")
    private Long idpreguntas;
    @Size(max = 2147483647)
    @Column(name = "texto")
    private String texto;
    @JoinColumn(name = "idanosacademicos", referencedColumnName = "idanosacademicos")
    @ManyToOne
    private Anosacademicos idanosacademicos;
    @JoinColumn(name = "segmentopreguntas", referencedColumnName = "idsegmentopreguntas")
    @ManyToOne
    private Segmentopreguntas segmentopreguntas;

    public Preguntas() {
    }

    public Preguntas(Long idpreguntas) {
        this.idpreguntas = idpreguntas;
    }

    public Long getIdpreguntas() {
        return idpreguntas;
    }

    public void setIdpreguntas(Long idpreguntas) {
        this.idpreguntas = idpreguntas;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Anosacademicos getIdanosacademicos() {
        return idanosacademicos;
    }

    public void setIdanosacademicos(Anosacademicos idanosacademicos) {
        this.idanosacademicos = idanosacademicos;
    }

    public Segmentopreguntas getSegmentopreguntas() {
        return segmentopreguntas;
    }

    public void setSegmentopreguntas(Segmentopreguntas segmentopreguntas) {
        this.segmentopreguntas = segmentopreguntas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpreguntas != null ? idpreguntas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preguntas)) {
            return false;
        }
        Preguntas other = (Preguntas) object;
        if ((this.idpreguntas == null && other.idpreguntas != null) || (this.idpreguntas != null && !this.idpreguntas.equals(other.idpreguntas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.losalpes.entities.Preguntas[ idpreguntas=" + idpreguntas + " ]";
    }
    
}
