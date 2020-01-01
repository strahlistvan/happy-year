package hu.happyyear.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import hu.happyyear.service.DateService;
import hu.happyyear.service.NumberTheoryService;

@Controller
public class MainController {

    @Autowired
    DateService dateService;
    
    @Autowired
    NumberTheoryService numService;
    
    private Model getModelByYear(Model model, Integer year) {
        Boolean isHappyYear = numService.isHappyNumber(year);
        model.addAttribute("year", year);
        model.addAttribute("days_until_year", dateService.daysUntilYear(year));
        model.addAttribute("is_happy_year", isHappyYear);
        model.addAttribute("is_lucky_year", numService.isLuckyNumber(year));
        model.addAttribute("prev_lucky", numService.getPrevLuckyNumber(year));
        model.addAttribute("next_lucky", numService.getNextLuckyNumber(year));
        model.addAttribute("chain", numService.getChain(year));
        Integer biggestHappyDivider =  numService.getBiggestHappyDivider(year);
        model.addAttribute("biggest_happy_divider", biggestHappyDivider);
        model.addAttribute("optimist_chain", numService.getChain(biggestHappyDivider));
        model.addAttribute("tense", dateService.getYearTense(year));
        return model;
    }

    @GetMapping("/")
    public String mainPage(Model model, @RequestParam(name="year", required=false) Integer year) {
        Integer nextYear = year == null ? dateService.getNextYear() : year;
        model = getModelByYear(model, nextYear);
        numService.getLuckyNumbers(2020);
        return "main";
    }

    @GetMapping("/year/{year}")
    public String otherYearPage(@PathVariable(value="year") Integer year, Model model) {
        model = getModelByYear(model, year);
        return "main";
    }
    
    @GetMapping("/info")
    public String infoPage(Model model) {
        Integer nextYear =  dateService.getNextYear();
        model.addAttribute("year", nextYear);
        return "info";
    }
}
