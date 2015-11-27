package com.github.changedispenser;

import java.util.Random;

/**
 *
 */
public class Main {

	private static final Random r;

	static {
		r = new Random();
	}

	public static void main(String[] args) {
		final Transaction t;
		final int price;
		final int diff;
		final Coins in;
		final Coins out;

		price = 100;
		in = getPurse(price);
		diff = in.getNominalValue() - price;

		t = new Transaction(in, price).setDebug(true);
		out = t.getChange();

		System.out.println("PRICE: " + price);
		System.out.println("IN:");
		System.out.println(in);
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
	private static int getRandomNominalPrice() {
		return (r.nextInt(1000) + 1) * PseudoSterling.getMinimumFace().getValue();
	}

	/**
	 * Get a purse guaranteed to be at least as valuable as the given value.
	 * @param atLeast
	 * @return
	 */
	private static Coins getPurse(final int atLeast) {
		final Coins purse = new Coins();
		final PseudoSterling[] c = PseudoSterling.values();

		while (purse.getNominalValue() < atLeast)
			purse.addCoins(c[r.nextInt(c.length)], r.nextInt(5) + 1);

		return purse;
	}
}
