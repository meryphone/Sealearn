package excepciones;

public class ExcepcionCursoNoEncontrado extends Exception {
	private static final long serialVersionUID = 1L;

	public ExcepcionCursoNoEncontrado(String mensaje) {
		super(mensaje);
	}
}
