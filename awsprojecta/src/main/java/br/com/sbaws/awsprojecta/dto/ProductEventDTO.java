package br.com.sbaws.awsprojecta.dto;

import lombok.Data;

@Data
public class ProductEventDTO {

    private long productId;
    private String code;
    private String username;
}
