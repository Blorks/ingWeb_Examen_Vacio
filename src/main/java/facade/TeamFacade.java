package facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import entity.Team;

public class TeamFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private DatastoreService datastore;
	private Entity entidad;
	private Key key;
	private Transaction conexion;
	
	public TeamFacade(){}

	@SuppressWarnings("unchecked")
	private List<Team> crearEntidades(List<Entity> listaEntidades) {
		List<Team> lista = new ArrayList<>();
		
		for(Entity e: listaEntidades) {
			Team tm = new Team();
			
			Object val = e.getProperty("nombre");
			tm.setNombre(val.toString());
			
			val = e.getProperty("listaJugadores");
			List<String> listaJugadores = new ArrayList<>();
			if(val!=null) {
				listaJugadores = (List<String>) val;
			}else {
				listaJugadores.add("");
			}
			
			tm.setListaJugadores(listaJugadores);


			lista.add(tm);
		}
		
		return lista;
	}

	
	
	
	
	//Métodos Públicos
	public void crearTeam(Team tm) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		entidad = new Entity("Team");

		entidad.setProperty("nombre", tm.getNombre());
		entidad.setProperty("listaJugadores", tm.getListaJugadores());
		
		conexion = datastore.beginTransaction();
		
		datastore.put(conexion, entidad);
		conexion.commit();
	}
	
	public List<Team> encontrarTeamPorNombre(String nombre) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		List<Team> lista = new ArrayList<>();
		
		conexion = datastore.beginTransaction();
		
		Query q = new Query("Team").addSort("nombre", Query.SortDirection.ASCENDING);
		FilterPredicate filtro = new FilterPredicate("nombre", FilterOperator.EQUAL, nombre);
		q.setFilter(filtro);

		List<Entity> listaEntidades = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(20)); ;
		lista = crearEntidades(listaEntidades);
		
		return lista;
	}
}
