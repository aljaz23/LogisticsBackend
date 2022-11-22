package com.example.controller;

import com.example.model.Article;
import com.example.model.User;
import com.example.model.Warehouse;
import com.example.repository.UserRepository;
import com.example.service.ArticleService;
import com.example.service.UserService;
import com.example.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.registerUser(user);
        return "registerSuccess";
    }

    @GetMapping("/logistics_home")
    public String showHome(Model model) {
        List<User> activeUsers = userService.getAllUsers();
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        List<Warehouse> warehouseInUse = warehouseList.stream().filter(warehouse -> warehouse.getArticlesList().size() > 0).toList();
        List<Article> activeArticles = articleService.getAllArticles();
        model.addAttribute("activeUsers", activeUsers.size());
        model.addAttribute("warehouseInUse", warehouseInUse);
        model.addAttribute("activeArticles", activeArticles.size());

        return "logisticsHome";
    }


    @GetMapping("/logistics_user_list")
    public String viewUserList(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "logisticsListUsers";
    }

    @DeleteMapping("/logistics_user_list/delete_user/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/logistics_user_list";
    }

    @GetMapping("/logistics_user_list/update_user/id={id}")
    public ModelAndView viewUpdateForm(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("logisticsUpdateUser");

        User user = userRepository.findById(id).get();

        mav.addObject("user", user);

        return mav;
    }

    @PostMapping("/logistics_user_list/update_user/{id}")
    public String updateUser(@PathVariable long id, User user) {
        try {
            userService.updateUser(user, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/logistics_user_list";
    }
}