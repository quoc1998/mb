package com.backend.bank.controller.fontend;

import com.backend.bank.dto.request.NetworksRequestDto;
import com.backend.bank.dto.response.NetworksReponseDto;
import com.backend.bank.service.NetworksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/fe/network")
public class NetworkFrontendController {

    @Autowired
    private NetworksService networksService;


    @GetMapping
    public List<NetworksReponseDto> getAll(@PathVariable("locale") String locale) {
        return networksService.getNetworks(locale);
    }


    @GetMapping("/search")
    public List<NetworksReponseDto> searchAll(@PathVariable("locale") String locale,
                                              @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                              @RequestParam(name = "status", required = false, defaultValue = "1") Integer status,
                                              @RequestParam(name = "provinceCity", required = false, defaultValue = "") String provinceCity,
                                              @RequestParam(name = "districtCity", required = false, defaultValue = "") String districtCity,
                                              @RequestParam(name = "networkCategory", required = false, defaultValue = "") String networkCategory) {
        return networksService.searchAll(locale, search, networkCategory, districtCity, provinceCity, status);
    }


    @GetMapping("/{id}")
    public NetworksReponseDto getNetworkById(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return networksService.getNetworkById(id, locale);
    }

}

