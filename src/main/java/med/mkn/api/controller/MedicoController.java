package med.mkn.api.controller;

import jakarta.validation.Valid;
import med.mkn.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao){ //pageable para paginar a listagem, classe do Spring
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page); //Receber o código 200 Ok
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());// carregando o medico pelo id no DTO
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build(); //ResponseEntity classe do pacote org.springframework.http
        //noContent() ele nao devolve nenhum conteudo e cria o objeto. O build constroi o objeto ResponseEntity
        //No Postman o retorno vai ser (204 No Content) no lugar de (200 OK), BOA PRATICA
    }

}
