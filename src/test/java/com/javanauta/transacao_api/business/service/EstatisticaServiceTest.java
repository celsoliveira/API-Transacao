package com.javanauta.transacao_api.business.service;

import com.javanauta.transacao_api.business.services.EstatisticasService;
import com.javanauta.transacao_api.business.services.TransacaoService;
import com.javanauta.transacao_api.controller.dto.EstatisticasResponseDTO;
import com.javanauta.transacao_api.controller.dto.TransacaoRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {

    @InjectMocks
    EstatisticasService estatisticasService;

    @Mock
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;

    EstatisticasResponseDTO estatisticas;

    @BeforeEach
    void setUp(){
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1l, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void calcularEstatisticasComSucesso(){
        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.singletonList(transacao));

        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticas);
    }

    @Test
    void calcularEstatisticasQuandoListaVazia(){

        EstatisticasResponseDTO estasticaEsperado =
                new EstatisticasResponseDTO(0l, 0.0, 0.0, 0.0, 0.0);

        when(transacaoService.buscarTransacoes(60))
                .thenReturn(Collections.emptyList());

        EstatisticasResponseDTO resultado = estatisticasService.calcularEstatisticasTransacoes(60);

        verify(transacaoService, times(1)).buscarTransacoes(60);
        Assertions.assertThat(resultado).usingRecursiveComparison().isEqualTo(estasticaEsperado);
    }

}
/*
Principais diferenças

@Mock é usado para criar objetos simulados de classes ou interfaces.
@Mock é aplicado a campos que precisam ser simulados.
@Mock não tem conhecimento da classe em teste.

@InjectMocks é usado para injetar simulações na classe em teste.
@InjectMocks é aplicado à instância da classe em teste para injetar as dependências simuladas.
@InjectMocks está ciente da classe em teste e injeta simulações nela.

*/