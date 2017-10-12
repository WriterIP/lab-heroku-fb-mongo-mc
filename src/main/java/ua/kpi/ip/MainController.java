package ua.kpi.ip;

import net.spy.memcached.MemcachedClient;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/")
public class MainController {

    MemcachedClient mc;
    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    private HumanRepository humanRepository;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository, HumanRepository humanRepository,
                          MemcachedClient mc) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.humanRepository = humanRepository;
//        this.redisTemplate = redisTemplate;
        this.mc = mc;
//        this.hashOps = redisTemplate.opsForHash();
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        String[] fields = {"id", "email", "first_name", "last_name"};
        User userProfile = facebook.fetchObject("me", User.class, fields);
        Human h = new Human(userProfile.getFirstName(), userProfile.getLastName(), userProfile.getId());
        model.addAttribute("first_name", h.getFirstName());
        model.addAttribute("last_name", h.getLastName());

        if (humanRepository.findByFbId(h.getFbId()) == null) {
            humanRepository.save(h);
            System.out.println("saved him");
        } else
            System.out.println("already in DB");
        mc.set(h.getFirstName() + " " + h.getLastName(), 3600, new Date().toString());
        return "info";
    }

}