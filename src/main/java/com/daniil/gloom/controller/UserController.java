package com.daniil.gloom.controller;

import com.daniil.gloom.domain.User;
import com.daniil.gloom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String showMyPage(Principal principal, Model model) {
        model.addAttribute("user", principal);

        return "users/mainPage";
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('user:read')")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "users/allUsers";
    }

    @GetMapping("/{id}/management")
    @PreAuthorize("hasAuthority('user:write')")
    public String userManagement(@PathVariable("id") long id, Model model) {
        if (userRepository.findById(id).isEmpty()) return "redirect:/user/allUsers";

        model.addAttribute(userRepository.findById(id).get());

        return "users/management/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) return "users/management/edit";

        userRepository.update(user.getUsername(), user.getPassword(),
                user.getEmail(), id);

        return "redirect:/user/allUsers";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public String deleteUser(@PathVariable("id") long id) {
        userRepository.deleteById(id);

        return "redirect:/user/allUsers";
    }

}
