package com.backend.bank.controller;

import com.backend.bank.dto.request.NetworksRequestDto;
import com.backend.bank.dto.response.NetworksReponseDto;
import com.backend.bank.service.NetworksService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/network")
public class NetworkController {

    @Autowired
    private NetworksService networksService;

    @Secured({"ROLE_GET NETWORKS", "ROLE_XEM MẠNG LƯỚI"})
    @GetMapping
    public List<NetworksReponseDto> getAll(@PathVariable("locale") String locale) {
        return networksService.getNetworks(locale);
    }


    @Secured({"ROLE_GET NETWORKS", "ROLE_XEM MẠNG LƯỚI"})
    @GetMapping("/search")
    public List<NetworksReponseDto> searchAll(@PathVariable("locale") String locale,
                                              @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                              @RequestParam(name = "status", required = false, defaultValue = "1") Integer status,
                                              @RequestParam(name = "provinceCity", required = false, defaultValue = "") String provinceCity,
                                              @RequestParam(name = "districtCity", required = false, defaultValue = "") String districtCity,
                                              @RequestParam(name = "networkCategory", required = false, defaultValue = "") String networkCategory) {
        return networksService.searchAll(locale, search, networkCategory, districtCity, provinceCity, status);
    }

    @Secured({"ROLE_CREATE NETWORKS", "ROLE_TẠO MẠNG LƯỚI"})
    @PostMapping
    public NetworksReponseDto addNetwork(@PathVariable("locale") String locale, @RequestBody NetworksRequestDto networksRequestDto) {
        return networksService.addNetwork(locale, networksRequestDto);
    }

    @Secured({"ROLE_GET NETWORKS", "ROLE_XEM MẠNG LƯỚI"})
    @GetMapping("/{id}")
    public NetworksReponseDto getNetworkById(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return networksService.getNetworkById(id, locale);
    }

    @Secured({"ROLE_EDIT NETWORKS", "ROLE_SỬA MẠNG LƯỚI"})
    @PutMapping("/{id}")
    public NetworksReponseDto editNetwork(@PathVariable("locale") String locale, @PathVariable("id") Integer id, @RequestBody NetworksRequestDto networksRequestDto) {
        return networksService.editNetwork(id, locale, networksRequestDto);
    }

    @Secured({"ROLE_DELETE NETWORKS", "ROLE_XÓA MẠNG LƯỚI"})
    @DeleteMapping("/{id}")
    public void deleteNetwork(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        networksService.deleteNetwork(id, locale);
    }

    @Secured({"ROLE_EDIT NETWORKS", "ROLE_SỬA MẠNG LƯỚI"})
    @PutMapping("/change_status/{id}")
    public NetworksReponseDto changeStatus(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return networksService.changeStatusNetwork(id, locale);
    }

    @Secured({"ROLE_DELETE NETWORKS", "ROLE_XÓA MẠNG LƯỚI"})
    @DeleteMapping
    public void deleteNetworks(@PathVariable("locale") String locale, @RequestBody List<Integer> listId) {
        for (int id : listId) {
            networksService.deleteNetwork(id, locale);
        }
    }
}

