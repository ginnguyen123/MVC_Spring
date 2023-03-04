package com.banking.controller;

import com.banking.model.*;
import com.banking.service.customer.ICustomerService;
import com.banking.service.deposit.IDepositService;
import com.banking.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IWithdrawService withdrawService;


    @GetMapping("")
    public String showListPage(Model model){
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers",customers);
        return "customer/list";
    }

    @GetMapping("create")
    public String create(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer",customer);

        return "customer/create";
    }
    @PostMapping("create")
    public String create(@ModelAttribute Customer customer, Model model,BindingResult bindingResult){
        if (customer.getFullName() == null){
            model.addAttribute("message", "Vui lòng nhập đầy đủ họ và tên.");
            model.addAttribute("message", "Thêm tài khoản mới không thành công");
            model.addAttribute("customer",customer);
        }else {
            customer.setId(null);
            customer.setBalance(BigDecimal.ZERO);
            customerService.save(customer);
            model.addAttribute("customer", new Customer());
            model.addAttribute("message", "Thêm tài khoản mới thành công");
        }
        return "customer/create";
    }

    @GetMapping("/edit/{customerId}")
    public String showEdit(Model model, @PathVariable Long customerId){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (!optionalCustomer.isPresent()){
            return "error/404";
        }
        model.addAttribute("customer", optionalCustomer.get());
        return "customer/edit";
    }

    @PostMapping("/edit/{customerId}")
    public String edit(@PathVariable Long customerId, @ModelAttribute Customer customer){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if(!optionalCustomer.isPresent()){
            return "error/404";
        }
        customer.setBalance(optionalCustomer.get().getBalance());
        customer.setId(optionalCustomer.get().getId());
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDeposit(Model model, @PathVariable Long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()){
            return "error/404";
        }
        model.addAttribute("customer", customerOptional.get());
        Deposit newDeposit = new Deposit();
        newDeposit.setCustomer(customerOptional.get());

        model.addAttribute("deposit", newDeposit);

        return "customer/deposit";
    }

    @PostMapping("/deposit/{customerId}")
    public String deposit(Model model, @PathVariable Long customerId,@Validated @ModelAttribute Deposit deposit, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            return "customer/deposit";
        }
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (!optionalCustomer.isPresent()){
            return "error/404";
        }
        BigDecimal transAmount = deposit.getTransactionAmount();
        Customer customer = optionalCustomer.get();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal newBalance = currentBalance.add(transAmount);
        customer.setBalance(newBalance);
        deposit.setCustomer(customer);

        customerService.deposit(deposit);

        deposit.setTransactionAmount(BigDecimal.ZERO);

        model.addAttribute("deposit",deposit);
        model.addAttribute("success",true);
        model.addAttribute("message", "Nạp tiền thành công");

        return "customer/deposit";
    }


    @GetMapping("/transfer/{customerId}")
    public String showTransfer(Model model, @PathVariable Long customerId){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (!optionalCustomer.isPresent()){
            return "error/404";
        }
        Customer sender = optionalCustomer.get();
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        model.addAttribute("transfer",transfer);
        List<Customer> recipients = customerService.findAllByIdNot(customerId);
        model.addAttribute("recipients",recipients);
        return "customer/transfer";
    }
    @PostMapping("/transfer/{customerId}")
    public String transfer(Model model, @PathVariable Long customerId, @ModelAttribute Transfer transfer){
        Optional<Customer> optionalSender = customerService.findById(customerId);
        Optional<Customer> optionalRecipient = customerService.findById(transfer.getRecipient().getId());


        if (transfer.getRecipient().getId() == -1){
            model.addAttribute("error",true);
            model.addAttribute("message", "Vui lòng chọn người nhận chuyển khoản.");
            model.addAttribute("tranfAmount", BigDecimal.ZERO);
            model.addAttribute("recipients", customerService.findAllByIdNot(customerId));
            return "customer/transfer";
        }
        if (!optionalRecipient.isPresent()){
            return "error/404";
        }

        if (!optionalSender.isPresent()){
            return "error/404";
        }
        System.out.println(transfer);
        Customer sender = optionalSender.get();
        transfer.setSender(sender);

        Customer recipient = optionalRecipient.get();
        transfer.setRecipient(recipient);

        BigDecimal transAmount = transfer.getTransAmount();
        Long fees = 10L;
        BigDecimal feesAmount = transAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
        BigDecimal totalTransAmount = transAmount.add(feesAmount);

        BigDecimal minAmount = BigDecimal.valueOf(10000L);
        BigDecimal maxAmount = BigDecimal.valueOf(1000000000L);
        if (transAmount.compareTo(minAmount)<0){
            model.addAttribute("error",true);
            model.addAttribute("message", "Số tiền chuyển khoản tối thiểu 10.000 VNĐ");
        } else if (transAmount.compareTo(maxAmount)>0) {
            model.addAttribute("error",true);
            model.addAttribute("message", "Số tiền chuyển khoản tối đa 1.000.000.000 VNĐ");
        }else {
            if (sender.getBalance().compareTo(totalTransAmount)<0){
                model.addAttribute("error",true);
                model.addAttribute("message", "Số dư người chuyển không đủ để thực hiện giao dịch.");
                return "customer/transfer";
            }
            if (sender.getId().equals(recipient.getId())){
                model.addAttribute("error",true);
                model.addAttribute("message", "Tài khoản người nhận không hợp lệ");
                return "customer/transfer";
            }

            BigDecimal newSenderBalance = sender.getBalance().subtract(totalTransAmount);
            sender.setBalance(newSenderBalance);

            BigDecimal newRecipientBalance = recipient.getBalance().add(transAmount);
            recipient.setBalance(newRecipientBalance);
            //Transfer(Long id, Customer sender, Customer recipient, Long fees, BigDecimal feesAmount, BigDecimal transAmount)
            transfer.setRecipient(recipient);
            transfer.setSender(sender);
            transfer.setFees(fees);
            transfer.setFeesAmount(feesAmount);
            transfer.setTransAmount(transAmount);

            customerService.transfer(transfer);

            model.addAttribute("success",true);
            model.addAttribute("message", "Giao dịch chuyển khoản thành công.");
            Transfer newTransfer = new Transfer();
            newTransfer.setSender(sender);
            newTransfer.setTransAmount(BigDecimal.ZERO);
            model.addAttribute("transfer", newTransfer);

        }
        return "customer/transfer";
    }

    @GetMapping("/suspend/{customerId}")
    public String showSuspend(Model model, @PathVariable Long customerId){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if(!optionalCustomer.isPresent()){
            return "error/404";
        }
        Customer customer = optionalCustomer.get();
        model.addAttribute("customer", customer);
        return "customer/suspend";
    }
    @PostMapping("/suspend/{customerId}")
    public String suspend(Model model, @PathVariable Long customerId, @ModelAttribute Customer customer){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if(!optionalCustomer.isPresent()){
            return "error/404";
        }

        if (customer.getFullName() == null && customer.getBalance().compareTo(BigDecimal.ZERO)<0){
            model.addAttribute("customer", customer);
            model.addAttribute("success", true);
            model.addAttribute("message", "Xóa tài khoản thành công");
        }
        Customer customerSuspend = optionalCustomer.get();
        customerService.delete(customerSuspend);
        Customer newCustomer = new Customer();
        model.addAttribute("customer", newCustomer);
        model.addAttribute("error", true);
        model.addAttribute("message", "Xóa tài khoản không thành công");
        return "customer/suspend";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdraw(Model model, @PathVariable Long customerId ){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (!optionalCustomer.isPresent()){
            return "error/404";
        }
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(optionalCustomer.get());
        model.addAttribute("withdraw",withdraw);

        return "customer/withdraw";
    }
    @PostMapping("/withdraw/{customerId}")
    public String withdraw(Model model, @PathVariable Long customerId,@ModelAttribute Withdraw withdraw, BindingResult bindingResult){
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (!optionalCustomer.isPresent()){
            return "error/404";
        }
        Customer customer = optionalCustomer.get();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transAmount = withdraw.getTransAmount();
        if (transAmount.compareTo(currentBalance)>0){
            model.addAttribute("error", true);
            model.addAttribute("message","Số tiền rút lớn hơn số dư đang tồn tại.");
        }else {
            customer.setBalance(currentBalance.subtract(transAmount));
            withdraw.setCustomer(customer);
            withdraw.setTransAmount(BigDecimal.ZERO);
            customerService.withdraw(withdraw);
            model.addAttribute("success", true);
            model.addAttribute("message","Rút tiền thành công.");
            model.addAttribute("withdraw",withdraw);
        }
        return "customer/withdraw";
    }
}
