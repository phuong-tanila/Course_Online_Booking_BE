package fa.training.backend.controller;

import fa.training.backend.entities.User;
import fa.training.backend.mapper.UserMapper;
import fa.training.backend.model.UserModel;
import fa.training.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserModel> getUserById(
            @PathVariable("id") int id,
            @RequestParam(defaultValue = "TC") String role
    ) {
        User user = (User) userService.findUserById(id, role);
        UserModel userModel = userMapper.toModel(user);
        return new ResponseEntity<UserModel>(userModel, new HttpHeaders(), HttpStatus.OK);
    }
}
