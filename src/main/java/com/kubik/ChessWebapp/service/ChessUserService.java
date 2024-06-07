package com.kubik.ChessWebapp.service;

import com.kubik.ChessWebapp.dto.ChessUserDto;
import com.kubik.ChessWebapp.entity.ChessUser;
import com.kubik.ChessWebapp.repository.ChessUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChessUserService {
    private final ChessUserRepository chessUserRepository;
    private final ModelMapper modelMapper;

    public Long createUser(ChessUserDto chessUserDto) {
        return chessUserRepository.save(modelMapper.map(chessUserDto, ChessUser.class)).getId();
    }

    public ChessUser getUserByNickname(String nickname){
        return chessUserRepository.findByNickname(nickname);
    }

    public List<ChessUser> getAllUsers() {
        return (List<ChessUser>) chessUserRepository.findAll();
    }
}
