/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carros.resources;

import co.edu.uniandes.csw.carros.dtos.EmpleadoDTO;
import co.edu.uniandes.csw.carros.dtos.EmpleadoDetailDTO;
import co.edu.uniandes.csw.carros.dtos.MarcaDTO;
import co.edu.uniandes.csw.carros.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.carros.ejb.PuntoVentaEmpleadoLogic;
import co.edu.uniandes.csw.carros.entities.EmpleadoEntity;
import co.edu.uniandes.csw.carros.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Daniel Lozano
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PuntoVentaEmpleadoResource 
{
    private static final Logger LOGGER = Logger.getLogger(PuntoVentaEmpleadoResource.class.getName());    
 
    @Inject
    private PuntoVentaEmpleadoLogic puntoVentaEmpleadoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EmpleadoLogic empleadoLogic;
    
    @POST
    @Path("{puntoVentaID: \\d+}")
    public EmpleadoDTO addEmpleado(@PathParam("puntoVentaID") Long puntoVentaId, @PathParam("empleadoID") Long empleadoId) {
        LOGGER.log(Level.INFO, "PuntoVentaEmpleadoResource addEmpleado: input: puntoVentaID: {0} , empleadoID: {1}", new Object[]{puntoVentaId, empleadoId});
        if (empleadoLogic.getEmpleado(empleadoId) == null) {
            throw new WebApplicationException("El recurso /empleado/" + empleadoId + " no existe.", 404);
        }
        EmpleadoDTO empleadoDTO = new EmpleadoDTO(puntoVentaEmpleadoLogic.addEmpleado(empleadoId, puntoVentaId));
        LOGGER.log(Level.INFO, "PuntoVentaMarcasResource addEmpleado: output: {0}", empleadoDTO);
        return empleadoDTO;
    }
    
       @GET
    public List<EmpleadoDetailDTO> getEmpleados(@PathParam("puntoVentaID") Long puntoVentaId) {
        LOGGER.log(Level.INFO, "PuntoVentaEmpleadoResource getEmpleados: input: {0}", puntoVentaId);
        List<EmpleadoDetailDTO> listaDetailDTOs = empleadoListEntity2DTO(puntoVentaEmpleadoLogic.getEmpleado(puntoVentaId));
        LOGGER.log(Level.INFO, "PuntoVentaEmpleadoResource getEmpleados: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la editorial con id asociado.
     *
     * @param editorialsId Identificador de la editorial que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param booksId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * editorial.
     */
    @GET
      @Path("{puntoVentaID: \\d+}")
    public EmpleadoDetailDTO getEmpleado(@PathParam("puntoVentaID") Long puntoVentaId, @PathParam("empleadoID") Long empleadoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PuntoVentaEmpleadoResource getEmpleado: input: editorialsID: {0} , booksId: {1}", new Object[]{puntoVentaId, empleadoId});
        if (empleadoLogic.getEmpleado(empleadoId) == null) {
            throw new WebApplicationException("El recurso /PuntoVenta/" + puntoVentaId + "/empleado/" + empleadoId + " no existe.", 404);
        }
        EmpleadoDetailDTO empleadoDetailDTO = new EmpleadoDetailDTO(puntoVentaEmpleadoLogic.getEmpleado(puntoVentaId, empleadoId));
        LOGGER.log(Level.INFO, "PuntoVentaEmpleadoResource getEmpleado:  output: {0}", empleadoDetailDTO);
        return empleadoDetailDTO;
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Editorial
     *
     * @param editorialsId Identificador de la editorial que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param books JSONArray {@link BookDTO} El arreglo de libros nuevo para la
     * editorial.
     * @return JSON {@link BookDTO} - El arreglo de libros guardado en la
     * editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<EmpleadoDetailDTO> replaceEmpleado(@PathParam("puntoVentaID") Long puntoVentaId, List<EmpleadoDetailDTO> empleados) {
        LOGGER.log(Level.INFO, "PuntoVentaEmpleadoResource replaceEmpleados: input: editorialsId: {0} , marcas: {1}", new Object[]{puntoVentaId, empleados});
        for (EmpleadoDetailDTO empleado : empleados) {
            if (empleadoLogic.getEmpleado(empleado.getEmpleadoID()) == null) 
                    {
                throw new WebApplicationException("El recurso /empleados/" + empleado.getEmpleadoID() + " no existe.", 404);
            }
        }
        List<EmpleadoDetailDTO> listaDetailDTOs = empleadoListEntity2DTO(puntoVentaEmpleadoLogic.replaceEmpleado(puntoVentaId, empleadoListDTO2Entity(empleados)));
        LOGGER.log(Level.INFO, "PuntoVentaMEmpleadoResource replaceEmpleado: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de BookEntity a una lista de BookDetailDTO.
     *
     * @param entityList Lista de BookEntity a convertir.
     * @return Lista de BookDTO convertida.
     */
    private List<EmpleadoDetailDTO> empleadoListEntity2DTO(List<EmpleadoEntity> entityList) {
        List<EmpleadoDetailDTO> list = new ArrayList();
        for (EmpleadoEntity entity : entityList) {
            list.add(new EmpleadoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BookDetailDTO a una lista de BookEntity.
     *
     * @param dtos Lista de BookDetailDTO a convertir.
     * @return Lista de BookEntity convertida.
     */
    private List<EmpleadoEntity> empleadoListDTO2Entity(List<EmpleadoDetailDTO> dtos) {
        List<EmpleadoEntity> list = new ArrayList<>();
        for (EmpleadoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}
