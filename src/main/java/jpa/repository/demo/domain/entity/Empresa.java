package jpa.repository.demo.domain.entity;

import jakarta.persistence.Id;

public class Empresa {

    @Id
    private  Long id;

    private String razaoSocial;

    private String NomeFantasia;

    private String cnpj;

    private String inscricaoEstadual;

    private String inscricaoMunicipal;

    private Integer regimeTributario;

    razão social
•
    nome fantasia
•
    inscrição municipal, quando aplicável
•
    regime tributário
•
    município
•
    CNAE
•
    códigos de serviço permitidos
•
    alíquota ISS
•
    retenção de ISS
•
    certificado digital, quando exigido pelo provedor
•
    credenciais/API tokens do emissor
}
