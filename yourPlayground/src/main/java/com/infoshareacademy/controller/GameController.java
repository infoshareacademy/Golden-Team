package com.infoshareacademy.controller;

import com.infoshareacademy.dto.FindGameDto;
import com.infoshareacademy.dto.GameDto;
import com.infoshareacademy.dto.PlayerDto;
import com.infoshareacademy.entity.Player;

import com.infoshareacademy.mappers.PlayerMapper;
import com.infoshareacademy.service.GameService;
import com.infoshareacademy.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;

@Controller
@RequestMapping("games")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameController {


    private final GameService gameService;

    private final PlayerService playerService;

    private final PlayerMapper playerMapper;

    @GetMapping("{id}")
    public String getGame(@PathVariable Integer id, Model model) throws IOException {
        model.addAttribute("game", gameService.findById(id));
        return "game";
    }

    @GetMapping
    public String getGames(Model model) {
        model.addAttribute("games", gameService.findAll());
        return "games";
    }

    @PostMapping("find")
    public String getFoundGames(@Valid @ModelAttribute("foundGames") FindGameDto findGameDto,
                                BindingResult bindingResult) {
        gameService.findByCriteriaBuilder(findGameDto);

        if (bindingResult.hasErrors()) {
            return "games";
        }
        return "foundGames";
    }

    @GetMapping("new")
    public String createGame(Model model) throws IOException {
        model.addAttribute("game", new GameDto());
        return "game-form";
    }

    @Secured("ROLE_USER")
    @PostMapping("new")
    public String postGame(@Valid @ModelAttribute("game") GameDto gameDto,
                           BindingResult bindingResult) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerDto gameOwnerDto = playerService.findByUsername(authentication.getName());
        Player gameOwner = playerMapper.toEntity(gameOwnerDto);

        if(bindingResult.hasErrors()) {
            return "game-form";
        }

        gameService.create(gameDto, gameOwner);
        return "game-form-success";
    }
}