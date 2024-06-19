package com.fourdays.foodage.common.enums.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fourdays.foodage.common.enums.ReviewViewType;

@Component
public class ReviewViewTypeConverter implements Converter<String, ReviewViewType> {

	@Override
	public ReviewViewType convert(String source) {
		return ReviewViewType.of(source);
	}
}
