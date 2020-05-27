package ru.otus.svdovin.employmenthistory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;
import ru.otus.svdovin.employmenthistory.dto.CompanyDto;
import ru.otus.svdovin.employmenthistory.dto.EmployeeDto;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;
import ru.otus.svdovin.employmenthistory.service.AuthUserProvider;
import ru.otus.svdovin.employmenthistory.service.CompanyProvider;
import ru.otus.svdovin.employmenthistory.service.EmployeeProvider;
import ru.otus.svdovin.employmenthistory.service.RecordProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class PageController {

    private final AuthUserProvider authUserProvider;
    private final CompanyProvider companyProvider;
    private final EmployeeProvider employeeProvider;
    private final RecordProvider recordProvider;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/staff")
    public String staffPage() {
        return "staff";
    }

    @GetMapping("/employeeList")
    public String employeeListPage(Model model) {
        return "employeeList";
    }

    @GetMapping("/employeeEdit")
    public String employeeEditPage() {
        return "employeeEdit";
    }

    @GetMapping("/employeeDelete")
    public String employeeDeletePage() {
        return "employeeDelete";
    }

    @GetMapping("/employeeAdd")
    public String employeeAddPage() {
        return "employeeAdd";
    }

    @GetMapping("/empBookView")
    public String empBookViewPage() {
        return "empBookView";
    }

    @GetMapping("/empBookEdit")
    public String empBookEditPage() {
        return "empBookEdit";
    }

    @GetMapping("/empBookAdd")
    public String empBookAddPage() {
        return "empBookAdd";
    }

    @GetMapping("/empBookDelete")
    public String empBookDeletePage() {
        return "empBookDelete";
    }

    @GetMapping("/companyView")
    public String companyViewPage() {
        return "companyView";
    }

    @GetMapping("/companyEdit")
    public String companyEditPage() {
        return "companyEdit";
    }

    @GetMapping("/client")
    public String clientPage(Authentication authentication) {
        AuthUserDto user = authUserProvider.getAuthUserByLogin(authentication.getName());
        return "redirect:clientView?employeeId=" + user.getEmployeeId();
    }

    @GetMapping("/clientView")
    public String clientViewPage() {
        return "clientView";
    }

    @GetMapping("/bookView")
    public String bookViewPage() {
        return "bookView";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/roleList")
    public String roleListPage() {
        return "roleList";
    }

    @GetMapping("/userList")
    public String userListPage() {
        return "userList";
    }

    @GetMapping("/userAdd")
    public String userAddPage() {
        return "userAdd";
    }

    @GetMapping("/userEdit")
    public String userEditPage() {
        return "userEdit";
    }

    @GetMapping("/userDelete")
    public String userDeletePage() {
        return "userDelete";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/employeeBook/{employeeId}")
    public ModelAndView employeeBookPage(@PathVariable("employeeId") Long employeeId) {
        Map<String, Object> model = new HashMap<>();

        CompanyDto company = companyProvider.getCompany(1L);
        model.put("company", company);

        EmployeeDto employee = employeeProvider.getEmployee(employeeId);
        model.put("employee", employee);

        List<RecordDto> records = recordProvider.getRecordsByEmployeeId(employeeId);
        model.put("records", records);

        return new ModelAndView(new EmployeeBookView(), model);
    }

}
