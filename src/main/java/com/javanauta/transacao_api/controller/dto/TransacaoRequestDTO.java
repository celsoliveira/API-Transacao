package com.javanauta.transacao_api.controller.dto;

import java.time.OffsetDateTime;

public record TransacaoRequestDTO(Double valor, OffsetDateTime dataHora) {


}
