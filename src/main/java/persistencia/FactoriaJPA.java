package persistencia;

public class FactoriaJPA extends Factoria {
	
	public FactoriaJPA() {
	}

	@Override
	public ICursoEnProgreso crearAdaptadorCursoEnProgreso() {
		return AdaptadorCursoEnProgresoJPA.getIntance();
	}

	@Override
	public IEstadistica crearAdaptadorEstadistica() {
		return AdaptadorEstadisticaJPA.getIntance();
	}
	
}
