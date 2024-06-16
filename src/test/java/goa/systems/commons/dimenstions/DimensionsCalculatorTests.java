package goa.systems.commons.dimenstions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import goa.systems.commons.dimensions.DimensionsCalculator;

class DimensionsCalculatorTests {

	private static final Logger logger = LoggerFactory.getLogger(DimensionsCalculatorTests.class);

	@Test
	void testPercentage() {
		DimensionsCalculator dimcalc = new DimensionsCalculator();

		assertEquals(50.0, dimcalc.calcPercentage(1920, 960));
		assertEquals(200.0, dimcalc.calcPercentage(960, 1920));
		assertEquals(100.0, dimcalc.calcPercentage(1920, 1920));
		assertEquals(0.0, dimcalc.calcPercentage(0, -1920));
		assertEquals(0.0, dimcalc.calcPercentage(0, 0));
		assertEquals(0.0, dimcalc.calcPercentage(0, 1920));

		logger.info("Percentage tests finished.");
	}

	@Test
	void testFactor() {
		DimensionsCalculator dimcalc = new DimensionsCalculator();

		assertEquals(0.5, dimcalc.calcFactor(dimcalc.calcPercentage(1920, 960)));
		assertEquals(2.0, dimcalc.calcFactor(dimcalc.calcPercentage(960, 1920)));
		assertEquals(1.0, dimcalc.calcFactor(dimcalc.calcPercentage(1920, 1920)));
		assertEquals(0.0, dimcalc.calcFactor(dimcalc.calcPercentage(0, -1920)));
		assertEquals(0.0, dimcalc.calcFactor(dimcalc.calcPercentage(0, 0)));
		assertEquals(0.0, dimcalc.calcFactor(dimcalc.calcPercentage(0, 1920)));

		logger.info("Factor tests finished.");
	}
}
