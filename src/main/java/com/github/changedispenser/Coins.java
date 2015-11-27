package com.github.changedispenser;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Coin aggregator. Encapsulates a Map structure that contains the denomination tally.
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

	/**
	 * Get the nominal value of this aggregation.
	 * @return The nominal value of all coins contained within
	 */
	public int getNominalValue() {
		return nominal;
	}

	/**
	 * Adds a single coin to the collection.
	 * @param face
	 * @return This object
	 */
	public Coins addCoin(final PseudoSterling face) {
		return addCoins(face, 1);
	}

	/**
	 * Add a number of same-faced coins. Also updates the nominal value of this instance.
	 * @param face Denomination to be added
	 * @param count Non-negative denomination count.
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

		coins.forEach((face, nValue) -> {
			nominal += face.getValue() * nValue;
		});

	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();

		for (final PseudoSterling p : coins.keySet())
			b.append("\t")
					.append(coins.get(p))
					.append(" x ")
					.append(p)
					.append("\n");

		b.append("Total: ").append(getNominalValue());

		return b.toString();
	}

}
