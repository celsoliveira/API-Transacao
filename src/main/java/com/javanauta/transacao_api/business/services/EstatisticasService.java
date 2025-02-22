package com.javanauta.transacao_api.business.services;

import com.javanauta.transacao_api.controller.dto.EstatisticasResponseDTO;
import com.javanauta.transacao_api.controller.dto.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticasTransacoes(Integer intervaloBusca){

        log.info("Iniciada a busca de estatísticas de transações pelo período de tempo " + intervaloBusca);

        Long start = System.currentTimeMillis();

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);

        DoubleSummaryStatistics estatisticasTransacoes =
                transacoes.stream().mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();

        Long finish = System.currentTimeMillis();
        long timeRequest = finish - start;
        log.info("Tempo de requisição: " + timeRequest + "ms"); //milisegundos

        log.info("Estatística retornada com sucesso");
        if (transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L,0.0,0.0,0.0,0.0);
        }

        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(),
                                          estatisticasTransacoes.getSum(),
                                          estatisticasTransacoes.getAverage(),
                                          estatisticasTransacoes.getMin(),
                                          estatisticasTransacoes.getMax());
    }


}
