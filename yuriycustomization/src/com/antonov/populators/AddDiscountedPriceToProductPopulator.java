package com.antonov.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import org.springframework.beans.factory.annotation.Required;

import com.antonov.service.impl.DefaultYuriyCustomizationDiscountService;


public class AddDiscountedPriceToProductPopulator implements Populator<ProductModel, ProductData> {

	private DefaultYuriyCustomizationDiscountService defaultService;

	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException {
		ServicesUtil.validateParameterNotNull(source, "Source has to be not null");
		ServicesUtil.validateParameterNotNull(target, "Target has to be not null");
		target.setPriceWithDiscount(defaultService.getDiscountedPriceForProduct(source));
	}

	@Required
	public void setDefaultService(final DefaultYuriyCustomizationDiscountService defaultService) {
		this.defaultService = defaultService;
	}
}
