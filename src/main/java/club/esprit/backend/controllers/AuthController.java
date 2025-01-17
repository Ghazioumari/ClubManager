package club.esprit.backend.controllers;

import club.esprit.backend.dto.LoginRequest;
import club.esprit.backend.dto.LoginResponse;
import club.esprit.backend.dto.SignupRequest;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.IAuth;
import club.esprit.backend.services.jwt.UserServiceImpl;
import club.esprit.backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final IAuth iAuth;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl customerService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestParam("email") String email,
                                        @RequestParam("name") String name,
                                        @RequestParam("password") String password,
                                        @RequestParam("profileImage") MultipartFile profileImage) {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            }

            // Process the profile image and create a SignupRequest
            String filename = null;
            if (profileImage != null && !profileImage.isEmpty()) {
                String imagePath = "C:\\\\MAMP\\\\htdocs\\\\img\\\\"; // Update this path
                Path path = Paths.get(imagePath + profileImage.getOriginalFilename());
                Files.write(path, profileImage.getBytes());
                filename = profileImage.getOriginalFilename();
            }

            SignupRequest signupRequest = new SignupRequest();
            signupRequest.setEmail(email);
            signupRequest.setName(name);
            signupRequest.setPassword(password);
            signupRequest.setProfileImage(filename);

            User createdUser = iAuth.createUser(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Check if the user is active and retrieve role
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

            //if (!user.isActive()) {
              //  throw new DisabledException("User is not activated");
            //}

            // Generate JWT token
            UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            // Prepare the response with the user's role
            LoginResponse loginResponse = new LoginResponse(jwt, refreshToken, user.getRole().name());

            return ResponseEntity.ok(loginResponse);
        } catch (DisabledException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("User is not activated");
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }








}
