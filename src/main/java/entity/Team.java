package entity;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nombre;
	private List<Player> listaJugadores;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Player> getListaJugadores() {
		return listaJugadores;
	}
	public void setListaJugadores(List<Player> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}
	
	

}
