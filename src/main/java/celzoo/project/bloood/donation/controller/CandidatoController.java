package celzoo.project.bloood.donation.controller;

import celzoo.project.bloood.donation.model.Candidato;
import celzoo.project.bloood.donation.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/candidatos")
@CrossOrigin(origins = "http://localhost:4200")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;


    @PostMapping("/importar")
    @PreAuthorize("hasAuthority('ROLE_USER')") // Exemplo: exige que o usuário tenha a ROLE_USER
    public ResponseEntity<Void> importarCandidatos(@RequestBody List<Candidato> candidatos) {
        candidatoService.importarCandidatos(candidatos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contarPorEstado")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, Long> contarCandidatosPorEstado() {
        return candidatoService.contarCandidatosPorEstado();
    }

    @GetMapping("/recuperarImcMedio")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<Integer, Double> recuperaImcMedio() {
        return candidatoService.calcularIMCMedioPorFaixaDeIdade();
    }

    @GetMapping("/recuperarPercentualDeObesosPorGenero")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, Double> recuperaObesidadePorSexo() {
        return candidatoService.calcularPercentualObesosPorSexo();
    }

    @GetMapping("/recuperarMediaDeIdadePorTipoSanguineo")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, Double> recuperaMediaDeIdadePorTipoSanguineo() {
        return candidatoService.calcularMediaIdadePorTipoSanguineo();
    }

    @GetMapping("/recuperarDoadoresPorTipoSanguineo")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, Long> recuperaDoadoresPorTipoSanguineo() {
        return candidatoService.contarDoadoresPorTipoSanguineo();
    }

}
