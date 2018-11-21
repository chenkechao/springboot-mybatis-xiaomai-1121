package com.niu.controller;

import com.niu.entity.Institution;
import com.niu.mapper.InstitutionMapper;
import com.niu.service.InstService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ami on 2018/11/21.
 */
@RestController
@RequestMapping(value = "/inst")
public class InstitutionController {

    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private InstService instService;

    @RequestMapping(value = "/getall",method = RequestMethod.GET)
    @ResponseBody
    List<Institution> getAll(
            @RequestParam(value = "pageNo",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",required = false) Integer pageSize
    ){
        if(null == pageNo || null == pageSize){
            return instService.selectAll();
        }else {
            return instService.selectAllByPage(pageNo,pageSize);
        }
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    Institution get(
            @RequestParam(value = "id",required = true)Long id
    ){
        return instService.getById(id);
    }

}
