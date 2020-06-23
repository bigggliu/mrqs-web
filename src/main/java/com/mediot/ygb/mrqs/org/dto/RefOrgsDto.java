package com.mediot.ygb.mrqs.org.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class RefOrgsDto implements Serializable {

    private static long serialVersionUID= 9052619805234351130L;

    private String pid;

    private String orgIds;

    private String orgNames;

}
