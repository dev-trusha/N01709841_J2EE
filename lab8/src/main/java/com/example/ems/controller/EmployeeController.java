package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final EmployeeImpl employeeImpl;

    public EmployeeController(EmployeeImpl employeeImpl) {
        this.employeeImpl = employeeImpl;
    }

    @GetMapping("/")
    public String getAllEmployees(Model model) {
        model.addAttribute("employeeList", employeeImpl.getAllEmployees());
        return "index";
    }

    @GetMapping("/showAddEmployeeForm")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employee,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addEmployee";
        }
        employeeImpl.addEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Employee employee = employeeImpl.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") @Valid Employee employee,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateEmployee";
        }
        employeeImpl.updateEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeImpl.deleteEmployee(id);
        return "redirect:/";
    }
}
