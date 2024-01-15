package med.mkn.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.mkn.api.endereco.DadosEndereco;

//com a classe RECORD podemos passar um conjunto de dados para o construtor
//o Spring converte o conjunto de dados em um objeto

//Aqui utilazeremos o bean validation para validar os dados nos atributos
public record DadosCadastroMedico(

        //NotBlank apenas para Strings
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //expressão regular para validar o crm de 4 a 6 digitos
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid
        DadosEndereco endereco) {
}
