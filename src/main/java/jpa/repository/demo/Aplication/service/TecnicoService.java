package jpa.repository.demo.Aplication.service;

import jpa.repository.demo.domain.dto.TecnicoRequestDTO;
import jpa.repository.demo.domain.dto.TecnicoResponseDTO;
import jpa.repository.demo.domain.entity.Tecnico;

import jpa.repository.demo.domain.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TecnicoService {


    @Autowired
    TecnicoRepository tecnicoRepository;

    public TecnicoResponseDTO salvarTecnico(TecnicoRequestDTO tecnicoRequestDTO)throws Exception{
        if(tecnicoRequestDTO.getNome() == null){
            throw new Exception("não pode ser salvo sem nome informado");
        }
        Tecnico tecnico = toEntity(tecnicoRequestDTO);

        Tecnico tecnicoSalvo =  tecnicoRepository.save(tecnico);

        return new TecnicoResponseDTO(tecnicoSalvo.getId(),tecnicoSalvo.getNome());
    }
    public String deletarTecnico(Long id)throws Exception{
        Tecnico tecnicoLocalizado = tecnicoRepository.findById(id).orElseThrow(()-> new  Exception("não foi localizado esse tecnico"));
        tecnicoRepository.delete(tecnicoLocalizado);
        return "deletado com sucesso";
    }

    public Tecnico buscarTecnicoId(Long id) throws Exception{
        return tecnicoRepository.findById(id).orElseThrow(()-> new Exception("tecnico não localizado"));
    }

    public TecnicoResponseDTO buscarTecnico(Long id) throws Exception{
        Tecnico tecnicoLocalizado =  tecnicoRepository.findById(id).orElseThrow(()-> new Exception("tecnico não localizado"));
        return new TecnicoResponseDTO(tecnicoLocalizado.getId(),tecnicoLocalizado.getNome());
    }
    public List<TecnicoResponseDTO> listarTecnicos() throws Exception{
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        ArrayList<TecnicoResponseDTO> tecnicoResponseDTOS = new ArrayList<>();
        for (Tecnico tecnicoLocalizado : tecnicos) {
            tecnicoResponseDTOS.add(toResponseDTO(tecnicoLocalizado));
        }
        return tecnicoResponseDTOS;
    }

    public Tecnico toEntity (TecnicoRequestDTO tecnicoRequestDTO) throws Exception{
        return new Tecnico(tecnicoRequestDTO.getNome());
    }
    public TecnicoResponseDTO toResponseDTO(Tecnico tecnico){
        return new TecnicoResponseDTO(tecnico.getId(),tecnico.getNome());
    }


}
