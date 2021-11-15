package br.com.sbaws.awsprojecta.service;

import br.com.sbaws.awsprojecta.dto.EnvelopeDTO;
import br.com.sbaws.awsprojecta.dto.ProductEventDTO;
import br.com.sbaws.awsprojecta.entity.Product;
import br.com.sbaws.awsprojecta.enums.EventType;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ProductPublished {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPublished.class);

    private AmazonSNS snsClient;
    private Topic productEventsTopic;
    private ObjectMapper objectMapper;

    public ProductPublished(AmazonSNS snsClient,
                            @Qualifier("productEventsTopic") Topic productEventsTopic,
                            ObjectMapper objectMapper) {

        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.objectMapper = objectMapper;

    }

    public void publishedProductEvent(Product product, EventType eventType, String username) {

        try{
            EnvelopeDTO envelopeDTO = getEnvelopeDTO(product,eventType,username);
            snsClient.publish(productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelopeDTO));

        }catch (JsonProcessingException jpe){
            LOG.error("Failed to create product event message");
        }

    }

    private ProductEventDTO getProductEventDTO(Product product, String username){
        ProductEventDTO productEventDTO = new ProductEventDTO();
        productEventDTO.setProductId(product.getId());
        productEventDTO.setCode(product.getCode());
        productEventDTO.setUsername(username);

        return productEventDTO;
    }

    private EnvelopeDTO getEnvelopeDTO(Product product, EventType eventType, String username) throws JsonProcessingException {
        EnvelopeDTO envelopeDTO = new EnvelopeDTO();
        envelopeDTO.setEventType(eventType);
        envelopeDTO.setData(objectMapper.writeValueAsString(getProductEventDTO(product,username)));

        return envelopeDTO;
    }

}
