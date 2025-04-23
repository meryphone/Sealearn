package persistencia;


import dominio.CursoEnProgreso;
import java.util.List;

public interface ICursoEnProgreso {
    void guardar(CursoEnProgreso entidad);
    void actualizar(CursoEnProgreso entidad);
    void eliminar(CursoEnProgreso entidad);
    CursoEnProgreso buscarPorId(Long id);
    List<CursoEnProgreso> buscarTodos();
}

