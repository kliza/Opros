//package ru.mai.opros.mvc;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import ru.mai.opros.dto.PollAnalytic;
//import ru.mai.opros.dto.PollStat;
//import ru.mai.opros.entity.Poll;
//import ru.mai.opros.service.PollService;
//
//import java.util.List;
//import java.util.UUID;
//
//@Controller
//@RequiredArgsConstructor
//public class PollsController {
//    private final PollService pollService;
//
//    @GetMapping("/polls")
//    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
//    public String getPolls(Model model, Authentication authentication) {
//        List<Poll> polls = pollService.getAll(authentication);
//        model.addAttribute("polls", polls);
//
//        return "managerPolls";
//    }
//

//
//    @GetMapping("/polls/{id}/stat")
//    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
//    public String getPollStat(@PathVariable UUID id, Model model) {
//        PollStat stat = pollService.getStat(id);
//        model.addAttribute("stat", stat);
//
//        return "pollStat";
//    }
//
//    @GetMapping("/polls/{id}/analytic")
//    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
//    public String getPollAnalytic(@PathVariable UUID id, Model model) {
//        PollAnalytic analytic = pollService.getAnalytic(id);
//        model.addAttribute("analytic", analytic);
//
//        return "pollAnalytic";
//    }
//
////    @GetMapping("/polls/{id}/edit")
////    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
////    public String editPollAnalytic(@PathVariable UUID id, Model model) {
////        Poll poll = pollService.getPoll(id);
////
////        model.addAttribute("poll", poll);
////
////        return "editPoll";
////    }
//
//    @PostMapping("/polls/{id}/pages")
//    @PreAuthorize("hasRole('ROLE_POLL_MANAGER')")
//    public String addPage(@PathVariable UUID id) {
//        pollService.addPage(id);
//
//        return "redirect:/polls/%s/edit".formatted(id);
//    }
//

//}
