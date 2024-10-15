package celzoo.project.bloood.donation.service;


import celzoo.project.bloood.donation.model.Candidato;
import celzoo.project.bloood.donation.repository.CandidatoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class CandidatoLoader implements CommandLineRunner {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        TypeReference<List<Candidato>> typeReference = new TypeReference<List<Candidato>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");

        try {
            List<Candidato> pessoas = mapper.readValue(inputStream, typeReference);
            candidatoRepository.saveAll(pessoas);
            System.out.println("Candidatos salvos no banco de dados!");
        } catch (IOException e) {
            System.out.println("Não foi possível salvar os Candidatos: " + e.getMessage());
        }
    }
}
