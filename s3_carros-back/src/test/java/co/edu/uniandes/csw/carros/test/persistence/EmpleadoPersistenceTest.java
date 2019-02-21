/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.carros.test.persistence;

import co.edu.uniandes.csw.carros.entities.EmpleadoEntity;
import co.edu.uniandes.csw.carros.persistence.EmpleadoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan Pablo Patarroyo Duque
 */
@RunWith(Arquillian.class)
public class EmpleadoPersistenceTest {
    
    @Inject
    private EmpleadoPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
   
    private List<EmpleadoEntity> data = new ArrayList<EmpleadoEntity>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpleadoEntity.class.getPackage())
                .addPackage(EmpleadoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest(){
        try{
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            } catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData(){
        em.createQuery("delete from EmpleadoEntity").executeUpdate();
    }
    
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for(int i=0;i<3; i++){
            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    @Test
    public void createEmpleadoTest(){
        
        PodamFactory factory = new PodamFactoryImpl();
        EmpleadoEntity newEntity = factory.manufacturePojo(EmpleadoEntity.class);
        
        EmpleadoEntity ee = ep.create(newEntity);
        Assert.assertNotNull(ee);
        
        EmpleadoEntity entity = em.find(EmpleadoEntity.class, newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
    @Test
    public void findEmpleadoTest(){
        
        EmpleadoEntity entity = data.get(0);
        EmpleadoEntity search = ep.findEmpleado(entity.getId());
        
        Assert.assertNotNull(search);
        Assert.assertEquals(entity.getNombre(), search.getNombre());
    }     
    
    @Test
    public void findAllEmpleadosTest(){
      
        TypedQuery<EmpleadoEntity> query = em.createQuery("Select u from EmpleadoEntity u", EmpleadoEntity.class);
        Assert.assertEquals(query.getResultList(), ep.findAllEmpleados());
    }
    
    @Test
    public void deleteEmpleadoTest(){
        EmpleadoEntity entity = data.get(1);
        ep.deleteEmpleado(entity.getId());
        EmpleadoEntity search = em.find(EmpleadoEntity.class, entity.getId());
        Assert.assertNull(search);
    }
    
    @Test
    public void updateEmpleadoTest(){
        EmpleadoEntity entity = data.get(0);
        entity.setNombre("Carlos Moreno");
        ep.updateEmpleado(entity);
        EmpleadoEntity search = em.find(EmpleadoEntity.class, entity.getId());
        Assert.assertEquals(search.getNombre(),"Carlos Moreno" );
    }
    
}
