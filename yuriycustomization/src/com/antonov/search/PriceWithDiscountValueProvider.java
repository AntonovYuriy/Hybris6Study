package com.antonov.search;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.antonov.service.impl.DefaultYuriyCustomizationDiscountService;


public class PriceWithDiscountValueProvider implements FieldValueProvider, Serializable {

	private FieldNameProvider fieldNameProvider;
	private DefaultYuriyCustomizationDiscountService discountService;

	@SuppressWarnings("deprecation")
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException {

		final Collection<FieldValue> fieldValues = new ArrayList<>();

		fieldValues.addAll(createFieldValue((ProductModel) model, indexedProperty));
		return fieldValues;
	}

	protected List<FieldValue> createFieldValue(final ProductModel product, final IndexedProperty indexedProperty) {

		final List<FieldValue> fieldValues = new ArrayList<>();

		Double priceWithDiscount = discountService.getDiscountedPriceForProduct(product);

		if (priceWithDiscount != null) {
			addFieldValues(fieldValues, indexedProperty, priceWithDiscount);
		}
		return fieldValues;
	}

	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value) {
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);

		fieldNames.stream().forEach(fieldName -> fieldValues.add(new FieldValue(fieldName, value)));
	}

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider) {
		this.fieldNameProvider = fieldNameProvider;
	}

	@Required
	public void setDiscountService(final DefaultYuriyCustomizationDiscountService discountService) {
		this.discountService = discountService;
	}
}
