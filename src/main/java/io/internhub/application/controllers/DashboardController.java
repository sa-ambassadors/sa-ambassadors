package io.internhub.application.controllers;

import io.internhub.application.models.*;
import io.internhub.application.repositories.EmployerProfiles;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private Jobs jobs;
    private Users users;
    private InternRepository interns;
    private EmployerProfiles employers;

    public DashboardController (Users users, Jobs jobs, InternRepository interns, EmployerProfiles employers) {
        this.users = users;
        this.jobs = jobs;
        this.employers = employers;
        this.interns = interns;
    }

    @GetMapping("dashboard")
    public String getDashboardPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserWithRoles)) {
            return "redirect:/login";
        }

        UserWithRoles userWithRoles = (UserWithRoles) principal;
        User user = users.findByUsername(userWithRoles.getUsername());


//        EMPLOYER DASHBOARD
        if (user.getEmployerProfile()!= null) {
            EmployerProfile employerProfile = user.getEmployerProfile();
            List<Job> jobs = employerProfile.getJobs();
            int totalApps = 0;
            List<InternProfile> profiles = new ArrayList<>();
            for (Job job: jobs
                 ) {
                totalApps += job.getInternProfiles().size();
                profiles.addAll(job.getInternProfiles());
            }
            ArrayList<Long> profileIds = new ArrayList<>();
            for(InternProfile profile: profiles){
                profileIds.add(profile.getId());
            }
            List<Long> uniqueProfileIds = profileIds.stream().distinct().collect(Collectors.toList());
            List<InternProfile> uniqueProfiles = new ArrayList<>();
            for(Long profileId: uniqueProfileIds){
                uniqueProfiles.add(interns.findOne(profileId));
            }


            int numOfJobs;
            if(jobs.size() == 0){
                numOfJobs = 0;
            }else{
                numOfJobs = jobs.size();
            }
            model.addAttribute("internProfiles",uniqueProfiles);
            model.addAttribute("jobs", jobs);
            model.addAttribute("numOfJobs", numOfJobs);
            model.addAttribute("totalApps", totalApps);
        }

//        INTERN DASHBOARD
       if(user.getInternProfile() != null) {
           InternProfile userProfile = user.getInternProfile();
           //total number of jobs intern has applied for
           int appliedTotal = userProfile.getAppliedJobs().size();
           int totalJobs = 0;
           int totalRelevantJobs = 0;
           boolean isComplete;
           Iterable<Job> allJobs;
           List<Job> appliedList = null;
           if (!userProfile.isComplete()) {
                isComplete = false;
               String message = "You must complete your profile before applying for positions";
               if (jobs.findAll() == null) {
                   return "There are no current job postings";
               } else {
                   allJobs = jobs.findAll();
                   totalJobs = ((List<Job>) allJobs).size();
               }

           } else {

               appliedList = userProfile.getAppliedJobs();
               appliedTotal = appliedList.size();
               allJobs = jobs.findAll();
               totalJobs = ((List<Job>) allJobs).size();
               isComplete = true;
           }

           model.addAttribute("appliedJobs", appliedList);
           model.addAttribute("allJobs", allJobs);
           model.addAttribute("isComplete", isComplete);
           model.addAttribute("userProfile", userProfile);
           model.addAttribute("totalRelevantJobs", totalRelevantJobs);
           model.addAttribute("appliedTotal", appliedTotal);
           model.addAttribute("totalJobs", totalJobs);

       }


//       ADMIN DASHBOARD
        if(user.getRole().getId() == 5){
            int approvedInternCount = 0;
            int totalInternCount = 0;
            int hiredCount = 0;

            int totalEmployerCount = 0;
            int approvedEmployerCount = 0;
            int totalJobs;

            List<EmployerProfile> pendingEmployers = new ArrayList<>();
            List<InternProfile> pendingInterns = new ArrayList<>();

            Iterable<InternProfile> allInternProfiles = interns.findAll();

//            COUNTS FOR DASHBOARD
            for (InternProfile intern: allInternProfiles
                 ) {
                totalInternCount += 1;
                if(intern.isApproved()){
                    approvedInternCount += 1;
                }
                if(intern.isHired()){
                    hiredCount += 1;
                }
//               PROFILE IS COMPLETE BUT NOT APPROVED SEND TO ADMIN
                if (intern.isComplete() && !intern.isApproved()){
                    pendingInterns.add(intern);
                }
            }
            Iterable<EmployerProfile> allEmployerProfiles = employers.findAll();
            for(EmployerProfile employer : allEmployerProfiles){
                totalEmployerCount += 1;
                if(employer.isApproved()){
                    approvedEmployerCount += 1;
                }else{
                    pendingEmployers.add(employer);
                }
            }
            Iterable<Job> allJobs = jobs.findAll();
            totalJobs = ((List<Job>) allJobs).size();

            model.addAttribute("pendingInterns",pendingInterns);
            model.addAttribute("pendingEmployers",pendingEmployers);
            model.addAttribute("totalInternCount",totalInternCount);
            model.addAttribute("approvedInternCount",approvedInternCount);
            model.addAttribute("hiredCount",hiredCount);
            model.addAttribute("totalEmployerCount",totalEmployerCount);
            model.addAttribute("approvedEmployerCount",approvedEmployerCount);
            model.addAttribute("totalJobs",totalJobs);


        }


        return "dashboard";
    }
}
