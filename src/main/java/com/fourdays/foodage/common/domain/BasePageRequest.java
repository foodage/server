package com.fourdays.foodage.common.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class BasePageRequest {

    @NotNull
    int page;

    @NotNull
    int size;
    
    String sort;

    public String getSort() {

        return sort != null ? sort : "createdDate,DESC";
    }

    private Sort toSort() {

        return Sort.by(
            "DESC".equalsIgnoreCase(getSort()
                .split(",")[1]) ? Sort.Direction.DESC : Sort.Direction.ASC,
            this.sort
                .split(",")[0]
        );
    }

    public Pageable toPageable() {

        return PageRequest.of(getPage(), getSize(), toSort());
    }

    public String getSortAttribute() {

        String[] sortArr = getSort().split(",");
        return sortArr[0];
    }

    public String getSortDirection() {

        String[] sortArr = getSort().split(",");
        return sortArr[1];
    }
}
