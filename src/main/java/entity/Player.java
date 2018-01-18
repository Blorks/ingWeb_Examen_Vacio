package entity;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nombre;
	private Integer equipoId;
	

	public Player(Integer id, String nombre, Integer equipoId) {
		this.id = id;
		this.nombre = nombre;
		this.equipoId = equipoId;
	}
	
	
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

	public Integer getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(Integer equipoId) {
		this.equipoId = equipoId;
	}
	
	
	
}
