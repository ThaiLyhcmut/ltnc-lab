package vn.edu.hcmut.cse.adse.lab.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

import java.util.List;

@Controller
public class StudentWebController {

    private static final int PAGE_SIZE = 20;

    private final StudentService studentService;

    public StudentWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            Model model) {

        Sort sort = Sort.by(order.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(Math.max(page, 0), PAGE_SIZE, sort);

        Page<Student> studentPage;
        if (search != null && !search.isBlank()) {
            studentPage = studentService.search(search, pageable);
            model.addAttribute("search", search);
        } else {
            studentPage = studentService.getAll(pageable);
        }

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", studentPage.getNumber());
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        return "students";
    }

    @GetMapping("/students/{id}")
    public String studentDetail(@PathVariable String id, Model model) {
        return studentService.getById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    return "student-detail";
                })
                .orElse("redirect:/students");
    }

    @GetMapping("/students/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "student-form";
    }

    @PostMapping("/students")
    public String createStudent(@Valid @ModelAttribute Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "student-form";
        }
        try {
            studentService.create(student);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            model.addAttribute("isEdit", false);
            return "student-form";
        }
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        return studentService.getById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    model.addAttribute("isEdit", true);
                    return "student-form";
                })
                .orElse("redirect:/students");
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable String id, @Valid @ModelAttribute Student student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "student-form";
        }
        try {
            studentService.update(id, student);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            model.addAttribute("isEdit", true);
            return "student-form";
        }
        return "redirect:/students/" + id;
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable String id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    @PostMapping("/students/delete-many")
    public String deleteMany(@RequestParam List<String> ids) {
        studentService.deleteMany(ids);
        return "redirect:/students";
    }
}
