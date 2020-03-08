package org.geeksword.rest.controller;

import org.geekswrod.api.Id;
import org.geekswrod.api.IdService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdController {


    private final IdService idService;

    public IdController(IdService idService) {
        this.idService = idService;
    }

    @RequestMapping("/genId")
    public long genId() {
        return idService.genId();
    }

    @RequestMapping("/expId/{id}")
    public Id expId(@PathVariable("id") long id) {
        return idService.expId(id);
    }
}
