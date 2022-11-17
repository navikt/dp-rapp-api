package no.nav.raptus.dprapp.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;
//import java.util.UUID;

/**
 * Entitetsklasse som representerer en logglinje i KALL_LOGG-tabellen.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class KallLogg {

    public static final String RETNING_INN = "INN";
    public static final String RETNING_UT = "UT";
    public static final String TYPE_REST = "REST";
    public static final String TYPE_KAFKA = "KAFKA";
    public static final String METODE_POST = "POST";

    private Long kallLoggId;

    //private UUID korrelasjonId;
    private String korrelasjonId;

    private LocalDateTime tidspunkt;

    private String type;

    private String retning;

    private String metode;

    private String path;

    private Integer status;

    private Long kalltid;

    private String request;

    private String response;

    private String info;
}
