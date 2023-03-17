package fa.training.backend.controller;

import fa.training.backend.entities.User;
import fa.training.backend.exception.DuppicatedUserInfoException;
import fa.training.backend.helpers.JwtProvider;
import fa.training.backend.mapper.UserMapper;
import fa.training.backend.mapper.UserRegisterMapper;
import fa.training.backend.model.LoginRequestModel;
import fa.training.backend.model.LoginResponseModel;
import fa.training.backend.model.RegisterRequestModel;
import fa.training.backend.model.UserModel;
import fa.training.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
public class AuthenticateController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRegisterMapper userRegisterMapper;

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is welcome page!");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("userInfo", loginedUser.getUsername());

//		return "adminPage";
		return  loginedUser.getUsername();
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResponseModel login(@Valid @RequestBody LoginRequestModel loginRequestModel) {

		try{
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequestModel.getEmail(),
							loginRequestModel.getPassword()
					)
			);
			log.error(authentication.toString());
			log.error(loginRequestModel.toString());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String accessToken = JwtProvider.generateAccessToken((fa.training.backend.entities.User) authentication.getPrincipal());
			String refreshToken = JwtProvider.generateRefreshToken((fa.training.backend.entities.User) authentication.getPrincipal());
			return new LoginResponseModel(accessToken, refreshToken);
		}catch (Exception ex) {
			ex.printStackTrace();
		}

		return new LoginResponseModel();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(Model model) {
		return "13";
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity registerNewUser(@RequestBody @Valid RegisterRequestModel registerRequestModel) throws Exception {
		List<Integer> duplicatedEmailOrPhoneUserId = userService
														.checkExistUserEmailorPhone(
																registerRequestModel.email,
																registerRequestModel.phone
														);
		if(duplicatedEmailOrPhoneUserId.isEmpty()){
			User mappedUser = userRegisterMapper.toEntity(registerRequestModel);
			mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
			User createdUser = userService.createUser(mappedUser);
			LoginResponseModel loginResponseModel = login(new LoginRequestModel(createdUser.email, registerRequestModel.password));
			return new ResponseEntity<LoginResponseModel>(loginResponseModel, new HttpHeaders(), HttpStatus.OK);
		}else{
			return new ResponseEntity<>("Email or phone are existed", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/user-info/{id}", method = RequestMethod.GET)
	public String userInfo(@PathVariable(value = "id") int userId) {


//		return "userInfoPage";
		return "userName: " ;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			loginedUser.getUsername();

			model.addAttribute("userInfo");

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}
}
