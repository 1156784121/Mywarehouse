package com.bjex.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FileParam {
    private String InterfaceCnName;
    private Map Param;
    private List<ReturnValue> ReturnValue;
    private String Method;
    private String RequestAddress;
}
