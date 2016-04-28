package car.tp4;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class Bibliotheque {

	@PersistenceContext
	private EntityManager em;

	public void initBiblio() {
		init();
		Book tmp = new Book("Victor Hugo", "Les miserables", "1862");
		em.persist(tmp);
		tmp = new Book("Charles Baudelaire", "Les fleurs du mal", "1867");
		em.persist(tmp);
		tmp = new Book("Lionel Seinturier", "Apprendre à faire un cours", "2016");
		em.persist(tmp);

	}

	public List<String> retourneAuteurs() {
		// em =
		// Persistence.createEntityManagerFactory("livre-pu").createEntityManager();
		Query q = em.createQuery("SELECT b FROM Book b");
		List<Book> listLivres = (List<Book>) q.getResultList();
		List<String> listAuteurs = new ArrayList<String>();
		for (Book l : listLivres) {
			listAuteurs.add(l.getAuteur());
		}
		return listAuteurs;
	}

	public void init() {
		Query q = em.createQuery("DELETE FROM Book");
		q.executeUpdate();
		em.flush();
		em.clear();
	}

}
