package ua.kpi.ip;

import net.spy.memcached.MemcachedClient;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kpi.ip.persistance.HumanRepository;
import ua.kpi.ip.persistance.model.Human;
import ua.kpi.ip.persistance.model.LoginData;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/")
public class MainController {

    private static final int EXP_INTERVAL = 24 * 60 * 60;
    private MemcachedClient mc;
    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    private HumanRepository humanRepository;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository, HumanRepository humanRepository,
                          MemcachedClient mc) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.humanRepository = humanRepository;
        this.mc = mc;
    }

    @GetMapping
    public String helloFacebook(Model model, HttpServletRequest request) {
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


        LoginData lastIn = (mc.get(h.getFbId()) != null) ? (LoginData) mc.get(h.getFbId()) : null;

        model.addAttribute("last_date", lastIn != null ? lastIn.getDate() : " n/a ");
        model.addAttribute("last_client", lastIn != null ? lastIn.getName() : " n/a ");

        //якщо замість сет здобити add буде час першого входу за останню добу
        mc.set(h.getFbId(), EXP_INTERVAL,new LoginData( new Date().toString(),request.getRemoteAddr()));

        return "info";
    }

}