package com.api.Controledeestacionamento.servicesTest;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoBadRequest;
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

    @Test
    void deveTestarConverterDtoEmEntity() {
        ControleDeEstacionamentoModel converToModel = controleDeEstacionamentoService.convertControleEstacionamentoDTO(controleDeEstacionamentoDtoResponseMock());

        Assertions.assertNotNull(converToModel);
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
}