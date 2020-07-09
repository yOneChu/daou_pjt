package org.kyhslam.controller;

import org.kyhslam.domain.Company;
import org.kyhslam.persistence.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DaouController {

    Logger logger = LoggerFactory.getLogger(DaouController.class);

    @Autowired
    CompanyRepository comrepo;

    @RequestMapping("/index")
    public String hello(Model model){

        Company com = comrepo.findById(1l).get();

        logger.info("name == " + com.getName());

        model.addAttribute("name", "kyhslam");
        model.addAttribute("com", com);

        return "index";
    }

}
