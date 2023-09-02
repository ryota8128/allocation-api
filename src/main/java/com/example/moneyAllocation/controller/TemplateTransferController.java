package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.TemplateTransfer;
import com.example.moneyAllocation.domain.TemplateTransferList;
import com.example.moneyAllocation.domain.dto.TemplateTransferDto;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.TemplateTransferService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template")
@CrossOrigin(origins = {"http://localhost:3000", "https://money-allocation.vercel.app"})
@AllArgsConstructor
public class TemplateTransferController {
  private final TemplateTransferService service;

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<TemplateTransferDto> find(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails) {

    TemplateTransferList templateTransferList =
        this.service.find(loginUserDetails.getLoginUser().id());
    return TemplateTransferDto.valueFrom(templateTransferList);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public TemplateTransferDto findOne(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails, @PathVariable Long id) {

    return TemplateTransferDto.valueOf(service.findOne(id, loginUserDetails.getLoginUser().id()));
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public TemplateTransferDto add(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails,
      @RequestBody TemplateTransferDto dto) {

    dto.setUserId(loginUserDetails.getLoginUser().id());
    return TemplateTransferDto.valueOf(service.insert(TemplateTransfer.from(dto)));
  }

  @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public void update(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails,
      @RequestBody TemplateTransferDto dto) {
    dto.setUserId(loginUserDetails.getLoginUser().id());
    service.update(TemplateTransfer.from(dto));
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void delete(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails, @PathVariable Long id) {
    service.delete(id, loginUserDetails.getLoginUser().id());
  }
}
