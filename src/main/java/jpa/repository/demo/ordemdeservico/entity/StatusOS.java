package jpa.repository.demo.ordemdeservico.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum StatusOS {

    AGUARDANDO_APROVACAO,
    APROVADA,
    EM_ANDAMENTO,
    AGUARDANDO_PECA,
    FINALIZADA,
    FATURADA,
    ENTREGUE,
    CANCELADA

}
