package com.antonov.facade.impl;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import org.springframework.beans.factory.annotation.Required;

import com.antonov.facade.YuriyCustomizationDiscountFacade;


public class DefaultYuriyCustomizationDiscountFacade implements YuriyCustomizationDiscountFacade {

	private Converter<ProductModel, ProductData> defaultProductConverter;

	@Required
	public void setDefaultProductConverter(final Converter defaultConverter) {
		this.defaultProductConverter = defaultProductConverter;
	}
}