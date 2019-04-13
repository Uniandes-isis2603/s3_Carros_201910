/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carros.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Andres Forero
 */
@Entity     
public class MarcaEntity extends BaseEntity implements Serializable{    

    /**
     * @return the puntosVenta
     */
    public List<PuntoVentaEntity> getPuntosVenta() {
        return puntosVenta;
    }

    /**
     * @param puntosVenta the puntosVenta to set
     */
    public void setPuntosVenta(List<PuntoVentaEntity> puntosVenta) {
        this.puntosVenta = puntosVenta;
    }
       
    public void addPuntoVenta(PuntoVentaEntity nuevoPuntoVenta)
    {
        this.puntosVenta.add(nuevoPuntoVenta);
    }
    /**
     * @return the modelos
     */
    public List<ModeloEntity> getModelos() {
        return modelos;
    }

    /**
     * @param modelos the modelos to set
     */
    public void setModelos(List<ModeloEntity> modelos) {
        this.modelos = modelos;
    }
    
    @PodamExclude                                 
    @ManyToMany
    private List<PuntoVentaEntity> puntosVenta = new ArrayList<>();
    
    
    @PodamExclude
    @OneToMany(mappedBy = "marca", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ModeloEntity> modelos = new ArrayList<>();
    
       
    
   
    /**
     * ruta de la imagen de la marca 
     */
    private String imagen_marca;
    
    
    /**
     * nombre de la marca
     */
    private String nombreMarca;
    
    

    /**
     * @return the nombreMarca
     */
    public String getNombreMarca() {
        return nombreMarca;
    }

    /**
     * @param nombreMarca the nombreMarca to set
     */
    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    /**
     * @return the imagen_marca
     */
    public String getImagen_marca() {
        return imagen_marca;
    }

    /**
     * @param imagen_marca the imagen_marca to set
     */
    public void setImagen_marca(String imagen_marca) {
        this.imagen_marca = imagen_marca;
    }
    
    
    
}
