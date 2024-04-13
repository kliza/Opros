package ru.mai.opros.mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mai.opros.dto.PollAnalytic;
import ru.mai.opros.dto.PollDto;
import ru.mai.opros.entity.Poll;
import ru.mai.opros.service.PollService;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PollsV2Controller {
    private final PollService pollService;
    private final ObjectMapper objectMapper;

    @PostMapping("/polls/{id}")
    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
    public String savePoll(@PathVariable UUID id,
                           @ModelAttribute("pollJson") String pollJson,
                           Authentication authentication,
                           Model model) throws JsonProcessingException {
        PollDto updatedPoll = pollService.updatePoll(
                id,
                objectMapper.readValue(pollJson, PollDto.class),
                authentication);
        model.addAttribute("poll", updatedPoll);
        model.addAttribute("username", authentication.getName());

        return "editPollV2";
    }

    @GetMapping("/polls/{id}/edit")
    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
    public String getPoll(@PathVariable UUID id,
                          Model model,
                          Authentication authentication) {
        PollDto poll = pollService.getPoll(id);
        model.addAttribute("poll", poll);
        model.addAttribute("username", authentication.getName());

        return "editPollV2";
    }

    @PostMapping("/polls")
    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
    public String createPoll(Model model,
                             Authentication authentication) {
        Poll newPoll = pollService.createNewPoll();
        model.addAttribute("poll", newPoll);
        model.addAttribute("username", authentication.getName());

        return "redirect:/polls";
    }

    @PostMapping("/polls/{id}/delete")
    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
    public String deletePoll(@PathVariable UUID id,
                             Model model,
                             Authentication authentication) {
        pollService.deletePoll(id);
        model.addAttribute("username", authentication.getName());

        return "redirect:/polls";
    }

    @GetMapping("/polls/{id}/analytic")
    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
    public String getAnalytic(@PathVariable UUID id,
                              Model model,
                              Authentication authentication) {
        PollAnalytic analytic = pollService.getPollAnalytic(id);
        model.addAttribute("analytic", analytic);
        model.addAttribute("username", authentication.getName());

        return "pollAnalytic";
    }
}
