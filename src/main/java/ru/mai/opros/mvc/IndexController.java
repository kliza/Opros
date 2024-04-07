package ru.mai.opros.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.service.PollService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PollService pollService;

    @GetMapping({"/", "/polls"})
    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
    public String index(Model model, Authentication authentication) {
        List<Poll> polls = pollService.getAll(authentication);

        model.addAttribute("username", authentication.getName());
        model.addAttribute("polls", polls);

        return "index";
    }

}
