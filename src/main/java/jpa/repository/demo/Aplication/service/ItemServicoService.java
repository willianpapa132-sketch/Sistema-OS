package jpa.repository.demo.Aplication.service;

import jpa.repository.demo.domain.dto.ItemServicoRequestDTO;
import jpa.repository.demo.domain.dto.ItemServicoResponseDTO;
import jpa.repository.demo.domain.entity.ItemServico;

import jpa.repository.demo.domain.entity.OrdemDeServico;
import jpa.repository.demo.domain.repository.ItemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jpa.repository.demo.domain.entity.StatusOS;

@Service
public class ItemServicoService {

    @Autowired
    ItemServicoRepository itemServicoRepository;


    public ItemServico salvarItem(ItemServico itemServico) throws Exception {
        if (itemServico.getOrdemDeServico() == null) {
            throw new Exception("não pode ser feito adição de item fora da OS");
        }
        if (itemServico.getServico() == null) {
            throw new Exception("precisa colocar um serviço");
        }
        if (itemServico.getQuantidade() <= 0) {
            throw new Exception("precisa colocar uma quantidade");
        }
        if (itemServico.getOrdemDeServico().getStatus() == StatusOS.CANCELADA || itemServico.getOrdemDeServico().getStatus() == StatusOS.FATURADA) {
            throw new Exception("não pode ser alterado os itens apos Status da ordem estar em cancelada ou faturada");
        }

        itemServico.setValortot(itemServico.getServico().getValor() * itemServico.getQuantidade());
        return itemServicoRepository.save(itemServico);
    }

    public ItemServico toEntity(ItemServicoRequestDTO itemServicoRequestDTO, OrdemDeServico ordemDeServico) throws Exception{
        return new ItemServico(
                ordemDeServico,itemServicoRequestDTO.getServico(),
                itemServicoRequestDTO.getQuantidade(),0
        );
    }
    public ItemServicoResponseDTO toResponse(ItemServico itemServico) throws Exception{
        return new ItemServicoResponseDTO(
                itemServico.getId(),itemServico.getOrdemDeServico().getId(),
                itemServico.getServico(),itemServico.getQuantidade(),itemServico.getValortot()
        );
    }


}
