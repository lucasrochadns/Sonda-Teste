package br.com.sonda.aeronave.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name="aeronaves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aeronave implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Fabricante fabricante;

    @Column(nullable = false)
    private Integer anoFabricacao;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private Boolean vendido;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updateAt;

}
