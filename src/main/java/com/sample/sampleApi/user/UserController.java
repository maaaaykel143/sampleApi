package com.sample.sampleApi.user;

import com.sample.sampleApi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/user/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "/user/new")
    public User addUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PutMapping(path = "/update_user/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName, @RequestParam(required = false) String emailAddress) {
        userService.updateUserInfo(id, firstName, lastName, emailAddress);
    }

    @DeleteMapping(path = "/user/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }

}
