/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jspcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Ponomarev
 */
@Controller
public class MainController {

    @RequestMapping(method = RequestMethod.GET, value = "/offlinehistory")
    public ModelAndView OfflineHistory() {
        ModelAndView mav = new ModelAndView("main/offlinehistory");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public ModelAndView Index() {
        ModelAndView mav = new ModelAndView("main/index");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView("autorization/signin");
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
//		model.setViewName("login");

        return model;

    }
}
