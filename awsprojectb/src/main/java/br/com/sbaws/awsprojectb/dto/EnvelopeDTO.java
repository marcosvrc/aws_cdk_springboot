package br.com.sbaws.awsprojectb.dto;

import br.com.sbaws.awsprojectb.enums.EventType;
import lombok.Data;

@Data
public class EnvelopeDTO {

    private EventType eventType;
    private String data;
}
