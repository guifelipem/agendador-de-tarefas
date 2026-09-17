package com.github.guifelipem.bffagendadortarefas.infrastructure.client;

import com.github.guifelipem.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.github.guifelipem.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.github.guifelipem.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.github.guifelipem.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.ViaCepDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping
    UsuarioDTOResponse buscaUsuarioPorEmail(@RequestParam("email") String email,
                                            @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOResponse salvarUsuario(@RequestBody UsuarioDTORequest usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody LoginRequestDTO usuarioDTO);

    @DeleteMapping("/{email}")
    Void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioDTOResponse atualizarDadoUsuario(@RequestBody UsuarioDTORequest dto,
                                           @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoDTOResponse atualizarEndereco(@RequestBody EnderecoDTORequest dto,
                                          @RequestParam("id") Long id,
                                          @RequestHeader("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneDTOResponse atualizarTelefone(@RequestBody TelefoneDTORequest dto,
                                          @RequestParam("id") Long id,
                                          @RequestHeader("Authorization") String token);

    @PostMapping("/endereco")
    EnderecoDTOResponse cadastraEndereco(@RequestBody EnderecoDTORequest dto,
                                        @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneDTOResponse cadastraTelefone(@RequestBody TelefoneDTORequest dto,
                                        @RequestHeader("Authorization") String token);

    @GetMapping("/endereco/{cep}")
    public ViaCepDTOResponse buscarDadosCep(@PathVariable("cep") String cep);
}
