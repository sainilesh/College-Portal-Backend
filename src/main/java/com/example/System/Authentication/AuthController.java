package com.example.System.Authentication;


import com.example.System.DTO.Security.*;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final GoogleAccountService googleAccountService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginRequestDto, response));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequestDto) {
        authService.forgotPassword(forgotPasswordRequestDto.getEmail());
        return ResponseEntity.ok("Sent code to email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequestDto) {
        authService.resetPassword(resetPasswordRequestDto);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refresh_token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new EntityNotFoundException("Refresh token not found"));

        LoginResponseDto loginResponseDto = authService.validateRefreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDto);

    }

    @GetMapping("/connect/google")
    public void connectGoogle(HttpServletRequest request,
                              HttpServletResponse response,
                              @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long userId = user.getStudent().getId();

        request.getSession().setAttribute("userId", 2L);

        response.sendRedirect("/oauth2/authorization/google");
    }

}
