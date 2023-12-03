package com.example.backend.mappers;

import com.example.backend.dao.ResumeDao;
import com.example.backend.dto.ResumeDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component/*(value = "mapper")*/
public interface MyMapper {

    ResumeDto resumeDaoToDto(ResumeDao dao);
}
