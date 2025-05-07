package persistencia;


public abstract class Factoria {

	private static Factoria factoria;
	public static final String JPA = "persistencia.FactoriaJPA";
	

	public static Factoria getInstance(String tipoFactoria) {
		if (factoria == null) {
			try {
				factoria = (Factoria) Class.forName(tipoFactoria).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

		}

		return factoria;
	}
	
	public static Factoria getInstance() {
		if (factoria == null) return getInstance (Factoria.JPA);
				else return factoria;
	}
	
	protected Factoria (){}
	
	public abstract ICursoEnProgreso crearAdaptadorCursoEnProgreso();
	public abstract IEstadistica crearAdaptadorEstadistica();


}
