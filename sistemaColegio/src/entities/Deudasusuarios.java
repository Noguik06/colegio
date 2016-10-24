/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "deudasusuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deudasusuarios.findAll", query = "SELECT d FROM Deudasusuarios d"),
    @NamedQuery(name = "Deudasusuarios.findByTotal", query = "SELECT d FROM Deudasusuarios d WHERE d.total = :total"),
    @NamedQuery(name = "Deudasusuarios.findByUsuarios", query = "SELECT d FROM Deudasusuarios d WHERE d.usuarios = :usuarios")})
public class Deudasusuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Id
    @JoinColumn(name = "usuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Deudasusuarios() {
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    
}
