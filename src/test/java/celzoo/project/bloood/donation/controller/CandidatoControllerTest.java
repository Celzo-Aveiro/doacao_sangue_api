package celzoo.project.bloood.donation.controller;

import celzoo.project.bloood.donation.model.Candidato;
import celzoo.project.bloood.donation.service.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CandidatoControllerTest {

    @InjectMocks
    private CandidatoController candidatoController;

    @Mock
    private CandidatoService candidatoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportarCandidatos() {
        // Arrange
        List<Candidato> candidatos = new ArrayList<>();
        doNothing().when(candidatoService).importarCandidatos(candidatos);

        // Act
        ResponseEntity<Void> response = candidatoController.importarCandidatos(candidatos);

        // Assert
        assertEquals(ResponseEntity.ok().build(), response);
        verify(candidatoService, times(1)).importarCandidatos(candidatos);
    }

    @Test
    void testContarCandidatosPorEstado() {
        // Arrange
        Map<String, Long> expectedResult = new HashMap<>();
        expectedResult.put("SP", 100L);
        when(candidatoService.contarCandidatosPorEstado()).thenReturn(expectedResult);

        // Act
        Map<String, Long> result = candidatoController.contarCandidatosPorEstado();

        // Assert
        assertEquals(expectedResult, result);
        verify(candidatoService, times(1)).contarCandidatosPorEstado();
    }

    @Test
    void testRecuperaImcMedio() {
        // Arrange
        Map<Integer, Double> expectedResult = new HashMap<>();
        expectedResult.put(20, 22.5);
        when(candidatoService.calcularIMCMedioPorFaixaDeIdade()).thenReturn(expectedResult);

        // Act
        Map<Integer, Double> result = candidatoController.recuperaImcMedio();

        // Assert
        assertEquals(expectedResult, result);
        verify(candidatoService, times(1)).calcularIMCMedioPorFaixaDeIdade();
    }

    @Test
    void testRecuperaObesidadePorSexo() {
        // Arrange
        Map<String, Double> expectedResult = new HashMap<>();
        expectedResult.put("M", 15.5);
        expectedResult.put("F", 20.3);
        when(candidatoService.calcularPercentualObesosPorSexo()).thenReturn(expectedResult);

        // Act
        Map<String, Double> result = candidatoController.recuperaObesidadePorSexo();

        // Assert
        assertEquals(expectedResult, result);
        verify(candidatoService, times(1)).calcularPercentualObesosPorSexo();
    }

    @Test
    void testRecuperaMediaDeIdadePorTipoSanguineo() {
        // Arrange
        Map<String, Double> expectedResult = new HashMap<>();
        expectedResult.put("O+", 35.5);
        when(candidatoService.calcularMediaIdadePorTipoSanguineo()).thenReturn(expectedResult);

        // Act
        Map<String, Double> result = candidatoController.recuperaMediaDeIdadePorTipoSanguineo();

        // Assert
        assertEquals(expectedResult, result);
        verify(candidatoService, times(1)).calcularMediaIdadePorTipoSanguineo();
    }

    @Test
    void testRecuperaDoadoresPorTipoSanguineo() {
        // Arrange
        Map<String, Long> expectedResult = new HashMap<>();
        expectedResult.put("A+", 50L);
        when(candidatoService.contarDoadoresPorTipoSanguineo()).thenReturn(expectedResult);

        // Act
        Map<String, Long> result = candidatoController.recuperaDoadoresPorTipoSanguineo();

        // Assert
        assertEquals(expectedResult, result);
        verify(candidatoService, times(1)).contarDoadoresPorTipoSanguineo();
    }
}