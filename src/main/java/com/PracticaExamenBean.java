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
	private String nombreEdit;
	private String equipoNombre;
	private String nombreEquipo;
	private String listaJugadores;
	
	private Player playerTemp = new Player();
	
	
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

	public String getNombreEdit() {
		return nombreEdit;
	}

	public void setNombreEdit(String nombreEdit) {
		this.nombreEdit = nombreEdit;
	}
	
	
	


	public String crearJugador() {
		int edit = 1;
		PlayerFacade playerFacade = new PlayerFacade();
		Player pl = new Player();
		
		try {
			pl = playerFacade.encontrarPlayerPorNombre(nombre).get(0);
		}catch (Exception e) {
			//Entidad no creada
			edit = 0;
		}
		
		pl.setNombre(nombre);
		pl.setNombreEquipo("");

		if(edit == 1) {
			playerFacade.editarPlayer(nombre, pl);
		}else {
			playerFacade.crearPlayer(pl);
		}

		nombre = "";
		return "index.xhtml";
	}

	public String editarJugador() {
		PlayerFacade playerFacade = new PlayerFacade();
		
		playerFacade.editarPlayer(nombreEdit, playerTemp);

		nombreEdit = "";
		return "mostrarTodosLosJugadores.xhtml";
	}
	
	public String eliminarJugador(Player pl) {
		PlayerFacade playerFacade = new PlayerFacade();
		
		playerFacade.eliminarPlayer(pl);
		
		
		return "mostrarTodosLosJugadores.xhtml";
	}
	
	
	
	
	public int asignarEquipoAJugadores(String nombre, List<Player> listaPl) {
		PlayerFacade playerFacade = new PlayerFacade();
		Player pl = new Player();
		
		for(int i=0; i<listaPl.size(); i++) {
			pl.setNombre(listaPl.get(i).getNombre());
			pl.setNombreEquipo(nombre);
			
			playerFacade.editarPlayer(pl.getNombre(), pl);
		}
		
		return 1;
	}
	
	public String crearEquipo() {
		int edit = 1;
		TeamFacade teamFacade = new TeamFacade();
		PlayerFacade playerFacade = new PlayerFacade();
		List<Player> jugadores = new ArrayList<>();
		
		Team tm = new Team();
		Team tmTemp = new Team();
		
		
		try {
			tm = teamFacade.encontrarTeamPorNombre(nombreEquipo).get(0);
		}catch (Exception e) {
			//Entidad no creada
			edit = 0;
		}
		

		tmTemp.setNombre(nombreEquipo);
		
		if(!listaJugadores.isEmpty()) { //Si no está vacía
			String[] jug = listaJugadores.toLowerCase().split(",");
			List<String> listaPl = new ArrayList<>();			
			
			for(int i=0; i<jug.length; i++) {
				try {
					
					Player pl = playerFacade.encontrarPlayerPorNombre(jug[i].trim()).get(0);
					listaPl.add(pl.getNombre());
					
					jugadores.add(pl);
				}catch (Exception e) {
					System.out.println("Jugador no Encontrado");
				}
			}
			
			tmTemp.setListaJugadores(listaPl);
		}
		
		if(edit == 1) {
			teamFacade.editarTeam(tm.getNombre(), tmTemp);
		}else {
			teamFacade.crearTeam(tmTemp);
		}

		asignarEquipoAJugadores(tmTemp.getNombre(), jugadores);
		
		nombreEquipo = "";
		listaJugadores = "";
		return "index.xhtml";
	}

 
	public List<Player> mostrarTodosLosJugadores(){
		PlayerFacade playerFacade = new PlayerFacade();
		List<Player> listaPl = playerFacade.encontrarTodosLosPlayer();
		
		return listaPl;
	}
	
	public String irMostrarTodosLosJugadores() {
		return "mostrarTodosLosJugadores.xhtml";
	}
	
	public String irPortada() {
		return "index.xhtml";
	}
	
	public String irEditarNombreJugador(Player pl) {
		playerTemp = pl;
		
		return "editarNombrePlayer";
	}
	
}