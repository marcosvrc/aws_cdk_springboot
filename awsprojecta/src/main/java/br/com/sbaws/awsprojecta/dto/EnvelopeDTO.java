package br.com.sbaws.awsprojecta.dto;

import br.com.sbaws.awsprojecta.enums.EventType;
import lombok.Data;

@Data
public class EnvelopeDTO {

    private EventType eventType;
    private String data;
}
