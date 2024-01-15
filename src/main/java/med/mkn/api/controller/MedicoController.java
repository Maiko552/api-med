package med.mkn.api.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import med.mkn.api.medico.DadosCadastroMedico;
import med.mkn.api.medico.DadosListagemMedico;
import med.mkn.api.medico.Medico;
import med.mkn.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    //pegar o repository e mandar persistir no BD
    //o Spring boot vai fazer isso automaticamente isso chama de: injecão de dependência
    //com a annotation @Autowired
    @Autowired
    private MedicoRepository repository;

    //se chegar requisição do tipo Post, chamar o metodo cadastrar
    //receber dados do JSON no corpo da requisição
    //dados que chegaram do Json na classe DadosCadastroMedico Record
    @PostMapping
    @Transactional //insert no banco de dados, precisamos de uma transação ativa no BD
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        //receberemos um parametro DTO e precisamos converter para um objeto Medico
        repository.save(new Medico(dados)); //precisamos criar o construtor na classe Medico que recebe os dados do DTO
    }

    //método de listagem de medicos

    @GetMapping
    //@PagebleDefault(size = 10, sort = {"nome"}) caso nao for informada a ordenação na url do postman
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){ //pageable para paginar a listagem, classe do Spring
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

}
