package kg.smarttailor.usersservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "JWT")
@Tag(name = "Demo Secured controller")
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping
    public String demoSecured() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Success";
    }
}
