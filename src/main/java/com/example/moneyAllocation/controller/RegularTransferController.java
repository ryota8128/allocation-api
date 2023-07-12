package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.RegularTransferService;
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
@RequestMapping("/api/regular")
@CrossOrigin(origins = {"http://localhost:3000"})
public class RegularTransferController {
    private final RegularTransferService service;

    public RegularTransferController(RegularTransferService service) {
        this.service = service;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegularTransfer> find(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setUserId(loginUserDetails.getLoginUser().id());
        return this.service.find(selector);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public RegularTransfer findOne(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestParam Long id) {
        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setId(id);
        selector.setUserId(loginUserDetails.getLoginUser().id());
        return service.findOne(selector);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody RegularTransfer regularTransfer) {
        this.service.add(regularTransfer);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody RegularTransfer regularTransfer) {
        service.set(regularTransfer);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestParam("id") Long deleteId) {
        service.delete(deleteId);
    }


}
