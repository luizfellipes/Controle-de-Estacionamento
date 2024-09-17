package com.api.Controledeestacionamento.servicesTest;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoBadRequest;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoConflito;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoNaoEncontrada;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.repositories.ControleDeEstacionamentoRepository;
import com.api.Controledeestacionamento.services.ControleDeEstacionamentoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.api.Controledeestacionamento.mocks.MocksDTO.controleDeEstacionamentoDtoNullMock;
import static com.api.Controledeestacionamento.mocks.MocksDTO.controleDeEstacionamentoDtoResponseMock;
import static com.api.Controledeestacionamento.mocks.MocksModel.controleDeEstacionamentoModelResponseMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ServiceTest {

    private ControleDeEstacionamentoService controleDeEstacionamentoService;
    private ControleDeEstacionamentoRepository repository;


    @BeforeEach
    void setUp() {
        this.repository = mock(ControleDeEstacionamentoRepository.class);
        this.controleDeEstacionamentoService = new ControleDeEstacionamentoService(repository);
    }

    //Caso de sucesso
    @Test
    void deveTestarSaveComSucesso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel savedModel = controleDeEstacionamentoService.save(controleDeEstacionamentoDtoResponseMock());

        Assertions.assertEquals(mockModel.getId(), savedModel.getId());
        Assertions.assertEquals(mockModel.getApartamento(), savedModel.getApartamento());
        Assertions.assertEquals(mockModel.getBloco(), savedModel.getBloco());
        Assertions.assertEquals(mockModel.getPlacaDoCarro(), savedModel.getPlacaDoCarro());
        Assertions.assertEquals(mockModel.getCorDoCarro(), savedModel.getCorDoCarro());
    }


    @Test
    void deveTestarFindAllComSucesso() {
        when(repository.findAll()).thenReturn(List.of());

        List<ControleDeEstacionamentoModel> models = controleDeEstacionamentoService.findAll();

        Assertions.assertTrue(models.isEmpty());
    }

    @Test
    void deveTestarFindByIdComSucesso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        when(repository.findById(any())).thenReturn(Optional.of(mockModel));

        Optional<ControleDeEstacionamentoModel> models = Optional.ofNullable(controleDeEstacionamentoService.findById(UUID.randomUUID()));

        Assertions.assertTrue(models.isPresent());
    }

    @Test
    void deveTestarDeleteComSucesso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        when(repository.findById(any())).thenReturn(Optional.of(mockModel));

        Executable transacaoApagada = () -> controleDeEstacionamentoService.deleteById(UUID.randomUUID());

        Assertions.assertDoesNotThrow(transacaoApagada);
    }

    @Test
    void deveTestarUpdateComSucesso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        when(repository.findById(any())).thenReturn(Optional.of(mockModel));
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel updatedModel = controleDeEstacionamentoService.update(UUID.randomUUID(), controleDeEstacionamentoDtoResponseMock());

        Assertions.assertEquals(mockModel, updatedModel);
    }

    @Test
    void deveTestarPatchByIdComSucesso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        when(repository.findById(any())).thenReturn(Optional.of(mockModel));
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel patchedModel = controleDeEstacionamentoService.patch(UUID.randomUUID(), controleDeEstacionamentoDtoResponseMock());

        Assertions.assertEquals(mockModel, patchedModel);
    }


    //Caso de falha
    @Test
    void deveDarErroAoSalvarVagas() {
        when(repository.save(any())).thenThrow(new VagaEstacionamentoBadRequest());

        Executable save = () -> controleDeEstacionamentoService.save(controleDeEstacionamentoDtoResponseMock());

        Assertions.assertThrows(VagaEstacionamentoBadRequest.class, save);
    }


    @Test
    void deveDarErroAoBuscarTodasAsVagas() {
        when(repository.findAll()).thenThrow(new VagaEstacionamentoNaoEncontrada());

        Executable findAll = () -> controleDeEstacionamentoService.findAll();

        Assertions.assertThrows(VagaEstacionamentoNaoEncontrada.class, findAll);
    }

    @Test
    void deveDarErroAoRealizarFindById() {
        when(repository.findById(any())).thenThrow(new VagaEstacionamentoNaoEncontrada());

        Executable findById = () -> controleDeEstacionamentoService.findById(UUID.randomUUID());

        Assertions.assertThrows(VagaEstacionamentoNaoEncontrada.class, findById);
    }

    @Test
    void deveDarErroAoRealizarDelete() {
        doThrow(new VagaEstacionamentoBadRequest()).when(repository).deleteById(any(UUID.class));
        when(repository.findById(any())).thenThrow(new VagaEstacionamentoBadRequest());

        Executable delete = () -> controleDeEstacionamentoService.deleteById(UUID.randomUUID());

        Assertions.assertThrows(VagaEstacionamentoBadRequest.class, delete);
    }

    @Test
    void deveDarErroAoRealizarUpdate() {
        when(repository.save(any())).thenThrow(new VagaEstacionamentoNaoEncontrada());
        when(repository.findById(any())).thenThrow(new VagaEstacionamentoNaoEncontrada());

        Executable update = () -> controleDeEstacionamentoService.update(UUID.randomUUID(), controleDeEstacionamentoDtoResponseMock());

        Assertions.assertThrows(VagaEstacionamentoNaoEncontrada.class, update);
    }

    @Test
    void deveDarErroAoAplicarPatch() {
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoNullMock();

        when(repository.findById(any())).thenThrow(new VagaEstacionamentoNaoEncontrada());
        Executable patchById = () -> controleDeEstacionamentoService.patch(UUID.randomUUID(), DTO);
        Assertions.assertThrows(VagaEstacionamentoNaoEncontrada.class, patchById);
    }

    @Test
    void deveTestarSalvarCarroQuandoPlacaNaoExiste() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();

        when(repository.existsByPlacaDoCarro(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel mockModelSaved = controleDeEstacionamentoService.save(DTO);

        Assertions.assertNotEquals("", mockModelSaved.getPlacaDoCarro());
        Assertions.assertEquals(mockModelSaved.getPlacaDoCarro(), mockModel.getPlacaDoCarro());

        verify(repository).existsByPlacaDoCarro(DTO.placaDoCarro());
    }

    @Test
    void deveTestarPlacaExistente() {
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();
        when(repository.existsByPlacaDoCarro(anyString())).thenReturn(true);

        Executable placaExistente = () -> controleDeEstacionamentoService.save(DTO);

        Assertions.assertThrows(VagaEstacionamentoConflito.class, placaExistente);
        verify(repository).existsByPlacaDoCarro(DTO.placaDoCarro());
    }

    @Test
    void deveTestarSalvarCarroQuandoExisteVagaDeCarroEmUso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();

        when(repository.existsByVagaDoEstacionamento(anyInt())).thenReturn(false);
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel mockModelSaved = controleDeEstacionamentoService.save(DTO);

        Assertions.assertNotEquals(2, mockModelSaved.getVagaDoEstacionamento());
        Assertions.assertEquals(mockModelSaved.getVagaDoEstacionamento(), mockModel.getVagaDoEstacionamento());

        verify(repository).existsByVagaDoEstacionamento(DTO.vagaDoEstacionamento());
    }

    @Test
    void deveTestarVagaDeCarroEmUso() {
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();
        when(repository.existsByVagaDoEstacionamento(anyInt())).thenReturn(true);

        Executable vagaExistente = () -> controleDeEstacionamentoService.save(DTO);

        Assertions.assertThrows(VagaEstacionamentoConflito.class, vagaExistente);
        verify(repository).existsByVagaDoEstacionamento(DTO.vagaDoEstacionamento());
    }

    @Test
    void deveTestarSalvarQuandoApartamentoEBlocoNaoEstaoEmUso() {
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();

        when(repository.existsByApartamentoAndBloco(anyString(), anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel mockModelSaved = controleDeEstacionamentoService.save(DTO);

        Assertions.assertEquals(mockModelSaved.getApartamento(), mockModel.getApartamento());
        Assertions.assertEquals(mockModelSaved.getBloco(), mockModel.getBloco());

        verify(repository).existsByApartamentoAndBloco(DTO.apartamento(), DTO.bloco());
    }

    @Test
    void deveTestarConflitoQuandoApartamentoEBlocoEstaoEmUso() {
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();

        when(repository.existsByApartamentoAndBloco(anyString(), anyString())).thenReturn(true);

        Executable apBloco = () -> controleDeEstacionamentoService.save(DTO);

        Assertions.assertThrows(VagaEstacionamentoConflito.class, apBloco);

        verify(repository).existsByApartamentoAndBloco(DTO.apartamento(), DTO.bloco());
    }

    @Test
    void deveTestarSaveComConversaoCorretaDeDTOParaModelo() {
        ControleDeEstacionamentoDto DTO = controleDeEstacionamentoDtoResponseMock();
        ControleDeEstacionamentoModel mockModel = controleDeEstacionamentoModelResponseMock();

        when(repository.existsByPlacaDoCarro(anyString())).thenReturn(false);
        when(repository.existsByApartamentoAndBloco(anyString(), anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(mockModel);

        ControleDeEstacionamentoModel mockModelSaved = controleDeEstacionamentoService.save(DTO);

        Assertions.assertEquals(DTO.vagaDoEstacionamento(), mockModelSaved.getVagaDoEstacionamento());
        Assertions.assertEquals(DTO.placaDoCarro(), mockModelSaved.getPlacaDoCarro());
        Assertions.assertEquals(DTO.marcaDoCarro(), mockModelSaved.getMarcaDoCarro());
        Assertions.assertEquals(DTO.modeloDoCarro(), mockModelSaved.getModeloDoCarro());
        Assertions.assertEquals(DTO.corDoCarro(), mockModelSaved.getCorDoCarro());
        Assertions.assertEquals(DTO.dataDeRegistro(), mockModelSaved.getDataDeRegistro());
        Assertions.assertEquals(DTO.nomeDoResponsavel(), mockModelSaved.getNomeDoResponsavel());
        Assertions.assertEquals(DTO.apartamento(), mockModelSaved.getApartamento());
        Assertions.assertEquals(DTO.bloco(), mockModelSaved.getBloco());

        verify(repository).save(any(ControleDeEstacionamentoModel.class));
    }
}