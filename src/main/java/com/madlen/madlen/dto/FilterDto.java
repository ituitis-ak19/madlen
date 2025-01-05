package com.madlen.madlen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    String field;
    List<String> operators;
    List<String> values;
    String logic;
}