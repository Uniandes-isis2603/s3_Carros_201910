/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carros.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Julio Ruiz
 */
@Entity
public class QuejasReclamosEntity extends BaseEntity{
    
    private Long casoId;
    private boolean solucionado;
    private int tipo;
    private String comentarios;
    
    @PodamExclude
    @ManyToOne()
    private CompraVentaEntity compraVenta;
    
    public QuejasReclamosEntity(){
        
    }
/**
     * @return the solucionado
     */
    public boolean isSolucionado() {
        return solucionado;
    }

    /**
     * @param solucionado the solucionado to set
     */
    public void setSolucionado(boolean solucionado) {
        this.solucionado = solucionado;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the carroId
     */
    public Long getCarroId() {
        return casoId;
    }

    /**
     * @param carroId the carroId to set
     */
    public void setCarroId(Long carroId) {
        this.casoId = carroId;
    }
}
