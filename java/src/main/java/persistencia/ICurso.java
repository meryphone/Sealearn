package persistencia;

import dominio.Curso;

import java.util.List;
import java.util.UUID;

public interface ICurso {
    void guardar(Curso curso);
    void eliminar(Curso curso);
    Curso buscarPorId(UUID id);
    List<Curso> buscarTodos();
}
