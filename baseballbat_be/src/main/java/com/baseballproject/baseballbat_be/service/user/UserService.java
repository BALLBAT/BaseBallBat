package com.baseballproject.baseballbat_be.service.user;

import com.baseballproject.baseballbat_be.model.dto.auth.UserReq;
import com.baseballproject.baseballbat_be.model.dto.auth.UserRes;
import com.baseballproject.baseballbat_be.model.entity.auth.User;
import com.baseballproject.baseballbat_be.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.baseballproject.baseballbat_be.service.user
 * fileName : UserService
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
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * 새로운 사용자를 등록합니다. 이메일 중복 여부를 확인합니다.
     * @param req 등록할 사용자 정보가 담긴 DTO
     * @return 등록된 사용자 정보가 담긴 DTO
     */
    @Transactional
    public UserRes registerUser(UserReq req) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(req.getEmail()) != null) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }
        User user = modelMapper.map(req, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserRes.class);
    }

    /**
     * 사용자명을 통해 사용자를 찾습니다.
     * @param username 찾을 사용자명
     * @return 사용자가 존재하면 해당 사용자 정보를 담은 DTO
     */
    public Optional<UserRes> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> modelMapper.map(u, UserRes.class));
    }

    /**
     * 이메일을 통해 사용자를 찾습니다.
     * @param email 찾을 이메일
     * @return 사용자가 존재하면 해당 사용자 정보를 담은 DTO
     */
    public Optional<UserRes> findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(u -> modelMapper.map(u, UserRes.class));
    }

    /**
     * 사용자 정보를 수정합니다.
     * @param userId 수정할 사용자의 ID
     * @param updatedUser 수정할 사용자 정보가 담긴 DTO
     * @return 수정된 사용자 정보가 담긴 DTO
     */
    @Transactional
    public UserRes updateUser(Long userId, UserReq updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        modelMapper.map(updatedUser, user);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserRes.class);
    }

    /**
     * 사용자를 ID로 삭제합니다.
     * @param userId 삭제할 사용자의 ID
     */
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}