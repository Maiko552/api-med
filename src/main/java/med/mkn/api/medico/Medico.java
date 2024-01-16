package med.mkn.api.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.mkn.api.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    //não vai criar tabela endereço no BD
    //usando Embeddable Attribute da JPA
    //Vai ficar em uma classe separada mas no BD é considerado dessa classe endereço
    //fazem parte da mesma tabela de medicos
    //na classe Endereco precisa ter anotação @Embeddable
    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    //métodos

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    //Construtor
    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        //criar construtor na classe Endereco para receber dados.endereco
        this.endereco = new Endereco(dados.endereco());
    }

    //Atualizar Informações
    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        //atualizar informações
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }

        if(dados.email() != null) {
            this.email = dados.email();
        }

        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }

        if(dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    //Excluir/Desativar médico
    public void excluir() {
        this.ativo = false;

    }
}
