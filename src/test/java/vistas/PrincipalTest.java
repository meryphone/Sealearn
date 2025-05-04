import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import controlador.Controlador;
import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Dificultad;
import dominio.Pregunta;
import excepciones.CursoSinPreguntasCiertaDificultad;
import excepciones.ExcepcionCursoActualVacio;
import excepciones.ExcepcionCursoDuplicado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Mensajes;
import vistas.Principal;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class PrincipalTest {

    private Principal principal;
    private Controlador controladorMock;

    @BeforeEach
    public void setUp() {
    	controladorMock = mock(Controlador.class);
    	Curso curso = mock(Curso.class);
        CursoEnProgreso cursoEnProgreso = mock(CursoEnProgreso.class);
        principal = new Principal(controladorMock, );
    }

    @Test
    void testIniciarCurso_Restablecer() throws Exception {
        Curso curso = mock(Curso.class);
        CursoEnProgreso cursoEnProgreso = mock(CursoEnProgreso.class);

        when(controladorMock.reanudarCurso(curso)).thenReturn(cursoEnProgreso);
        ReanudarCursoView dialog = mock(ReanudarCursoView.class);
        when(dialog.getOpcionSeleccionada()).thenReturn(ReanudarCursoView.Opcion.RESTABLECER);
        when(controladorMock.restablecerCurso()).thenReturn(cursoEnProgreso);

        // Simular que dialogo se muestra (tendrías que inyectarlo o hacer override si lo deseas testear más limpio)
        principal.iniciarCurso(curso);

        verify(controladorMock).restablecerCurso();
    }

    @Test
    void testIniciarCurso_NuevoCurso() throws Exception {
        Curso curso = mock(Curso.class);
        CursoEnProgreso cursoEnProgreso = mock(CursoEnProgreso.class);
        when(controladorMock.reanudarCurso(curso)).thenReturn(null);
        when(controladorMock.iniciarCurso(eq(curso), anyString(), anyString())).thenReturn(cursoEnProgreso);

        // Inyectar parámetros simulados (deberías adaptar para mockear Configuracion.mostrarDialogo)
        // Este test asumiría un método sobrescribible para parametrización

        // principal.iniciarCurso(curso);
        // assertNotNull(principal.getCursoActual()); // si añades getter
    }

    @Test
    void testEliminarCurso_Confirmado() {
        Curso curso = mock(Curso.class);
        principal.model.addElement(curso);

        mockStatic(Mensajes.class);
        when(Mensajes.mostrarSIoNO(any(), any())).thenReturn(JOptionPane.YES_OPTION);

        principal.eliminarCurso(curso);
        verify(controladorMock).eliminarCurso(curso.getId());
        assertFalse(principal.model.contains(curso));
    }

    @Test
    void testExportarEstadisticas_RutaValida() throws IOException {
        // Este test requiere que exportarEstadisticas() permita inyección o sobreescritura del JFileChooser.
        // Lo ideal sería extraer la lógica en un método testeable directamente con una ruta simulada.
    }

    @Test
    void testRealizarCurso_CursoCompletado() {
        CursoEnProgreso curso = mock(CursoEnProgreso.class);
        when(curso.getPreguntaActual()).thenReturn(null);
        when(controladorMock.finalizarSesionCurso()).thenReturn(null);

        principal.cursoActual = curso;
        principal.realizarCurso();

        verify(controladorMock).finalizarSesionCurso();
    }
}
