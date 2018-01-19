package entity;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private List<String> listaJugadores;

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<String> getListaJugadores() {
		return listaJugadores;
	}
	public void setListaJugadores(List<String> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}
	
	

}
