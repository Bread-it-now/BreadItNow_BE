package com.breaditnow.customer.domain.bakery.controller.req.converter;

import static com.breaditnow.customer.domain.bakery.controller.req.enumerate.FavoriteSortType.*;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.breaditnow.customer.domain.bakery.controller.req.enumerate.FavoriteSortType;

import io.micrometer.common.util.StringUtils;

@Component
public class FavoriteSortTypeConverter implements Converter<String, FavoriteSortType> {
	@Override
	public FavoriteSortType convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return LATEST;
		}
		return FavoriteSortType.of(source);
	}
}
