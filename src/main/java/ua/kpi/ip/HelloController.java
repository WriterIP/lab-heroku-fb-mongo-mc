package ua.kpi.ip;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;
    private CustomerRepository customerRepository;

    public HelloController(Facebook facebook, ConnectionRepository connectionRepository, CustomerRepository customerRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
        this.customerRepository = customerRepository;
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

        if (customerRepository.findByFbId(userProfile.getId()) == null) {
            customerRepository.save(new Customer(userProfile.getFirstName(), userProfile.getLastName(), userProfile.getId()));
            System.out.println("saved him");
        } else
            System.out.println("already in DB");

        return "info";
    }

}