package io.internhub.application.controllers;
import io.internhub.application.models.EmployerProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import io.internhub.application.repositories.EmployerProfiles;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobsController {

    private EmployerProfiles employerProfiles;
    private Users users;
  
    public JobsController(EmployerProfiles employerProfiles, Users users) {
        this.employerProfiles = employerProfiles;
        this.users = users;
    }

    @GetMapping("employers/add-job")
    public String getAddJobPage(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("job", new Job());
        return "employers/add-job";
    }

    @PostMapping("employers/add-job")
    public String postAddJobPage(@ModelAttribute Job job) {
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        EmployerProfile employerProfile = user.getEmployerProfile();
        employerProfile.addJob(job);
        job.setEmployerProfile(employerProfile);
        employerProfiles.save(employerProfile);
        return "redirect:/employers/add-job";
    }

}  // JobsController class