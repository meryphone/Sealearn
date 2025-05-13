package excepciones;

public class ExcepcionCursoDuplicado extends Exception {
	private static final long serialVersionUID = 1L;

	public ExcepcionCursoDuplicado(String mensaje) {
		super(mensaje);
	}
}
