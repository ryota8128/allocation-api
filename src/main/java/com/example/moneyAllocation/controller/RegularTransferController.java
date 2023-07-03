package com.example.moneyAllocation.controller;


import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.service.RegularTransferService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<RegularTransfer> get(@RequestParam(required = false) Long userId) {
        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setUserId(userId);
        return this.service.find(selector);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody RegularTransfer regularTransfer) {
        this.service.add(regularTransfer);
    }


}
