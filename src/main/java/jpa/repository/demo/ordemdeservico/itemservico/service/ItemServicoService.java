package jpa.repository.demo.ordemdeservico.itemservico.service;

import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoResponseDTO;
import jpa.repository.demo.ordemdeservico.itemservico.entity.ItemServico;
import jpa.repository.demo.servico.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
public class ItemServicoService {

    @Autowired
    ServicoService servicoService;

    public ItemServico toEntity(ItemServicoRequestDTO itemServicoRequestDTO)  {
        return new ItemServico(
                servicoService.buscarServicoId(itemServicoRequestDTO.getServicoid()),
                itemServicoRequestDTO.getQuantidade(),BigDecimal.valueOf(0)
        );
    }
    public ItemServicoResponseDTO toResponse(ItemServico itemServico)  {
        return new ItemServicoResponseDTO(
                itemServico.getId(),itemServico.getOrdemDeServico().getId(),
                servicoService.toResponseDTO( itemServico.getServico()),itemServico.getQuantidade(),itemServico.getValortot()
        );
    }


}
