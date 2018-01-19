package facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import entity.Player;

public class PlayerFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private DatastoreService datastore;
	private Entity entidad;
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

		entidad.setProperty("nombre", pl.getNombre().toLowerCase());
		entidad.setProperty("nombreEquipo", pl.getNombreEquipo().toLowerCase());
		
		conexion = datastore.beginTransaction();
		
		datastore.put(conexion, entidad);
		conexion.commit();
	}
	
	public void editarPlayer(String nombre, Player plTemp) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		entidad = new Entity("Player");
		String nombreTemp = plTemp.getNombre();
		
		conexion = datastore.beginTransaction();
		
		Query q = new Query("Player").addSort("nombre", Query.SortDirection.ASCENDING);
		FilterPredicate filtro = new FilterPredicate("nombre", FilterOperator.EQUAL, nombreTemp);
		q.setFilter(filtro);
		
		List<Entity> listaEntidades = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		
		entidad = listaEntidades.get(0);
		
		entidad.setProperty("nombre", nombre);
		entidad.setProperty("nombreEquipo", plTemp.getNombreEquipo());

		datastore.put(conexion, entidad);
		conexion.commit();
	}
	
	public void eliminarPlayer(Player plTemp) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		entidad = new Entity("Player");
		String nombreTemp = plTemp.getNombre();
		
		conexion = datastore.beginTransaction();
		
		Query q = new Query("Player").addSort("nombre", Query.SortDirection.ASCENDING);
		FilterPredicate filtro = new FilterPredicate("nombre", FilterOperator.EQUAL, nombreTemp);
		q.setFilter(filtro);
		
		List<Entity> listaEntidades = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		
		entidad = listaEntidades.get(0);

		datastore.delete(entidad.getKey());
		conexion.commit();
	}
	
	public List<Player> encontrarPlayerPorNombre(String nombre) {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		List<Player> lista = new ArrayList<>();
		
		conexion = datastore.beginTransaction();
		
		Query q = new Query("Player").addSort("nombre", Query.SortDirection.ASCENDING);
		FilterPredicate filtro = new FilterPredicate("nombre", FilterOperator.EQUAL, nombre);
		q.setFilter(filtro);

		List<Entity> listaEntidades = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		lista = crearEntidades(listaEntidades);
		
		conexion.commit();
		
		return lista;
	}
	
	public List<Player> encontrarTodosLosPlayer() {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
		List<Player> lista = new ArrayList<>();
		
		conexion = datastore.beginTransaction();
		
		Query q = new Query("Player").addSort("nombre", Query.SortDirection.ASCENDING);

		List<Entity> listaEntidades = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(20));
		lista = crearEntidades(listaEntidades);
		
		conexion.commit();
		
		return lista;
	}
	
	

}
