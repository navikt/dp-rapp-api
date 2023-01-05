package no.nav.raptus.dprapp.db.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entitetsklasse som representerer en meldeperiode.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Meldeperiode {

    private Long meldeperiodeId;
    private LocalDate fomDato;
    private LocalDate tomDato;
    private LocalDate frist;
}
