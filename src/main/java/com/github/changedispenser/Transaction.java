package com.github.changedispenser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A Transaction represents an attempt to cover a price and get back change. In this
 * setting the attempt is always successful (ie. the constructor demands there are enough
 * funds to cover the fee).
 */
public final class Transaction {

	private final int price;
	private final int input;
	private boolean debug = false;

	/**
	 * Create a new Transaction object. Both parameters are nominal values, and must be
	 * representable within Pseudo Sterling, or an exception will be thrown.
	 * @param in Input payment. Must be >= price
	 * @param price Price to be covered. Must be >0
	 */
	public Transaction(final int in, final int price) {
		if (price <= 0)
			throw new IllegalArgumentException("Price must be greater than zero");
		if (in < price)
			throw new IllegalArgumentException("Insufficient funds");
		if (!isRepresentable(in - price))
			throw new IllegalArgumentException("Change value is unrepresentable");

		input = in;
		this.price = price;
	}

	public Coins getChange() {
		final Coins change;
		final List<PseudoSterling> available = Arrays.asList(PseudoSterling.values());
		final int difference; //Input value - price
		int changePending; // Change not yet dispensed

		//Faces are declared incrementally lowest to highest. Turn them around.
		Collections.reverse(available);

		difference = input - price;
		changePending = difference;

		change = new Coins();

		//Start by examining the highest denomination
		for (final PseudoSterling p : available) {
			int tally = changePending / p.getValue(); //non-zero if it fits

			change.addCoins(p, tally);

			debugPrint("Diffr: " + changePending);
			debugPrint("Tally: " + tally);
			debugPrint("Face: " + p + " (val: " + p.getValue() + ")");
			debugPrint("------------------");

			changePending = difference - change.getNominalValue();

			if (changePending == 0)
				break;
		}
		return change;
	}

	/**
	 * Enables or disables debug statements.
	 * @param b
	 * @return This object
	 */
	public Transaction setDebug(final boolean b) {
		debug = b;
		return this;
	}

	private void debugPrint(final String s) {
		if (debug)
			System.out.println(s);
	}

	/**
	 * Check if a given value can be represented by Pseudo Sterling.
	 * @param val Nominal value to check
	 * @return True if representable, false otherwise
	 */
	public static boolean isRepresentable(final int val) {
		return val % PseudoSterling.QUARTER.getValue() == 0;
	}

}
