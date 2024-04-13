package ru.mai.opros.mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mai.opros.dto.PollDto;
import ru.mai.opros.dto.RespondentDto;
import ru.mai.opros.service.PollService;
import ru.mai.opros.service.RespondentService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RespondentController {
    private final PollService pollService;
    private final RespondentService respondentService;
    private final ObjectMapper objectMapper;

    @GetMapping("/polls/{id}/respondent")
    public String getPollForRespondent(@PathVariable UUID id,
                                       Model model) {
        PollDto poll = pollService.getPoll(id);
        model.addAttribute("poll", poll);
        model.addAttribute("respondent", new RespondentDto());

        return "pollRespondent";
    }

    @PostMapping("/polls/{id}/respondent/answers")
    public String saveRespondentAnswers(@PathVariable UUID id,
                                        @ModelAttribute("respondent") String respondent,
                                        Model model) throws JsonProcessingException {
        RespondentDto respondentDto = respondentService.saveAnswers(id, objectMapper.readValue(respondent, RespondentDto.class));
        PollDto poll = pollService.getPoll(id);

        model.addAttribute("poll", poll);
        model.addAttribute("respondent", respondentDto);

        return "pollRespondent";
    }
}
