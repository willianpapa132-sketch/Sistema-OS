package jpa.repository.demo.tecnico.service;

import jpa.repository.demo.tecnico.dto.TecnicoRequestDTO;
import jpa.repository.demo.tecnico.dto.TecnicoResponseDTO;
import jpa.repository.demo.tecnico.entity.Tecnico;

import jpa.repository.demo.tecnico.repository.TecnicoRepository;
import jpa.repository.demo.handler.BusinessException;
import jpa.repository.demo.handler.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TecnicoService {


    @Autowired
    TecnicoRepository tecnicoRepository;

    public TecnicoResponseDTO salvarTecnico(TecnicoRequestDTO tecnicoRequestDTO){
        if(tecnicoRequestDTO.getNome() == null){
            throw new BusinessException("não pode ser salvo sem nome informado");
        }
        Tecnico tecnico = toEntity(tecnicoRequestDTO);

        Tecnico tecnicoSalvo =  tecnicoRepository.save(tecnico);

        return new TecnicoResponseDTO(tecnicoSalvo.getId(),tecnicoSalvo.getNome());
    }
    public String deletarTecnico(Long id){
        Tecnico tecnicoLocalizado = buscarTecnicoId(id);
        tecnicoRepository.delete(tecnicoLocalizado);
        return "deletado com sucesso";
    }

    public Tecnico buscarTecnicoId(Long id){
        return tecnicoRepository.findById(id).orElseThrow(()-> new NotFoundException("tecnico não localizado"));
    }

    public TecnicoResponseDTO buscarTecnico(Long id) {
        Tecnico tecnicoLocalizado =  tecnicoRepository.findById(id).orElseThrow(()-> new NotFoundException("tecnico não localizado"));
        return new TecnicoResponseDTO(tecnicoLocalizado.getId(),tecnicoLocalizado.getNome());
    }
    public List<TecnicoResponseDTO> listarTecnicos() {
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        ArrayList<TecnicoResponseDTO> tecnicoResponseDTOS = new ArrayList<>();
        for (Tecnico tecnicoLocalizado : tecnicos) {
            tecnicoResponseDTOS.add(toResponseDTO(tecnicoLocalizado));
        }
        return tecnicoResponseDTOS;
    }

    public Tecnico toEntity (TecnicoRequestDTO tecnicoRequestDTO){
        return new Tecnico(tecnicoRequestDTO.getNome());
    }
    public TecnicoResponseDTO toResponseDTO(Tecnico tecnico){
        return new TecnicoResponseDTO(tecnico.getId(),tecnico.getNome());
    }


}
