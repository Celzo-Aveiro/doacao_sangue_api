package celzoo.project.bloood.donation.service;

import celzoo.project.bloood.donation.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class CandidatoCleanUpService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @PreDestroy
    public void onDestroy() {
        System.out.println("Limpando registros do banco de dados antes de encerrar a aplicação...");
        candidatoRepository.deleteAll();
    }
}
