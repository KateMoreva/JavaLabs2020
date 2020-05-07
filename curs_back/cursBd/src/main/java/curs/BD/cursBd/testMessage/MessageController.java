//package curs.BD.cursBd.testMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriComponentsBuilder;
//
//@RestController
//@RequestMapping("/")
//public class MessageController {
//    @Autowired
//    private final MessageRepo messageRepo;
//
//    public MessageController(MessageRepo messageRepo) {
//        this.messageRepo = messageRepo;
//    }
//
//    @GetMapping("/messages")
//        public Iterable<Message> getSMessageList(){
//        return messageRepo.findAll();
//        }
//    @GetMapping("/messages/{id}")
//        public ResponseEntity<Message> get (@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//        Message message = messageRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("kgu"));
//        return ResponseEntity.ok().body(message);
//    }
//    @RequestMapping(value = "/messages", method = RequestMethod.POST )
//    public ResponseEntity<?> create (@RequestBody Message message) {
//       messageRepo.save(message);
//        return new ResponseEntity<String>(HttpStatus.CREATED);
//    }
//    @RequestMapping(value = "/messages/{id}", method = RequestMethod.DELETE )
//    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws ResourceNotFoundException{
//        Message message = messageRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("kgu"));
//        messageRepo.deleteById(id);
//        return new ResponseEntity<Message>(HttpStatus.NO_CONTENT);
//
//    }
//    @RequestMapping(value = "/messages/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<Message> update(@PathVariable("id") Long id, @RequestBody Message message){
//        if (messageRepo.findById(id).isPresent()){
//            Message exMessage = messageRepo.findById(id).get();
//            exMessage.setTag(message.getTag());
//            exMessage.setText(message.getText());
//            Message newMessage = messageRepo.save(exMessage);
//            }
//        return new ResponseEntity<Message>(message, HttpStatus.OK);
//
//    }
//
//
//}
