package jpa.repository.demo.nfs.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Nfs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
