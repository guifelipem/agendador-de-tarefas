package com.github.guifelipem.bffagendadortarefas.infrastructure.client.config;

import com.github.guifelipem.bffagendadortarefas.infrastructure.exceptions.BusinessException;
import com.github.guifelipem.bffagendadortarefas.infrastructure.exceptions.ConflictException;
import com.github.guifelipem.bffagendadortarefas.infrastructure.exceptions.IllegalArgumentException;
import com.github.guifelipem.bffagendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.github.guifelipem.bffagendadortarefas.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {

        final String PREFIXO_ERRO = "Erro: ";

        String mensagemErro = mensagemErro(response);

        switch (response.status()) {
            case 409:
                return new ConflictException(PREFIXO_ERRO + mensagemErro);
            case 403:
                return new ResourceNotFoundException(PREFIXO_ERRO + mensagemErro);
            case 401:
                return new UnauthorizedException(PREFIXO_ERRO + mensagemErro);
            case 400:
                return new IllegalArgumentException(PREFIXO_ERRO + mensagemErro);
            default:
                return new BusinessException(PREFIXO_ERRO + mensagemErro);
        }
    }

    private String mensagemErro(Response response) {
        try {
            if (Objects.isNull(response.body())) {
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
