package com.github.changedispenser;

import java.util.Random;

/**
 *
 */
public class Main {

	private static final Random r;
	private static final int NOMINAL_LIMIT;

	static {
		NOMINAL_LIMIT = 1000;
		r = new Random();
	}

	public static void main(String[] args) {
		final Transaction t;
		final int price;
		final int diff;
		final int payIn;
		final Coins out;

		price = getRandomNominalPrice(NOMINAL_LIMIT);
		payIn = price + getRandomNominalPrice(NOMINAL_LIMIT);
		diff = payIn - price;

		t = new Transaction(payIn, price).setDebug(true);
		out = t.getChange();

		System.out.println("PRICE: " + price);
		System.out.println("IN: " + payIn);
		System.out.println("OUT:");
		System.out.println(out);
		System.out.println(diff == out.getNominalValue()
				? "Success! Change nom. value: " + out.getNominalValue()
				: "Failure. Expected " + diff + ", got " + out.getNominalValue()
		);

	}

	/**
	 * Get a random price. The value is guaranteed to be representable in PseudoSterling.
	 * @return
	 */
	private static int getRandomNominalPrice(final int atLeast) {
		return (r.nextInt(atLeast) + 1) * PseudoSterling.getMinimumFace().getValue();
	}

}
