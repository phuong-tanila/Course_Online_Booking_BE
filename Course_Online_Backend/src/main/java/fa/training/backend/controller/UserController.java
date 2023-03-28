package fa.training.backend.controller;

import fa.training.backend.entities.User;
import fa.training.backend.mapper.UserMapper;
import fa.training.backend.model.UserModel;
import fa.training.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    public UserMapper userMapper;
	@GetMapping("/list-user")
	public ResponseEntity<List<UserModel>> getListUser() {
		List<UserModel> result = new ArrayList<>();
		List<User> listUser = userService.findAllUser();
        listUser.forEach(u -> result.add(userMapper.toModel(u)));
        return new ResponseEntity<List<UserModel>>(result, new HttpHeaders(), HttpStatus.OK);
	}

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

    @GetMapping("/list-teacher")
    public ResponseEntity<List<UserModel>> getListTeacher(
            @RequestParam(defaultValue = "TC") String role
    ) {
        List<UserModel> result = new ArrayList<>();
        List<User> listUser = userService.findListTeacher(role);
        listUser.forEach(u -> result.add(userMapper.toModel(u)));
        return new ResponseEntity<List<UserModel>>(result, new HttpHeaders(), HttpStatus.OK);
    }

    /*Show list all user*/
//    @GetMapping("/list-student")
//    public ResponseEntity<List<User>> getListUser() {
//        return ResponseEntity.ok(userService.findAllUser());
//    }
    /*Show user theo id*/

    @GetMapping("/{id}/{role}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") int id, @PathVariable String role) {
        User user = userService.findUserById(id, role);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<UserModel> getStudentById(@PathVariable("id") int id, @RequestParam(defaultValue = "US") String role) {
        User user = userService.findUserById(id, role);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/update-profile")
    public ResponseEntity updateProfile(@Valid @RequestBody User user, Principal principal) {
        User updateUser = (User) ((Authentication) principal).getPrincipal();
        updateUser.setEmail(user.getEmail());
        updateUser.setFullname(user.getFullname());
        updateUser.setPhone(user.getPhone());
        updateUser.setDescription(user.getDescription());
        updateUser.setAvatar(user.getAvatar());
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @GetMapping("/profile-info")
    public ResponseEntity<UserModel> getProfile(Principal principal) {
        User profileUser = (User) ((Authentication) principal).getPrincipal();
        int id = profileUser.getId();
        String role = profileUser.getRole();
        User user = userService.findUserById(id, role);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/count-teacher")
    public List<Integer> totalTeacherByCategory() {
        try {
            return userService.countTeacherEachCategory();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
