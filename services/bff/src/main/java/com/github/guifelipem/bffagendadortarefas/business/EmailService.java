package com.github.guifelipem.bffagendadortarefas.business;

import com.github.guifelipem.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.github.guifelipem.bffagendadortarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviarEmail(TarefasDTOResponse dto) {
        emailClient.enviarEmail(dto);
    }
}
