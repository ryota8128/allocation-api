package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.TemplateTransferService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template")
@CrossOrigin(origins = {"http://localhost:3000", "https://money-allocation.vercel.app"})
@AllArgsConstructor
public class TemplateTransferController {
  private final TemplateTransferService service;

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<TemplateTransfer> find(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
    return this.service.find(loginUserDetails.getLoginUser().id()).getList();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public TemplateTransfer findOne(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails, @PathVariable Long id) {
    return service.findOne(id, loginUserDetails.getLoginUser().id());
  }
}
