package com.workoutportal.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.workoutportal.User.User;
import com.workoutportal.User.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<String> createUser(@RequestBody User userObj) {
		Map<String, Object> message = new HashMap<String, Object>();
		Long userExists = userService.findByUserName(userObj);
		ResponseEntity<String> response = null;
		if (null != userExists && 0 != userExists) {
			message.put("message", "There is already a user registered with the email provided!");
			message.put("Status", "Error");
			response = new ResponseEntity<String>(new Gson().toJson(message), HttpStatus.FORBIDDEN);
		} else {
			userObj.setPassword(passwordEncoder.encode(userObj.getPassword()));
			userService.createUser(userObj);
			message.put("message", "User has been registered successfully!");
			message.put("Status", "Success");
			response = new ResponseEntity<String>(new Gson().toJson(message), HttpStatus.OK);
		}
		return response;
	}


	
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ResponseEntity<String> authenticateUser(@RequestHeader("userName") String user_name,
                                                   @RequestHeader("password") String password) {

        ResponseEntity<String> response = null;
        Long userId = userService.authenticateUser(user_name, password);
        Map<String, Object> message = new HashMap<String, Object>();
        if (userId != null) {
            message.put("message", "User Authenticated Successfully.");
            message.put("Status", "Sucess");
            message.put("UserId", userId);
            response = new ResponseEntity<String>(new Gson().toJson(message), HttpStatus.OK);
        } else {
            message.put("message", "Invalid User Credentails.");
            message.put("Status", "Error");
            response = new ResponseEntity<String>(new Gson().toJson(message), HttpStatus.FORBIDDEN);
        }

        return response;
    }
}
