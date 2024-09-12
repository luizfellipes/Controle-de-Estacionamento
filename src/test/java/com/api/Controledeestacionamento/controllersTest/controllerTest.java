package com.api.Controledeestacionamento.controllersTest;

import com.api.Controledeestacionamento.controllers.ControleDeEstacionamentoController;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoBadRequest;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoNaoEncontrada;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.services.ControleDeEstacionamentoService;
import com.api.Controledeestacionamento.exceptions.RequestsValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.api.Controledeestacionamento.mocks.MocksDTO.controleDeEstacionamentoDtoNullMock;
import static com.api.Controledeestacionamento.mocks.MocksDTO.controleDeEstacionamentoDtoResponseMock;
import static com.api.Controledeestacionamento.mocks.MocksModel.controleDeEstacionamentoModelRequestMock;
import static com.api.Controledeestacionamento.mocks.MocksModel.controleDeEstacionamentoModelResponseMock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
class ControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ControleDeEstacionamentoService controleDeEstacionamentoService;

    @BeforeEach
    void setUp() {
        this.controleDeEstacionamentoService = mock(ControleDeEstacionamentoService.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new ControleDeEstacionamentoController(controleDeEstacionamentoService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RequestsValidation()).build();
        this.objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JavaTimeModule());
    }

    //deve testar caso de sucesso
    @Test
    void deveCriarUmaNovaTransacao() throws Exception {
        when(controleDeEstacionamentoService.save(any())).thenReturn(controleDeEstacionamentoModelRequestMock());

        mockMvc.perform(post("/controleDeEstacionamento")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(controleDeEstacionamentoDtoResponseMock())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.vagaDoEstacionamento").value(controleDeEstacionamentoDtoResponseMock().vagaDoEstacionamento()))
                .andReturn();
    }

    @Test
    void deveRealizarUmUpdate() throws Exception {
        when(controleDeEstacionamentoService.findById(any())).thenReturn(controleDeEstacionamentoModelResponseMock());

        mockMvc.perform(put("/controleDeEstacionamento/" + controleDeEstacionamentoModelRequestMock().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(controleDeEstacionamentoDtoResponseMock())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deveRealizarUmPatch() throws Exception {
        when(controleDeEstacionamentoService.patch(any(), any())).thenReturn(controleDeEstacionamentoModelResponseMock());

        mockMvc.perform(patch("/controleDeEstacionamento/" + controleDeEstacionamentoModelRequestMock().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(controleDeEstacionamentoDtoResponseMock())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vagaDoEstacionamento").value(controleDeEstacionamentoDtoResponseMock().vagaDoEstacionamento()))
                .andReturn();
    }

    @Test
    void deveTestarTransacaoGetAll() throws Exception {
        when(controleDeEstacionamentoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/controleDeEstacionamento"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deveTestarTransacaoGetOne() throws Exception {
        ControleDeEstacionamentoModel controleDeEstacionamentoModel = controleDeEstacionamentoModelRequestMock();
        when(controleDeEstacionamentoService.findById(any())).thenReturn(controleDeEstacionamentoModel);

        mockMvc.perform(get("/controleDeEstacionamento/" + controleDeEstacionamentoModelRequestMock().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(controleDeEstacionamentoDtoResponseMock())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deveTestarTransacaoDelete() throws Exception {
        when(controleDeEstacionamentoService.findById(any())).thenReturn(null);

        mockMvc.perform(delete("/controleDeEstacionamento/" + controleDeEstacionamentoModelRequestMock().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(controleDeEstacionamentoDtoResponseMock())))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    //deve testar caso de falha
    @Test
    void deveDarErroNaCriacaoDeUmaNovaTransacao() throws Exception {
        when(controleDeEstacionamentoService.save(any())).thenThrow(new VagaEstacionamentoBadRequest());

        mockMvc.perform(post("/controleDeEstacionamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(controleDeEstacionamentoDtoNullMock())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void deveDarErroAoRealizarUmaTransacaoUpdate() throws Exception {
        when(controleDeEstacionamentoService.update(any(), any())).thenThrow(new VagaEstacionamentoBadRequest("requisicao nao feita"));

        mockMvc.perform(put("/controleDeEstacionamento/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Optional.empty())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void deveDarErroAoSolicitarTransacaoVaziasNoGetAll() throws Exception {
        doThrow(VagaEstacionamentoNaoEncontrada.class).when(controleDeEstacionamentoService).findAll();

        mockMvc.perform(get("/controleDeEstacionamento"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deveTestarTransacaoGetOne_CasoDeFalha() throws Exception {
        when(controleDeEstacionamentoService.findById(any())).thenThrow(new VagaEstacionamentoNaoEncontrada("transacao nao existente"));

        mockMvc.perform(get("/controleDeEstacionamento/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deveDarErroAoRealizarUmaTransacaoDelete() throws Exception {
        doThrow(VagaEstacionamentoNaoEncontrada.class).when(controleDeEstacionamentoService).deleteById(any());

        mockMvc.perform(delete("/controleDeEstacionamento/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

}