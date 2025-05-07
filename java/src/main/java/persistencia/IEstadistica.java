package persistencia;

import java.util.List;
import dominio.Estadistica;

public interface IEstadistica {
	void guardar(Estadistica entidad);
	void actualizar(Estadistica entidad);
	void eliminar(Estadistica entidad);
	Estadistica buscarPorId(Long id);
	List<Estadistica> buscarTodos();
}
