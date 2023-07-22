package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.Transfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.TransferService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@CrossOrigin(origins = {"http://localhost:3000", "https://money-allocation.vercel.app"})
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transfer> find(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        return this.transferService.find(loginUserDetails.getLoginUser().id());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Transfer findOne(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestParam Long transferId) {
        TransferSelector selector = new TransferSelector();
        selector.setId(transferId);
        selector.setUserId(loginUserDetails.getLoginUser().id());
        return transferService.findOne(selector);
    }


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Transfer transfer) {
        transfer.setUserId(loginUserDetails.getLoginUser().id());
        transferService.add(transfer);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void set(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Transfer transfer) {
        transfer.setUserId(loginUserDetails.getLoginUser().id());
        transferService.set(transfer);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestParam Long transferId) {
        TransferSelector selector = new TransferSelector();
        selector.setUserId(loginUserDetails.getLoginUser().id());
        selector.setId(transferId);
        transferService.delete(selector);
    }

}
