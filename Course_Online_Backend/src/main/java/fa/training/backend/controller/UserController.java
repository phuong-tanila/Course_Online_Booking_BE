package fa.training.backend.controller;

import fa.training.backend.entities.User;

import fa.training.backend.exception.RecordNotFoundException;

import fa.training.backend.mapper.UserMapper;
import fa.training.backend.model.UserModel;
import fa.training.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired

    private UserService userService;
    @Autowired
    public UserMapper userMapper;
//	@GetMapping("/list-user")
//	public List<UserModel> getListUser() {
//		List<UserModel> modelList = new ArrayList<>();
//		List<User> userList = userService.findAllUser();
//		for (User u : userList) {
//			UserModel userModel = mapStructConverter.sourceToDestination(u);
//			modelList.add(userModel);
//		}
//		return modelList;
//	}

    //	Get user profile
    @GetMapping("/profile/{searchKey}")
    public ResponseEntity<UserModel> getUser(
            @PathVariable("searchKey") String searchKey
    ) {
        User user = (User) userService.loadUserByUsername(searchKey);
        if (user != null) {
            UserModel userModel = userMapper.toModel(user);
            return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<UserModel>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<UserModel> getTeacherById(
            @PathVariable("id") int id,
            @RequestParam(defaultValue = "TC") String role
    ) {
        User user = (User) userService.findUserById(id, role);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }

    /*Show list all user*/
//    @GetMapping("/list-student")
//    public ResponseEntity<List<User>> getListUser() {
//        return ResponseEntity.ok(userService.findAllUser());
//    }

    /*Show user theo id*/
    @GetMapping("/student/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") int id, @RequestParam(defaultValue = "US") String role) {
        User user = userService.findUserById(id, role);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/update-student/{id}")
    public ResponseEntity<User> edit(@PathVariable("id") int id, @RequestParam(defaultValue = "US") String role, @RequestBody User u) {
        Optional<User> optionalUpdatedUser = Optional.ofNullable(userService.findUserById(id, role));
        User updatedUser = optionalUpdatedUser.get();

        updatedUser.setFullname(u.getFullname());
        updatedUser.setPhone(u.getPhone());
        updatedUser.setEmail(u.getEmail());
        updatedUser.setAvatar(u.getAvatar());
        updatedUser.setDescription(u.getDescription());

        return ResponseEntity.ok(userService.saveUser(updatedUser));

    }
}
