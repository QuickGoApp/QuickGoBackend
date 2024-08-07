package com.QuickGo.backend.Util;

import com.QuickGo.backend.models.User;
import com.QuickGo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class IdGenerationUtil {

    final UserRepository userRepository;


    public String userCodeGenerator(){
        Optional<User> lastRow = userRepository.findTopByOrderByIdDesc();
        return lastRow.map(user -> {
            String lastRowCode = user.getUserCode();
            int numericPart = Integer.parseInt(lastRowCode.substring(1)) + 1;
            return "U" + String.format("%04d", numericPart);
        }).orElse("U0001");
    }



}
