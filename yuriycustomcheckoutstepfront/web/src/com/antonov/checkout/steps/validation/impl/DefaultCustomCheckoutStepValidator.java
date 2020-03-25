package com.antonov.checkout.steps.validation.impl;

import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.AbstractCheckoutStepValidator;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.validation.ValidationResults;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public class DefaultCustomCheckoutStepValidator extends AbstractCheckoutStepValidator {

	@Override
	public ValidationResults validateOnEnter(final RedirectAttributes redirectAttributes) {
		return ValidationResults.SUCCESS;
	}
}
