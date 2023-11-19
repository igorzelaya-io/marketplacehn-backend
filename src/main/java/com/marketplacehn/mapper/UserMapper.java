package com.marketplacehn.mapper;

import com.marketplacehn.entity.User;
import com.marketplacehn.entity.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDto, User> { }