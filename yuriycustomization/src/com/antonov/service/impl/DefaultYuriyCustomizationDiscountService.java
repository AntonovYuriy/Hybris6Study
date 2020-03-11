package com.antonov.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.DiscountRowModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import com.antonov.service.YuriyCustomizationDiscountService;


public class DefaultYuriyCustomizationDiscountService implements YuriyCustomizationDiscountService {

	public Double getDiscountedPriceForProduct(final ProductModel product) {

		ServicesUtil.validateParameterNotNull(product.getEurope1Prices(), "Product must have price !!!");
		Double priceFromProduct = product.getEurope1Prices().stream().filter(o -> o.getCurrency().getIsocode().equals("USD"))
				.findFirst().get().getPrice();

		Double discountedPrice = priceFromProduct;

		for (DiscountRowModel d : product.getEurope1Discounts()) {
			if (isDiscountForOnlineCatalog(d)) {
				if (d.getDiscount().getAbsolute()) {
					discountedPrice -= d.getDiscount().getValue();
				}
				else {
					discountedPrice = discountedPrice * (100 - d.getDiscount().getValue()) / 100;
				}
			}
		}
		return discountedPrice;
	}

	private Boolean isDiscountForOnlineCatalog(final DiscountRowModel discountRowModel) {
		return "Online".equals(discountRowModel.getCatalogVersion().getVersion());
	}
}