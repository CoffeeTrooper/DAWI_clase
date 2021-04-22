package org.ciberfarma.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.ciberfarma.modelo.Usuario;

public class JPATest01 {

	public static void main(String[] args) {
		
		// Crear un objeto de Usuario a grabar
		Usuario u = new Usuario();
		//u.setCodigo(10); => El codigo se autogenera en la BD
		u.setNombre("Eren");
		u.setApellido("Lopez");
		u.setUsuario("usuario4@ciber.com");
		u.setClave("123");
		u.setFnacim("2000/10/15");
		u.setTipo(1);
		u.setEstado(1);
		
		// grabar el objeto
		// 1. fabricar el acceso a los datos
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("dawi_sesion04");
		// 2. crear el manejador de entidades
		EntityManager em = fabrica.createEntityManager();
		
		// 3. empezar mi transacción
		em.getTransaction().begin();
		// proceso a realizar (persistencia)
		em.persist(u);
		//em.merge(u); => Merge es para actualización, Persist para ingreso de nuevos datos
		// 4. confirmar la transacción
		em.getTransaction().commit();
		System.out.println("Registro Agregado");
		em.close();
	}

}
