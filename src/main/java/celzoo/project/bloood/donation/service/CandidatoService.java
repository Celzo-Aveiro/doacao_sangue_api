package celzoo.project.bloood.donation.service;

import celzoo.project.bloood.donation.model.Candidato;
import celzoo.project.bloood.donation.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    private static final Map<String, List<String>> compatibilidade = new HashMap<>();

    static {
        compatibilidade.put("A+", List.of("A+", "A-", "O+", "O-"));
        compatibilidade.put("A-", List.of("A-", "O-"));
        compatibilidade.put("B+", List.of("B+", "B-", "O+", "O-"));
        compatibilidade.put("B-", List.of("B-", "O-"));
        compatibilidade.put("AB+", List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"));
        compatibilidade.put("AB-", List.of("A-", "B-", "AB-", "O-"));
        compatibilidade.put("O+", List.of("O+", "O-"));
        compatibilidade.put("O-", List.of("O-"));
    }

    public void importarCandidatos(List<Candidato> candidatos) {
            candidatoRepository.saveAll(candidatos);
    }

    public Map<String, Long> contarCandidatosPorEstado() {
        return candidatoRepository.findAll().stream()
                .collect(Collectors.groupingBy(Candidato::getEstado, Collectors.counting()));
    }

    public Map<Integer, Double> calcularIMCMedioPorFaixaDeIdade() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        return candidatos.stream()
                .collect(Collectors.groupingBy(candidato -> {
                    int idade = LocalDate.now().getYear() - candidato.getDataNasc().getYear();
                    return idade / 10 * 10;
                }, Collectors.mapping(candidato -> candidato.getPeso() / Math.pow(candidato.getAltura(), 2),
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(Double::doubleValue),
                                avg -> BigDecimal.valueOf(avg)
                                        .setScale(2, RoundingMode.HALF_UP)
                                        .doubleValue()
                        )
                )));
    }

    public Map<String, Double> calcularPercentualObesosPorSexo() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        // Filtrar candidatos obesos (IMC > 30)
        Predicate<Candidato> obeso = candidato -> {
            double imc = candidato.getPeso() / Math.pow(candidato.getAltura(), 2);
            return imc > 30;
        };

        // Contagem total de homens e mulheres
        long totalHomens = candidatos.stream().filter(candidato -> candidato.getSexo().equalsIgnoreCase("Masculino")).count();
        long totalMulheres = candidatos.size() - totalHomens;

        // Contagem de homens e mulheres obesos
        long obesosHomens = candidatos.stream().filter(candidato -> candidato.getSexo().equalsIgnoreCase("Masculino")).filter(obeso).count();
        long obesasMulheres = candidatos.stream().filter(candidato -> candidato.getSexo().equalsIgnoreCase("Feminino")).filter(obeso).count();

        // Cálculo do percentual de obesos entre homens e mulheres
        double percentualObesosHomens = (double) obesosHomens / totalHomens * 100;
        double percentualObesosMulheres = (double) obesasMulheres / totalMulheres * 100;

        // Arredondar para duas casas decimais
        percentualObesosHomens = Math.round(percentualObesosHomens * 100.0) / 100.0;
        percentualObesosMulheres = Math.round(percentualObesosMulheres * 100.0) / 100.0;

        // Criar mapa com os resultados
          Map<String, Double> percentualObesosPorSexo = Map.of(
                "Homens", percentualObesosHomens,
                "Mulheres", percentualObesosMulheres
        );

        return percentualObesosPorSexo;
    }

    public Map<String, Double> calcularMediaIdadePorTipoSanguineo() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        // Agrupar candidatos por tipo sanguíneo e calcular a média de idade

        return candidatos.stream()
                .collect(Collectors.groupingBy(Candidato::getTipoSanguineo,
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(candidato -> {
                                    int idade = LocalDate.now().getYear() - candidato.getDataNasc().getYear();
                                    return (double) idade;
                                }),
                                avg -> Math.round(avg * 100.0) / 100.0
                        )));
    }

    public Map<String, Long> contarDoadoresPorTipoSanguineo() {
        List<Candidato> doadores = candidatoRepository.findAll();
        Map<String, Long> contador = new HashMap<>();

        for (String tipoReceptor : compatibilidade.keySet()) {
            long count = doadores.stream()
                    .filter(doador -> compatibilidade.get(tipoReceptor).contains(doador.getTipoSanguineo()))
                    .count();
            contador.put(tipoReceptor, count);
        }

        return contador;
    }
}
