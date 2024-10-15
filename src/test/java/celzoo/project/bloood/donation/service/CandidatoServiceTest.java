package celzoo.project.bloood.donation.service;

import celzoo.project.bloood.donation.model.Candidato;
import celzoo.project.bloood.donation.repository.CandidatoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CandidatoServiceTest {


    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    private List<Candidato> candidatos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        candidatos = new ArrayList<>();

        // Criando dados simulados para os testes
        Candidato candidato1 = new Candidato();
        candidato1.setNome("João");
        candidato1.setSexo("Masculino");
        candidato1.setTipoSanguineo("O+");
        candidato1.setEstado("SP");
        candidato1.setDataNasc(LocalDate.of(1990, 1, 1));
        candidato1.setPeso(80.0);
        candidato1.setAltura(1.75);

        Candidato candidato2 = new Candidato();
        candidato2.setNome("Maria");
        candidato2.setSexo("Feminino");
        candidato2.setTipoSanguineo("A+");
        candidato2.setEstado("SP");
        candidato2.setDataNasc(LocalDate.of(1995, 2, 15));
        candidato2.setPeso(60.0);
        candidato2.setAltura(1.65);

        Candidato candidato3 = new Candidato();
        candidato3.setNome("Pedro");
        candidato3.setSexo("Masculino");
        candidato3.setTipoSanguineo("B+");
        candidato3.setEstado("RJ");
        candidato3.setDataNasc(LocalDate.of(1985, 6, 10));
        candidato3.setPeso(90.0);
        candidato3.setAltura(1.80);

        candidatos.add(candidato1);
        candidatos.add(candidato2);
        candidatos.add(candidato3);
    }

    @AfterEach
    void tearDown() {
        candidatos.clear();
    }

    @Test
    void testImportarCandidatos() {
        candidatoService.importarCandidatos(candidatos);
        verify(candidatoRepository, times(1)).saveAll(candidatos);
    }

    @Test
    void testContarCandidatosPorEstado() {
        when(candidatoRepository.findAll()).thenReturn(candidatos);

        Map<String, Long> resultado = candidatoService.contarCandidatosPorEstado();
        assertEquals(2L, resultado.get("SP"));
        assertEquals(1L, resultado.get("RJ"));
    }

    @Test
    void testCalcularIMCMedioPorFaixaDeIdade() {
        when(candidatoRepository.findAll()).thenReturn(candidatos);

        Map<Integer, Double> resultado = candidatoService.calcularIMCMedioPorFaixaDeIdade();
        assertEquals(26.95, resultado.get(30)); // 30 é a faixa etária (20-30 anos)
    }

    @Test
    void testCalcularPercentualObesosPorSexo() {
        Candidato candidatoObesoHomem = new Candidato();
        candidatoObesoHomem.setNome("Carlos");
        candidatoObesoHomem.setSexo("Masculino");
        candidatoObesoHomem.setTipoSanguineo("AB+");
        candidatoObesoHomem.setDataNasc(LocalDate.of(1992, 5, 10));
        candidatoObesoHomem.setPeso(110.0); // IMC > 30
        candidatoObesoHomem.setAltura(1.75);

        candidatos.add(candidatoObesoHomem);
        when(candidatoRepository.findAll()).thenReturn(candidatos);

        Map<String, Double> resultado = candidatoService.calcularPercentualObesosPorSexo();
        assertEquals(33.33, resultado.get("Homens"));
        assertEquals(0.0, resultado.get("Mulheres"));
    }

    @Test
    void testCalcularMediaIdadePorTipoSanguineo() {
        when(candidatoRepository.findAll()).thenReturn(candidatos);

        Map<String, Double> resultado = candidatoService.calcularMediaIdadePorTipoSanguineo();
        assertEquals(29.0, resultado.get("A+"));
        assertEquals(39.0, resultado.get("B+"));
    }

    @Test
    void testContarDoadoresPorTipoSanguineo() {
        when(candidatoRepository.findAll()).thenReturn(candidatos);

        Map<String, Long> resultado = candidatoService.contarDoadoresPorTipoSanguineo();
        assertEquals(2L, resultado.get("A+")); // A+ recebe de A+ e O+
        assertEquals(1L, resultado.get("O+")); // O+ só recebe de O+ e O-
    }
}