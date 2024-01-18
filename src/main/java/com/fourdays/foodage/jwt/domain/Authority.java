package com.fourdays.foodage.jwt.domain;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_authority")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

	@Id
	@Column(name = "authority_name", length = 50)
	private String authorityName;

	@Builder
	public Authority(String authorityName) {
		this.authorityName = authorityName;
	}
}