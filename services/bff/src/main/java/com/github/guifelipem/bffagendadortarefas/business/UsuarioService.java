package com.github.guifelipem.bffagendadortarefas.business;

import com.github.guifelipem.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.github.guifelipem.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.github.guifelipem.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.github.guifelipem.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.github.guifelipem.bffagendadortarefas.business.dto.out.ViaCepDTOResponse;
import com.github.guifelipem.bffagendadortarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioClient client;

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioDTO){
        return client.salvarUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginRequestDTO usuarioDTO){
        return client.login(usuarioDTO);
    }

    public UsuarioDTOResponse buscarUsuarioPorEmail(String email, String token){
        return client.buscaUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token) {
        client.deletaUsuarioPorEmail(email, token);
    }

    public UsuarioDTOResponse atualizarDadosUsuario(String token, UsuarioDTORequest dto) {
        return client.atualizarDadoUsuario(dto, token);
    }

    public EnderecoDTOResponse atualizaEndereco(Long idEndereco, EnderecoDTORequest enderecoDTO, String token) {
        return client.atualizarEndereco(enderecoDTO, idEndereco, token);
    }

    public TelefoneDTOResponse atualizaTelefone(Long idTelefone, TelefoneDTORequest telefoneDTO, String token) {
        return client.atualizarTelefone(telefoneDTO, idTelefone, token);
    }

    public EnderecoDTOResponse cadastraEndereco(String token, EnderecoDTORequest enderecoDTO){
        return client.cadastraEndereco(enderecoDTO, token);
    }

    public TelefoneDTOResponse cadastraTelefone(String token, TelefoneDTORequest telefoneDTO){
        return client.cadastraTelefone(telefoneDTO, token);
    }

    public ViaCepDTOResponse buscarEnderecoPorCep(String cep) {
        return client.buscarDadosCep(cep);
    }
}
