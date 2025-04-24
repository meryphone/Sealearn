package persistencia;


import dominio.CursoEnProgreso;
import java.util.List;
import java.util.UUID;

public interface ICursoEnProgreso {
    void guardar(CursoEnProgreso entidad);
    void actualizar(CursoEnProgreso entidad);
    void eliminar(CursoEnProgreso entidad);
    CursoEnProgreso buscarPorId(Long id);
    List<CursoEnProgreso> buscarTodos();
    CursoEnProgreso buscarPorCursoId(UUID cursoId);
}

