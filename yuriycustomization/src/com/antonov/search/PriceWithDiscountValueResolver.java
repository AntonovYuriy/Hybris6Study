package com.antonov.search;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;

import org.springframework.beans.factory.annotation.Required;

import com.antonov.service.impl.DefaultYuriyCustomizationDiscountService;


public class PriceWithDiscountValueResolver extends AbstractValueResolver {

	private DefaultYuriyCustomizationDiscountService discountService;

	@Override
	protected void addFieldValues(final InputDocument inputDocument, final IndexerBatchContext indexerBatchContext,
			final IndexedProperty indexedProperty, final ItemModel product, final ValueResolverContext valueResolverContext)
			throws FieldValueProviderException {

		Double value = discountService.getDiscountedPriceForProduct((ProductModel) product);
		inputDocument.addField(indexedProperty, value);
	}

	@Required
	public void setDiscountService(final DefaultYuriyCustomizationDiscountService discountService) {
		this.discountService = discountService;
	}
}