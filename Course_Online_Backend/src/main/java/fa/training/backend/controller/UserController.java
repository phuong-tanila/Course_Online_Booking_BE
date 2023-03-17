package fa.training.backend.controller;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.User;
import fa.training.backend.model.UserModel;
import fa.training.backend.services.CourseService;
import fa.training.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class UserController {
    @Autowired
    private UserService userService;
	@Autowired
	public CourseService courseService;
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
}
