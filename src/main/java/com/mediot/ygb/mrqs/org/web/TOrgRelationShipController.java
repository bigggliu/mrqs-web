package com.mediot.ygb.mrqs.org.web;



import com.mediot.ygb.mrqs.org.dto.RefOrgsDto;
import com.mediot.ygb.mrqs.org.entity.TOrgRelationship;
import com.mediot.ygb.mrqs.org.service.TOrgRelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tOrgRelationShip")
public class TOrgRelationShipController {

    @Autowired
    TOrgRelationShipService tOrgRelationShipService;

    @RequestMapping(value = "getOrgsByPid")
    public List<TOrgRelationship> getOrgsByPid(RefOrgsDto refOrgsDto){
        return tOrgRelationShipService.getOrgsByPid(refOrgsDto);
    }

    @RequestMapping(value = "addRefOrgs")
    public void addRefOrgs(RefOrgsDto refOrgsDto){
        tOrgRelationShipService.addRefOrgs(refOrgsDto);
    }

}
