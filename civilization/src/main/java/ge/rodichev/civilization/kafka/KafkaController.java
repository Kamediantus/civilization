//package ge.rodichev.civilization.kafka;
//
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class KafkaController {
//    private final KafkaProducerService producerService;
//
//    public KafkaController(KafkaProducerService producerService) {
//        this.producerService = producerService;
//    }
//
//    @PostMapping("/messages")
//    public void sendMessageToKafka(@RequestBody String message) {
//        producerService.sendMessage(message);
//    }
//}
