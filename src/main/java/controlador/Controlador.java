package controlador;

import java.util.List;
import dominio.*;

public class Controlador {

	private static Controlador controlador;
	private List<Curso> listaCursos;

	private Controlador() {
		// Aquí cargar cursos predeterminados
		// PROVISIONAL
		
		listaCursos = CursoUtils.cargarTodosLosCursos(); // ESTO SOLO SE HACE SI LA BASE DE DATOS ESTÁ VACIA
		
	}

	public static synchronized Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}


	public List<Curso> getListaCursos() {
		return listaCursos;
	}	
	
}
