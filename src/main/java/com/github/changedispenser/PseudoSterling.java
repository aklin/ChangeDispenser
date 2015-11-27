package com.github.changedispenser;

/**
 * Coin denominations. The nominal value of a penny is 100.
 */
public enum PseudoSterling {

	/**
	 * Quarter penny. Nominal value: 25
	 */
	/**
	 * Quarter penny. Nominal value: 25
	 */
	QUARTER(25), //This can be changed to any positive integer.
	/**
	 * Half penny. Nominal value: 50
	 */
	HALF(2 * QUARTER.value),
	/**
	 * Penny. Nominal value: 100
	 */
	PSEUDO(4 * QUARTER.value),
	/**
	 * Three pennies. Nominal value: 300
	 */
	THREEP(3 * PSEUDO.value),
	/**
	 * Fifteen pennies. Nominal value: 1500
	 */
	FIFTEENP(15 * PSEUDO.value),
	/**
	 * Seventy-five pennies. Nominal value: 7500
	 */
	SEVFIVEP(75 * PSEUDO.value);

	private final int value;

	private PseudoSterling(int val) {
		value = val;
	}

	/**
	 * Get nominal value of face.
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Get the face of minimum value in this coinage.
	 * @return A quarter of a pseudo
	 */
	public static PseudoSterling getMinimumFace() {
		return QUARTER;
	}

	/**
	 * Get maximum denomination of currency. Included for the sake of completeness.
	 * @return A 75P coin (quin-septuagintion?).
	 */
	public static PseudoSterling getMaximumFace() {
		return SEVFIVEP;
	}

}
