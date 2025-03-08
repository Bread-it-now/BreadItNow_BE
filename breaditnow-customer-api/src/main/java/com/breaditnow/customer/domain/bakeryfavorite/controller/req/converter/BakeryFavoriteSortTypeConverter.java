package com.breaditnow.customer.domain.bakeryfavorite.controller.req.converter;

import static com.breaditnow.customer.domain.bakeryfavorite.controller.req.enumerate.BakeryFavoriteSortType.*;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.breaditnow.customer.domain.bakeryfavorite.controller.req.enumerate.BakeryFavoriteSortType;

import io.micrometer.common.util.StringUtils;

@Component
public class BakeryFavoriteSortTypeConverter implements Converter<String, BakeryFavoriteSortType> {
	@Override
	public BakeryFavoriteSortType convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return LATEST;
		}
		return BakeryFavoriteSortType.of(source);
	}
}
