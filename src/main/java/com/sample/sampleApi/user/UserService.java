package com.sample.sampleApi.user;

import com.sample.sampleApi.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id).orElse(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return userRepository.findById(id);
    }

    public User addNewUser(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmailAddress(user.getEmailAddress());

        if(user.getEmailAddress() == null) {
            throw new IllegalStateException("Invalid email address!");
        }

        if(userByEmail.isPresent()) {
            throw new IllegalStateException("Email already registered!");
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws ResourceNotFoundException {
        var user = getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        userRepository.delete(user);
    }

    @Transactional
    public void updateUserInfo(Long id, String firstName, String lastName, String emailAddress) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User ID does not exists"));

        if(firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            user.setFirstName(firstName);
        }

        if(lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            user.setLastName(lastName);
        }


        if(emailAddress != null && emailAddress.length() > 0 && !Objects.equals(user.getEmailAddress(), emailAddress)) {
            Optional<User> result = userRepository.findUserByEmailAddress(emailAddress);

            if(result.isPresent()) {
                throw new IllegalStateException("Email already taken");
            }

            user.setEmailAddress(emailAddress);
        }
    }
}
