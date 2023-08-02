package com.kata.auction.common;

/**
 * Model class, the bidder is the user who place bids on an auction. It's
 * identified by his name.
 * 
 * @author ehmme
 *
 */
public class Bidder {

	String name;

	public Bidder(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Bidder buyer = (Bidder) o;

		return !(name != null ? !name.equals(buyer.name) : buyer.name != null);

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return name;
	}
}
