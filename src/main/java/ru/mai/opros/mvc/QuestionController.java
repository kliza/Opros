//package ru.mai.opros.mvc;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ru.mai.opros.entity.enums.AnswerType;
//import ru.mai.opros.service.AnswerService;
//import ru.mai.opros.service.PollService;
//import ru.mai.opros.service.QuestionService;
//
//import java.util.UUID;
//
//@Controller
//@RequiredArgsConstructor
//public class QuestionController {
//    private final QuestionService questionService;
//    private final PollService pollService;
//    private final AnswerService answerService;
//
//    @PostMapping("/pages/{pageId}/questions")
//    public String addQuestion(@PathVariable UUID pageId) {
//        questionService.create(pageId);
//        UUID pollId = pollService.getPollIdByPageId(pageId);
//
//        return "redirect:/polls/%s/edit".formatted(pollId);
//    }
//
//    @PostMapping("/questions/{id}/answer-params")
//    public String addAnswerParam(@PathVariable UUID id) {
//        answerService.createParams(id);
//        UUID pollId = questionService.getPollIdByQuestionId(id);
//
//        return "redirect:/polls/%s/edit".formatted(pollId);
//    }
//}
