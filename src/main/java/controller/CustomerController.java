package controller;

import model.Customer;
import model.CustomerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CustomerService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    public CustomerService customerService;

    @Autowired
    public Environment environment;

    @GetMapping("/customer")
    public ModelAndView showlistCustomer() {
        List<Customer> customerList = customerService.findAll();
        return new ModelAndView("/index", "customers", customerList);
    }

    @GetMapping("/customer/create")
    public ModelAndView createCustomer() {
        return new ModelAndView("/create", "customer", new CustomerForm());
    }

    @PostMapping(value = "/customer/create")
    public ModelAndView createCustomer(@ModelAttribute("customer") CustomerForm customerForm, Model model){
        MultipartFile file = customerForm.getImage();
        String image = file.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer(customerForm.getId(), customerForm.getName(), customerForm.getEmail(), customerForm.getAddress(), image);
        customerService.save(customer);
        model.addAttribute("success","Create customer successfully!");
        return showlistCustomer();
    }

    @GetMapping("/customer/{id}/view")
    public ModelAndView view(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        return new ModelAndView("/view", "customer", customer);
    }
}
