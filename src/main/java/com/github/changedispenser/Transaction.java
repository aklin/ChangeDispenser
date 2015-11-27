package com.github.changedispenser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A Transaction represents an attempt to cover a nominal value with a set of coins. In
 * this setting, the attempt is always successful (ie. the constructor demands there are
 * enough funds to cover the fee).
 */
public final class Transaction {

//	private final Coins coins;
	private final int price;
	private final int input;
	private boolean debug = false;

	public Transaction(final int in, final int price) {
		Objects.requireNonNull(in);

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
		final int difference;
		int changePending;

		//Faces are declared incrementally. Turn them around.
		Collections.reverse(available);
		change = new Coins();

		/*
		 Greedy algorithm: Select as many faces from max face.
		 Figure that out using modulo.
		 */
		difference = input - price;
		changePending = difference;

		for (final PseudoSterling p : available) {

			int tally = changePending / p.getValue();

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

	/**
	 * Check whether the coin input is sufficient or not.
	 * @return True of coins cover the price, false otherwise
	 */
	public boolean isSufficient() {
		return input >= price;
	}

}
