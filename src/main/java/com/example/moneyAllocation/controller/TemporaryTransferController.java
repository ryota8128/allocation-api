package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.service.TemporaryTransferService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/temporary")
@CrossOrigin(origins = {"http://localhost:3000"})
public class TemporaryTransferController {
    private final TemporaryTransferService service;

    public TemporaryTransferController(TemporaryTransferService service) {
        this.service = service;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TemporaryTransfer> find(@RequestParam(required = false) Long userId) {
        TemporaryTransferSelector selector = new TemporaryTransferSelector();
        selector.setUserId(userId);
        return service.find(selector);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public TemporaryTransfer findOne(@RequestParam Long id) {
        return service.findOne(id);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody TemporaryTransfer temporaryTransfer) {
        service.add(temporaryTransfer);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void set(@RequestBody TemporaryTransfer temporaryTransfer) {
        service.set(temporaryTransfer);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
