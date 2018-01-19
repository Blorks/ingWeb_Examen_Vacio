package com;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entity.Player;
import entity.Team;
import facade.PlayerFacade;
import facade.TeamFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
@ManagedBean
@SessionScoped
public class PracticaExamenBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String equipoNombre;
	private String nombreEquipo;
	private String listaJugadores;
	
	
	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(String listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEquipoNombre() {
		return equipoNombre;
	}

	public void setEquipoNombre(String equipoId) {
		this.equipoNombre = equipoId;
	}



	public String crearJugador() {
		PlayerFacade playerFacade = new PlayerFacade();
		TeamFacade teamFacade = new TeamFacade();
		Player pl = new Player();
		
		pl.setNombre(nombre);
		
		Team tm = teamFacade.encontrarTeamPorNombre(equipoNombre).get(0);
		pl.setNombreEquipo(tm.getNombre());
		
		playerFacade.crearPlayer(pl);
		
		return "index.xhtml";
	}
	
	public String crearEquipo() {
		TeamFacade teamFacade = new TeamFacade();
		PlayerFacade playerFacade = new PlayerFacade();
		
		Team tm = new Team();
		tm.setNombre(nombreEquipo);
		
		if(!listaJugadores.isEmpty()) { //Si no está vacía
			String[] jug = listaJugadores.trim().toLowerCase().split(",");
			List<String> listaPl = new ArrayList<>();
			
			for(int i=0; i<jug.length; i++) {
				Player pl = playerFacade.encontrarPlayerPorNombre(jug[i]).get(0);
				
				listaPl.add(pl.getNombre());
			}
			
			tm.setListaJugadores(listaPl);
		}
		

		teamFacade.crearTeam(tm);
		
		return "index.xhtml";
	}

 
}