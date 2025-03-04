package dominio;

public class Pruebas {
	
	public static void main(String[] args) {
		
		System.out.println(Pruebas.class.getResource("/cursos/curso_basico_poo.yaml"));
		
		 Curso curso;
		try {
			curso = CursoUtils.importarCursoDesdeYaml(Pruebas.class.getResource("/cursos/curso_basico_poo.yaml").getPath());
			// Mostrar el curso importado
	        if (curso != null) {
	            System.out.println("Curso importado:");
	            System.out.println(curso);
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}	    
		
	}

}
