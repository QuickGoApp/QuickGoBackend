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


//    public String userCodeGenerator(){
//        Optional<User> lastRow = userRepository.findTopByOrderByIdDesc();
//        return lastRow.map(user -> {
//            String lastRowCode = user.getUserCode();
//            int numericPart = Integer.parseInt(lastRowCode.substring(1)) + 1;
//            return "U" + String.format("%04d", numericPart);
//        }).orElse("U0001");
//    }

    public String userCodeGenerator() {
        Optional<User> lastRow = userRepository.findTopByOrderByIdDesc();

        return lastRow.map(user -> {
            String lastRowCode = user.getUserCode();
            // Check if lastRowCode is null or not formatted properly
            if (lastRowCode != null && lastRowCode.length() > 1) {
                try {
                    int numericPart = Integer.parseInt(lastRowCode.substring(1)) + 1;
                    return "U" + String.format("%04d", numericPart);
                } catch (NumberFormatException e) {
                    // Handle case where the numeric part is not a valid number
                    return "U0001"; // Return a default code in case of error
                }
            } else {
                return "U0001"; // Return default if the last code is not properly formatted
            }
        }).orElse("U0001"); // Return the default code if no user exists
    }




}
