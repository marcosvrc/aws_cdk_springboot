package br.com.sbaws.awsprojectb.dto;

import lombok.Data;

@Data
public class ProductEventDTO {

    private long productId;
    private String code;
    private String username;
}
