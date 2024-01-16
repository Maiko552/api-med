package med.mkn.api.medico;

import jakarta.validation.constraints.NotNull;
import med.mkn.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone,
        DadosEndereco endereco) {
}
