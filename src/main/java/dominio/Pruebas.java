package dominio;

import java.util.List;

import controlador.Controlador;

public class Pruebas {
	
	public static void main(String[] args) {
		
		Controlador controlador = Controlador.getInstance();
		System.out.println("Cargando archivo desde: " + Controlador.class.getResource("/cursos/CursoPython.yaml"));
		List<Curso> listaCursos;

		 Curso curso;
		
		try {
			
			listaCursos = CursoUtils.cargarTodosLosCursos();
			
			for(Curso cu : listaCursos) {
				System.out.print(cu.toString() + "\n");
			}
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

	    
		
	}

}
