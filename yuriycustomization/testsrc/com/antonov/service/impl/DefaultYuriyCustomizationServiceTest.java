package com.antonov.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.DiscountRowModel;
import de.hybris.platform.europe1.model.PriceRowModel;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultYuriyCustomizationServiceTest {

	private static final String NON_USD_ISOCODE = "EUR";
	private static final String IS_USD_ISOCODE = "USD";
	private static final Double SOME_PRICE = 1000D;
	private static final String STAGED_CATALOG = "Staged";

	@InjectMocks
	private DefaultYuriyCustomizationDiscountService defaultYuDiscountService;

	@Mock
	private ProductModel productModel;

	@Mock
	private DiscountRowModel discountRowModel;

	@Mock
	private CatalogVersionModel catalogVersion;

	private List<PriceRowModel> priceRowModelList = new ArrayList<>();
	private List<DiscountRowModel> discountRowModelList = new ArrayList<>();

	private PriceRowModel realPriceRowModel = new PriceRowModel();
	private CurrencyModel realCurrencyModel = new CurrencyModel();

	@Test(expected = IllegalArgumentException.class)
	public void shouldReturnIAEWhenNoPricesAndNoDiscounts() {
		when(productModel.getEurope1Prices()).thenReturn(priceRowModelList);
		when(productModel.getEurope1Discounts()).thenReturn(discountRowModelList);
		defaultYuDiscountService.getDiscountedPriceForProduct(productModel);
	}

	@Test
	public void shouldReturnPriceValueWhenPriceAndDiscountNotFound() {
		realCurrencyModel.setIsocode(IS_USD_ISOCODE);
		realPriceRowModel.setCurrency(realCurrencyModel);
		realPriceRowModel.setPrice(SOME_PRICE);

		priceRowModelList.add(realPriceRowModel);
		when(productModel.getEurope1Prices()).thenReturn(priceRowModelList);

		when(productModel.getEurope1Discounts()).thenReturn(discountRowModelList);

		assertEquals(SOME_PRICE, defaultYuDiscountService.getDiscountedPriceForProduct(productModel));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldthrowIAEWhenPriceIsNotSetForPriceRow() {

		realCurrencyModel.setIsocode(IS_USD_ISOCODE);
		realPriceRowModel.setCurrency(realCurrencyModel);

		defaultYuDiscountService.getDiscountedPriceForProduct(productModel);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldthrowIAEWhenPriceIsNotForUSD() {
		realCurrencyModel.setIsocode(NON_USD_ISOCODE);
		realPriceRowModel.setCurrency(realCurrencyModel);

		defaultYuDiscountService.getDiscountedPriceForProduct(productModel);
	}

	@Test
	public void shouldReturnSamePriceWhenDiscountRawFromStagedCatalog() {
		realCurrencyModel.setIsocode(IS_USD_ISOCODE);
		realPriceRowModel.setCurrency(realCurrencyModel);
		realPriceRowModel.setPrice(SOME_PRICE);

		priceRowModelList.add(realPriceRowModel);
		when(productModel.getEurope1Prices()).thenReturn(priceRowModelList);

		discountRowModelList.add(discountRowModel);
		when(discountRowModel.getCatalogVersion()).thenReturn(catalogVersion);
		when(catalogVersion.getVersion()).thenReturn(STAGED_CATALOG);
		when(productModel.getEurope1Discounts()).thenReturn(discountRowModelList);

		assertEquals(SOME_PRICE, defaultYuDiscountService.getDiscountedPriceForProduct(productModel));
	}
}