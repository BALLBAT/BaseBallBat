package com.baseballproject.baseballbat_be.controller.auth;

import com.baseballproject.baseballbat_be.model.dto.auth.UserReq;
import com.baseballproject.baseballbat_be.model.dto.auth.UserRes;
import com.baseballproject.baseballbat_be.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * packageName : com.baseballproject.baseballbat_be.controller.auth
 * fileName : UserController
 * author : BALLBAT
 * date : 24. 11. 25.
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 25.         BALLBAT          최초 생성
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원 가입을 처리합니다.
     * @param userReq 등록할 사용자 정보가 담긴 DTO
     * @return 등록된 사용자 정보와 상태 코드
     */
    @PostMapping("/register")
    public ResponseEntity<UserRes> registerUser(@RequestBody UserReq userReq) {
        UserRes registeredUser = userService.registerUser(userReq);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    /**
     * 사용자명을 통해 사용자를 조회합니다.
     * @param username 조회할 사용자명
     * @return 사용자 정보와 상태 코드
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserRes> getUserByUsername(@PathVariable String username) {
        Optional<UserRes> user = userService.findUserByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 이메일을 통해 사용자를 조회합니다.
     * @param email 조회할 이메일
     * @return 사용자 정보와 상태 코드
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserRes> getUserByEmail(@PathVariable String email) {
        Optional<UserRes> user = userService.findUserByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 아이디 중복을 확인합니다.
     * @param username 확인할 사용자명
     * @return 중복 여부 상태 코드
     */
    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> checkUsernameAvailability(@PathVariable("username") String username) {
        boolean isAvailable = userService.findUserByUsername(username).isEmpty();
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }

    /**
     * 사용자 정보를 수정합니다.
     * @param userId 수정할 사용자의 ID
     * @param updatedUser 수정할 사용자 정보가 담긴 DTO
     * @return 수정된 사용자 정보와 상태 코드
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserRes> updateUser(@PathVariable Long userId, @RequestBody UserReq updatedUser) {
        UserRes updated = userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * 사용자를 삭제합니다.
     * @param userId 삭제할 사용자의 ID
     * @return 상태 코드
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
