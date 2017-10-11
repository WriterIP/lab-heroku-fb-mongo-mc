package ua.kpi.ip;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    private HumanRepository humanRepository;
    private RedisTemplate<String, String> redisTemplate;
    private HashOperations hashOps;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository, HumanRepository humanRepository,RedisTemplate<String,String> redisTemplate) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.humanRepository = humanRepository;
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        String[] fields = {"id", "email", "first_name", "last_name"};
        User userProfile = facebook.fetchObject("me", User.class, fields);

        System.out.println(userProfile.getEmail() + userProfile.getFirstName() + userProfile.getLastName());
        model.addAttribute("first_name", userProfile.getFirstName());
        model.addAttribute("last_name", userProfile.getLastName());

        if (humanRepository.findByFbId(userProfile.getId()) == null) {
            humanRepository.save(new Human(userProfile.getFirstName(), userProfile.getLastName(), userProfile.getId()));
            System.out.println("saved him");
        } else
            System.out.println("already in DB");
        return "info";
    }

}