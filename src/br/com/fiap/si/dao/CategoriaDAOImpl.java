package br.com.fiap.si.dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.fiap.si.modelo.Categoria;
import br.com.fiap.si.util.JPAUtil;

public class CategoriaDAOImpl implements CategoriaDAO {

	@Override
	public boolean saveCategoria(Categoria categoria) {
		EntityManager em = new JPAUtil().getEntityManager();
		boolean valid;
		try {
			em.getTransaction().begin();

			em.persist(categoria);

			em.getTransaction().commit();
			valid = true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("A categoria j� esta cadastrada"));
			valid = false;
		}
		return valid;
	}

	@Override
	public void updateCategoria(Categoria categoria) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
			
			em.merge(categoria);

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	@Override
	public void deleteCategoria(Categoria categoria) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();

			Categoria attached = em.merge(categoria);
			em.remove(attached);

			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	@Override
	public List<Categoria> selectCategoria() {
		List<Categoria> lista;
		EntityManager em = new JPAUtil().getEntityManager();
		lista = em.createQuery("select c from Categoria c").getResultList();
		return lista;

	}

	@Override
	public Categoria getCategoriaID(Long id) {
		EntityManager em = new JPAUtil().getEntityManager();
		Query q = em.createQuery("select c from Categoria c where id = :id");
		q.setParameter("id", id);
		Categoria categoria = (Categoria) q.getSingleResult();

		return categoria;
	}

}
