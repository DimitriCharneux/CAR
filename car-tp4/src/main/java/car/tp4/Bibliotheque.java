package car.tp4;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class Bibliotheque implements BibliothequeItf{
	
	@PersistenceContext(unitName="book-pu")
	private EntityManager em;
	
	@Override
	public void initBiblio() {
		//em = Persistence.createEntityManagerFactory("livre-pu").createEntityManager();
		Livre tmp = new Livre("Victor Hugo", "Les miserables", "1862");
		em.persist(tmp);
		tmp = new Livre("Charles Baudelaire", "Les fleurs du mal", "1867");
		em.persist(tmp);
		tmp = new Livre("Andrzej Sapkowski", "Le sorceleur : Tome 1", "1992");
		em.persist(tmp);
		
	}

	@Override
	public List<String> retourneAuteurs() {
		//em = Persistence.createEntityManagerFactory("livre-pu").createEntityManager();
		Query q = em.createQuery("SELECT * FROM Livre");
		List<Livre> listLivres = (List<Livre>)q.getResultList();
		List<String> listAuteurs = new ArrayList<String>();
		for(Livre l : listLivres){
			listAuteurs.add(l.getAuteur());
		}
		return listAuteurs;
	}

	@Override
	public String ok() {
		return "ok";
	}
	
}
