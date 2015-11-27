package com.github.changedispenser;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public final class Coins {

	private final EnumMap<PseudoSterling, Integer> coins;
	private int nominal;

	public Coins() {
		this(Collections.EMPTY_MAP);
	}

	public Coins(final Map<PseudoSterling, Integer> in) {
		Objects.requireNonNull(in, "Map parameter must be non-null.");

		coins = new EnumMap<>(PseudoSterling.class);
		coins.putAll(in);
		calculateNominalValue();
	}

	public int getNominalValue() {
		return nominal;
	}

	/**
	 * Adds a single coin to the collection.
	 * @param face
	 * @return
	 */
	public Coins addCoin(final PseudoSterling face) {
		return addCoins(face, 1);
	}

	/**
	 * Add a number of same-faced coins. Also updates the nominal value of this instance.
	 * @param face
	 * @param count How many coins of same face to add
	 * @return This object
	 */
	public Coins addCoins(final PseudoSterling face, final int count) {
		final int tally;

//		System.out.println("*Adding coin " + count + " * " + face);
		if (count < 0)
			throw new IllegalArgumentException("Coins count must be >= 0");

		if (count > 0) {
			//check if face exists and update the tally
			tally = coins.containsKey(face)
					? coins.get(face) + count
					: count;
			coins.put(face, tally);

			nominal += face.getValue() * count; //while we're at it
		}
		return this;
	}

	/**
	 * Calculate the nominal value of this instance.
	 */
	private void calculateNominalValue() {
		nominal = 0;

		coins.forEach((k, v) -> {
			nominal += k.getValue() * v;
		});

	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();

		for (PseudoSterling p : coins.keySet()) {
			b.append("\t")
					.append(coins.get(p))
					.append(" x ")
					.append(p)
					.append("\n");
		}

		b.append("Total: ").append(getNominalValue());

		return b.toString();
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + Objects.hashCode(this.coins);
		hash = 97 * hash + this.nominal;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		final Coins other;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		other = (Coins) obj;
		if (!Objects.equals(this.coins, other.coins))
			return false;

		if (this.nominal != other.nominal)
			return false;

		return true;
	}

}
