package com.example.moneyAllocation.controller;

import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TransferSelector;
import com.example.moneyAllocation.security.LoginUserDetails;
import com.example.moneyAllocation.service.TemporaryTransferService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/temporary")
@CrossOrigin(origins = {"http://localhost:3000", "https://money-allocation.vercel.app"})
public class TemporaryTransferController {
  private final TemporaryTransferService service;

  public TemporaryTransferController(TemporaryTransferService service) {
    this.service = service;
  }

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<TemporaryTransfer> find(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestParam Long transferId) {
    TransferSelector selector =
        TransferSelector.withTransferId(transferId, loginUserDetails.getLoginUser().id());
    return service.find(selector);
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public TemporaryTransfer findOne(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestParam Long id) {
    TransferSelector selector =
        TransferSelector.withId(id, loginUserDetails.getLoginUser().id());
    return service.findOne(selector);
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public void add(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails,
      @RequestBody TemporaryTransfer temporaryTransfer) {
    temporaryTransfer.setUserId(loginUserDetails.getLoginUser().id());
    service.add(temporaryTransfer);
  }

  @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public void set(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails,
      @RequestBody TemporaryTransfer temporaryTransfer) {
    temporaryTransfer.setUserId(loginUserDetails.getLoginUser().id());
    service.set(temporaryTransfer);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void delete(
      @AuthenticationPrincipal LoginUserDetails loginUserDetails, @PathVariable Long id) {
    TransferSelector selector =
        TransferSelector.withId(id, loginUserDetails.getLoginUser().id());
    service.delete(selector);
  }
}
