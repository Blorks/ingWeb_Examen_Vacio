package facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import entity.Player;

public class PlayerFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private DatastoreService datastore;
	private Entity entidad;
	private Key key;
	private Transaction conexion;
	
	public PlayerFacade(){}

	private List<Player> crearEntidades(List<Entity> listaEntidades) {
		List<Player> lista = new ArrayList<>();
		
		for(Entity e: listaEntidades) {
			Player pl = new Player();
			
			Object val = e.getProperty("nombre");
			pl.setNombre(val.toString());
			
			val = e.getProperty("nombreEquipo");
			pl.setNombreEquipo(val.toString());


			lista.add(pl);
		}
		
		return lista;
	}

	
	
	
	
	//Métodos Públicos
	public void crearPlayer(Player pl) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		entidad = new Entity("Player");

		entidad.setProperty("nombre", pl.getNombre());
		entidad.setProperty("nombreEquipo", pl.getNombreEquipo());
		
		conexion = datastore.beginTransaction();
		
		datastore.put(conexion, entidad);
		conexion.commit();
	}
	
	public List<Player> encontrarPlayerPorNombre(String nombre) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		List<Player> lista = new ArrayList<>();
		
		conexion = datastore.beginTransaction();
		
		Query q = new Query("Player").addSort("nombre", Query.SortDirection.ASCENDING);
		FilterPredicate filtro = new FilterPredicate("nombre", FilterOperator.EQUAL, nombre);
		q.setFilter(filtro);

		List<Entity> listaEntidades = datastore.prepare(q).asList(null);
		lista = crearEntidades(listaEntidades);
		
		return lista;
	}
	
	

}
