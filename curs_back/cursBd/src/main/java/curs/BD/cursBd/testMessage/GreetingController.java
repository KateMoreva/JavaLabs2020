package curs.BD.cursBd.testMessage;
//
//import curs.BD.cursBd.testMessage.Message;
//import curs.BD.cursBd.testMessage.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicLong;
//
//@RestController
//public class GreetingController {
//
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @GetMapping("/greeting")
//    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }
//}
//    @GetMapping("/greeting")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    @Autowired
//    private MessageRepo messageRepo;
//
//    @GetMapping("/greeting")
//    public String greeting(
//            @RequestParam(name="name", required=false, defaultValue="World") String name,
//            Map<String, Object> model
//    ) {
//        model.put("name", name);
//        return "greeting";
//    }
//
//    @GetMapping
//    public String main(Map<String, Object> model) {
//        Iterable<Message> messages = messageRepo.findAll();
//
//        model.put("messages", messages);
//
//        return "main";
//    }
//
//    @PostMapping
//    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
//        Message message = new Message(text, tag);
//
//        messageRepo.save(message);
//
//        Iterable<Message> messages = messageRepo.findAll();
//
//        model.put("messages", messages);
//
//        return "main";
//    }
//
//    @PostMapping("filter")
//    public String filter(@RequestParam String filter, Map<String, Object> model) {
//        Iterable<Message> messages;
//
//        if (filter != null && !filter.isEmpty()) {
//            messages = messageRepo.findByTag(filter);
//        } else {
//            messages = messageRepo.findAll();
//        }
//
//        model.put("messages", messages);
//
//        return "main";
//    }


