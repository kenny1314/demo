package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.BankAccountDAO;
import com.psu.kurs.demo.model.BankAccountInfo;
import com.psu.kurs.demo.entity.BankTransactionException;
import com.psu.kurs.demo.form.SendMoneyForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ControllerName {

    private static Logger logger = LoggerFactory.getLogger(ControllerName.class);

    @Autowired
    private BankAccountDAO bankAccountDAO;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/page")
    public String page(Model model) {
        return "page2";
    }

    //получить все list всех аккаунтов
    @GetMapping("/1")
    public String showBankAccounts(Model model) {
        List<BankAccountInfo> list = bankAccountDAO.listBankAccountInfo();
        model.addAttribute("accountInfos", list);
        return "accountsPage";
    }

    //передать деньги от первого второму
    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public String viewSendMoneyPage(Model model) {
        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);
        model.addAttribute("sendMoneyForm", form);
        return "sendMoneyPage";
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {
        System.out.println("Send Money::" + sendMoneyForm.getAmount());
        try {
            bankAccountDAO.sendMoney(sendMoneyForm.getFromAccountId(),
                    sendMoneyForm.getToAccountId(),
                    sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/sendMoneyPage";
        }
        return "redirect:/";
    }


}
