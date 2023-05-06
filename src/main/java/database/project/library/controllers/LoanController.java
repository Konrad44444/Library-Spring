package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import database.project.library.commands.LoanCommand;
import database.project.library.services.LoanService;

@Controller
public class LoanController {
    private static final String SHOW_LOANS_MAP = "/showloans";
    private static final String SHOW_LOAN_PATH = "/user/loans";
    private static final String LOANS = "loans";
    private static final String MESSAGE = "message";
    private static final String NO_LOANS = "Jeszcze nie masz żadnych wypożyczeń.";
    private static final String ALL_LOANS_MAP = "/all_loans";
    private static final String ALL_LOANS_PATH = "/librarian/loans";

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    @GetMapping(SHOW_LOANS_MAP)
    public String showLoans(Model model) {

        List<LoanCommand> loans = loanService.getAllCurrentUserLoans();

        if(loans.isEmpty())
            model.addAttribute(MESSAGE, NO_LOANS);

        model.addAttribute(LOANS, loans);

        return SHOW_LOAN_PATH;
    }

    @GetMapping(ALL_LOANS_MAP)
    public String showAllLoans(Model model) {

        model.addAttribute(LOANS, loanService.getAllLoans());

        return ALL_LOANS_PATH;
    }
}
