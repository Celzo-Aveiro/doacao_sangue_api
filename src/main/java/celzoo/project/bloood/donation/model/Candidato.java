package celzoo.project.bloood.donation.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String cpf;

    @Getter
    @Setter
    private String rg;

    @Getter
    @Setter
    @JsonProperty("data_nasc")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNasc;
    @Getter
    @Setter
    private String sexo;

    @Getter
    @Setter
    private String mae;

    @Getter
    @Setter
    private String pai;

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter

    private String cep;

    @Getter
    @Setter
    private String endereco;

    @Getter
    @Setter
    private int numero;

    @Getter
    @Setter
    private String bairro;
    @Getter
    @Setter

    private String cidade;
    @Getter
    @Setter
    private String estado;
    @Getter
    @Setter
    @JsonProperty("telefone_fixo")
    private String telefoneFixo;

    @Getter
    @Setter
    private String celular;

    @Getter
    @Setter
    private double altura;

    @Getter
    @Setter
    private double peso;

    @Getter
    @Setter
    @JsonProperty("tipo_sanguineo")
    private String tipoSanguineo;

}
