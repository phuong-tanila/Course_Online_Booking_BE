package fa.training.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fa.training.backend.entities.User;
import fa.training.backend.helpers.jwt.JwtProvider;
import fa.training.backend.mapper.UserMapper;
import fa.training.backend.mapper.UserRegisterMapper;
import fa.training.backend.model.LoginRequestModel;
import fa.training.backend.model.TokenAuthModel;
import fa.training.backend.model.RegisterRequestModel;
import fa.training.backend.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@RestController
@Slf4j
@RequestMapping("/auth")
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


    @PostMapping("/login/google")
    public ResponseEntity<TokenAuthModel> loginByGoogle(@Valid @RequestBody LoginRequestModel loginRequestModel, HttpServletRequest request) {
        try {

            UserDetails user = userService.loadUserByUsername(loginRequestModel.email);
            if (user == null) {
                throw new Exception();
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println((fa.training.backend.entities.User) authentication.getPrincipal());
            String accessToken = JwtProvider.generateAccessToken((fa.training.backend.entities.User) authentication.getPrincipal());
            String refreshToken = JwtProvider.generateRefreshToken((fa.training.backend.entities.User) authentication.getPrincipal());
            return new ResponseEntity(new TokenAuthModel(accessToken, refreshToken), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenAuthModel> refreshNewToken(@Valid @RequestBody TokenAuthModel tokenAuthModel) throws JsonProcessingException {
        String refreshToken = tokenAuthModel.getRefreshToken();
        String accessToken = tokenAuthModel.getAccessToken();
        try {
            JwtProvider.validateAccessToken(accessToken);
            JwtProvider.validateRefreshToken(refreshToken);

        } catch (Exception ex) {
            String userEmailInRefreshToken = JwtProvider.getUserEmailFromJWT(refreshToken);
            System.out.println(userEmailInRefreshToken);
            System.out.println(JwtProvider.getUserEmailFromJWT(accessToken));
            if (userEmailInRefreshToken.equals(JwtProvider.getUserEmailFromJWT(accessToken))) {
                User user = (User) userService.loadUserByUsername(userEmailInRefreshToken);
                tokenAuthModel.setAccessToken(JwtProvider.generateAccessToken(user));
                System.out.println(tokenAuthModel);
                return new ResponseEntity<TokenAuthModel>(tokenAuthModel, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TokenAuthModel login(@Valid @RequestBody LoginRequestModel loginRequestModel) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestModel.getEmail(),
                            loginRequestModel.getPassword()
                    )
            );
            log.error(authentication.toString());
            log.error(loginRequestModel.toString());
//			SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = JwtProvider.generateAccessToken((fa.training.backend.entities.User) authentication.getPrincipal());
            String refreshToken = JwtProvider.generateRefreshToken((fa.training.backend.entities.User) authentication.getPrincipal());
            return new TokenAuthModel(accessToken, refreshToken);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new TokenAuthModel();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(Model model) {
        return "13";
    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody @Valid RegisterRequestModel registerRequestModel) throws Exception {
        List<Integer> duplicatedEmailOrPhoneUserId = userService
                .checkExistUserEmailorPhone(registerRequestModel.email,
                        registerRequestModel.phone);
        if (duplicatedEmailOrPhoneUserId.isEmpty()) {
            User mappedUser = userRegisterMapper.toEntity(registerRequestModel);
            mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
            mappedUser.setRole("US");
            mappedUser.setAvatar("https://www.caribbeangamezone.com/wp-content/uploads/2018/03/avatar-placeholder.png");
            User createdUser = userService.createUser(mappedUser);
            TokenAuthModel tokenAuthModel = login(new LoginRequestModel(createdUser.email, registerRequestModel.password));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Email or phone number already exists", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user-info/{id}", method = RequestMethod.GET)
    public String userInfo(@PathVariable(value = "id") int userId) {

//		return "userInfoPage";
        return "userName: ";
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

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("userInfo", loginedUser.getUsername());

//		return "adminPage";
        return loginedUser.getUsername();
    }
}
