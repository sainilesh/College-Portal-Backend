package com.example.System.Authentication;


import com.example.System.DTO.Security.*;
import com.example.System.Entity.Student;
import com.example.System.Entity.Teacher;
import com.example.System.Entity.User;
import com.example.System.Enum.RoleType;
import com.example.System.Repository.StudentRepository;
import com.example.System.Repository.TeacherRepository;
import com.example.System.Repository.UserRepository;
import com.example.System.Email.EmailService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final EmailService emailService;
    private final RedisTemplate<String, Object> redisTemplate;

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        String refreshToken = createRefreshToken(user);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/auth");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);

//        Cookie jwtCookie = new Cookie("jwt_token", token);
//        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(false);
//        jwtCookie.setPath("/auth");
//        jwtCookie.setMaxAge(20 * 60);
//
//        response.addCookie(jwtCookie);
        response.addCookie(refreshCookie);

        return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(SignUpRequestDto dto) {

        Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        // create user
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(dto.getRole())
                .build();

        user = userRepository.save(user);

        if (dto.getRole() == RoleType.STUDENT) {

            Student student = Student.builder()
                    .name(dto.getName())
                    .user(user)
                    .build();

            studentRepository.save(student);

            user.setStudent(student);

        } else if (dto.getRole() == RoleType.TEACHER) {

            Teacher teacher = Teacher.builder()
                    .name(dto.getName())
                    .user(user)
                    .build();

            teacherRepository.save(teacher);

            user.setTeacher(teacher);

        } else {
            throw new IllegalArgumentException("Invalid role");
        }

        return userRepository.save(user);
    }

    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user = signUpInternal(signupRequestDto);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    public void forgotPassword(String email) {
        String code = generateCode();
        String key = "OTP: " + email;

        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(10));

        emailService.sendMail(email, "Reset Password","Your Reset Code is : "+code);

    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

        User user = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new
                IllegalArgumentException("Invalid email"));

        String key = "OTP: " + resetPasswordRequest.getEmail();
        String storedOtp = (String) redisTemplate.opsForValue().get(key);

        if(storedOtp == null) {
            throw new IllegalArgumentException("OTP is null");
        }

        if(!storedOtp.equals(resetPasswordRequest.getCode())){
            throw new IllegalArgumentException("OTP is invalid");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        redisTemplate.delete(key);

        userRepository.save(user);

    }

    private String generateCode() {

        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public String createRefreshToken(User user) {

        String key = UUID.randomUUID().toString();
        String value = String.valueOf(user.getId());

        redisTemplate.opsForValue().set(key, value, Duration.ofDays(10));

        return key;
    }

    public LoginResponseDto validateRefreshToken(String token) {

        String Id = (String) redisTemplate.opsForValue().get(token);
        Long userId = Long.valueOf(Id);

        if(Id == null) {
            throw new IllegalArgumentException("Invalid token or token expired");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new
                EntityNotFoundException("user not found"));

        String jwtToken = authUtil.generateAccessToken(user);

        return LoginResponseDto.builder()
                .jwt(jwtToken)
                .userId(user.getId())
                .build();
    }




}
