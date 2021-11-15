package br.com.sbaws.awsprojectb.service;

import br.com.sbaws.awsprojectb.dto.EnvelopeDTO;
import br.com.sbaws.awsprojectb.dto.ProductEventDTO;
import br.com.sbaws.awsprojectb.dto.SnsMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
public class ProductEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ProductEventConsumer.class);

    private ObjectMapper objectMapper;

    @Autowired
    public ProductEventConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${asw.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage) throws IOException, JMSException {

        SnsMessageDTO snsMessageDTO = objectMapper.readValue(textMessage.getText(),SnsMessageDTO.class);
        EnvelopeDTO envelopeDTO = objectMapper.readValue(snsMessageDTO.getMessage(),EnvelopeDTO.class);
        ProductEventDTO productEventDTO = objectMapper.readValue(envelopeDTO.getData(), ProductEventDTO.class);

        LOG.info("Product event received - Event: {} - ProductId: {} - ",
                envelopeDTO.getEventType(),productEventDTO.getProductId());
    }

}
