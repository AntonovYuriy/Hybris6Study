package com.antonov.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;

import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;


public class AddDiscountedPriceToSearchResultVariantProductPopulator extends SearchResultVariantProductPopulator {

	private static final String PRICE_WITH_DISCOUNT = "priceWithDiscount";

	@Override
	public void populate(SearchResultValueData source, ProductData target) {

		super.populate(source, target);

		target.setPriceWithDiscount(this.<Double>getValue(source, PRICE_WITH_DISCOUNT));

	}
}
