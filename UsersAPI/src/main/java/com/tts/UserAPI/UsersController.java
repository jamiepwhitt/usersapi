package com.tts.UserAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	 @Autowired
	    private UsersRepository userRepository;
	    
	    @GetMapping("/users")
	    public List<User> getUsers(@RequestParam(value="state", required=false) String state){
	    if (state != null) {
	    return (List<User>) userRepository.findByState(state);
	    }
	    return (List<User>) userRepository.findAll();
	    }
	    
	    @GetMapping("/users/{id}")
	    public Optional<User> getUserById(@PathVariable(value="id") Long id){
	    return userRepository.findById(id);
	    }

	    @PostMapping("/users")
	    public ResponseEntity<Void> createUser(@RequestBody @Valid User user,   BindingResult bindingResult){
	    	 if (bindingResult.hasErrors()) {
	    	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    	    }
	    	    userRepository.save(user);
	    	    return new ResponseEntity<>(HttpStatus.CREATED);
	    	}
	    
	    @PutMapping("/users/{id}")
	    public ResponseEntity<Void> changeUser(@PathVariable(value="id") Long id, @RequestBody User user,  BindingResult bindingResult){
	    	if (userRepository.findById(user.getId()) != null){
	            bindingResult.rejectValue("Id", "error.Id", "Id does not exist.");
	        }
	        if (bindingResult.hasErrors()) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        userRepository.save(user);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    }
	    
	    
	    @DeleteMapping("/users/{id}")
	    public void createUser(@PathVariable(value="id") Long id){
	    userRepository.deleteById(id);
	    }
}
